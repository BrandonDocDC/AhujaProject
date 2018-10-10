package multiclient;

//Imports
import java.io.BufferedReader;
import java.util.InputMismatchException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**Project 1 - Networks and Distributed Systems
 * Dr. Ahuja
 * Brandon DeCrescenzo, Kristoffer Binek, Nahjani Rhymer
 * MultiClient.java
*/

public class MultiClient {
	// Global Vars
    public static String userInput;
    public static int times, option;
	
    public static void main(String[] args) throws IOException, InterruptedException {
        //Variables 
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        int numClients = Integer.parseInt(args[2]);
        MultiClientThread[] clientThread = new MultiClientThread[numClients]; 

        //ERROR: If hostName, portNumber, or numClients is wrong 
        if (args.length != 2) {
            System.err.println("Usage: java Client <host name> <port number> <number of clients>");
            System.exit(1);
        }

        //If hostName, portNumber, and numClients is correct, proceed...
        try {
            //Create socket 
            Socket clientSocket = new Socket(hostName, portNumber);
            System.out.println("Client started... Listening on port " + portNumber);
            System.out.println("Waiting for clients...");

            //While socket is opening & listening...
            while (true) {
                new MultiClientThread(clientSocket).start(); 
                //Create threads 
                for(int i = 0; i <numClients; i++){
                    clientThread[i] = new MultiClientThread(clientSocket);                
                }
                //Start threads 
                for(int i = 0; i < numClients; i++){
                    clientThread[i].start(); 
                }
                //Join threads
                for (int i = 0; i < numClients; i++){
                    clientThread[i].join(); 
                }
            }//End while loop
        }//End try
        
        //Catch errors 
        //If input for menu is not in number format 
        catch (NumberFormatException e) {
            System.out.print("Please enter a valid option...\n");
            System.exit(1);
        }//End catch 
        //If hostName is incorrect
        catch (UnknownHostException e) {
            System.err.println("Unable to find " + hostName + " ...");
            System.exit(1);
        }//End catch
        //If server cannot connect to host 
        catch (IOException e) {
            System.err.println("Couldn't connection to " + hostName + " ...");
            System.exit(1);
        }//End catch
        //If input is incorrect
        catch (InputMismatchException e) {
            System.err.println("Unrecognized input: " + e + " Please enter a valid option...");
        }//End catch

    }//End main

}//End MultiClient
