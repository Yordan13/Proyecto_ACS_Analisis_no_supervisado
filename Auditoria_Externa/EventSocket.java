package unittests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@ClientEndpoint
public class EventSocket {
	Session session;

	@OnOpen
	public void onWebSocketConnect(Session sess) {
		session = sess;
		System.out.println("Socket Connected: " + sess);
	}

	@OnMessage
	public void onWebSocketText(String message) {
		System.out.println("Received TEXT message: " + message);
		synchronized (session.getBasicRemote()) {
			try {
				Files.write(Paths.get("response.txt"), message.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			session.getBasicRemote().notify();
		}
	}

	@OnClose
	public void onWebSocketClose(CloseReason reason) {
		System.out.println("Socket Closed: " + reason);
	}

	@OnError
	public void onWebSocketError(Throwable cause) {
		cause.printStackTrace(System.err);
	}
}