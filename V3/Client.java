/** Project 1 - Networks and Distributed Systems
  * Dr. Ahuja
  * Brandon DeCrescenzo, Kristoffer Binek, Nahjani Rhymer
  * MultiClient.java
*/

//Imports
import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
	//global vars
	public static String serverResponse;
    public static int times, option;
	public static long end_time;

    public static void main(String[] args) throws IOException, InterruptedException  {
	//local vars
    String hostName = args[0];
    int portNumber = Integer.parseInt(args[1]);

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
									  + "2) Host Uptime\n"
									  + "3) Host Memory Use\n"
									  + "4) Host Netstat\n"
									  + "5) Host Current Users\n"
									  + "6) Host Running Processes\n"
									  + "7) Quit\n" );
									  
					System.out.println("Select your option: ");
					option = stdIn.read();
					System.out.println("======================================================");
					
					//start switch for menu
					switch (option) {
						//Host Current Date & Time
						case '1':
						//	Start Time
							start_time = System.currentTimeMillis();
							out.println("1");
							System.out.println("Current Date & Time: " + in.readLine());
							break;
						//Host Uptime
						case '2':
						//	Start Time
							start_time = System.currentTimeMillis();
							out.println("2");
							System.out.println("Uptime: " + in.readLine());
							break;
						//Host Memory Use
						case '3':
						//	Start Time
							start_time = System.currentTimeMillis();
							out.println("3");
							System.out.println("Memory Use: " + in.readLine());
							while ((serverResponse = in.readLine()) != null && !serverResponse.equals("EndResponse")) {
								System.out.println(serverResponse);
							}
							end_time = System.currentTimeMillis();
							System.out.println("======================================================");
						//	Print length of time and status of option
							System.out.println("  --  Completed in " + (end_time-start_time) + "ms");
							System.out.println("======================================================");
							System.out.println("");
							continue;
						//Host Netstat
						case '4':
						//	Start Time
							start_time = System.currentTimeMillis();
							out.println("4");
							System.out.println("Netstat: " + in.readLine());
							while ((serverResponse = in.readLine()) != null && !serverResponse.equals("EndResponse")) {
								System.out.println(serverResponse);
							}
							end_time = System.currentTimeMillis();
							System.out.println("======================================================");
						//	Print length of time and status of option
							System.out.println("  --  Completed in " + (end_time-start_time) + "ms");
							System.out.println("======================================================");
							System.out.println("");
							serverResponse = null;
							continue;
						//Host Current Users 
						case '5':
						//	Start Time
							start_time = System.currentTimeMillis();
							out.println("5");
						//	String users = in.readLine();
							System.out.println("Current Users: " + in.readLine());
							break;
						//Host Running Processes 
						case '6':
							// Start Time
							start_time = System.currentTimeMillis();
							out.println("6");
							System.out.println("Running Processes: " + in.readLine());
							while ((serverResponse = in.readLine()) != null && !serverResponse.equals("EndResponse")) {
								System.out.println(serverResponse);
							}
							end_time = System.currentTimeMillis();
							System.out.println("======================================================");
						//	Print length of time and status of option
							System.out.println("  --  Completed in " + (end_time-start_time) + "ms");
							System.out.println("======================================================");
							System.out.println("");
							serverResponse = null;
							continue;
						//Quit 
						case '7':
						//	Start Time
							start_time = System.currentTimeMillis();
							out.println("7");
							System.out.println("======================================================");
							System.out.println("Quiting...");
							System.out.println("======================================================");
							System.out.println("");
							//close socket & kill process
							s.close();
							in.close();
							out.close();
							System.exit(1);
							return;
						//Invalid Input 
						default:
							System.out.println("======================================================");
							System.err.println("ERROR! Invalid input... Please type a number between 1 and 7.");
							System.out.println("======================================================");
							System.out.println("");
							continue;
					}//End switch
					
					System.out.println("======================================================");
					//prints out response from server.
					while ((serverResponse = in.readLine()) != null && !serverResponse.equalsIgnoreCase("EndResponse")) {
						System.out.println("======================================================");
						out.println(serverResponse);
					}
					//end the time per command
					end_time = System.currentTimeMillis();
				//Print length of time and status of option
				System.out.println("Completed in " + (end_time-start_time) + "ms");
				System.out.println("======================================================");
				System.out.println("");
				//}//end while2
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
