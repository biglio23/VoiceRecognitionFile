package me.pasqualinosorice.voicerecognitionfile.network;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.QueryMap;
import retrofit.mime.TypedInput;

public interface AppService {

    @POST("/recognize")
    void getUp(@Body TypedInput body, @QueryMap Map<String, String> options, Callback<Response> cb);
}
