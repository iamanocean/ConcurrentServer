package com.company;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;

import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by louis on 3/28/15.
 *
 */
public class Response {

    private static final int bufferSize = 1024;
    Request request;
    OutputStream output;

    public Response(OutputStream output) {
        this.output = output;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource() throws IOException {
        byte[] bytes = new byte[bufferSize];
        FileInputStream fileInputStream = null;
        File file;
        try {
            File tempFile = new File(SingleServer.webRootDirectory, request.getURI());
            if (tempFile.isDirectory()) {
                file = new File(SingleServer.webRootDirectory, request.getURI() + "/index.html");
            } else {
                file = new File(SingleServer.webRootDirectory, request.getURI());
            }
            if (file.exists()) {
                fileInputStream = new FileInputStream(file);
                int character = fileInputStream.read(bytes, 0, bufferSize);
                while (character != -1) {
                    output.write(bytes, 0, character);
                    character = fileInputStream.read(bytes, 0, bufferSize);
                }
            } else {
                String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: 23\r\n" +
                        "\r\n" +
                        "<h1>File Not Found </h1>";
                output.write(errorMessage.getBytes());
            }
        } catch (Exception error) {
            System.out.println(error.toString());
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
    }
}
