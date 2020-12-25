package com.me.webf.routing;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsResource;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.FormFieldPart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.me.webf.misc.ApiResponse;
import com.me.webf.model.JWTCookieUser;
import com.me.webf.model.Role;
import com.me.webf.model.VideoInfo;
import com.me.webf.model.repositories.VideoInfoRepository;
import com.me.webf.model.services.JWTCookieUserService;
import com.me.webf.model.services.VideoInfoService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class RestHandler {

	@Autowired
	public RestHandler(ReactiveGridFsTemplate reactiveGridFsTemplate,
			VideoInfoService videoInfoService, JWTCookieUserService userService, PasswordEncoder passwordEncoder) {
		this.reactiveGridFsTemplate = reactiveGridFsTemplate;
		this.videoInfoService = videoInfoService;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	private JWTCookieUserService userService;
	private VideoInfoService videoInfoService;
	private ReactiveGridFsTemplate reactiveGridFsTemplate;
	private PasswordEncoder passwordEncoder;

	private Logger logger = LoggerFactory.getLogger(RestHandler.class);


	public Mono<ServerResponse> findPage(ServerRequest serverRequest) {
		Pageable page = PageRequest.of(Integer.parseInt(serverRequest.queryParam("page").orElse("0")),
				Integer.parseInt(serverRequest.queryParam("size").orElse("4")));
		Flux<VideoInfo> flux = videoInfoService
				.findByVideonameContainingOrderById(serverRequest.queryParam("name").orElse(""), page);
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(flux, VideoInfo.class);
	}

	public Mono<ServerResponse> getPageCount(ServerRequest serverRequest) {
		Mono<Long> numberOfPages = videoInfoService.findAll().count().map(count -> {
			Integer psize = Integer.parseInt(serverRequest.queryParam("size").orElse("8"));
			if (count % psize == 0)
				return count / psize;
			else
				return count / psize + 1;
		});
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(numberOfPages, Long.class);
	}

	public Mono<ServerResponse> findAllVideoInfos(ServerRequest serverRequest) {
		Flux<VideoInfo> flux = videoInfoService.findAll();
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(flux, VideoInfo.class);
	}

	public Mono<ServerResponse> findVideoInfoById(ServerRequest serverRequest) {
		Mono<VideoInfo> mono = videoInfoService.findById(serverRequest.pathVariable("id"));
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(mono, VideoInfo.class);
	}

	public Mono<ServerResponse> findUserByName(ServerRequest serverRequest) {
		Mono<JWTCookieUser> mono = userService.findByUsername(serverRequest.pathVariable("name"));
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(mono, JWTCookieUser.class);
	}

	@Transactional
	public Mono<ServerResponse> saveVideoInfo(ServerRequest serverRequest) {

		return serverRequest.body(BodyExtractors.toMultipartData()).flatMap(part -> {
			Map<String, Part> partMap = part.toSingleValueMap();

			FilePart videoPart = (FilePart) partMap.get("video");	
			FilePart thumbnailPart = (FilePart) partMap.get("thumbnail");
			FormFieldPart videonamePart = (FormFieldPart) partMap.get("videoname");
			FormFieldPart descriptionPart = (FormFieldPart) partMap.get("description");
			FormFieldPart usernamePart = (FormFieldPart) partMap.get("username");
	
			Mono<String> videoId = reactiveGridFsTemplate.store(videoPart.content(), videoPart.filename())
					.map(ObjectId::toHexString);

			Mono<String> thumbnailId = reactiveGridFsTemplate.store(thumbnailPart.content(), thumbnailPart.filename())
					.map(ObjectId::toHexString);

			Mono<JWTCookieUser> userMono = userService.findByUsername(usernamePart.value());

			Mono<VideoInfo> videoMono = Mono
					.zip(videoId, thumbnailId,
							(video, thumbnail) -> new VideoInfo(null, videonamePart.value(), video, thumbnail,
									descriptionPart.value(), usernamePart.value(), null))
					.flatMap(video -> videoInfoService.save(video));
			Mono<VideoInfo> resultMono = Mono.zip(userMono, videoMono, (user, video) -> {
				video.setUserAvatarId(user.getAvatarId());
				user.getVideoInfoIds().add(video.getId());
				//userService.save(user).subscribe();
				List<Object> list = new ArrayList<Object>();
				list.add(user);
				list.add(video);
				//video = videoInfoService.save(video).block();
				return list;
			})
					.flatMap(list -> userService.save((JWTCookieUser) list.get(0))
							.flatMap(user -> videoInfoService.save((VideoInfo)list.get(1))));
			return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(resultMono, VideoInfo.class);
		});
	}

	@Transactional
	public Mono<ServerResponse> deleteVideoInfoById(ServerRequest serverRequest) {
		return videoInfoService.findById(serverRequest.pathVariable("id")).flatMap(video -> {
			reactiveGridFsTemplate.delete(query(where("_id").is(video.getVideoId()))).subscribe();
			reactiveGridFsTemplate.delete(query(where("_id").is(video.getThumbnailId()))).subscribe();
			userService.findByUsername(video.getUsername()).flatMap(user -> {
				user.getVideoInfoIds().remove(video.getId());
				return userService.save(user);
			}).subscribe();
			return videoInfoService.deleteVideoInfoById(video.getId());
		}).flatMap(r -> (r != null) ? ServerResponse.ok().build() : ServerResponse.notFound().build());

	}

	public Mono<ServerResponse> findVideoById(ServerRequest serverRequest) {
		Flux<DataBuffer> videoFlux = reactiveGridFsTemplate
				.findOne(query(where("_id").is(serverRequest.pathVariable("id")))).flatMap(file -> {
					logger.info("file name: {}, length: {}", file.getFilename(), file.getLength());
					return reactiveGridFsTemplate.getResource(file);
				}).flatMapMany(ReactiveGridFsResource::getDownloadStream);
		return ServerResponse.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(videoFlux, DataBuffer.class);
	}

	public Mono<ServerResponse> findThumbnailById(ServerRequest serverRequest) {
		Flux<DataBuffer> thumbnailFlux = reactiveGridFsTemplate
				.findOne(query(where("_id").is(serverRequest.pathVariable("id")))).flatMap(file -> {
					logger.info("file name: {}, length: {}", file.getFilename(), file.getLength());
					return reactiveGridFsTemplate.getResource(file);
				}).flatMapMany(ReactiveGridFsResource::getDownloadStream);
		return ServerResponse.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(thumbnailFlux,
				DataBuffer.class);
	}

	public Mono<ServerResponse> findAvatarById(ServerRequest serverRequest) {
		Flux<DataBuffer> avatarFlux = reactiveGridFsTemplate
				.findOne(query(where("_id").is(serverRequest.queryParam("av").orElse("")))).flatMap(file -> {
					logger.info("file name: {}, length: {}", file.getFilename(), file.getLength());
					return reactiveGridFsTemplate.getResource(file);
				}).flatMapMany(ReactiveGridFsResource::getDownloadStream);
		return ServerResponse.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(avatarFlux, DataBuffer.class);
	}

	@Transactional
	public Mono<ServerResponse> saveUser(ServerRequest serverRequest) {
		Mono<ApiResponse> apiResponseMono = serverRequest.body(BodyExtractors.toMultipartData())
				.flatMap(part -> {
					Map<String, Part> partMap = part.toSingleValueMap();
					
					FilePart avatarPart = (FilePart) partMap.get("avatar");
					FormFieldPart usernamePart = (FormFieldPart) partMap.get("username");
					FormFieldPart passwordPart = (FormFieldPart) partMap.get("password");
					JWTCookieUser user = new JWTCookieUser(usernamePart.value());
					List<Role> roles = new ArrayList<>();
					roles.add(Role.ROLE_USER);
					user.setRoles(roles);
					user.setEnabled(Boolean.TRUE);
					user.setPassword(passwordEncoder.encode(passwordPart.value()));
					return userService.findByUsername(user.getUsername()).flatMap(gk -> {
						return Mono.error(new RuntimeException("User already exists"));
						})
							.switchIfEmpty( avatarPart!=null ? reactiveGridFsTemplate.store(avatarPart.content(), avatarPart.filename())
									.map(ObjectId::toHexString) : Mono.empty())
							.flatMap(avatarId -> {
								user.setAvatarId((String)avatarId);
								return userService.save(user);
							})
							.switchIfEmpty(userService.save(user));
							
				}).thenReturn(new ApiResponse(Boolean.TRUE, "User successfully saved"))
				.onErrorResume(throwable -> Mono.just(new ApiResponse(Boolean.FALSE, "Error in saving user")));
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(apiResponseMono, ApiResponse.class);
	}

	public Mono<ServerResponse> checkNameAvailability(ServerRequest serverRequest) {
		Mono<ApiResponse> userNameAvailabilityMono = userService.findByUsername(serverRequest.queryParam("ch").orElse("user"))
				.map(isAvailable -> new ApiResponse(false, "Username unavailable"))
				.defaultIfEmpty(new ApiResponse(true, "Username available"));
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(userNameAvailabilityMono,
				ApiResponse.class);
	}

}