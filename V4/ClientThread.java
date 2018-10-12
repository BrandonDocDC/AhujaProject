/** Project 1 - Networks and Distributed Systems
  * Dr. Ahuja
  * Brandon DeCrescenzo, Kristoffer Binek, Nahjani Rhymer
  * ClientThread.java
*/

//Imports
import java.io.*;
import java.net.*;
import java.util.*;

// This is where you will find run() - timers, case switch, sending/receiving requests
public class ClientThread extends Thread {
	public String hostName;
	public int port;
	public int menuSelected;
	public ClientThread(String host, int portNumber, int menuItem) {
		hostName = host;
		port = portNumber;
		menuSelected = menuItem;
	}// end ClientThread constructor
	
	public void run() {
		double timeStart;
		double timeEnd;
		Socket clientSocket;
		int timeOut = 15000;
		
		try {
			timeStart = System.currentTimeMillis();
			clientSocket = new Socket(hostName, port);
			clientSocket.setSoTimeout(timeOut);
			// Output from server
			BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			boolean validInput = false;
			
			
			switch(menuSelected)
			{
				case 1:
					System.out.println("Date & Time Request from Client");
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
			   		System.out.println("NETSTAT Request from Client");
			   		out.println("4");
			   		validInput =true;
			   		break;
		    	case 5:
			   		System.out.println("Current Users Request from Client");
			   		out.println("5");
			   		validInput =true;
			   		break;
		    	case 6:
			   		System.out.println("Processes Request from Client");
			   		out.println("6");
			   		validInput =true;
			   		break;
		    	case 7:
			   		System.out.println("Quit");
			   		out.println("7");
			   		System.exit(5);
			   		break;
		    	default:
					System.out.println("======================================================");
			   		System.out.println("ERROR! Invalid input... Please type a number between 1 and 7.");
					System.out.println("======================================================");
					System.out.println("");
			   		validInput =false;
			   		break;
			}//end switch
			if (validInput)
	    	{
       			String answer;
   				while((answer = input.readLine()) != null && !answer.equals("ServerDone"))
   				{
   					//bw.write(answer);
   					//bw.newLine();
   					System.out.println(answer);
   				
   				}// end while answer loop
	    	}
			timeEnd = System.currentTimeMillis();
			System.out.println("======================================================");
			clientSocket.close();
			System.out.println("Response time = " + (timeEnd - timeStart));
			System.out.println("======================================================");
			out.close();
		}// end try
		catch (Exception e) {
			System.out.println("Can not open socket at " + hostName + ":" + port);
			e.printStackTrace();
			System.exit(-1);
		}
	}// end run method

}// end ClientThread class
