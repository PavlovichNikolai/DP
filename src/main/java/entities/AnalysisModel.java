package entities;

import java.util.List;

public class AnalysisModel {

    private List<List<String>> data;
    private List<String> headers;

    public AnalysisModel(List<List<String>> data, List<String> headers) {
        this.data = data;
        this.headers = headers;
    }

    public AnalysisModel() {
    }

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }
}
