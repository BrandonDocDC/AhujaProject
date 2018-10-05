// import libraries
import java.io.*;
import java.net.*;
import java.lang.*;
import java.util.*;
import java.util.concurrent.*;

/* Project 1 Test for user to make graphs - Networks and Distributed Systems
/* Dr. Ahuja
/* Brandon DeCrescenzo, Kristoffer Binek, Nahjani Rhymer
*/

// "javac TestClient.java" "java TestClient <IP> <PORT> <NUMBER_OF_CLIENTS> <COMMAND>"
// port is hardcoxded as 8080 on both the client and server to prevent error
public class TestClient
{
	public static void main(String[] args) {
		IPInfo ip = new IPInfo();
		ip.setIP(args[0]); // set IP
		ip.setPort(Integer.parseInt(args[1])); // set port
		int numJobs = Integer.parseInt(args[2]); // set #of clients
		String command = String.valueOf(args[3]); // set command to send to server
 		command = command.toUpperCase(); // make sure it is exactly the same for the case-switch on server.
		
 		// Set up the Queue for printing. Add header for csv formatting
		ConcurrentLinkedQueue<String> printQueue = new ConcurrentLinkedQueue<String>();
 		Thread printThread = new Thread(new Printer(printQueue, command));
		printThread.start();
 		// start primary threads
		System.out.println("Client connected to Server (" + ip.getIP() + ") on Port: " + ip.getPort());
		System.out.println("Transmitting " + numJobs + " client requests...");
		Thread[] threads = new Thread[numJobs];
		// thread for each command
 		for (int i = 0; i < numJobs; i++) {
			Thread newThread = new Thread(new ThreadProcess(printQueue, command));
			newThread.setName("Thread" + i);
			threads[i] = newThread;
 		}
		// spawn clients (threads)
 		for (int i = 0; i < numJobs; i++) {
			threads[i].start();
		}
		// connect each thread to the server
 		for(int i = 0; i < numJobs; i++){
			try{
				threads[i].join();
			}catch(Exception e){
 			}
		}
		// join the print thread
		try {
			printThread.join();
		} catch (Exception e) {
		}
 	}
} // end main

// bits and pieces to ensure there are multiple client threads, each is connected to the server, and each command is sent threaded. all responses come back as a time, all times are averaged at the end.
class ThreadProcess implements Runnable
{
	ConcurrentLinkedQueue<String> queue;
	String command;
	
	ThreadProcess(ConcurrentLinkedQueue<String> queue, String command)
	{
		this.queue = queue;
		this.command = command;
	}
	
	public void run()
	{
		this.Connect();
	}
	public void Connect()
	{
		
		try
		{
			IPInfo ip = new IPInfo();
			InputStreamReader stdInReader = new InputStreamReader(System.in);
			BufferedReader stdIn = new BufferedReader(stdInReader);
			long startTime = 0, finishTime = 0, delay1 = 0, delay2 = 0;
			
			// moved to bottom: Thread.sleep();
			
			// make connection
			Socket socket = new Socket(ip.getIP(), ip.getPort());
			
			// setup for socket toServer
			BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			// setup for socket fromServer
			PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
			
			
			String userInput = "";
			String serverOutput = "";
			
			// userInput = stdIn.readLine();
			ArrayList<String> firstCom = new ArrayList<String>();
 			startTime = System.currentTimeMillis();
			
 			firstCom = toServer(fromServer, toServer, command);
			
			finishTime = System.currentTimeMillis();
			delay1 = finishTime - startTime;
			
 			String passer = Thread.currentThread().getName() + " request processed in " + delay1 + " ms";
			
			// System.out.println(passer);
			queue.add(passer);
			fromServer.close();
			toServer.close();
			socket.close();
		
		}catch(IOException e)
		{
			System.out.println(e.toString());			
		}
	// END CONNECT
	}
	
	public ArrayList<String> toServer(BufferedReader fromServer, PrintWriter toServer, String input)
	{
		String serverOutput;
		ArrayList<String> inputList = new ArrayList<String>();
		try{
			
			toServer.println(input);
			while(!fromServer.ready())
				{
				}
			
			do{
				serverOutput = fromServer.readLine();
				
				inputList.add(serverOutput);
			}while(!serverOutput.equals("-2"));
			
			if(inputList.get(0).equals("-1"))
			{
				System.out.println("Error on input.");
			}
		}catch(IOException e)
		{
			
		}
		return inputList;
	}
}
// all of the ip:port infos for the threads
class IPInfo
{
	private static String ip;
	private static int port;
	
	public IPInfo()
	{
	}
	
	
	public IPInfo(String ip)
	{
		this.ip = ip;
	}
	
	public IPInfo(int port)
	{
		this.port = port;
	}
	
	public IPInfo(String ip, int port)
	{
		this.ip = ip;
		this.port = port;
	}
	
	public String getIP(){return ip;}
	
	public void setIP(String ip) {this.ip = ip;}
	
	public int getPort() {return port;}
	
	public void setPort(int port) {this.port = port;}
	//END IP INFO
}
// this will calculate the response time of the server. from the time the request is sent from client to the time it receives it from server.
class Printer implements Runnable
{
	ConcurrentLinkedQueue<String> queue;
	String filename;
	String command;
	int i;
	
	Printer(ConcurrentLinkedQueue<String> queue, String command)
	{
		this.queue = queue;
		this.command = command;
		i = 0;
	}
	
	public void run()
	{
		String str;
		long startTime = System.currentTimeMillis();
		long currentTime=startTime;
		long startTotalTime = System.currentTimeMillis();
		while(currentTime - startTime < 1000){
 			try{
				Thread.sleep(25);
				filename = "TestData"+command+".csv";
				FileWriter fw = new FileWriter(filename, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw);
				currentTime = System.currentTimeMillis();
				while((str = queue.poll())!= null)
				{
					System.out.println(str);
					out.println(str);
					startTime=System.currentTimeMillis();
					i++;
				}
				out.close();
				}catch(Exception e)
				{
					System.out.println("Error saving to testdata");
					
				}
			
		}
		long endtime = System.currentTimeMillis();
		long totalTimeEnd = endtime - startTotalTime;
		long AvgTime = totalTimeEnd / i;
		// response tiem for each request to return from server
		System.out.println("Total Turn-Around Time: " + totalTimeEnd + " ms");
		// response time averaged for all requests from server
		System.out.println("Average Turn-Around Time: " + AvgTime + " ms");
	} // end run
}
