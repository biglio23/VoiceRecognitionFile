package me.pasqualinosorice.voicerecognitionfile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.pasqualinosorice.voicerecognitionfile.network.Response;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

public class MainActivity extends Activity {

    String language = "it_it";

    String api_key = "AIzaSyBgnC5fljMTmCFeilkgLsOKBvvnx6CBS0M";

    String path = Environment.getExternalStorageDirectory() + "/VoiceRecognitionAudio/";

    int sampleRate = 44100;

    TextView txtView;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtView = (TextView) findViewById(R.id.textView);
    }

    public void startClick(View view) {
        pd = DialogUtil.createProgressDialog(this, "CARICAMENTO");
        //getTranscription();
        List<File> files = getListFiles(new File(path));
        for (File f : files) {
            Log.d("LINO", f.getName());
        }
    }

    public void getTranscription(String filename) {

        File myfil = new File(filename);
        if (!myfil.canRead())
            Log.d("ParseStarter", "FATAL no read access");

        try {
            FileInputStream fis = new FileInputStream(myfil);
            FileChannel fc = fis.getChannel();
            int sz = (int) fc.size();
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, sz);
            byte[] data2 = new byte[bb.remaining()];
            bb.get(data2);

            Map<String, String> options = new HashMap<>();
            options.put("lang", language);
            options.put("key", api_key);
            options.put("output", "json");

            TypedInput in = new TypedByteArray("audio/x-flac; rate=" + sampleRate, data2);

            App.cesService.getUp(in, options, new Callback<Response>() {
                @Override
                public void success(Response response, retrofit.client.Response response2) {
                    DialogUtil.removeProgressDialog(pd);
                    txtView.setText(response.getFirstTranscription());
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d("LINO", "ERROR");
                    txtView.setText("ERRORE");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getListFiles(file));
            } else {
                if (file.getName().endsWith(".flac")) {
                    inFiles.add(file);
                }
            }
        }
        return inFiles;
    }
}
