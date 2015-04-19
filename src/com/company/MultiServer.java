package com.company;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by louis on 3/31/15.
 * Multi-threaded version of SingleServer
 */
public class MultiServer {

    public static final String webRootDirectory = System.getProperty("user.dir") + File.separator + "webroot/" + "html";
    public Boolean shutdown = false;

    private int queueSize;
    private int port;
    private String address;
    private CoarseList<RequestData> clientData;
    private BoundedQueue<Runnable> workQueue;


    /**
     * Constructor for the class, consider switching parameter order
     * */
    public MultiServer(int port, String address) {
        this.port = port;
        this.address = address;
        clientData = new CoarseList<>();
        queueSize = 10000;
        workQueue = new BoundedQueue<>(queueSize);
    }

    /**
     * Starts listening on a port
     * */
    public void await() {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        ServerSocket serverSocket = null;
        try {
            serverSocket =  new ServerSocket(port, 1, InetAddress.getByName(address));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        while(!shutdown) {
            Socket socket;
                try {
                    socket = serverSocket.accept();

                    //Send a thread off to do the hard work
                    Runnable transaction = new Transaction(socket, clientData, this);

//                    workQueue.enqueue(transaction);
//                    while (Thread.activeCount() >= threadLimit) {}
                    System.out.println(Thread.activeCount());
//                    Runnable worker = workQueue.dequeue();
                    executor.execute(transaction);

                } catch (Exception error) {
                    error.printStackTrace();
                } finally {
                    //Print the client data every iteration through the while loop
                    clientData.print();
                }
            }
        }
    }

