package com.gmail.npnster.first_project.api_params;

public class GetFollowersRequest {
	private String id;

	public GetFollowersRequest(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

}
