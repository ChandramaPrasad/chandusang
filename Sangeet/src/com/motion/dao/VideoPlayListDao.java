package com.motion.dao;

public class VideoPlayListDao {

	private String videoThumbnailImage;
	private String videoName;
	private String videoDescription;

	public String getVideoThumbnailImage() {
		return videoThumbnailImage;
	}

	public void setVideoThumbnailImage(String videoThumbnailImage) {
		this.videoThumbnailImage = videoThumbnailImage;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getVideoDescription() {
		return videoDescription;
	}

	public void setVideoDescription(String videoDescription) {
		this.videoDescription = videoDescription;
	}

	@Override
	public String toString() {
		return "VideoPlayListDao [videoThumbnailImage=" + videoThumbnailImage + ", videoName=" + videoName
				+ ", videoDescription=" + videoDescription + "]";
	}

}
