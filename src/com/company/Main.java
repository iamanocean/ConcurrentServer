package com.company;

public class Main {

    public static void main(String[] args) {

        /**
         * Server Instantiation
         *      Select your desired port and address to use (respectively)
         * */

        SingleServer noLoggingSingleServer = new SingleServer(8080, "0.0.0.0", false);
        SingleServer loggingSingleServer = new SingleServer(8080, "0.0.0.0", true);
        MultiServer noLoggingMultiServer = new MultiServer(8080, "0.0.0.0", 8, false);
        MultiServer loggingMultiServer = new MultiServer(8080, "0.0.0.0", 8, true);

        //noLoggingSingleServer.await();
        //loggingSingleServer.await();
        //noLoggingMultiServer.await();
        //loggingMultiServer.await();
    }
}
