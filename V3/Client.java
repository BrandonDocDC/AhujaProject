/** Project 1 - Networks and Distributed Systems
  * Dr. Ahuja
  * Brandon DeCrescenzo, Kristoffer Binek, Nahjani Rhymer
  * MultiClient.java
*/

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
	//global vars
    public static String userInput;
	public static String stopVar = "Stop";
    public static int times, option;

    public static void main(String[] args) throws IOException {
	//local vars
    String hostName = args[0];
    int portNumber = Integer.parseInt(args[1]);

		//if the user executes the java without params (args)
        if (args.length != 2){
			System.err.println("You need the server and port: java Client <host name> <port number>");
			System.err.println("  --                 Example: java Client 192.168.100.107 8012");
            System.exit(1);
        }//end if

		//When successful, it will establish a socket
        try{         
            Socket clientSocket = new Socket(hostName, portNumber);
      
            //While the socket is open, it will listen for host response and display menu after
			//while1
			while(true){  
           
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); // doorstop to keep listening on socket
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // in is the input from server response
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in)); //user menu input
				Scanner s = new Scanner(System.in); // s var to loop executions

				//while the socket is live, it will keep posting the menu
				//while2
				while (true) {
					long start_time; 
					 //log print line, broken up to show output (menu)
					System.out.println( "1) Host Current Date and Time\n"
									  + "2) Host Current Uptime\n"
									  + "3) Host Current Memory Use\n"
									  + "4) Host Current Netstat\n"
									  + "5) Host Current Users\n"
									  + "6) Host Running Processes\n"
									  + "7) Quit\n" );
									  
					System.out.println("Select your option: ");
					option = stdIn.read();

					// Switch Menu
					switch (option) {
						case '1':
							//start_time = System.currentTimeMillis();
							//System.out.println("Requesting Time");
							out.println("1");
							System.out.println("");
							System.out.println("The Date and Time: " + in.readLine());
							break;
						case '2':
							out.println("2");
							System.out.println("");
							System.out.println("System has been " + in.readLine());
							break;
						case '3':
							out.println("3");
							System.out.println("");
							while (in.readLine() != null) {
								System.out.println(in.readLine());
							}
							break;
						case '4':
							out.println("4");
							System.out.println("");
							while (in.readLine() != null) {
								System.out.println(in.readLine());
							}
							break;
						case '5':
							out.println("5");
							System.out.println("");
							System.out.println("Current Users: " + in.readLine());
							break;
						case '6':
							out.println("6");
							System.out.println("");
							while (in.readLine() != null) {
								System.out.println(in.readLine());
							}
							break;
						case '7':
							out.println("7");
							System.out.println("");
							System.out.println(in.readLine());
							return;
						default:
							System.err.println("  --  Unrecognized option.");
							continue;
					} //end switch
					System.out.println("\n  --  Request is done");

					while ((userInput = in.readLine()) != null && !userInput.equalsIgnoreCase("Finished")) {
						out.println(userInput);
					}//end while
					//long end_time = System.currentTimeMillis();

				}//end while2
				//Print length of time and status of option
				//System.out.println("  --  Completed in " + (end_time-start_time) + "ms");
			}// end while1
		}//end try

		//Catchs
		catch(NumberFormatException e){
			System.out.print("  --  Please enter valid option!!\n");
			System.exit(1); 
		}//end catch
		catch (UnknownHostException e){
			System.err.println("  --  Unknown Host: " + hostName );
			System.exit(1);
		}//end catch
		catch (IOException e){
			System.err.println("  --  Couldn't get I/O for the connection to " + hostName);
			System.exit(1);
		} //end catch
		catch (InputMismatchException e){
			System.err.println ("  --  Unrecognized input "  + e );
		}//end catch
	}//end main
}//end class MultiClient
