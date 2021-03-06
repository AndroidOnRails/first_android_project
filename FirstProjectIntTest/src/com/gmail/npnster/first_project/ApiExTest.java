package com.gmail.npnster.first_project;

import java.security.SecureRandom;
import java.sql.Timestamp;

import javax.inject.Inject;

import android.app.Activity;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;

import com.gmail.npnster.first_project.ApiExActivity;
import com.gmail.npnster.first_project.ApiExActivity.ApiCall;
import com.gmail.npnster.first_project.MyApp;
import com.gmail.npnster.first_project.PersistData;
import com.gmail.npnster.first_project.api_params.CreateDeviceRequest;
import com.gmail.npnster.first_project.api_params.CreateDeviceResponse;
import com.gmail.npnster.first_project.api_params.GetMapMarkersRequest;
import com.gmail.npnster.first_project.api_params.GetMapMarkersResponse;
import com.gmail.npnster.first_project.api_params.GrantFollowerPermissionRequest;
import com.gmail.npnster.first_project.api_params.GrantFollowerPermissionResponse;
import com.gmail.npnster.first_project.api_params.BaseResponse;
import com.gmail.npnster.first_project.api_params.CreateMicropostRequest;
import com.gmail.npnster.first_project.api_params.CreateMicropostResponse;
import com.gmail.npnster.first_project.api_params.DeleteMicropostRequest;
import com.gmail.npnster.first_project.api_params.DeleteMicropostResponse;
import com.gmail.npnster.first_project.api_params.FollowRequest;
import com.gmail.npnster.first_project.api_params.FollowResponse;
import com.gmail.npnster.first_project.api_params.GetFollowedUsersRequest;
import com.gmail.npnster.first_project.api_params.GetFollowedUsersResponse;
import com.gmail.npnster.first_project.api_params.GetFollowersRequest;
import com.gmail.npnster.first_project.api_params.GetFollowersResponse;
import com.gmail.npnster.first_project.api_params.GetMicropostsRequest;
import com.gmail.npnster.first_project.api_params.GetMicropostsResponse;
import com.gmail.npnster.first_project.api_params.GetMicropostsResponse.Micropost;
import com.gmail.npnster.first_project.api_params.GetUserProfileRequest;
import com.gmail.npnster.first_project.api_params.GetUserProfileResponse;
import com.gmail.npnster.first_project.api_params.GetUsersRequest;
import com.gmail.npnster.first_project.api_params.GetUsersResponse;
import com.gmail.npnster.first_project.api_params.LeaveRequest;
import com.gmail.npnster.first_project.api_params.PatchLocationRequest;
import com.gmail.npnster.first_project.api_params.PatchLocationResponse;
import com.gmail.npnster.first_project.api_params.PostLocationRequest;
import com.gmail.npnster.first_project.api_params.PostLocationResponse;
import com.gmail.npnster.first_project.api_params.PushLocationsUpdateRequestRequest;
import com.gmail.npnster.first_project.api_params.PushLocationsUpdateRequestResponse;
import com.gmail.npnster.first_project.api_params.RevokeFollowerPermissionRequest;
import com.gmail.npnster.first_project.api_params.RevokeFollowerPermissionResponse;
import com.gmail.npnster.first_project.api_params.SigninRequest;
import com.gmail.npnster.first_project.api_params.SignoutRequest;
import com.gmail.npnster.first_project.api_params.SignupRequest;
import com.gmail.npnster.first_project.api_params.UnfollowRequest;
import com.gmail.npnster.first_project.api_params.UnfollowResponse;
import com.gmail.npnster.first_project.api_params.UpdateUserRequest;
import com.gmail.npnster.first_project.api_params.UpdateUserResponse;
import com.gmail.npnster.first_project.api_params.UserListResponse.User;
import com.robotium.solo.Solo;

public class ApiExTest extends ActivityInstrumentationTestCase2<ApiExActivity> {

	public ApiExTest() {
		super(ApiExActivity.class);
//		mApp = Injector.getInstance().getObjectGraph().get(MyApp.class);
//		System.out.println(String.format("activity = %s",  getActivity()));
//		PersistData persistData = mApp.getPersistData();

	}

	private Solo solo;  
	private Activity activity;
	private String valid_android_token;
	private String storedEmailId;
	private String storedToken;
	private final Integer DEFAULT_TIMEOUT = 1000;
	private Integer timeout = DEFAULT_TIMEOUT;
	private PersistData mPersistData = null;
	private String VALID_ACCESS_TOKEN_FOR_USER_1 = "1:GJxpzRsBQpf7BDE1sdHQBw"; 
	
//	@Inject PersistData mPersistData;

    private void setTimeout(Integer timeout) {
    	this.timeout = timeout;
    }
	
    private void clickAndWait() {
    	solo.clickOnButton("Go!");
    	solo.waitForText("Done!", 1, timeout);
    }
    
	private void clickAndWait(Integer localTimeout) {
		solo.clickOnButton("Go!");
		solo.waitForText("Done!", 1, localTimeout);
	}

	private void setValidTokenAndId() {
		mPersistData.saveToken(valid_android_token);
		mPersistData.saveEmailId("valid_android_user@example.com");
	}

