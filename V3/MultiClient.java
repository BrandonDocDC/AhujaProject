package multiclient;

/** Project 1 - Networks and Distributed Systems
  * Dr. Ahuja
  * Brandon DeCrescenzo, Kristoffer Binek, Nahjani Rhymer
  * MultiClient.java
*/

//Imports
import java.io.*;
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

public class MultiClient {
	static String hostName;
	static int portNumber;
	static int menuSelected;
	
	//main method
	public static void main(String[] args) throws IOException, InterruptedException {
		// check the args to ensure the correct number of args are given
		if(args.length < 2) {
			System.out.println("Please enter arguments in this format java MultiClient <server IP Address> <port number>\n");
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
}// end MultiClient class
