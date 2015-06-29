package me.pasqualinosorice.voicerecognitionfile;

import android.app.Application;

import me.pasqualinosorice.voicerecognitionfile.network.AppService;
import me.pasqualinosorice.voicerecognitionfile.network.RestClient;

public class App extends Application {

    public static AppService cesService;

    @Override
    public void onCreate() {
        super.onCreate();
        RestClient restClient = new RestClient();
        cesService = restClient.getApiService();
    }
}
