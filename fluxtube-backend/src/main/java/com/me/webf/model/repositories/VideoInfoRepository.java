package com.me.webf.model.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.me.webf.model.VideoInfo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface VideoInfoRepository extends ReactiveMongoRepository<VideoInfo, String> {
	Flux<VideoInfo> findAll();

	Mono<VideoInfo> findById(String id);

	Mono<VideoInfo> save(Mono<VideoInfo> videoinfo);

	Mono<VideoInfo> deleteVideoInfoById(String id);
	
	Flux<VideoInfo> findByVideonameContainingOrderById(String videoname, Pageable pageable);
}
