package com.me.webf.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Document
@Data
@AllArgsConstructor
public abstract class AbstractUser {

	public AbstractUser() {
		this.videoInfoIds = new ArrayList<String>();
	}
	
	@Id
	private String id;
	private List<String> videoInfoIds;
}
