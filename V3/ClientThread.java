/** Project 1 - Networks and Distributed Systems
  * Dr. Ahuja
  * Brandon DeCrescenzo, Kristoffer Binek, Nahjani Rhymer
  * ClientThreaded.java
*/

//Imports
import java.io.*;
import java.net.*;
import java.util.*;

/** This is the threaded Load Test Client. You will select the number for the request &
 *  how many threads you would like. The client will create the threads, connect the
 *  threads and send the request. Then server will respond to the requests (unthreaded 
 *  will respond iteratively, project 2 concurrently). It will send the response back
 *  then the client will process the times for each one.
 *
 *  (starts time) (Client: builds the array, sends it) (Server: receives the array, processes it, sends array back
 *  (Client: receives it, processes the array)(ends time)
 */

public class ClientThreaded extends Thread {
	//Global Vars
	public String hostName;
	public int port, option;
	public ClientThreaded(String host, int portNumber, int menuItem) {
		hostName = host;
		port = portNumber;
		option = menuItem;
	}// end ClientThreaded constructor
	
	public void run() {
		//Vars
		double timeStart;
		double timeEnd;
		Socket clientSocket;
		int timeOut = 15000;
		
		try {
			//start timers
			timeStart = System.currentTimeMillis();
			//open sockets
			clientSocket = new Socket(hostName, port);
			clientSocket.setSoTimeout(timeOut);
			// Output from server
			BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			//user input
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			boolean validInput = false;
			
			//
			switch(option)
			{
				case 1:
					System.out.println("Date Request from Client");
					out.println("1");
					validInput =true;
					break;
		    	case 2:
		    		System.out.println("Uptime Request from Client");
					out.println("2");
			   		validInput =true;
			   		break;
		    	case 3:
			   		System.out.println("Memory Use Request from Client");
			   		out.println("3");
			   		validInput =true;
			   		break;
		    	case 4:
			   		System.out.println("IPV4 Socket Connections Request from Client");
			   		out.println("4");
			   		validInput =true;
			   		break;
		    	case 5:
			   		System.out.println("Current Users Request from Client");
			   		out.println("5");
			   		validInput =true;
			   		break;
		    	case 6:
			   		System.out.println("Current OS Version Request from Client");
			   		out.println("6");
			   		validInput =true;
			   		break;
		    	case 7:
			   		System.out.println("Quit");
			   		out.println("7");
			   		System.exit(5);
			   		break;
		    	default:
			   		System.out.println("Invalid Input");
			   		validInput =false;
			   		break;
			}//end switch
			if (validInput)
	    	{
       			String serverResponse;
   				while((serverResponse = input.readLine()) != null && !serverResponse.equals("EndResponse"))
   				{
					// Get answer from server and print.
					System.out.println(serverResponse);
   				
   				}// end while answer loop
	    	}
			//end timer after response from server
			timeEnd = System.currentTimeMillis();
			clientSocket.close();
			//close socket and display the time for the request.
			System.out.println("Response time = " + (timeEnd - timeStart));
			out.close();
		}// end try
		catch (Exception e) {
			System.out.println("Can not open socket at " + hostName + ":" + port);
			e.printStackTrace();
			System.exit(-1);
		}
	}// end run method
}// end ClientThreaded class