	private void setBadTokenAndId() {
		mPersistData.saveToken("0:blah");
		mPersistData.saveEmailId("invalid_android_user@example.com");
	}

	protected void setUp() throws Exception {
		super.setUp();
		mPersistData = ((MyApp) getActivity().getApplication()).getPersistData();
//		if (mPersistData == null) mPersistData  = new PersistData((MyApp) getActivity().getApplication());
//		System.out.println(String.format("injector = %s ",Injector.getInstance() ));
//		System.out.println(String.format("graph = %s ",Injector.getInstance().getObjectGraph() ));
//		Injector.getInstance().initialize( (MyApp) this.getActivity().getApplication());
//		Injector.getInstance().inject(this);
//		storedEmailId = mApp.getEmail();
//		storedToken = mApp.getToken();
//		mPersistData.setApiRootUrl("http://10.0.2.2:3000");
		System.out.println(String.format("got the following from the device email = %s. token = %s", storedEmailId, storedToken));
		solo = new Solo(getInstrumentation(), getActivity());
		getActivity().setLeaveRequest(
				new LeaveRequest("valid_android_user@example.com", "foobar"));
		clickAndWait();
		getActivity().setLeaveRequest(
				new LeaveRequest("new_email_address@example.com", "foobar"));
		clickAndWait();
		getActivity().setSignupRequest(
				new SignupRequest("robot", "valid_android_user@example.com",
						"foobar", "foobar"));
		clickAndWait();
		valid_android_token = getActivity().getSignupResponse().getToken();
		System.out.println(String.format("got token = %s ", valid_android_token));
		mPersistData.saveToken(valid_android_token);
		mPersistData.saveEmailId("valid_android_user@example.com");

	}

	protected void tearDown() throws Exception {
		super.tearDown();
//		System.out.println(String.format("saving the following from the device email = %s. token = %s", storedEmailId, storedToken));
//		mApp.saveEmailId(storedEmailId);
//		mApp.saveToken(storedToken);

	}

	public void testLeave() throws Exception {
		getActivity().setLeaveRequest(
				new LeaveRequest("jdd_012@example.com", "foobar"));
		clickAndWait();
		assertTrue("leave is successful", getActivity().getLeaveResponse()
				.isSuccessful());
		assertEquals("response is NO CONTENT (204) ", 204, getActivity()
				.getLeaveResponse().getRawResponse().getStatus());
		System.out.println("leave request done");

	}

	public void testSignupSucess() throws Exception {
		getActivity().setLeaveRequest(
				new LeaveRequest("jdd_012@example.com", "foobar"));
		clickAndWait();
		getActivity().setSignupRequest(
				new SignupRequest("john", "jdd_012@example.com", "foobar",
						"foobar"));
		clickAndWait();
		assertTrue("signup is successful", getActivity().getSignupResponse()
				.isSuccessful());
		assertEquals("response is CREATED (201) ", 201, getActivity()
				.getSignupResponse().getRawResponse().getStatus());

	}

	public void testSigninSucess() throws Exception {
		getActivity().setSigninRequest(
				new SigninRequest("example@railstutorial.org", "foobar"));
		clickAndWait();
		assertTrue("signin is successful", getActivity().getSigninResponse()
				.isSuccessful());
		assertEquals("response is CREATED (201) ", 201, getActivity()
				.getSigninResponse().getRawResponse().getStatus());
		String token = getActivity().getSigninResponse().getToken();
		assertNotNull("token is not null", token);

	}

	public void testSigninFail() throws Exception {
		getActivity().setSigninRequest(
				new SigninRequest("example@railstutorial.org", "fxxbar"));
		clickAndWait();
		assertFalse("signin is unsuccessful", getActivity().getSigninResponse()
				.isSuccessful());
		assertEquals("response is unauthorized (401) ", 401, getActivity()
				.getSigninResponse().getRawResponse().getStatus());
		String token = getActivity().getSigninResponse().getToken();
		assertNull("token is not null", token);

	}
	
	
	public void testSignoutSucess() throws Exception {
		// use the existing token to get the users list
		getActivity().setGetUserProfileRequest(new GetUserProfileRequest());
		clickAndWait();
		GetUserProfileResponse getUserPrfofileResponse = getActivity().getGetUserProfileResponse();
		assertEquals("response is OK (200)", 200, getUserPrfofileResponse
				.getRawResponse().getStatus());
		// signout
		SignoutRequest signoutRequest = new SignoutRequest("valid_android_user@example.com", "wrong");
		getActivity().setSignoutRequest(signoutRequest);
		clickAndWait();		
		assertEquals("response is ok (200) ", 204, getActivity()
				.getSignoutResponse().getRawResponse().getStatus());
		// try to get users list after signing out .. should return unauthorized
		getActivity().setGetUserProfileRequest(new GetUserProfileRequest());
		clickAndWait();
		getUserPrfofileResponse = getActivity().getGetUserProfileResponse();
		assertEquals("response is unauthorized (401)", 401, getUserPrfofileResponse
				.getRawResponse().getStatus());

	}

