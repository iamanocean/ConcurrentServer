package com.company;

import com.sun.org.apache.xpath.internal.operations.Mult;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by louis on 4/1/15.
 * Transaction class for encapsulating the work done by threads
 */
public class Transaction implements Runnable {

    private static final String shutdownCommand = "/SHUTDOWN";

    public Request request;
    public Response response;
    public Socket socket;
    private CoarseList<RequestData> clientData;
    public MultiServer server;


    /**
     * Constructor for the Transaction class, takes a reference to both the socket and list of client data.
     * */
    public Transaction(Socket socket, CoarseList<RequestData> clientData, MultiServer server) throws IOException {
        this.socket = socket;
        this.request = new Request(socket.getInputStream());
        this.response = new Response(socket.getOutputStream());
        this.clientData = clientData;
        this.server = server;
    }

    public void run() {
        request.parse();
        response.setRequest(request);

        if(server.logging) {
            RequestData temporaryInformation;
            temporaryInformation = new RequestData(socket.getInetAddress().toString(), request.getURI());
            clientData.add(temporaryInformation);
        }

//        server.shutdown = request.getURI().equals(shutdownCommand);

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
