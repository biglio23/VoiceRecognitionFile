package me.pasqualinosorice.voicerecognitionfile.network;

import retrofit.RestAdapter;

public class RestClient {

    private static final String BASE_URL = "https://www.google.com/speech-api/v2";
    private AppService apiService;

    public RestClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                //.setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setConverter(new GoogleConverter())
                .build();

        apiService = restAdapter.create(AppService.class);
    }

    public AppService getApiService() {
        return apiService;
    }
}