	public void testSignupEmailTaken() throws Exception {
		getActivity().setSignupRequest(
				new SignupRequest("john", "jdd_012@example.com", "foobar",
						"foobar"));
		clickAndWait();
		getActivity().setSignupRequest(
				new SignupRequest("john", "jdd_012@example.com", "foobar",
						"foobar"));
		clickAndWait();
		System.out.println(getActivity().getSignupResponse().getErrors()
				.toString());
		assertFalse("signup is not successful", getActivity()
				.getSignupResponse().isSuccessful());
		assertTrue(
				"errors includes email taken",
				getActivity().getSignupResponse().getErrors()
						.contains("Email has already been taken"));

	}

	public void testGetProfile() throws Exception {
		getActivity().setGetUserProfileRequest(new GetUserProfileRequest());
		clickAndWait();
		GetUserProfileResponse getUserProfileResponse = getActivity()
				.getGetUserProfileResponse();
		assertEquals("name is robot", "robot", getUserProfileResponse.getName());
		assertNotNull("email is not null", getUserProfileResponse.getEmail());
		assertNotNull("gravatar is not null",
				getUserProfileResponse.getGravatar_id());
//		assertNotNull("phone_number is not null",
//				getUserProfileResponse.getPhoneNumber());
		assertTrue("followed count is >= 0 ",
				getUserProfileResponse.getFollowed_users_count() >= 0);
		assertTrue("followers count is >= 0",
				getUserProfileResponse.getFollowers_count() >= 0);
		assertTrue("micropost list count is >= 0",
				getUserProfileResponse.getMicroposts().size() >= 0);
		assertTrue("permissions is not null",
				getUserProfileResponse.getPermissionsGrantedByCurrentUserToUser().size() >= 0);
		assertTrue("granted permissions is not null",
				getUserProfileResponse.getPermissionsGrantedByUserToCurrentUser().size() >= 0);
		
	}
	
	public void testGetOtherUserProfile() throws Exception {
		getActivity().setGetUserProfileRequest(new GetUserProfileRequest("1"));
		clickAndWait();
		GetUserProfileResponse getUserProfileResponse = getActivity()
				.getGetUserProfileResponse();
		assertEquals("name is john", "john", getUserProfileResponse.getName());
		assertNotNull("email is not null", getUserProfileResponse.getEmail());
		assertNotNull("gravatar is not null",
				getUserProfileResponse.getGravatar_id());
		assertNotNull("phone_number is not null",
				getUserProfileResponse.getPhoneNumber());
		assertTrue("followed count is >= 0 ",
				getUserProfileResponse.getFollowed_users_count() >= 0);
		assertTrue("followers count is >= 0",
				getUserProfileResponse.getFollowers_count() >= 0);
		assertTrue("micropost list count is >= 0",
				getUserProfileResponse.getMicroposts().size() >= 0);
		assertTrue("permissions is not null",
				getUserProfileResponse.getPermissionsGrantedByCurrentUserToUser().size() >= 0);
		assertTrue("granted permissions is not null",
				getUserProfileResponse.getPermissionsGrantedByUserToCurrentUser().size() >= 0);

	}

	public void testGetProfileFail() throws Exception {
		setBadTokenAndId();
		getActivity().setGetUserProfileRequest(new GetUserProfileRequest());
		clickAndWait();
		GetUserProfileResponse getUserProfileResponse = getActivity()
				.getGetUserProfileResponse();
		assertNull("name is null", getUserProfileResponse.getName());
		assertNull("email is not null", getUserProfileResponse.getEmail());
		assertNull("gravatar is not null",
				getUserProfileResponse.getGravatar_id());
		assertNull("followed count is null ",
				getUserProfileResponse.getFollowed_users_count());
		assertNull("followers count is null",
				getUserProfileResponse.getFollowers_count());
		assertEquals("response is unauthorized (401)", 401,
				getUserProfileResponse.getRawResponse().getStatus());

	}

	public void testGetUsers() throws Exception {
		getActivity().setGetUsersRequest(new GetUsersRequest());
		clickAndWait(10000);
		GetUsersResponse getUsersResponse = getActivity().getGetUsersResponse();
		System.out.println(String.format("response = %s",getUsersResponse ));
		assertNotNull("user list is not null", getUsersResponse.getUsers());
		assertTrue("user list has size > 0 ", getUsersResponse.getUsers()
				.size() > 0);
		assertEquals("response is OK (200)", 200, getUsersResponse
				.getRawResponse().getStatus());
	}

	public void testGetUsersFail() throws Exception {
		setBadTokenAndId();
		getActivity().setGetUsersRequest(new GetUsersRequest());
		clickAndWait();
		GetUsersResponse getUsersResponse = getActivity().getGetUsersResponse();
		assertNotNull("user list is not null", getUsersResponse.getUsers());
		assertTrue("user list is empty",
				getUsersResponse.getUsers().size() == 0);
		assertEquals("response is Unauthorized (401)", 401, getUsersResponse
				.getRawResponse().getStatus());
	}

	public void testUpdateUser() throws Exception {
		getActivity().setUpdateUserRequest(
				new UpdateUserRequest("new_user_name",
						"new_email_address@example.com", "foobar", "foobar", "foobar"));
		clickAndWait();
		UpdateUserResponse updateUserResponse = getActivity()
				.getUpdateUserResponse();
		assertEquals("response is  Accepted (202)", 202, updateUserResponse
				.getRawResponse().getStatus());
	}

