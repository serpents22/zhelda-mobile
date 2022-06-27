package com.germeny.pasqualesilvio.network;

import com.germeny.pasqualesilvio.model.AddGatewayResponse;
import com.germeny.pasqualesilvio.model.BaseResponse;
import com.germeny.pasqualesilvio.model.BaseResponseList;
import com.germeny.pasqualesilvio.model.DayNightResponse;
import com.germeny.pasqualesilvio.model.DevStatResponse;
import com.germeny.pasqualesilvio.model.IndiviDataDevStatResponse;
import com.germeny.pasqualesilvio.model.IndiviDataListResponse;
import com.germeny.pasqualesilvio.model.IndiviDataResponse;
import com.germeny.pasqualesilvio.model.IndiviDataSetResponse;
import com.germeny.pasqualesilvio.model.IndiviDataStatResponse;
import com.germeny.pasqualesilvio.model.LoginResponse;

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

    @GET("/gateway/dev-chrone/{device_id}/{type}")
    Call<BaseResponseList<DayNightResponse>> getSetupDayNight(
            @Path("device_id") String device_id,
            @Path("type") String type
    );

    @FormUrlEncoded
    @POST("/datacontrol")
    Call<BaseResponse> postDataControl(
            @Field("device_id") String device_id,
            @Field("message") String message
    );

    @GET("/gateway/dev-config/{device_id}")
    Call<BaseResponseList<IndiviDataResponse>> getIndiviData(
            @Path("device_id") String device_id
    );

    @GET("/gateway/th-info/{device_id}")
    Call<BaseResponseList<IndiviDataListResponse>> getIndiviDataList(
            @Path("device_id") String device_id
    );

    @GET("/gateway/th-stat/{device_id}")
    Call<BaseResponseList<IndiviDataStatResponse>> getIndiviDataStatList(
            @Path("device_id") String device_id
    );

    @GET("/gateway/th-set/{device_id}")
    Call<BaseResponseList<IndiviDataSetResponse>> getIndiviDataSetList(
            @Path("device_id") String device_id
    );

    @GET("/gateway/dev-stat/{device_id}")
    Call<BaseResponseList<IndiviDataDevStatResponse>> getIndiviDataDevStatList(
            @Path("device_id") String device_id
    );

    @FormUrlEncoded
    @POST("/gateway/th-info/{device_id}")
    Call<BaseResponse> postInvidiName(
            @Path("device_id") String device_id,
            @Field("tag_name") String tag_name,
            @Field("th_name") String th_name
    );

}
