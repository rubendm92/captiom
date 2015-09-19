package com.rubendm.captiom.mobile.infrastructure;

public class Server {

    private static final String BASE_URL = "http://192.168.1.4:4567";

    public ServerConnection connect(String url, RequestMethod method) {
        return new ServerConnection(BASE_URL + url, method.name().toUpperCase());
    }

    public enum RequestMethod {
        GET, POST
    }
}