	public void testUpdateUserFail() throws Exception {
		getActivity().setUpdateUserRequest(
				new UpdateUserRequest("new_user_name",
						"new_email_address@example.com", "foobar", "fxxbar", "foobar"));
		clickAndWait();
		UpdateUserResponse updateUserResponse = getActivity()
				.getUpdateUserResponse();
		assertEquals("response is  Not Acceptable (406)", 406,
				updateUserResponse.getRawResponse().getStatus());
		assertTrue(
				"errors include password must match",
				getActivity()
						.getUpdateUserResponse()
						.getErrors()
						.contains(
								"Password confirmation doesn't match Password"));

	}

	public void testGetFollowers() throws Exception {
		getActivity().setGetFollowersRequest(new GetFollowersRequest("1"));
		clickAndWait();
		GetFollowersResponse getFollowersResponse = getActivity()
				.getGetFollowersResponse();
		assertTrue("followers list > 0 ", getFollowersResponse.getUsers()
				.size() > 0);
		assertEquals("response is OK (200)", 200, getFollowersResponse
				.getRawResponse().getStatus());
	}

	public void testGetFollowersFail() throws Exception {
		setBadTokenAndId();
		getActivity().setGetFollowersRequest(new GetFollowersRequest("1"));
		clickAndWait();
		GetFollowersResponse getFollowersResponse = getActivity()
				.getGetFollowersResponse();
		assertTrue("followers == 0 ",
				getFollowersResponse.getUsers().size() == 0);
		assertEquals("response is OK (401)", 401, getFollowersResponse
				.getRawResponse().getStatus());
	}

	public void testGetFollowedUsers() throws Exception {
		GetFollowedUsersRequest request = new GetFollowedUsersRequest("1");
		request.setToken(VALID_ACCESS_TOKEN_FOR_USER_1);
		System.out.println(request.getToken());
		getActivity().setGetFollowedUsersRequest(request);
		clickAndWait(3000);
		GetFollowedUsersResponse getFollowedUsersResponse = getActivity()
				.getGetFollowedUsersResponse();
		assertTrue("followedUsers list > 0 ", getFollowedUsersResponse
				.getUsers().size() > 0);
		assertEquals("response is OK (200)", 200, getFollowedUsersResponse
				.getRawResponse().getStatus());
		for(User followed_user : getFollowedUsersResponse.getUsers() ) {
			if (followed_user.getName().equals("Jane M.")) {
				System.out.println(String.format("micro post = %s", followed_user.getName().toString()));
				assertTrue("this user has a micropost i can see", followed_user.hasMicropost());
				assertEquals("last micro post content is correct", "a newer post", followed_user.getLastMicropost().getContent());
			} else if (followed_user.getId() != 1 ){
				assertFalse("this user does not have a micropost I can see", followed_user.hasMicropost());
			}
		}
	}

	public void testGetFollowedUsersFail() throws Exception {
		setBadTokenAndId();
		getActivity().setGetFollowedUsersRequest(
				new GetFollowedUsersRequest("1"));
		clickAndWait();
		GetFollowedUsersResponse getFollowedUsersResponse = getActivity()
				.getGetFollowedUsersResponse();
		assertTrue("followedUsers == 0 ", getFollowedUsersResponse.getUsers()
				.size() == 0);
		assertEquals("response is OK (401)", 401, getFollowedUsersResponse
				.getRawResponse().getStatus());
	}

	public void testGetMicroposts() throws Exception {
		GetMicropostsRequest request = new GetMicropostsRequest("1"); 
		request.setToken(VALID_ACCESS_TOKEN_FOR_USER_1);
		getActivity().setGetMicropostsRequest(request);
		clickAndWait();
		GetMicropostsResponse getMicropostsResponse = getActivity()
				.getGetMicropostsResponse();
		assertTrue("microposts list > 0 ", getMicropostsResponse
				.getMicroposts().size() > 0);
		assertEquals("microposts list size == total number of microposts ",
				getMicropostsResponse.getMicroposts().size(),
				getMicropostsResponse.getTotal_user_microposts_count()
				.intValue());
		assertEquals("response is OK (200)", 200, getMicropostsResponse
				.getRawResponse().getStatus());
	}
	
	public void testGetMicropostsForWrongUser() throws Exception {
		GetMicropostsRequest request = new GetMicropostsRequest("2"); 
		request.setToken(VALID_ACCESS_TOKEN_FOR_USER_1);
		getActivity().setGetMicropostsRequest(request);
		clickAndWait();
		GetMicropostsResponse getMicropostsResponse = getActivity()
				.getGetMicropostsResponse();
		assertTrue("microposts list == 0 ", getMicropostsResponse
				.getMicroposts().size() == 0);
		assertEquals("response is OK (200)", 200, getMicropostsResponse
				.getRawResponse().getStatus());
	}

	public void testGetMicropostsFail() throws Exception {
		setBadTokenAndId();
		getActivity().setGetMicropostsRequest(new GetMicropostsRequest("1"));
		clickAndWait();
		GetMicropostsResponse getMicropostsResponse = getActivity()
				.getGetMicropostsResponse();
		assertTrue("microposts == 0 ", getMicropostsResponse.getMicroposts()
				.size() == 0);
		assertEquals("response is OK (401)", 401, getMicropostsResponse
				.getRawResponse().getStatus());
	}

