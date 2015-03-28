package com.company;

/**
 * Created by louis on 3/28/15.
 */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class SingleServer {

    public static final String webRootDirectory = System.getProperty("user.dir") + File.separator + "webroot";
    private static final String shutdownCommand = "/SHUTDOWN";
    private boolean shutdown = false;

    public void await() {
        ServerSocket serverSocket = null;
        int port = 8080;
        try {
            serverSocket =  new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"))
        } catch (IOException  e) {
            e.printStackTrace();
            System.exit(1);
        }

        while(!shutdown) {
            Socket socket = null;
            InputStream input = null;
            OutputStream output = null;
            try {
                socket = serverSocket.accept();
                input = socket.getInputStream();
                output = socket.getOutputStream();

                Request request = new Request(input);
                request.parse();

                Response response = new Response(output);
                response.setRequest(request);
                response.sendStaticResource();

                socket.close();

                shutdown = request.getUri().equals(shutdownCommand);
            } catch (Exception error) {
                error.printStackTrace();
                continue;
            }
        }
    }
}
