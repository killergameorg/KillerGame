package clients.automessenger;

import java.util.Hashtable;

import communications.ConnectionController;
import communications.P2PCommListener;

public class Messenger implements P2PCommListener {

	private ConnectionController comm;
	private Hashtable<String, Integer> connectedPeers;

	public Messenger() {
		connectedPeers = new Hashtable<>();
	}

	@Override
	public void onConnectionClosed(String ip) {
		System.err.println("[" + ip + "] " + "cerró su conexión");
		connectedPeers.remove(ip);
	}

	@Override
	public void onConnectionLost(String ip) {
		System.err.println("[" + ip + "] " + "conexión cerrada de forma inesperada");
		connectedPeers.remove(ip);
	}

	@Override
	public void onIncomingMessage(String ip, Object object) {
		System.out.println("[" + ip + "] " + (String) object);
	}

	@Override
	public void onNewConnection(String ip) {
		System.err.println("[" + ip + "] " + "conectado");
		connectedPeers.put(ip, 0);
	}

	private void senderBucle() {
		while (true) {
			connectedPeers.forEach((ip, n) -> {
				comm.sendPrivate(ip, String.valueOf(n));
				connectedPeers.put(ip, ++n);
			});
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}
	}

	public void setComm(ConnectionController comm) {
		this.comm = comm;
		new Thread(this::senderBucle).start();
	}

}
