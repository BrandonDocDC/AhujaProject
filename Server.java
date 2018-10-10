// Imports
import java.io.*;
import java.net.*;

/** Project 1 - Networks and Distributed Systems
 * Dr. Ahuja
 * Brandon DeCrescenzo, Kristoffer Binek, Nahjani Rhymer
 * Server.java
*/

public class Server extends Thread {
	//Global Vars
	Socket s = null;
    
	//Thread Method
    public Server(Socket clientSocket){
        this.s = clientSocket;
    } //end constructor

	//run method
    @Override
    public void run() {
		//Announce new client connection
		System.out.println("  --  Client connected...");

		//Listen
		try {
			PrintWriter out = new PrintWriter(s.getOutputStream(),true); //keep the output open
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream())); //listen for input

			//while it is open (listening for requests)
			while(true){     
				//local vars
				String option = in.readLine();
				String send = "str";
				Process cmdProc;
				cmdProc=null;
				String cmdans=null;

				//exectute commend in linux format
				switch (option){
					case "1": 
						System.out.println("Responding to date request from the client");
						String[] cmd = {"bash", "-c", "date +%D%t%T%Z"};
						cmdProc = Runtime.getRuntime().exec(cmd);
					break;
					case "2":
						System.out.println("Responding to uptime request from the client");
						String[] cmdA = {"bash", "-c", "uptime -p"};
						cmdProc = Runtime.getRuntime().exec(cmdA);
					break;
					case "3":
						System.out.println("Responding to number of active socket connections request from the client");
						String[] cmdB = {"bash", "-c", "free -m"};
						cmdProc = Runtime.getRuntime().exec(cmdB);
					break;
					case "4":
						System.out.println("Responding to netstat request from the client");
						String[] cmdC = {"bash", "-c", "netstat -r"};
						cmdProc = Runtime.getRuntime().exec(cmdC);
					break;
					case "5":
						System.out.println("Responding to current users request from the client");
						String[] cmdD = {"bash", "-c", "users"};
						cmdProc = Runtime.getRuntime().exec(cmdD);
					break;
					case "6":
						System.out.println("Responding to current processes request from the client");
						String[] cmdE = {"bash", "-c", "ps"};
						cmdProc = Runtime.getRuntime().exec(cmdE);
					break;
					case "7":
						System.out.println("Quitting...");
						String[] cmdF = {"bash", "-c", "exit"};
						cmdProc = Runtime.getRuntime().exec(cmdF);
						s.close();
						in.close();
						out.close();
					break;
					default:
						System.out.println("Unknown request ");
					return;
				}//end switch

				//output the answer from the bash command to the Client
				BufferedReader cmdin = new BufferedReader(new InputStreamReader(cmdProc.getInputStream()));
				while((cmdans = cmdin.readLine()) != null) {
					out.println(cmdans);
					if (cmdans.equalsIgnoreCase("Bye.")) {
						out.println("Bye.");
						break;
					}//end if
				}//end while
				out.println("Bye.");
			}//end while
		}//end try

		//catch errors
		catch (IOException e){
			System.out.println("Exception caught " + e);
			System.out.println(e.getMessage());
		}// end catch
		catch (NullPointerException e) {
			System.out.println("  --  All clients disconnected. Closing Socket...");
			System.exit(1);
		}//end catch

	}//end run()

	//main method
    public static void main(String[] args) throws IOException {
		//local vars
		int portNumber = Integer.parseInt(args[0]);

		//opens the socket to listen for client.
        try {
			ServerSocket serverSocket = new ServerSocket(portNumber);
			System.out.println("\nServer started. Listening on Port " + portNumber);
			System.out.println("\nWaiting for client request");

			//Keep server open and accept multiple clients
			while(true){      
				new Server(serverSocket.accept()).start();
			}//End while
        }//End try
        catch (IOException e){
            System.out.println("\nException caught" + e);
        }//End Catch

    }//End main
}//end Server
