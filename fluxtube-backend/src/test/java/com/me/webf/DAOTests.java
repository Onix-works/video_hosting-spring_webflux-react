package com.me.webf;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.me.webf.model.JWTCookieUser;
import com.me.webf.model.VideoInfo;
import com.me.webf.model.repositories.JWTCookieUserRepository;
import com.me.webf.model.repositories.VideoInfoRepository;
import com.me.webf.model.services.JWTCookieUserService;
import com.me.webf.model.services.VideoInfoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DAOTests {

	@Autowired
	VideoInfoService videoInfoService;
	@Autowired
	JWTCookieUserService jwtCookieUserService;
	
	@MockBean
	JWTCookieUserRepository jwtCookieUserRepository;
	@MockBean
	VideoInfoRepository videoInfoRepository;

	private JWTCookieUser testuser;
	private VideoInfo testVideoInfo;
	
	@PostConstruct
	public void init(){
		
	testuser = new JWTCookieUser("testuser");
	testuser.setAvatarId("testid");
	testuser.getVideoInfoIds().add("1");
	
	testVideoInfo = new VideoInfo("test", "test", "test", "test", "test", "test", "test");
	}
	
	
	@Test
	void testFindAll_videoInfoService() {
		Mockito.when(videoInfoRepository.findAll()).thenReturn(Flux.just(testVideoInfo));
		
		StepVerifier.create(videoInfoService.findAll()).expectNext(testVideoInfo);
	}
	
	@Test
	void testFindById_videoInfoService() {
		Mockito.when(videoInfoRepository.findById("test")).thenReturn(Mono.just(testVideoInfo));
		
		StepVerifier.create(videoInfoService.findById("test")).expectNext(testVideoInfo);
	}
	
	@Test
	void testSaveVideoInfo_videoInfoService() {
		Mockito.when(videoInfoRepository.save(testVideoInfo)).thenReturn(Mono.just(testVideoInfo));
		
		StepVerifier.create(videoInfoService.save(testVideoInfo)).expectNext(testVideoInfo);
	}
	
	@Test
	void testSaveMono_videoInfoService() {
		Mono<VideoInfo> mono = Mono.just(testVideoInfo);
		Mockito.when(videoInfoRepository.save(mono)).thenReturn(mono);
		
		StepVerifier.create(videoInfoService.save(mono)).expectNext(testVideoInfo);	
	}
	
	@Test
	void testDeleteVideoInfoById_videoInfoService() {
		Mockito.when(videoInfoRepository.deleteVideoInfoById("test")).thenReturn(Mono.just(testVideoInfo));
		
		
		StepVerifier.create(videoInfoService.deleteVideoInfoById("test")).expectNext(testVideoInfo);	
	}
	
	@Test
	void testFindByVideonameContainingOrderById_videoInfoService() {
		Pageable page = Mockito.mock(Pageable.class);
		Mockito.when(videoInfoRepository.findByVideonameContainingOrderById("test", page))
			.thenReturn(Flux.just(testVideoInfo));
		
		StepVerifier.create(videoInfoService.findByVideonameContainingOrderById("test", page))
			.expectNext(testVideoInfo);	
	}
	
	@Test
	void testFindAll_JWTCookieUserService() {
		Mockito.when(jwtCookieUserRepository.findAll())
			.thenReturn(Flux.just(testuser));
		
		StepVerifier.create(jwtCookieUserService.findAll())
			.expectNext(testuser);	
	}
	
	@Test
	void testFindById_JWTCookieUserService() {
		Mockito.when(jwtCookieUserRepository.findById("test")).thenReturn(Mono.just(testuser));
		
		StepVerifier.create(jwtCookieUserService.findById("test")).expectNext(testuser);	
	}
	
	@Test
	void testSaveMono_JWTCookieUserService() {
		Mono<JWTCookieUser> mono = Mono.just(testuser);
		Mockito.when(jwtCookieUserRepository.save(mono)).thenReturn(mono);
		
		StepVerifier.create(jwtCookieUserService.save(mono)).expectNext(testuser);	
	}
	
	@Test
	void testSaveVideoInfo_JWTCookieUserService() {
		Mockito.when(jwtCookieUserRepository.save(testuser)).thenReturn(Mono.just(testuser));
		
		StepVerifier.create(jwtCookieUserService.save(testuser)).expectNext(testuser);	
	}
	
	@Test
	void testDeleteUserById_JWTCookieUserService() {
		Mockito.when(jwtCookieUserRepository.deleteJWTCookieUserById("test")).thenReturn(Mono.just(testuser));
		
		StepVerifier.create(jwtCookieUserService.deleteUserById("test")).expectNext(testuser);
	}
	
	@Test
	void testExistsByName_JWTCookieUserService() {
		Mockito.when(jwtCookieUserRepository.existsByUsername("test")).thenReturn(Mono.just(true));
		
		StepVerifier.create(jwtCookieUserService.existsByName("test")).expectNext(true);
	}
	
}
