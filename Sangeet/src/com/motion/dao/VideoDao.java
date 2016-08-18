package com.motion.dao;

public class VideoDao {

	private String video_name;
	private String rating;
	private String likes;
	private String duration;
	private String description;
	private String views;
	private String thumbnails;

	public String getVideo_name() {
		return video_name;
	}

	public void setVideo_name(String video_name) {
		this.video_name = video_name;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getLikes() {
		return likes;
	}

	public void setLikes(String likes) {
		this.likes = likes;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getViews() {
		return views;
	}

	public void setViews(String views) {
		this.views = views;
	}

	public String getThumbnails() {
		return thumbnails;
	}

	public void setThumbnails(String thumbnails) {
		this.thumbnails = thumbnails;
	}

	@Override
	public String toString() {
		return "MostViewsDao [video_name=" + video_name + ", rating=" + rating
				+ ", likes=" + likes + ", duration=" + duration
				+ ", description=" + description + ", views=" + views
				+ ", thumbnails=" + thumbnails + "]";
	}

}
