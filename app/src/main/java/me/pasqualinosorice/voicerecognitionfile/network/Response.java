package me.pasqualinosorice.voicerecognitionfile.network;

import java.util.List;

public class Response {
    private List<Result> result;

    public String getFirstTranscription() {
        return result.get(0).getAlternative().get(0).getTranscript();
    }

    public double getConfidence() {
        return Double.parseDouble(result.get(0).getAlternative().get(0).getConfidence());
    }
}

class Result {
    private List<Alternative> alternative;

    public List<Alternative> getAlternative() {
        return alternative;
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
