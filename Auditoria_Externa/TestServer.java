package unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.net.URI;
import java.util.Scanner;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.junit.Test;

public class TestServer {

	@Test
	public void test1() {
		URI uri = URI.create("ws://localhost:9090/");
		String response;
		try {
			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			// Attempt Connect
			Session session = container.connectToServer(EventSocket.class, uri);
			// Send a message
			session.getBasicRemote().sendText("groundtruth.csv");
			session.getBasicRemote().sendText("Dissolve1-15.mp4");
			synchronized (session.getBasicRemote()) {
				session.getBasicRemote().wait();
				response = new Scanner(new File("response.txt")).useDelimiter("\\Z").next();
			}
			// Close session
			session.close();
			assertEquals(response, "2, 326");
		} catch (Throwable t) {
			fail(t.getMessage());
		}
	}

	@Test
	public void test2() {
		URI uri = URI.create("ws://localhost:8000/");
		String response;
		try {
			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			// Attempt Connect
			Session session = container.connectToServer(EventSocket.class, uri);
			// Send a message
			session.getBasicRemote().sendText("groundtruth.csv");
			session.getBasicRemote().sendText("Dissolve1-15.mp4");
			synchronized (session.getBasicRemote()) {
				session.getBasicRemote().wait();
				response = new Scanner(new File("response.txt")).useDelimiter("\\Z").next();
			}
			// Close session
			session.close();
			fail("Wrong URL");
		} catch (Exception t) {
			assertTrue(true);
		}
	}
}