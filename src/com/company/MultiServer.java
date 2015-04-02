package com.company;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by louis on 3/31/15.
 * Creates a multi-threaded version of Single Server
 */
public class MultiServer {

    public static final String webRootDirectory = System.getProperty("user.dir") + File.separator + "webroot/" + "html";
    private static final String shutdownCommand = "/SHUTDOWN";
    private boolean shutdown = false;
    private int port;
    private String address;


    public MultiServer(int port, String address) {
        this.port = port;
        this.address = address;
    }


    public void await() {
        ServerSocket serverSocket = null;
        try {
            serverSocket =  new ServerSocket(port, 1, InetAddress.getByName(address));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        while(!shutdown) {
            Socket socket;
            InputStream input;
            OutputStream output;
                try {
                    socket = serverSocket.accept();
                    input = socket.getInputStream();
                    output = socket.getOutputStream();

                    String clientAddress = serverSocket.getInetAddress().toString();
                    System.out.println(clientAddress);

                    Transaction transaction = new Transaction(input, output, socket);
                    Thread thread = new Thread(transaction);

                    thread.start();

//                    shutdown = request.getURI().equals(shutdownCommand);
                } catch (Exception error) {
                    error.printStackTrace();
                }
            }
        }
    }

