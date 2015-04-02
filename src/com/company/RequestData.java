package com.company;

/**
 * Created by louis on 4/1/15.
 * Simple class used for logging data on the server.
 */
public class RequestData extends Object {
    private String IPAddress;
    private String file;

    public RequestData(String address, String file) {
        this.IPAddress = address;
        this.file = file;
    }

    public String toString() {
        return IPAddress + " " + file;
    }
}

