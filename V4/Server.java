/** Project 1 - Networks and Distributed Systems
 * Dr. Ahuja
 * Brandon DeCrescenzo, Kristoffer Binek, Nahjani Rhymer
 * Server.java
*/

//Imports
import java.io.*;
import java.net.*;

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
		System.out.println("======================================================");
		//Announce new client connection
		System.out.println("  Client connected...");
		System.out.println("======================================================");

		//Listen
		try {
			PrintWriter out = new PrintWriter(s.getOutputStream(),true); //keep the output open
			//out.println("Success");
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream())); //listen for input
			//send success to client that connected

			//while it is open (listening for requests)
			while(true){     
				//local vars
				String option = in.readLine();
				String send = "str";
				Process cmdProc;
				cmdProc = null;
				String cmdans = null;

				//exectute commend in linux format
				switch (option){
					case "1": 
						System.out.println("======================================================");
						System.out.println("  Responding to date request from the client");
						String[] cmd = {"bash", "-c", "date +%D%t%T%Z"};
						cmdProc = Runtime.getRuntime().exec(cmd);
					break;
					case "2":
						System.out.println("======================================================");
						System.out.println("  Responding to uptime request from the client");
						String[] cmdA = {"bash", "-c", "uptime -p"}; // only returns the uptime, without other stuff
						//String[] cmdA = {"bash", "-c", "uptime"};
						cmdProc = Runtime.getRuntime().exec(cmdA);
					break;
					case "3":
						System.out.println("======================================================");
						System.out.println("  Responding to number of active socket connections request from the client");
						String[] cmdB = {"bash", "-c", "free -h"}; // returns formatted bytes
						//String[] cmdB = {"bash", "-c", "free"}; // returns in killabytes
						cmdProc = Runtime.getRuntime().exec(cmdB);
					break;
					case "4":
						System.out.println("  Responding to netstat request from the client");
						String[] cmdC = {"bash", "-c", "netstat"};
						cmdProc = Runtime.getRuntime().exec(cmdC);
					break;
					case "5":
						System.out.println("======================================================");
						System.out.println("  Responding to current users request from the client");
						String[] cmdD = {"bash", "-c", "users"};
						cmdProc = Runtime.getRuntime().exec(cmdD);
					break;
					case "6":
						System.out.println("======================================================");
						System.out.println("  Responding to current processes request from the client");
						String[] cmdE = {"bash", "-c", "ps"};
						cmdProc = Runtime.getRuntime().exec(cmdE);
					break;
					case "7":
						System.out.println("======================================================");
						System.out.println("  Client Quitting...");
						System.out.println("  All clients disconnected. Closing Socket...");
						System.out.println("======================================================");
						String[] cmdF = {"bash", "-c", "exit"};
						cmdProc = Runtime.getRuntime().exec(cmdF);
						s.close();
						in.close();
						out.close();
						System.exit(0);
					break;
					default:
						System.out.println("Received input other than 1 - 7.");
						out.println("EndResponse");
					return;
				}//end switch

				//output the answer from the bash command to the Client
				BufferedReader cmdin = new BufferedReader(new InputStreamReader(cmdProc.getInputStream()));
				while((cmdans = cmdin.readLine()) != null) {
					out.println(cmdans);
					System.out.println(cmdans);
					if (cmdans.equalsIgnoreCase("EndResponse")) {
						out.println("EndResponse");
						break;
					}//end if
				}//end while
				System.out.println("======================================================");
				out.println("EndResponse");
			}//end while
		}//end try

		//catch errors
		catch (IOException e){
			System.out.println("======================================================");
			System.out.println("  Quit command from client. Closing Sockets and quitting.");
			System.out.println(e.getMessage());
			System.out.println("======================================================");
		}// end catch
		catch (NullPointerException e) {
			System.out.println("======================================================");
			System.out.println("  All clients disconnected. Closing Socket...");
			System.out.println("======================================================");
		}//end catch

	}//end run()

	//main method
    public static void main(String[] args) throws IOException {
		//local vars
		int portNumber = Integer.parseInt(args[0]);

		//opens the socket to listen for client.
        try {
			//keep the socket open
			while (true) {
			ServerSocket serverSocket = new ServerSocket(portNumber);
			System.out.println("======================================================");
			System.out.println("  Server started. Listening on Port " + portNumber);
			System.out.println("  Waiting for client request");
			System.out.println("======================================================");
			//Keep server open and accept multiple clients
			while(true){      
				new Server(serverSocket.accept()).start();
			}//End while accept
			}//end while socket
        }//End try
        catch (IOException e){
			System.out.println("======================================================");
            System.out.println("  Quit command from client. Closing Sockets and quitting.");
			System.out.println("======================================================");
        }//End Catch

    }//End main
}//end Server
