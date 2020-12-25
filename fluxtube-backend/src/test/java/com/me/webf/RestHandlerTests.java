package com.me.webf;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.me.webf.model.JWTCookieUser;
import com.me.webf.model.VideoInfo;
import com.me.webf.model.services.JWTCookieUserService;
import com.me.webf.model.services.VideoInfoService;
import com.mongodb.client.gridfs.model.GridFSFile;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
class RestHandlerTests {

	@MockBean
	JWTCookieUserService userService;
	@MockBean
	VideoInfoService videoInfoService;
	@MockBean
	ReactiveGridFsTemplate reactiveGridFsTemplate;

	@Autowired
	private WebTestClient webClient;

	private JWTCookieUser testuser;
	private VideoInfo testVideoInfo;

	@PostConstruct
	public void init() {
		
		testuser = new JWTCookieUser("testuser");
		testuser.setAvatarId("testid");
		testuser.getVideoInfoIds().add("1");

		testVideoInfo = new VideoInfo("test", "test", "test", "test", "test", "test", "test");

		Mockito.when(reactiveGridFsTemplate.store(Mockito.any(), Mockito.anyString()))
				.thenReturn(Mono.just(new ObjectId(new Date(), 123)));
		Mockito.when(userService.findByUsername(Mockito.anyString())).thenReturn(Mono.just(testuser));
		Mockito.when(userService.save(Mockito.any(JWTCookieUser.class))).thenReturn(Mono.just(testuser));
		Mockito.when(videoInfoService.save(Mockito.any(VideoInfo.class))).thenReturn(Mono.just(testVideoInfo));
		Mockito.when(videoInfoService.findById(Mockito.anyString())).thenReturn(Mono.just(testVideoInfo));
		Mockito.when(videoInfoService.findAll()).thenReturn(Flux.just(testVideoInfo));
		Mockito.when(
				videoInfoService.findByVideonameContainingOrderById(Mockito.anyString(), Mockito.any(Pageable.class)))
				.thenReturn(Flux.just(testVideoInfo));
		Mockito.when(videoInfoService.deleteVideoInfoById("test")).thenReturn(Mono.just(testVideoInfo));
		Mockito.when(reactiveGridFsTemplate.delete(Mockito.any())).thenReturn(Mono.empty());
		Mockito.when(reactiveGridFsTemplate.findOne(Mockito.any())).thenReturn(Mono.empty());
		Mockito.when(reactiveGridFsTemplate.getResource(Mockito.any(GridFSFile.class))).thenReturn(Mono.empty());
	}

	@Test
	void testFindPage() {
		
		webClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("/api/page")
						.queryParam("page", "1")
						.queryParam("size", "1")
						.build())
				.exchange().expectStatus().isOk();

