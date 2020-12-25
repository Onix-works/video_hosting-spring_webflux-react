package com.me.webf.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.me.webf.model.VideoInfo;
import com.me.webf.model.repositories.VideoInfoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Transactional
@Service
public class VideoInfoServiceImp implements VideoInfoService {

	@Autowired
	private VideoInfoRepository videoInfoRepository;

	@Override
	public Flux<VideoInfo> findAll() {		
		return videoInfoRepository.findAll();
	}

	@Override
	public Mono<VideoInfo> findById(String id) {		
		return videoInfoRepository.findById(id);
	}

	@Override
	public Mono<VideoInfo> save(Mono<VideoInfo> book) {
		return videoInfoRepository.save(book);
	}
	
	@Override
	public Mono<VideoInfo> save(VideoInfo entity) {
		return videoInfoRepository.save(entity);
	}

	@Override
	public Flux<VideoInfo> findByVideonameContainingOrderById(String videoname,
			Pageable pageable) {
		return videoInfoRepository.findByVideonameContainingOrderById(videoname, pageable);
	}
	
	@Override
	public Mono<VideoInfo> deleteVideoInfoById(String id) {
		return videoInfoRepository.deleteVideoInfoById(id);
	}
}
