import java.io.*;
import java.net.*;
import java.lang.*;
import java.util.*;

// "java Client <IP>"
public class Client
{
	public static void main(String args[])
	{
		while(true)
		{
			try
			{
            String ip;
            if(args.length > 0){
				ip = args[0];
				port = args[1];
            }
            else{
				ip = "192.168.100.107";
				port = "8080";
            }
				//setup for user input from console
				InputStreamReader stdInReader = new InputStreamReader(System.in);
				BufferedReader stdIn = new BufferedReader(stdInReader);
				long startTime = 0, finishTime = 0, delay = 0;
				
				//flag to determine if q or Q for quitting time
				//boolean quit = false;
				
				//make connection
				System.out.println("Making connection....");
				
				Socket socket = new Socket(ip, port);
				System.out.println("Setting up reader/writer...");
				//setup for socket toServer
				BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				//setup for socket fromServer
				PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
				
				//String userInput = "A";
				String serverOutput = "";
				//PrintMenu();
				userInput = stdIn.readLine();
				startTime = System.currentTimeMillis();
				toServer.println("A");

				System.out.println("Sending to server...");
				//toServer.println(userInput);
				while(!fromServer.ready())
				{
				}
				System.out.println("Receiving from server...");
				ArrayList<String> inputList = new ArrayList<String>();
				do{
					serverOutput = fromServer.readLine();
					inputList.add(serverOutput); // need Processed in XXX Time
				}while(!serverOutput.equals("-2"));
				finishTime = System.currentTimeMillis();
				if(inputList.get(0).equals("-1"))
				{
					System.out.println("Error on input.");
				}else
				{
					for(int i = 0; i < inputList.size() - 1; i++)
					{
						System.out.println(inputList.get(i));
					}
					System.out.println("Delay is: " + (finishTime - startTime));
				}
					
				//terminates connection to server
				toServer.close();
				fromServer.close();
				stdInReader.close();
				stdIn.close();
				socket.close();
				System.exit(0);
					
			}
      catch(IOException e) {
				System.out.println(e.toString());
			}//end catch
		}//end main
	}//end client
}
