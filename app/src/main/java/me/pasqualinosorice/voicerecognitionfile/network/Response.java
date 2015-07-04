package me.pasqualinosorice.voicerecognitionfile.network;

import java.util.List;

public class Response {
    private List<Result> result;

    public String getFirstTranscription() {
        if (result != null && result.size() > 0 && result.get(0) != null)
            return result.get(0).getAlternative().get(0).getTranscript();
        return "";
    }

    public double getConfidence() {
        if (result != null && result.size() > 0 && result.get(0) != null && result.get(0).getAlternative().get(0).getConfidence() != null) {
            return Double.parseDouble(result.get(0).getAlternative().get(0).getConfidence());
        } else {
            return 1;
        }
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
