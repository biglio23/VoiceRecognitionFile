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
import java.io.FileOutputStream;
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

    final String language = "it_it";

    final String api_key = "AIzaSyBgnC5fljMTmCFeilkgLsOKBvvnx6CBS0M";

    final String path = Environment.getExternalStorageDirectory() + "/VoiceRecognitionAudio/";

    int sampleRate = 44100;

    File outputFile;
    List<File> files;
    int current;

    TextView txtView;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtView = (TextView) findViewById(R.id.textView);
    }

    public void startClick(View view) {
        files = getListFiles(new File(path));
        current = 0;
        outputFile = new File(path + "results.txt");
        if (outputFile.exists() && outputFile.delete()) {
            Log.d("LINO", "Vecchio file cancellato");
        }
        getTranscription(files.get(current));
    }

    public void getTranscription(final File audioFile) {
        pd = DialogUtil.createProgressDialog(this, "Riconoscimento #" + current);
        if (!audioFile.canRead())
            Log.d("ParseStarter", "FATAL no read access");

        try {
            final FileInputStream fis = new FileInputStream(audioFile);
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
            final long startTime = System.nanoTime();
            App.cesService.getUp(in, options, new Callback<Response>() {
                @Override
                public void success(Response response, retrofit.client.Response response2) {
                    DialogUtil.removeProgressDialog(pd);
                    txtView.setText(response.getFirstTranscription());
                    long endTime = System.nanoTime();
                    long duration = (endTime - startTime) / 1000000;
                    saveResultOnFile(audioFile.getName(), audioFile.length() / 1024, response.getFirstTranscription(),
                            response.getConfidence(), duration);
                    current++;
                    if (current < files.size()) {
                        getTranscription(files.get(current));
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d("LINO", "ERROR");
                    DialogUtil.removeProgressDialog(pd);
                    txtView.setText(error.getMessage());
                    getTranscription(files.get(current));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveResultOnFile(String name, long lenght, String transcription, double confidence, long responseTime) {
        try {
            FileOutputStream outputStream = new FileOutputStream(outputFile, true);
            String line = String.format("%s\t%d\t%s\t%f\t%d\n", name, lenght, transcription, confidence, responseTime);
            outputStream.write(line.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.getName().endsWith(".flac")) {
                inFiles.add(file);
            }
        }
        return inFiles;
    }
}
