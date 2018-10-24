package com.angellift.webservice;


import com.angellift.home.model.googleplacemodel.PlacesResults;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {


    @GET("/maps/api/place/autocomplete/json")
    Call<PlacesResults> getPlacelist(@Query("types") String types, @Query("input") String input, @Query("location") String location, @Query("radius") Integer radius, @Query("key") String key);

//
//    @GET("/maps/api/directions/json")
//    public void getDirection(@Query("origin") String origin,@Query("destination") String destination, Callback<DirectionResults> callback);
/*
    //login  http://ch.healthcareinfosystems.in/index.php/api/Login_module_api/CHCLogin
    @FormUrlEncoded
    @POST("Login_module_api/CHCLogin")
    Call<LoginModel> loginUser(@Field("identity") String Username,
                               @Field("password") String password,
                               @Field("token_device") String device_token,
                               @Field("deviceId") String device_id);


    // cardiologist add
    @Multipart
    @POST("CHC_cardiologist_register_api")
    Call<CardRegisreResMode> registerCardiologist(@Part("name") RequestBody name,
                                                  @Part("qualification") RequestBody qualification,
                                                  @Part("bio") RequestBody bio,
                                                  @Part("mobile") RequestBody mobile,
                                                  @Part("email") RequestBody email,
                                                  @Part("secretaryName") RequestBody secretaryName,
                                                  @Part("secretaryEmail") RequestBody secretaryEmail,
                                                  @Part("secretaryMobile") RequestBody secretaryMobile,
                                                  @Part("secretaryDirectPhone") RequestBody secretaryDirectPhone,
                                                  @Part("remarks") RequestBody remarks,
                                                  @Part("trainingDueOn") RequestBody trainingDueOn,
                                                  @Part("displayName") RequestBody displayName,
                                                  @Part("lincenceNo") RequestBody lincenceNo,
                                                  @Part("designation") RequestBody designation,
                                                  @Part("password") RequestBody password,
                                                  @Part("userRole") RequestBody userRole,
                                                  @Part("userDetailsId") RequestBody userDetailsId,
                                                  @Part("id") RequestBody id,
                                                  @Part("cardiologist_code") RequestBody cardiologist_code,
                                                  @Part MultipartBody.Part certificate_image,
                                                  @Part MultipartBody.Part cardiologist_image);


    //profile view
    @GET("CHC_profile_edit_api/index/{id}")
    Call<ProfileData> getProfileData(@Path("id") String id);*/


}

