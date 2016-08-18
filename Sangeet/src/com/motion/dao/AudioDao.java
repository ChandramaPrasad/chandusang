package com.motion.dao;

public class AudioDao {

	private String Song;
	private String rating;
	private String duration;
	private String singer;
	private String pathUrl;

	public String getSong() {
		return Song;
	}

	public void setSong(String song) {
		Song = song;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	public String getPathUrl() {
		return pathUrl;
	}

	public void setPathUrl(String pathUrl) {
		this.pathUrl = pathUrl;
	}

	@Override
	public String toString() {
		return "AudioDao [Song=" + Song + ", rating=" + rating + ", duration="
				+ duration + ", singer=" + singer + ", pathUrl=" + pathUrl
				+ "]";
	}

}
