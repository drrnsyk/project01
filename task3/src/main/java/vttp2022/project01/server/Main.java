package vttp2022.project01.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException{

        // Create a thread pool
        ExecutorService threadPool = Executors.newFixedThreadPool(2);

        // create a server socket and listen to an open port
        ServerSocket server = new ServerSocket(Integer.parseInt(args[1]));
        System.out.println("Starting shopping cart server on port " + args[1] + ", listening...");
        
        while (true) {
            // accept incoming connection from client
            Socket sock = server.accept();
            MainThread thr = new MainThread(sock, args[0]);
            threadPool.submit(thr);
            System.out.println("Submitted to threadpool");
        }
    }        
}
