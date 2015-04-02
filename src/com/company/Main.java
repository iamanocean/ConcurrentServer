package com.company;

public class Main {

    public static void main(String[] args) {

        /**
         * Server Instantiation
         *      Select your desired port and address to use (respectively)
         * */
        MultiServer server = new MultiServer(8080, "0.0.0.0");
        server.await();

    }
}
