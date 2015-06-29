package me.pasqualinosorice.voicerecognitionfile.network;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Scanner;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

public class GoogleConverter implements Converter {

    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        String json = "";
        try {
            Scanner inStream = new Scanner(body.in());
            while (inStream.hasNextLine()) {
                json = inStream.nextLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        return gson.fromJson(json, Response.class);
    }

    @Override
    public TypedOutput toBody(Object object) {
        return null;
    }
}
