package com.motion.dao;

public class DevotionalSongDao {

	private String albumName;
	private String thumnails;
	@Override
	public String toString() {
		return "DevotionalSongDao [albumName=" + albumName + ", thumnails=" + thumnails + "]";
	}
	public String getAlbumName() {
		return albumName;
	}
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	public String getThumnails() {
		return thumnails;
	}
	public void setThumnails(String thumnails) {
		this.thumnails = thumnails;
	}
	
}
