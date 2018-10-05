package server;

//Imports
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/* Project 1 - Networks and Distributed Systems
/* Dr. Ahuja
/* Brandon DeCrescenzo, Kristoffer Binek, Nahjani Rhymer
*/

// main server file. to compile: "javac server.java"; to run: "java server <port>"
public class Server {
    
    public static void main(String[] args) throws IOException {
        //Variables 
        int portNumber = Integer.parseInt(args[0]);
        //used in project 2 for ThreadedServer
        //int numThreads = Integer.parseInt(args[1]);
        //MultiThread[] clientThreads = new MultiThread[numThreads];
        
        //ERROR: If hostName or portNumber is wrong
        if (args.length < 1) {
            System.err.println("Usage: java server <port number>");
            System.exit(1);
        }
        
        //If port number is correct, proceed...
        try {
            //Create a server
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Server started... Listening on port " + portNumber);
            System.out.println("Waiting for clients...");
            
            // this is project 1, unthreaded server responses
            client[i] = new ServerThread(serverSocket.accept());
            client[i].start();
			
            // this is project 2, threaded server responses
            //Create clients threads in the background
            //for(int i = 0; i > numThreads; i++)
            //{
            //    clientThreads[i] = new MultiThread(serverSocket.accept());
            //}
            //for(int i = 0; i > numThreads; i++)
            //{
            //    clientThreads[i].start();
            //}

            //Keep server open to accept multiple clients
            while (true) {
                //Create a thread
                new ServerThread(serverSocket.accept()).start();
            }//End while
            
        }//End try
        
        //End catches
        catch (IOException e) {
            System.out.println("Exception caught..." + e);
        }//End catch
        
    }//End main
}//End Sever
