package com.me.webf.model.services;

import org.springframework.data.domain.Pageable;

import com.me.webf.model.VideoInfo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VideoInfoService {
	
	Flux<VideoInfo> findAll();

	Mono<VideoInfo> findById(String id);

	Mono<VideoInfo> save(Mono<VideoInfo> book);
	
	Mono<VideoInfo> save(VideoInfo book);

	Mono<VideoInfo> deleteVideoInfoById(String id);
	
	Flux<VideoInfo> findByVideonameContainingOrderById(String videoname, Pageable pageable);

}
