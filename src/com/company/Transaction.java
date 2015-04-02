package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by louis on 4/1/15.
 *
 */
public class Transaction implements Runnable {

    public Request request;
    public Response response;
    public Socket socket;


    public Transaction(InputStream input, OutputStream output, Socket socket) {
        this.request = new Request(input);
        this.response = new Response(output);
        this.socket = socket;
    }

    public void run() {
        request.parse();
        response.setRequest(request);
        try {
            response.sendStaticResource();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
