/** Project 1 - Networks and Distributed Systems
  * Dr. Ahuja
  * Brandon DeCrescenzo, Kristoffer Binek, Nahjani Rhymer
  * MultiClient.java
*/

//Imports
import java.io.IOException;
import java.util.Scanner;

// MultiClient Class - This will accept the args and open a socket, display menu
// This will also start the threads which will envoke the run() found in ClienThread.java
public class MultiClient {
	static String hostName;
	static int portNumber;
	static int menuSelected;
	static int counter = 0;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		// check the args to ensure the correct number of args are given
		if(args.length < 2) {
			System.out.println("No arguments: java MultiClient <server IP Address> <port number>\n");
			System.exit(0);
		}// end if args.length < 2 check
		hostName = args[0];
		
		try {
			portNumber = Integer.parseInt(args[1]);
		}// end try
		catch (NumberFormatException e) {
			System.out.println("Port number must be an Integer.");
			System.exit(-1);
		}// end catch (NumberFormatException)
		while(true) {
		menu();
		int numberOfTimes = 0;
		while (numberOfTimes < 1 || numberOfTimes > 7) {
			System.out.println("How many threads would you like:");
			Scanner keyboard = new Scanner(System.in);
			numberOfTimes = keyboard.nextInt();
		}
		long timerStart = System.currentTimeMillis();
		counter++;
		Thread[] theThreads = new Thread[numberOfTimes];
		runThreads(numberOfTimes, theThreads);
		//create an array of threads and join them
		for(int index = 0; index < numberOfTimes; index++) 
			theThreads[index].join();
		}
		if (counter > 0) {
			long timerStop = System.currentTimeMillis();
			long totalTime = timerStop - timerStart;
			System.out.println("======================================================");
			System.out.println("Total time for all responses: " + totalTime + "ms");
			System.out.println("======================================================");
		}
	}// end main method 
	
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
	
	
	public static void runThreads(int times, Thread[] theThreads) {
		System.out.println("Client Threading: " + times + " times.");
		for(int index1 = 0; index1 < theThreads.length; index1++) {
			theThreads[index1] = new ClientThread(hostName, portNumber, menuSelected);
		}
		for(int index = 0; index < theThreads.length; index++) {
			System.out.println("======================================================");
			System.out.println("Thread # " + (index + 1));
			theThreads[index].run();
		}// end runThreads method
	}// end runThreads method
}// end MultiClient class
