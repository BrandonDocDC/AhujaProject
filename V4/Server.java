/** Project 1 - Networks and Distributed Systems
  * Dr. Ahuja
  * Brandon DeCrescenzo, Kristoffer Binek, Nahjani Rhymer
  * Server.java
*/

//server will open a socket and accept connections. Processes each connection/request
//one at a time (iteratively).

//Imports
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

//Server Class
public class Server {
	static DataInputStream inputFromClient;
	static DataOutputStream outputToClient;
  
  //main method
	public static void main(String[] args) throws IOException {
		//Variables
		ServerSocket server;
		Socket clientConnection;
		String option;
		int portNum = Integer.parseInt(args[0]); // Preset port number
		int timeout = 15000;
		BufferedReader input;
		;

		try {
			//create socket & keep it open
			while (true) {
				server = new ServerSocket(portNum);
				
				System.out.println("======================================================");
				System.out.println("  Waiting for connection on Port " + portNum);
				System.out.println("======================================================");
				
				clientConnection = server.accept();
				clientConnection.setSoTimeout(timeout); // Timeout = 15 seconds
				
				System.out.println("======================================================");
				System.out.println("  Connection received from " + clientConnection.getInetAddress() + " : "
						+ clientConnection.getPort());
				System.out.println("======================================================");
				inputFromClient = new DataInputStream(clientConnection.getInputStream());
				outputToClient = new DataOutputStream(clientConnection.getOutputStream());

				input = new BufferedReader(new InputStreamReader(inputFromClient));
				option = input.readLine();
				//selection(option.trim());

				// Close streams
				outputToClient.close();
				inputFromClient.close();
				server.close();
				clientConnection.close();
        
        //quits if option 7 is selected.
				if(option.equals("7")){
					System.exit(0);
				}
				server.close();
			}
		} catch (Exception e) {
			System.out.println("======================================================");
			System.out.println("Error: Cannot open socket.");
			e.printStackTrace();
			System.out.println("======================================================");
			System.exit(-1);
		}
		
	}
	//respond to each client request
	public static void selection(String option) {
		Process process;
		String output;
		BufferedReader br;
		try {
    
      //switch case for requests/commands/reponses
			switch (option) {
			case "1":
				System.out.println("======================================================");
				System.out.println("  Responding to date request from the client");
				process = Runtime.getRuntime().exec("date +%D%t%T%Z");
				br = new BufferedReader(new InputStreamReader(process.getInputStream()));
				while ((output = br.readLine()) != null)
					outputToClient.writeChars(output);
			break;
			case "2":
				System.out.println("======================================================");
				System.out.println("  Responding to uptime request from the client");
				process = Runtime.getRuntime().exec("uptime -p");
				br = new BufferedReader(new InputStreamReader(process.getInputStream()));
				while ((output = br.readLine()) != null)
					outputToClient.writeChars(output);
			break;
			case "3":
				System.out.println("======================================================");
				System.out.println("  Responding to number of active socket connections request from the client");
				process = Runtime.getRuntime().exec("free -h");
				br = new BufferedReader(new InputStreamReader(process.getInputStream()));

				while ((output = br.readLine()) != null)
					outputToClient.writeChars(output);
			break;
			case "4":
				System.out.println("======================================================");
				System.out.println("  Responding to netstat request from the client");
				process = Runtime.getRuntime().exec("netstat");
				br = new BufferedReader(new InputStreamReader(process.getInputStream()));

				while ((output = br.readLine()) != null)
					outputToClient.writeChars(output);
			break;
			case "5":
				System.out.println("======================================================");
				System.out.println("  Responding to current users request from the client");
				process = Runtime.getRuntime().exec("who");
				br = new BufferedReader(new InputStreamReader(process.getInputStream()));

				while ((output = br.readLine()) != null)
					outputToClient.writeChars(output);
			break;
			case "6":
				System.out.println("======================================================");
				System.out.println("  Responding to current processes request from the client");
				process = Runtime.getRuntime().exec("ps -e");
				br = new BufferedReader(new InputStreamReader(process.getInputStream()));

				while ((output = br.readLine()) != null)
					outputToClient.writeChars(output);
			break;
			case "7":
				System.out.println("======================================================");
				System.out.println("  Client Quitting...");
				System.out.println("======================================================");
				outputToClient.writeChars("Closing socket...");
			break;
			default:
				System.out.println("======================================================");
				System.out.println("Received input other than 1-7");
				System.out.println("======================================================");
			break;
			}
		} catch (Exception e) {
			System.out.println("======================================================");
			System.out.println("Closing due to error:");
			e.printStackTrace();
			System.out.println("======================================================");
			System.exit(-1);
		}
	}
}