	public void testCreateMicropost() throws Exception {
		getActivity().setCreateMicropostRequest(
				new CreateMicropostRequest("content from android"));
		clickAndWait();
		CreateMicropostResponse createMicropostResponse = getActivity()
				.getCreateMicropostResponse();
		assertEquals("returned micropost content is correct",
				"content from android", createMicropostResponse.getMicropost()
						.getContent());
		assertEquals("response is created (201)", 201, createMicropostResponse
				.getRawResponse().getStatus());
		getActivity().setGetMicropostsRequest(
				new GetMicropostsRequest(createMicropostResponse.getMicropost()
						.getUser_id().toString()));
		clickAndWait();
		GetMicropostsResponse getMicropostsResponse = getActivity()
				.getGetMicropostsResponse();
		assertEquals(getMicropostsResponse.getMicroposts().get(0).getContent(),
				"content from android");

	}

	public void testCreateMicropostFail() throws Exception {
		setBadTokenAndId();
		getActivity().setCreateMicropostRequest(
				new CreateMicropostRequest("content from android"));
		clickAndWait();
		CreateMicropostResponse createMicropostResponse = getActivity()
				.getCreateMicropostResponse();
		assertNull("returned micropost is null",
				createMicropostResponse.getMicropost());
		assertEquals("response is created (401)", 401, createMicropostResponse
				.getRawResponse().getStatus());
	}

	public void testDeleteMicropost() throws Exception {
		getActivity().setCreateMicropostRequest(
				new CreateMicropostRequest("content from android"));
		clickAndWait();
		getActivity().setCreateMicropostRequest(
				new CreateMicropostRequest("another content from android"));
		clickAndWait();
		CreateMicropostResponse createMicropostResponse = getActivity()
				.getCreateMicropostResponse();
		assertEquals("returned micropost content is correct",
				"another content from android", createMicropostResponse
						.getMicropost().getContent());
		assertEquals("response is created (201)", 201, createMicropostResponse
				.getRawResponse().getStatus());
		getActivity().setGetMicropostsRequest(
				new GetMicropostsRequest(createMicropostResponse.getMicropost()
						.getUser_id().toString()));
		clickAndWait();
		GetMicropostsResponse getMicropostsResponse = getActivity()
				.getGetMicropostsResponse();
		assertEquals(getMicropostsResponse.getMicroposts().get(0).getContent(),
				"another content from android");
		getActivity().setDeleteMicropostRequest(
				new DeleteMicropostRequest(createMicropostResponse
						.getMicropost().getId().toString()));
		clickAndWait();
		DeleteMicropostResponse deleteMicropostResponse = getActivity()
				.getDeleteMicropostResponse();
		assertEquals("response is no_content (204)", 204,
				deleteMicropostResponse.getRawResponse().getStatus());
		getActivity().setGetMicropostsRequest(
				new GetMicropostsRequest(createMicropostResponse.getMicropost()
						.getUser_id().toString()));
		clickAndWait();
		getMicropostsResponse = getActivity().getGetMicropostsResponse();
		assertEquals("content should match the correct value",
				getMicropostsResponse.getMicroposts().get(0).getContent(),
				"content from android");

	}

	public void testDeleteMicropostFail() throws Exception {
		setBadTokenAndId();
		getActivity()
				.setDeleteMicropostRequest(new DeleteMicropostRequest("1"));
		clickAndWait();
		DeleteMicropostResponse deleteMicropostResponse = getActivity()
				.getDeleteMicropostResponse();
		assertEquals("response is unauthorized  (401)", 401,
				deleteMicropostResponse.getRawResponse().getStatus());

	}

