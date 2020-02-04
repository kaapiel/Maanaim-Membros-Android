package br.com.igreja.cellapp.model;

import java.io.Serializable;

public class RadioAndroid implements Serializable{

	private static final long serialVersionUID = 3296322210376507578L;
	
	private String status;
	private String playing;
	private String update;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPlaying() {
		return playing;
	}
	public void setPlaying(String playing) {
		this.playing = playing;
	}
	public String getUpdate() {
		return update;
	}
	public void setUpdate(String update) {
		this.update = update;
	}
	
}