		Mockito.verify(videoInfoService, times(1))
			.findByVideonameContainingOrderById(Mockito.anyString(),
					Mockito.any(Pageable.class));
	}

	@Test
	void testGetPageCount() {
		
		webClient.get()
		.uri(uriBuilder -> uriBuilder
				.path("/api/page")
				.queryParam("size", "1")
				.build())
		.exchange()
		.expectStatus()
		.isOk();

		Mockito.verify(videoInfoService, times(1)).findAll();
	}

	@Test
	void testfindAllVideoInfos() {
		
		webClient.get()
		.uri("/api/videos")
		.exchange()
		.expectStatus()
		.isOk();

		Mockito.verify(videoInfoService, times(1)).findAll();
	}

	@Test
	void testFindVideoInfoById() {
		
		webClient.get()
		.uri(uriBuilder -> uriBuilder
				.path("/api/videos/1")
				.build())
		.exchange()
		.expectStatus()
		.isOk();

		Mockito.verify(videoInfoService, times(1)).findById("1");
	}

	@Test
	void testFindUserByName() {
		
		webClient.get()
		.uri(uriBuilder -> uriBuilder.path("/api/user/testuser")
				.build())
		.exchange()
		.expectStatus()
		.isOk();

		Mockito.verify(userService, times(1)).findByUsername("testuser");
	}

	@WithMockUser
	@Test
	void testSaveVideoInfo() {
		
		Flux<DataBuffer> content = DataBufferUtils.read(new ClassPathResource("test.png", getClass()),
				new DefaultDataBufferFactory(), 1024);

		MultipartBodyBuilder builder = new MultipartBodyBuilder();
		builder.part("description", "test");
		builder.part("username", "testname");
		builder.part("videoname", "testname");
		builder.asyncPart("video", content, DataBuffer.class).headers(h -> {
			h.setContentDispositionFormData("video", "someFile.bin");
			h.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		});
		builder.asyncPart("thumbnail", content, DataBuffer.class).headers(h -> {
			h.setContentDispositionFormData("thumbnail", "someFile.bin");
			h.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		});

		webClient.post().uri("/api/videos")
		.contentType(MediaType.MULTIPART_FORM_DATA)
		.bodyValue(builder.build())
		.accept(MediaType.ALL)
		.exchange()
		.expectStatus()
		.isOk();

		Mockito.verify(videoInfoService, times(1)).save(testVideoInfo);
		Mockito.verify(userService, times(1)).save(testuser);
	}

	@WithMockUser
	@Test
	void testDeleteVideoInfoByName() {
		
		webClient.delete()
		.uri(uriBuilder -> uriBuilder
				.path("/api/videos/1")
				.build())
		.exchange()
		.expectStatus()
		.isOk();

		Mockito.verify(videoInfoService, times(1)).deleteVideoInfoById("test");
		Mockito.verify(userService, times(1)).findByUsername("test");
		Mockito.verify(userService, times(1)).save(Mockito.any(JWTCookieUser.class));
	}

	@Test
	void testFindVideoById() {
		
		webClient.get()
		.uri(uriBuilder -> uriBuilder
				.path("/api/videos/1")
				.queryParam("vd")
				.build())
		.exchange()
		.expectStatus()
		.isOk();

		Mockito.verify(reactiveGridFsTemplate, times(1)).findOne(Mockito.any());
	}

	@Test
	void testFindThumbnailById() {
		
		webClient.get()
		.uri(uriBuilder -> uriBuilder
				.path("/api/videos/1")
				.queryParam("th")
				.build())
		.exchange()
		.expectStatus()
		.isOk();

		Mockito.verify(reactiveGridFsTemplate, times(1)).findOne(Mockito.any());

	}

	@Test
	void testFindAvatarById() {
		
		webClient.get()
		.uri(uriBuilder -> uriBuilder
				.path("/api/user")
				.queryParam("av", "test")
				.build())
		.exchange()
		.expectStatus()
		.isOk();

		Mockito.verify(reactiveGridFsTemplate, times(1)).findOne(Mockito.any());
	}

	@WithMockUser
	@Test
	void testSaveUser() {

		Mockito.when(userService.findByUsername("test")).thenReturn(Mono.empty());

		Flux<DataBuffer> content = DataBufferUtils.read(new ClassPathResource("test.png", getClass()),
				new DefaultDataBufferFactory(), 1024);

		MultipartBodyBuilder builder = new MultipartBodyBuilder();
		builder.part("username", "test");
		builder.part("password", "testpassword");
		builder.asyncPart("avatar", content, DataBuffer.class).headers(h -> {
			h.setContentDispositionFormData("avatar", "someFile.bin");
			h.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		});

		webClient.post()
		.uri("/api/user")
		.contentType(MediaType.MULTIPART_FORM_DATA)
		.bodyValue(builder.build())
		.exchange()
		.expectStatus()
		.isOk();

		Mockito.verify(reactiveGridFsTemplate, times(1)).store(Mockito.any(), Mockito.anyString());
		Mockito.verify(userService, times(1)).findByUsername("test");
		Mockito.verify(userService, atLeast(1)).save(Mockito.any(JWTCookieUser.class));
	}

	@Test
	void testCheckNameAvailability() {

		webClient.get()
		.uri(uriBuilder -> uriBuilder
				.path("/api/user")
				.queryParam("ch", "user")
				.build())
		.exchange()
		.expectStatus()
		.isOk();

		Mockito.verify(userService, times(1)).findByUsername("user");
	}
}