	public void testRelationships() throws Exception {
		// first unfollow user 1 (just in case)
		getActivity().setUnfollowRequest(new UnfollowRequest("1"));
		clickAndWait();
		UnfollowResponse unfollowResponse = getActivity().getUnfollowResponse();
		assertEquals("response is no contetn  (204)", 204, unfollowResponse
				.getRawResponse().getStatus());
		// check that user is not following user 1
		getActivity().setGetFollowedUsersRequest(
				new GetFollowedUsersRequest(mPersistData.getUserId()));
		clickAndWait();
		GetFollowedUsersResponse followedUsersResponse = getActivity()
				.getGetFollowedUsersResponse();
		assertFalse("user is not following user 1", followedUsersResponse
				.getUserIds().contains("1"));

		// then follow user 1
		getActivity().setFollowRequest(new FollowRequest("1"));
		clickAndWait();
		FollowResponse followResponse = getActivity().getFollowResponse();
		assertEquals("response is no created  (201)", 201, followResponse
				.getRawResponse().getStatus());
		// check that user is following user 1
		getActivity().setGetFollowedUsersRequest(
				new GetFollowedUsersRequest(mPersistData.getUserId()));
		clickAndWait();
		followedUsersResponse = getActivity().getGetFollowedUsersResponse();
		assertTrue("user is following user 1", followedUsersResponse
				.getUserIds().contains("1"));

		// unfollow user 1
		getActivity().setUnfollowRequest(new UnfollowRequest("1"));
		clickAndWait();
		// check that user is not following user 1
		getActivity().setGetFollowedUsersRequest(
				new GetFollowedUsersRequest(mPersistData.getUserId()));
		clickAndWait();
		followedUsersResponse = getActivity().getGetFollowedUsersResponse();
		assertFalse("user is following user 1", followedUsersResponse
				.getUserIds().contains("1"));

	}
	public void testFollowerPermissions() throws Exception {
		
		// sign in as sample user
		getActivity().setSigninRequest(new SigninRequest("example@railstutorial.org", "foobar"));
		clickAndWait();
		assertTrue("signin is successful", getActivity().getSigninResponse()
				.isSuccessful());
		// follow current user
		String token = getActivity().getSigninResponse().getToken();
		String userId = token.split(":")[0];
		FollowRequest followRequest = new FollowRequest(mPersistData.getUserId());
		followRequest.setToken(token);
		getActivity().setFollowRequest(followRequest);
		clickAndWait();
		FollowResponse followResponse = getActivity().getFollowResponse();
		assertEquals("response is  created  (201)", 201, followResponse
				.getRawResponse().getStatus());
		// have current user grant sample user a permission and check that it exists
		GrantFollowerPermissionRequest grantFollowerPermissionRequest = new GrantFollowerPermissionRequest(mPersistData.getUserId(), userId, "allow_email_id");
		getActivity().setGrantFollowerPermissionRequest(grantFollowerPermissionRequest);
		clickAndWait();
		GrantFollowerPermissionResponse grantFollowerPermissionResponse = getActivity().getGrantFollowerPermissionResponse();
		assertTrue("follower has allow_email_id permission", grantFollowerPermissionResponse.getGrantedPermissions().contains("allow_email_id"));
		// revoke the permission and check that it is gond
		RevokeFollowerPermissionRequest revokeFollowerPermissionRequest = new RevokeFollowerPermissionRequest(mPersistData.getUserId(), userId, "allow_email_id");
		getActivity().setRevokeFollowerPermissionRequest(revokeFollowerPermissionRequest);
		clickAndWait();
		RevokeFollowerPermissionResponse revokeFollowerPermissionResponse = getActivity().getRevokeFollowerPermissionResponse();
		assertEquals("response is ok  (200)", 200, revokeFollowerPermissionResponse
				.getRawResponse().getStatus());
		assertFalse("follower does not have allow_email_id permission", revokeFollowerPermissionResponse.getGrantedPermissions().contains("allow_email_id"));
		// grant the same permission back, and check that it is there
		grantFollowerPermissionRequest = new GrantFollowerPermissionRequest(mPersistData.getUserId(), userId, "allow_email_id");
		getActivity().setGrantFollowerPermissionRequest(grantFollowerPermissionRequest);
		clickAndWait();
		grantFollowerPermissionResponse = getActivity().getGrantFollowerPermissionResponse();
		assertTrue("folloer has allow_email_id permission", grantFollowerPermissionResponse.getGrantedPermissions().contains("allow_email_id"));
		
	}
	
	public void testCreateDevice() throws Exception {
		String dummyGcmRegId = String.valueOf(System.currentTimeMillis());
		String name = "android_device" + dummyGcmRegId;
		String phoneNumber = "phone_number" + dummyGcmRegId;
		getActivity().setCreateDeviceRequest(new CreateDeviceRequest(dummyGcmRegId,name, true, phoneNumber)); 
		clickAndWait();
		CreateDeviceResponse createDeviceResponse = getActivity()
				.getCreateDeviceResponse();
		assertEquals("returned device name is correct",
				name, createDeviceResponse.getName());
		assertEquals("returned device id is correct",
				dummyGcmRegId, createDeviceResponse.getGcmRegId());
		assertEquals("returned device phone number is correct",
				phoneNumber, createDeviceResponse.getPhoneNumber());
		assertTrue("returned device isPrimary is true",
				 createDeviceResponse.isPrimary());
		assertEquals("response is created (201)", 201, createDeviceResponse
				.getRawResponse().getStatus());
		
		// should be changed to try creating a device with the same id but for a different user
//		getActivity().setCreateDeviceRequest(new CreateDeviceRequest(dummyGcmRegId,name, true, phoneNumber)); 
//        clickAndWait();
//        createDeviceResponse = getActivity().getCreateDeviceResponse();
//		assertEquals("response is created (406)", 406, createDeviceResponse
//				.getRawResponse().getStatus());
//		assertTrue(
//		"errors includes - gcm_reg_id is already taken",
//		createDeviceResponse.getErrors().contains(
//				"gcm_reg_id is already taken"));
		   
        
		

	}

	
	public void testPostLocation() throws Exception {
		String dummyGcmRegId = String.valueOf(System.currentTimeMillis());
		String name = "android_device" + dummyGcmRegId;
		getActivity().setCreateDeviceRequest(new CreateDeviceRequest(dummyGcmRegId,name, true, "dummy_phone_number")); 
		clickAndWait();
		CreateDeviceResponse createDeviceResponse = getActivity()
				.getCreateDeviceResponse();
		assertEquals("returned device name is correct",
				name, createDeviceResponse.getName());
		assertEquals("returned device id is correct",
				dummyGcmRegId, createDeviceResponse.getGcmRegId());
		assertTrue("returned device isPrimary is true",
				createDeviceResponse.isPrimary());
		assertEquals("response is created (201)", 201, createDeviceResponse
				.getRawResponse().getStatus());
		Location location = new Location("test");
		long locationTime = System.currentTimeMillis();
		location.setTime(locationTime);
		location.setAccuracy(5.5f);
		location.setAltitude(100.5);
		location.setBearing( 25.2f);
		location.setLatitude(45.1234567);
		location.setLongitude(-155.87654321);
		location.setSpeed(55.2f);
		getActivity().setPostLocationRequest(new PostLocationRequest(dummyGcmRegId,location)); 
		clickAndWait();
		PostLocationResponse postLocationResponse = getActivity().getPostLocationResponse();
		Timestamp timestamp = new Timestamp(postLocationResponse.getLocation().getTime());
		System.out.println(timestamp);
		assertEquals("response is created (201)", 201, postLocationResponse
				.getRawResponse().getStatus());
		assertEquals("time is correct", new Timestamp(locationTime) ,  timestamp);
		assertEquals("accuracy is correct", 5.5f, postLocationResponse.getLocation().getAccuracy());
		assertEquals("altitude is correct", 100.5, postLocationResponse.getLocation().getAltitude());
		assertEquals("bearing is correct", 25.2f, postLocationResponse.getLocation().getBearing());
		assertEquals("latitude is correct", 45.1234567, postLocationResponse.getLocation().getLatitude());
		assertEquals("longitude is correct", -155.87654321, postLocationResponse.getLocation().getLongitude());
		assertEquals("speed is correct", 55.2f, postLocationResponse.getLocation().getSpeed());
		assertTrue("has accuracy is correct", postLocationResponse.getLocation().hasAccuracy());
		assertTrue("has altitude is correct", postLocationResponse.getLocation().hasAltitude());
		assertTrue("has bearing is correct", postLocationResponse.getLocation().hasBearing());
		assertTrue("has speed is correct", postLocationResponse.getLocation().hasSpeed());
		
	}
	
