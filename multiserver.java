package multiserver;

//Imports 
import java.io.*;
import java.net.*;

/* Project 1 - Networks and Distributed Systems
/* Dr. Ahuja
/* Brandon DeCrescenzo, Kristoffer Binek, Nahjani Rhymer
*/

public class MultiServer {

    public static void main(String[] args) throws IOException {
        //Variables 
        int portNumber = Integer.parseInt(args[0]);
        //used in project 2 for ThreadedServer
        //int numThreads = Integer.parseInt(args[1]);
        //MultiThread[] clientThreads = new MultiThread[numThreads];

        //ERROR: If hostName or portNumber is wrong
        if (args.length < 1) {
            System.err.println("Usage: java MultiServer <port number>");
            System.exit(1);
        }
        
        //If port number is correct, proceed...
        try {
            //Create a server 
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Server started... Listening on port " + portNumber);
            System.out.println("Waiting for clients...");
            
            //Create clients threads in the background
            //for(int i = 0; i > numThreads; i++)
            //{
                clientThreads[i] = new MultiThread(serverSocket.accept());
            //}
            //for(int i = 0; i > numThreads; i++)
            //{
                clientThreads[i].start();
            //}

            //Keep server open to accept multiple clients 
            while (true) {
                //Create a thread 
                new MultiThread(serverSocket.accept()).start();
            }//End while 
            
        }//End try 
        
        //End catches 
        catch (IOException e) {
            System.out.println("Exception caught..." + e);
        }//End catch 

    }//End main 
}//End MultiSever 
