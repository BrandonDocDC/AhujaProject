package multiserver;

//Imports
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/* Project 2 - Networks and Distributed Systems
/* Dr. Ahuja
/* Brandon DeCrescenzo, Kristoffer Binek, Nahjani Rhymer
*/

// main server file. to compile: "javac multiserver.java"; to run: "java multiserver <port>"
public class MultiServer {
    
    public static void main(String[] args) throws IOException {
        //Variables 
        int portNumber = Integer.parseInt(args[0]);
        //used in project 2 for ThreadedServer
        int numThreads = Integer.parseInt(args[1]);
        ServerThread[] clientThreads = new ServerThread[numThreads];
        
        //ERROR: If hostName or portNumber is wrong
        if (args.length < 1) {
            System.err.println("Usage: java multiserver <port number>");
            System.exit(1);
        }
        
        //If port number is correct, proceed...
        try {
            //Create a server
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Server started... Listening on port " + portNumber);
            System.out.println("Waiting for clients...");

			
            // this is project 2, threaded server responses
            //Create clients threads in the background
            for(int i = 0; i > numThreads; i++)
            {
                clientThreads[i] = new ServerThread(serverSocket.accept());
            }
            for(int i = 0; i > numThreads; i++)
            {
                clientThreads[i].start();
            }

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
public class ServerThread extends Thread {

    //Variables 
    Socket s = null;

    //Constructor 
    public ServerThread(Socket clientSocket) {
        this.s = clientSocket;
    }//End constructor

    @Override
    public void run() {
        System.out.println("Client connected...starting " + Thread.currentThread().getName());

        try {
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

            while (true) {
                //Variables 
                String option = in.readLine();
                String send = "str";
                Process cmdProc;
                cmdProc = null;

                //Checks for unrecognized options   
                if (option.equalsIgnoreCase("/*!@#$%^&*()\"{}_[]|\\?/<>,.")) {
                    System.err.println("Unrecognized option...please try again");
                    return;
                }//End if

                //Execute the appropriate option
                switch (option) {
                    //Current Date & Time 
                    case "1":
                        System.out.println("Responding to current time & date request from the client...");
                        String[] cmd = {"bash", "-c", "date +%D%t%T%Z"};
                        cmdProc = Runtime.getRuntime().exec(cmd);
                        break;
                    //Uptime 
                    case "2":
                        System.out.println("Responding to uptime request from the client...");
                        String[] cmdA = {"bash", "-c", "uptime -p"};
                        cmdProc = Runtime.getRuntime().exec(cmdA);
                        break;
                    //Memory Use 
                    case "3":
                        System.out.println("Responding to memory use request from the client...");
                        String[] cmdB = {"bash", "-c", "free -m"};
                        cmdProc = Runtime.getRuntime().exec(cmdB);
                        break;
                    //Netstat 
                    case "4":
                        System.out.println("Responding to netstat request from the client...");
                        String[] cmdC = {"bash", "-c", "netstat -r"};
                        cmdProc = Runtime.getRuntime().exec(cmdC);
                        break;
                    //Current Users 
                    case "5":
                        System.out.println("Responding to current users request from the client...");
                        String[] cmdD = {"bash", "-c", "users"};
                        cmdProc = Runtime.getRuntime().exec(cmdD);
                        break;
                    //Running Processes 
                    case "6":
                        System.out.println("Responding to running processes request from the client...");
                        String[] cmdE = {"bash", "-c", "ps"};
                        cmdProc = Runtime.getRuntime().exec(cmdE);
                        break;
                    //Quit 
                    case "7":
                        System.out.println("Quitting...");
                        String[] cmdF = {"bash", "-c", "exit"};
                        cmdProc = Runtime.getRuntime().exec(cmdF);
                        in.close();
                        out.close();
                        break;
                    default:
                        System.out.println("Unknown request...");
                        return;
                }//End switch 
                //Print option 
                BufferedReader cmdin = new BufferedReader(new InputStreamReader(cmdProc.getInputStream()));
                String cmdans;
                while ((cmdans = cmdin.readLine()) != null) {
                    out.println(cmdans);
                }
                out.println("Server done...");
            }//End while
        }//End try
        //Catches 
        catch (IOException e) {
            System.out.println("Exception caught " + e);
            System.out.println(e.getMessage());
        }//End catch
    }//End run()
} //end ServerThread