	public void testPatchLocation() throws Exception {
		String dummyGcmRegId = String.valueOf(System.currentTimeMillis());
		String name = "android_device" + dummyGcmRegId;
		getActivity().setCreateDeviceRequest(new CreateDeviceRequest(dummyGcmRegId,name, true, "dummy_phone_number")); 
		clickAndWait();
		CreateDeviceResponse createDeviceResponse = getActivity()
				.getCreateDeviceResponse();
		assertEquals("returned device name is correct",
				name, createDeviceResponse.getName());
		assertEquals("returned device id is correct",
				dummyGcmRegId, createDeviceResponse.getGcmRegId());
		assertTrue("returned device isPrimary is true",
				createDeviceResponse.isPrimary());
		assertEquals("response is created (201)", 201, createDeviceResponse
				.getRawResponse().getStatus());
		Location location = new Location("test");
		long locationTime = System.currentTimeMillis();
		location.setTime(locationTime);
		location.setAccuracy(5.5f);
		location.setAltitude(100.5);
		location.setBearing( 25.2f);
		location.setLatitude(45.1234567);
		location.setLongitude(-155.87654321);
		location.setSpeed(55.2f);
		getActivity().setPatchLocationRequest(new PatchLocationRequest(dummyGcmRegId,location)); 
		clickAndWait();
		PatchLocationResponse patchLocationResponse = getActivity().getPatchLocationResponse();
		Timestamp timestamp = new Timestamp(patchLocationResponse.getLocation().getTime());
		System.out.println(timestamp);
		assertEquals("response is created (201)", 201, patchLocationResponse
				.getRawResponse().getStatus());
		assertEquals("time is correct", new Timestamp(locationTime) ,  timestamp);
		assertEquals("accuracy is correct", 5.5f, patchLocationResponse.getLocation().getAccuracy());
		assertEquals("altitude is correct", 100.5, patchLocationResponse.getLocation().getAltitude());
		assertEquals("bearing is correct", 25.2f, patchLocationResponse.getLocation().getBearing());
		assertEquals("latitude is correct", 45.1234567, patchLocationResponse.getLocation().getLatitude());
		assertEquals("longitude is correct", -155.87654321, patchLocationResponse.getLocation().getLongitude());
		assertEquals("speed is correct", 55.2f, patchLocationResponse.getLocation().getSpeed());
		assertTrue("has accuracy is correct", patchLocationResponse.getLocation().hasAccuracy());
		assertTrue("has altitude is correct", patchLocationResponse.getLocation().hasAltitude());
		assertTrue("has bearing is correct", patchLocationResponse.getLocation().hasBearing());
		assertTrue("has speed is correct", patchLocationResponse.getLocation().hasSpeed());
		
	}
	
