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

/** Project 1 - Networks and Distributed Systems
 * Dr. Ahuja
 * Brandon DeCrescenzo, Kristoffer Binek, Nahjani Rhymer
 * MultiClientThread.java
*/

public class MultiClientThread extends Thread {
    //Variables 
    Socket s = null;
    String serverResponse; 
    public static String userInput;
    public static int option;

    //Constructor
    public MultiClientThread(Socket clientSocket) {
        this.s = clientSocket;
    }//End constructor 

    @Override
    public void run() {
        //Success message 
        System.out.println("Connected to server...starting " + Thread.currentThread().getName());
        try {
            //While socket is opening & listening... 
            while (true) {
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
                Scanner s = new Scanner(System.in);
                 //Start time 
                long start_time = System.currentTimeMillis();

                //Display menu to client 
                System.out.println(
                        "1) Host Current Date and Time\n"
                        + "2) Host Uptime\n"
                        + "3) Host Memory Use\n"
                        + "4) Host Netstat\n"
                        + "5) Host Current Users\n"
                        + "6) Host Running Processes\n"
                        + "7) Quit\n"
                );

                //Ask client for selection 
                System.out.println("Select your option: ");
                option = stdIn.read();
                
                //Switch for options 
                switch (option) {
                    //Host Current Date & Time
                    case '1':
                        out.println("1");
                        System.out.println("Current Date & Time: " + in.readLine());
                        break;
                    //Host Uptime
                    case '2':
                        out.println("2");
                        System.out.println("Uptime: " + in.readLine());
                        break;
                    //Host Memory Use
                    case '3':
                        out.println("3");
                        System.out.println("Memory Use: " + in.readLine());
                        while ((serverResponse = in.readLine()) != null && !serverResponse.equals("Bye.")) {
                            System.out.println(serverResponse);
                        }
                        continue;
                    //Host Netstat
                    case '4':
                        out.println("4");
                        System.out.println("Netstat: " + in.readLine());
                        while ((serverResponse = in.readLine()) != null && !serverResponse.equals("Bye.")) {
                            System.out.println(serverResponse);
                        }
                        serverResponse = null;
                        continue;
                    //Host Current Users 
                    case '5':
                        out.println("5");
                        System.out.println("");
                        String users = in.readLine();
                        System.out.println("Current Users: " + in.readLine());
                        break;
                    //Host Running Processes 
                    case '6':
                        out.println("6");
                        System.out.println("Running Processes: " + in.readLine());
                        while ((serverResponse = in.readLine()) != null && !serverResponse.equals("Bye.")) {
                            System.out.println(serverResponse);
                        }
                        serverResponse = null;
                        continue;
                    //Quit 
                    case '7':
                        out.println("7");
                        System.out.println("Quiting..." + in.readLine());
                        return;
                    //Invalid Input 
                    default:
                        System.err.println("ERROR! Invalid input... Please type a number between 1 and 7.");
                        continue;
                }//End switch 

                while ((userInput = in.readLine()) != null && !userInput.equalsIgnoreCase("Bye.")) {
                    out.println(userInput);
                }
                
                //End timer 
                long end_time = System.currentTimeMillis();
                
                //Success message 
                System.out.println("Request is complete...\n"); 
                
                //Print reponse time 
                long responseTime = end_time - start_time; 
                System.out.println("Response time: " + responseTime + "ms\n");
            }//End while
        }//End try  
        //Catches 
        catch (IOException e) {
            System.out.println("Exception caught " + e);
            System.out.println(e.getMessage());
        }//End catch
    }//End run

}//End MultiClientThread
