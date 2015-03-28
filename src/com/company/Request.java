package com.company;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by louis on 3/28/15.
 *
 */
public class Request {

    private InputStream input;
    private String URI;
    public Request(InputStream input) {
        this.input = input;
    }

    public void parse() {
        StringBuffer request = new StringBuffer(2048);
        int i;
        byte[] buffer = new byte[2048];
        try {
            i = input.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            i = -1;
        }
        for (int j = 0; j < i; j++) {
            request.append((char) buffer[j]);
        }
        System.out.print(request.toString());
        URI = parseUri(request.toString());
    }

    private String parseUri(String requestString) {
        int firstIndex;
        int secondIndex;
        firstIndex = requestString.indexOf(' ');
        if (firstIndex != -1) {
            secondIndex = requestString.indexOf(' ', firstIndex + 1);
            if (secondIndex > firstIndex) {
                return requestString.substring(firstIndex + 1, secondIndex);
            }
        }
        return null;
    }

    public String getURI() {
        return URI;
    }
}
