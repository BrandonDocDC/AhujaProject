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
	public static String serverResponse;
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
				//while (option != 7) {
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
					
					switch (option) {
						//Host Current Date & Time
						case '1':
							out.println("1");
							System.out.println("Current Date & Time: " + in.readLine());
							break;
						//Host Uptime
						case '2':
							out.println("2");
							System.out.println("Uptime: " + in.readLine());
							break;
						//Host Memory Use
						case '3':
							out.println("3");
							System.out.println("Memory Use: " + in.readLine());
							while ((serverResponse = in.readLine()) != null && !serverResponse.equals("EndResponse")) {
								System.out.println(serverResponse);
							}
							System.out.println("======================================================");
							System.out.println("");
							continue;
						//Host Netstat
						case '4':
							out.println("4");
							System.out.println("Netstat: " + in.readLine());
							while ((serverResponse = in.readLine()) != null && !serverResponse.equals("EndResponse")) {
								System.out.println(serverResponse);
							}
							System.out.println("======================================================");
							System.out.println("");
							serverResponse = null;
							continue;
						//Host Current Users 
						case '5':
							out.println("5");
						//	String users = in.readLine();
							System.out.println("Current Users: " + in.readLine());
							break;
						//Host Running Processes 
						case '6':
							out.println("6");
							System.out.println("Running Processes: " + in.readLine());
							while ((serverResponse = in.readLine()) != null && !serverResponse.equals("EndResponse")) {
								System.out.println(serverResponse);
							}
							System.out.println("======================================================");
							System.out.println("");
							serverResponse = null;
							continue;
						//Quit 
						case '7':
							out.println("7");
							System.out.println("Quiting...");
							//close socket & kill process
							s.close();
							in.close();
							out.close();
							System.exit(1);
							return;
						//Invalid Input 
						default:
							System.err.println("ERROR! Invalid input... Please type a number between 1 and 7.");
							continue;
					}//End switch 
					
					// Start Time
					start_time = System.currentTimeMillis();
					
					while ((userInput = in.readLine()) != null && !userInput.equalsIgnoreCase("EndResponse")) {
						out.println(userInput);
					}
					long end_time = System.currentTimeMillis();
				System.out.println("======================================================");
				System.out.println("");
				//}//end while2
				//Print length of time and status of option
				//System.out.println("  --  Completed in " + (end_time-start_time) + "ms");
			//System.out.println("end while1");
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
