package br.com.igreja.cellapp.model;

import java.io.Serializable;

public class YoutubeAndroid implements Serializable{

	private static final long serialVersionUID = -542550222429823452L;

	private String id;
	private String title;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
