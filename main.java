package ServerClientDM;

/**
 * Created by marvin on 22.06.17.
 */
public class main {
	public static void main(String[] args) throws InterruptedException {
		Thread server = new Server();
		server.start();

		Thread.sleep(1000);
		Thread client = new Client();
		client.start();

	}
}
