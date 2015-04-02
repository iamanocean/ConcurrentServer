package com.company;

import com.sun.org.apache.xpath.internal.operations.Mult;

public class Main {

    public static void main(String[] args) {

        /*
        System.out.println("Welcome to the server!");
        System.out.println("Before we start, make sure this directory exists");
        System.out.println(SingleServer.webRootDirectory);
        System.out.println("");
        System.out.println("");
        System.out.println("After you've verified that, place a sample html file in the directory");
        System.out.println("");
        System.out.println("Open up chrome, and go to 127.0.0.1:8080/nameOfYourFileGoesHere");
        System.out.println("Uncomment lines 20 and 21, and restart the server");
        System.out.println("On the bottom left hand corner of the Intellij terminal there's a block with an arrow above it that restarts a program");
        */


//        SingleServer server = new SingleServer();
//        server.await();

        MultiServer server = new MultiServer(8080, "0.0.0.0");
        server.await();
    }
}
