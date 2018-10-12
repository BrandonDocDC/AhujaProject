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
 
// client threaded class: main method will call all methods to:
// create the socket, create the threads, and connect the threads. everything else is in run()
public class ClientThreaded extends Thread {
	//Global Vars
	public int portNumber;
	public int menuSelected;
	public String hostName;
	public int port, option;
	
	//main method
	public static void main(String[] args) throws IOException, InterruptedException {
		// check the args to ensure the correct number of args are given
		if(args.length < 2) {
			System.out.println("Please enter arguments in this format java ClientThreaded <server IP Address> <port number>\n");
			System.exit(0);
		}// end if args.length < 2 check
		hostName = args[0];
		
		try {
			portNumber = Integer.parseInt(args[1]);
		}// end try
		catch (NumberFormatException e) {
			System.out.println("User invalid input, please enter a port number as an Integer.");
			System.exit(-1);
		}// end catch (NumberFormatException)
		while(true) {
		menu();
		System.out.println("How many threads:");
		Scanner keyboard = new Scanner(System.in);
		int numberOfTimes = keyboard.nextInt();
		Thread[] theThreads = new Thread[numberOfTimes];
		//runThreads(numberOfTimes, theThreads, hostName, portNumber);
		runThreads(numberOfTimes, theThreads);
		for(int index = 0; index < numberOfTimes; index++) 
			theThreads[index].join();
		}
	}// end main method 
	
	//menu method
	public static void menu() {
		System.out.println( "1) Host Current Date and Time\n"
						  + "2) Host Uptime\n"
						  + "3) Host Memory Use\n"
						  + "4) Host Netstat\n"
						  + "5) Host Current Users\n"
						  + "6) Host Running Processes\n"
						  + "7) Quit\n" );
		System.out.println("Select your option: ");
		Scanner sc = new Scanner(System.in);
		while(!sc.hasNextInt())
		{
			System.out.println("User invalid input, input number between 1 or 7");
		        sc.next();
		}// end while !sc.hasNextInt loop
		menuSelected = sc.nextInt();
		System.out.println("======================================================");
		
	}// end menu method
	
	//runThreads mathod
	//public static void runThreads(int times, Thread[] theThreads, string hostName, int portNumber) {
	public static void runThreads(int times, Thread[] theThreads) {
		System.out.println("Client threaded " + times + " times.");
		for(int index1 = 0; index1 < theThreads.length; index1++) {
			theThreads[index1] = new ClientThreaded(hostName, portNumber, menuSelected);
		}
		for(int index = 0; index < theThreads.length; index++) {
			
			System.out.println("Thread_" + (index + 1));
			theThreads[index].run();
		}// end runThreads method
	}// end runThreads method

	//ClientThreaded Method
	public ClientThreaded(String host, int portNumber, int menuItem) {
		hostName = host;
		port = portNumber;
		option = menuItem;
	}// end ClientThreaded constructor
	
	//run method *all actions that were called from the main method are located here.
	public void run() {
		//Vars
		double timeStart;
		double timeEnd;
		Socket clientSocket;
		int timeOut = 15000;
		
		try {
			//start timer before request sent to server
			start_time = System.currentTimeMillis();
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
					System.out.println("Current Date & Time Request from Client");
					out.println("1");
					//System.out.println("Current Date & Time: " + in.readLine());
					validInput =true;
				break;
		    	case 2:
		    		System.out.println("Uptime Request from Client");
					out.println("2");
					//System.out.println("Uptime: " + in.readLine());
			   		validInput =true;
			   	break;
		    	case 3:
			   		System.out.println("Memory Use Request from Client");
			   		out.println("3");
					//System.out.println("Memory Use: " + in.readLine());
					//while ((serverResponse = in.readLine()) != null && !serverResponse.equals("EndResponse")) {
					//	System.out.println(serverResponse);
					//}
			   		validInput =true;
			   	break;
		    	case 4:
			   		System.out.println("Netstat Request from Client");
			   		out.println("4");
					//System.out.println("Netstat: " + in.readLine());
					//while ((serverResponse = in.readLine()) != null && !serverResponse.equals("EndResponse")) {
					//	System.out.println(serverResponse);
					//}
			   		validInput =true;
			   	break;
		    	case 5:
			   		System.out.println("Current Users Request from Client");
			   		out.println("5");
					//System.out.println("Current Users: " + in.readLine());
			   		validInput =true;
			   	break;
		    	case 6:
				//	original
			   		//System.out.println("Running Processes Request from Client");
			   		//out.println("6");
					//System.out.println("Running Processes: " + in.readLine());
				//	new
					// Start Time
					long startTime = System.currentTimeMillis();
					out.println("6");
					System.out.println("Running Processes: " + in.readLine());
					while ((serverResponse = in.readLine()) != null && !serverResponse.equals("EndResponse")) {
						System.out.println(serverResponse);
					}
					long endTime = System.currentTimeMillis();
					System.out.println("======================================================");
				//	Print length of time and status of option
					System.out.println("  --  Completed in " + (endTime-startTime) + "ms");
					System.out.println("======================================================");
					System.out.println("");
			   		validInput =true;
			   	break;
		    	case 7:
					System.out.println("======================================================");
					System.out.println("Quiting...");
					System.out.println("======================================================");
					System.out.println("");
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
       			String serverResponse;
   				while((serverResponse = input.readLine()) != null && !serverResponse.equals("EndResponse"))
   				{
					// Get answer from server and print.
					System.out.println("======================================================");
					System.out.println(serverResponse);
   				}// end while answer loop
	    	}
			clientSocket.close();
			//end the time after response is received from server
			end_time = System.currentTimeMillis();
			//Print length of time and status of option
			System.out.println("Completed in " + (end_time-start_time) + "ms");
			System.out.println("======================================================");
			System.out.println("");
			out.close();
		}// end try
		catch (Exception e) {
			System.out.println("======================================================");
			System.out.println("Cannot open socket at " + hostName + ":" + port);
			e.printStackTrace();
			System.out.println("======================================================");
			System.exit(-1);
		}
	}// end run method
}// end ClientThreaded class
