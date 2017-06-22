package ServerClientDM;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Simple server to make some calculations
 */
public class Server extends Thread{
	private StateEnum state = StateEnum.INITIAL;
	private boolean fromDMtoEuro;
	private boolean amount;
	private boolean running = true;
	@Override
	public void run() {
		try(ServerSocket socket = new ServerSocket(6789)){
			System.out.println("Server is running :) Type SHUTDOWN to shut down!");
			System.out.println("Which currency you want to enter (EURO or DM)?");
			while(running) {
				Socket connectionSocket = socket.accept();
				BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
				String request = inFromClient.readLine();
				String result = stateManager(request) + "\n";
				outToClient.writeBytes(result);
			}
		} catch (Exception e){
			System.out.println("Sth went wrong!");
		}

	}

	private String stateManager(String request){
		if(request.equals("SHUTDOWN")){
			running = false;
			return "Server is shutting down";
		} else if(state == StateEnum.INITIAL){
			if(request.equals("EURO")){
				fromDMtoEuro = false;
			} else if(request.equals("DM")){
				fromDMtoEuro = true;
			} else{
				return "Which currency you want to enter (EURO or DM)?";
			}
			state = StateEnum.ENTERAMOUNT;
			return "Enter an amount";
		} else if(state == StateEnum.ENTERAMOUNT){
			try{
				double amount = Double.parseDouble(request);
				if(fromDMtoEuro){
					amount = (amount / 100 * 51.129);
				} else{
					amount = amount / 100 * 195.583;
				}
				state = StateEnum.AGAIN;
				return Double.toString(amount) + "Again? type yes or no!";
			} catch (Exception e){
				state = StateEnum.INITIAL;
				return "Sorry, was not a valid amount! Which currency you want to enter (EURO or DM)?";
			}
		}
		//Again state
		else{
			if(request.equals("no")){
				running = false;
				return "Server shutting down";
			} else if(request.equals("yes")){
				state = StateEnum.INITIAL;
				return "Which currency you want to enter (EURO or DM)?";
			}
			return "Again? type yes or no!";
		}
	}
}