	public void testPostLocationsUpdateRequest() throws Exception {
		String dummyGcmRegId = String.valueOf(System.currentTimeMillis());
		String name = "android_device" + dummyGcmRegId;
		getActivity().setCreateDeviceRequest(new CreateDeviceRequest(dummyGcmRegId,name, true, "dummy_phone_number")); 
		clickAndWait();
		CreateDeviceResponse createDeviceResponse = getActivity()
				.getCreateDeviceResponse();
		assertEquals("returned device name is correct",
				name, createDeviceResponse.getName());
		getActivity().setPushLocationsUpdateRequestRequest(new PushLocationsUpdateRequestRequest());
		clickAndWait();
		PushLocationsUpdateRequestResponse pushLocationsUpdateRequestResponse = getActivity()
				.getPushLocationsUpdateRequestResponse();
		assertEquals("rails status code is 200",
				200, pushLocationsUpdateRequestResponse.getRailsStatusCode());
		assertEquals("bogus gcm_id should give failed count of 1",
				1, pushLocationsUpdateRequestResponse.getGcmFailedCount());
		assertEquals("bogus gcm_id should give success count of 0",
				0, pushLocationsUpdateRequestResponse.getGcmSuccessCount());
						
		
	}
	
	public void testGetMapMarkers() throws Exception {
		String dummyGcmRegId = String.valueOf(System.currentTimeMillis());
		String name = "android_device" + dummyGcmRegId;
		getActivity().setCreateDeviceRequest(new CreateDeviceRequest(dummyGcmRegId,name, true, "dummy_phone_number")); 
		clickAndWait(2000);
		CreateDeviceResponse createDeviceResponse = getActivity()
				.getCreateDeviceResponse();
		assertEquals("returned device name is correct",
				name, createDeviceResponse.getName());
		assertEquals("returned device id is correct",
				dummyGcmRegId, createDeviceResponse.getGcmRegId());
		assertTrue("returned device isPrimary is true",
				createDeviceResponse.isPrimary());
		assertEquals("response is created (201)", 201, createDeviceResponse
				.getRawResponse().getStatus());
		Location location = new Location("test");
		long locationTime = System.currentTimeMillis();
		location.setTime(locationTime);
		location.setAccuracy(5.5f);
		location.setAltitude(100.5);
		location.setBearing( 25.2f);
		location.setLatitude(45.1234567);
		location.setLongitude(-155.87654321);
		location.setSpeed(55.2f);
		getActivity().setPostLocationRequest(new PostLocationRequest(dummyGcmRegId,location)); 
		clickAndWait();
		PostLocationResponse postLocationResponse = getActivity().getPostLocationResponse();
		Timestamp timestamp = new Timestamp(postLocationResponse.getLocation().getTime());
		System.out.println(timestamp);
		assertEquals("response is created (201)", 201, postLocationResponse
				.getRawResponse().getStatus());
		assertEquals("time is correct", new Timestamp(locationTime) ,  timestamp);
		assertEquals("accuracy is correct", 5.5f, postLocationResponse.getLocation().getAccuracy());
		assertEquals("altitude is correct", 100.5, postLocationResponse.getLocation().getAltitude());
		assertEquals("bearing is correct", 25.2f, postLocationResponse.getLocation().getBearing());
		assertEquals("latitude is correct", 45.1234567, postLocationResponse.getLocation().getLatitude());
		assertEquals("longitude is correct", -155.87654321, postLocationResponse.getLocation().getLongitude());
		assertEquals("speed is correct", 55.2f, postLocationResponse.getLocation().getSpeed());
		assertTrue("has accuracy is correct", postLocationResponse.getLocation().hasAccuracy());
		assertTrue("has altitude is correct", postLocationResponse.getLocation().hasAltitude());
		assertTrue("has bearing is correct", postLocationResponse.getLocation().hasBearing());
		assertTrue("has speed is correct", postLocationResponse.getLocation().hasSpeed());
		
		getActivity().setGetMapMarkersRequest(new GetMapMarkersRequest()); 
		clickAndWait();
		GetMapMarkersResponse getGetMapMarkersResponse = getActivity()
				.getGetMapMarkersResponse();
		assertEquals("response is created (200)", 200, getGetMapMarkersResponse
				.getRawResponse().getStatus());
		assertEquals("number of markers is 1 ", 1, getGetMapMarkersResponse.getMarkers().size());
		assertEquals("latitude is correct", 45.1234567, getGetMapMarkersResponse.getMarkers().get(0).getLatitude());
		assertEquals("longitude is correct", -155.87654321, getGetMapMarkersResponse.getMarkers().get(0).getLongitude());
		assertEquals("name is correct", "robot", getGetMapMarkersResponse.getMarkers().get(0).getName());
		assertEquals("provider is correct", "test", getGetMapMarkersResponse.getMarkers().get(0).getLocationProvider());
		assertEquals("bearing is correct", 25.2f, getGetMapMarkersResponse.getMarkers().get(0).getBearing());
		assertEquals("speed is correct", 55.2f, getGetMapMarkersResponse.getMarkers().get(0).getSpeed());
		assertEquals("altitude is correct", 100.5, getGetMapMarkersResponse.getMarkers().get(0).getAltitude());
		assertTrue("has accuracy is correct", getGetMapMarkersResponse.getMarkers().get(0).hasAccuracy());
		assertTrue("has altitude is correct", getGetMapMarkersResponse.getMarkers().get(0).hasAltitude());
		assertTrue("has bearing is correct", getGetMapMarkersResponse.getMarkers().get(0).hasBearing());
		assertTrue("has speed is correct", getGetMapMarkersResponse.getMarkers().get(0).hasSpeed());
		
		
		
	}

}
