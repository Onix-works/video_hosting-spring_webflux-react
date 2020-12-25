package com.me.webf.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class VideoInfo {
	@Id
	private String id;
	private String videoname;
	private String videoId;
	private String thumbnailId;
	private String description;
	private String username;
	private String userAvatarId;
}
