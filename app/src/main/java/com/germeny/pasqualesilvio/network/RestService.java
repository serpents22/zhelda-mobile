package com.germeny.pasqualesilvio.network;

import com.germeny.pasqualesilvio.model.AddGatewayResponse;
import com.germeny.pasqualesilvio.model.BaseResponse;
import com.germeny.pasqualesilvio.model.BaseResponseList;
import com.germeny.pasqualesilvio.model.DevStatResponse;
import com.germeny.pasqualesilvio.model.LoginResponse;
import com.germeny.pasqualesilvio.model.SuccessResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RestService {
    @FormUrlEncoded
    @POST("/user/register")
    Call<BaseResponse> postRegister(
            @Field("username") String name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation,
            @Field("usage") String usage
    );

    @FormUrlEncoded
    @POST("/user/login/mobile")
    Call<BaseResponse<LoginResponse>> postLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("/gateway")
    Call<BaseResponseList<AddGatewayResponse>> postGateway(
            @Field("device_id") String device_id,
            @Field("device_name") String device_name,
            @Field("password") String password,
            @Field("type") String type,
            @Field("max_devices") int max_devices
    );

    @GET("/gateway/dev-stat/{device_id}")
    Call<BaseResponseList<DevStatResponse>> getDeviceStat(
            @Path("device_id") String device_id
    );

}
