package me.rubendm.captiom.mobile.infrastructure;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ServerConnection {

    private final String url;
    private final String methodName;
    private final Map<String, String> parameters = new HashMap<>();

    ServerConnection(String url, String methodName) {
        this.url = url;
        this.methodName = methodName;
    }

    public ServerConnection addParameter(String name, String value) {
        parameters.put(name, value);
        return this;
    }

    public ServerResponse send() {
        try {
            return sendRequest(url, methodName);
        } catch (IOException e) {
            return new ServerResponse(e.getMessage(), false);
        }
    }

    private ServerResponse sendRequest(String url, String method) throws IOException {
        HttpURLConnection connection = createConnection(url, method);
        addQueryParams(connection.getOutputStream());
        return readResponse(connection);
    }

    private HttpURLConnection createConnection(String url, String method) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.setRequestMethod(method);
        return connection;
    }

    private void addQueryParams(OutputStream stream) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream, "UTF-8"));
        writer.write(queryParams());
        writer.flush();
        writer.close();
        stream.close();
    }

    private String queryParams() throws UnsupportedEncodingException {
        StringBuilder query = new StringBuilder();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            query.append(utf8Encode(entry.getKey()))
                    .append("=")
                    .append(utf8Encode(entry.getValue()))
                    .append("&");
        }
        return query.substring(0, query.length() - 1);
    }

    private String utf8Encode(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, "UTF-8");
    }

    private ServerResponse readResponse(HttpURLConnection connection) throws IOException {
        String responseMessage = readResponseStream(connection.getInputStream());
        int responseCode = connection.getResponseCode();
        connection.disconnect();
        return new ServerResponse(responseMessage, responseCode >= 200 && responseCode < 300);
    }

    private String readResponseStream(InputStream stream) throws IOException {
        Scanner s = new Scanner(stream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public class ServerResponse {

        private final String body;
        private final boolean succeeded;

        public ServerResponse(String body, boolean succeeded) {
            this.body = body;
            this.succeeded = succeeded;
        }

        public String body() {
            return body;
        }

        public boolean succeeded() {
            return succeeded;
        }
    }
}
