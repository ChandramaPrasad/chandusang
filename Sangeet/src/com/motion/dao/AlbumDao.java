package com.motion.dao;

public class AlbumDao {

	private String albumName;
	private String imagePath;

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Override
	public String toString() {
		return "AlbumDao [albumName=" + albumName + ", imagePath=" + imagePath
				+ "]";
	}

}
