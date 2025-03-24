package response;

import java.util.Map;
import java.util.Map.Entry;

public class CustomHttpResponse {
    public final Map<String, String> headers;
    public final String status;
    public final String version;
    public final String body;

    public CustomHttpResponse(Map<String, String> headers, String status, String version,
                              String body) {
        this.headers = headers;
        this.status = status;
        this.version = version;
        this.body = body;
    }

    // TODO fill this out
    public String toString() {
        StringBuilder httpRespond = new StringBuilder(version + " " + status + "\n");
        for (Entry<String, String> header : headers.entrySet()){
            httpRespond.append(header.getKey()).append(": ").append(header.getValue()).append("\n");
        }

        httpRespond.append("\n").append(body);

        return httpRespond.toString();
    }
}
