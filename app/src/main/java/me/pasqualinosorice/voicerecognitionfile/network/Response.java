package me.pasqualinosorice.voicerecognitionfile.network;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Response {
    private List<Result> result = new ArrayList<>();
    private Integer resultIndex;

    public List<Result> getResult() {
        return result;
    }

    public Integer getResultIndex() {
        return resultIndex;
    }

    public String getFirstTranscription() {
        return result.get(0).getAlternative().get(0).getTranscript();
    }
}

class Result {
    private List<Alternative> alternative = new ArrayList<>();
    @SerializedName("final")
    private Boolean _final;

    public List<Alternative> getAlternative() {
        return alternative;
    }

    public Boolean getFinal() {
        return _final;
    }
}

class Alternative {
    private String transcript;
    private String confidence;

    public String getTranscript() {
        return transcript;
    }

    public String getConfidence() {
        return confidence;
    }
}
