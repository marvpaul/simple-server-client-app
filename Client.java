package ServerClientDM;

import java.io.*;
import java.net.Socket;

/**
 * Just a simple client
 */
public class Client extends Thread {
	boolean running = true;
	@Override
	public void run() {
		while(running){
			try(Socket clientSocket = new Socket("localhost", 6789)){
				BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));


				DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
				BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

				outToServer.writeBytes(inFromUser.readLine() + '\n');
				String result = inFromServer.readLine();
				System.out.println(result);
			} catch (Exception e){
				System.out.println("Server not reachable!");
				running = false;
			}
		}


	}
}
