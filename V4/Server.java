/** Project 1 - Networks and Distributed Systems
  * Dr. Ahuja
  * Brandon DeCrescenzo, Kristoffer Binek, Nahjani Rhymer
  * ClientThread.java
*/

//Imports
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	static DataInputStream inputFromClient;
	static DataOutputStream outputToClient;
	public static void main(String[] args) throws IOException {
		

	
		ServerSocket server;
		
		Socket clientConnection;
		
		String menuSelection;
		int portNum = args[0]; // Preset port number
		int timeout = 15000;
		BufferedReader input;
		;

		try {
			// Step 1: Create Server Socket
			

			while (true) {
				server = new ServerSocket(portNum);
				
				System.out.print("Waiting for connection");
				clientConnection = server.accept();
				clientConnection.setSoTimeout(timeout); // Timeout = 15 seconds
				
				System.out.println("Connection received from " + clientConnection.getInetAddress() + ":"
						+ clientConnection.getPort());
				inputFromClient = new DataInputStream(clientConnection.getInputStream());
				outputToClient = new DataOutputStream(clientConnection.getOutputStream());

				input = new BufferedReader(new InputStreamReader(inputFromClient));
				menuSelection = input.readLine();
				System.out.println(menuSelection);
				selection(menuSelection.trim());

				// Close streams
				outputToClient.close();
				inputFromClient.close();
				server.close();
				clientConnection.close();
				if(menuSelection.equals("Request 7")){
					System.exit(0);
				}
				server.close();
			}
		} catch (Exception e) {
			System.out.println("Can't open socket.");
			e.printStackTrace();
			System.exit(-1);
		}
		
	}

	public static void selection(String menuSelect) {
		Process process;
		String output;
		BufferedReader br;
		try {
			switch (menuSelect) {

			case "Request 1":
				process = Runtime.getRuntime().exec("date");
				br = new BufferedReader(new InputStreamReader(process.getInputStream()));
				while ((output = br.readLine()) != null)
					outputToClient.writeChars(output);
				break;
			case "Request 2":
				process = Runtime.getRuntime().exec("uptime");
				br = new BufferedReader(new InputStreamReader(process.getInputStream()));
				while ((output = br.readLine()) != null)
					outputToClient.writeChars(output);
				break;
			case "Request 3":
				process = Runtime.getRuntime().exec("free");
				br = new BufferedReader(new InputStreamReader(process.getInputStream()));

				while ((output = br.readLine()) != null)
					outputToClient.writeChars(output);
				break;
			case "Request 4":
				process = Runtime.getRuntime().exec("netstat");
				br = new BufferedReader(new InputStreamReader(process.getInputStream()));

				while ((output = br.readLine()) != null)
					outputToClient.writeChars(output);
				break;
			case "Request 5":
				process = Runtime.getRuntime().exec("who");
				br = new BufferedReader(new InputStreamReader(process.getInputStream()));

				while ((output = br.readLine()) != null)
					outputToClient.writeChars(output);
				break;
			case "Request 6":
				process = Runtime.getRuntime().exec("ps -e");
				br = new BufferedReader(new InputStreamReader(process.getInputStream()));

				while ((output = br.readLine()) != null)
					outputToClient.writeChars(output);
				break;
			case "Request 7":
				System.out.println("Quit option chosen.");
				outputToClient.writeChars("Closing socket...");
				break;
			default:
				System.out.println("Please enter a valid selection.");
				break;
			}
		} catch (Exception e) {
			System.out.println("There was an exception.  Exiting...");
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
