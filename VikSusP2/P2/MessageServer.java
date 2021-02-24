import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class MessageServer implements Runnable, IManager
{
	private MessageManager messageManager;
	private int port;
	private ServerSocket serverSocket;
	private Message messageFromMessageManager;


	public MessageServer(MessageManager messageManager, int port) throws IOException
	{
		this.messageManager = messageManager;
		this.port = port;
		messageManager.registerListener(this);
		serverSocket = new ServerSocket(port);
		new Thread(this).start();
	}


	@Override
	public void run()
	{
		while (true)
		{
			try
			{
				Socket socket = serverSocket.accept();
				new ClientHandler(socket).start();

			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

	}


	@Override
	public void sendMessage(Message message)
	{
		messageFromMessageManager = message;
	}


	private class ClientHandler extends Thread
	{
		private Socket socket;


		public ClientHandler(Socket socket)
		{
			this.socket = socket;
		}


		@Override
		public void run()
		{
			try (ObjectOutputStream dos = new ObjectOutputStream(socket.getOutputStream()); ObjectInputStream dis = new ObjectInputStream(socket.getInputStream()))
			{
						//if (messageFromMessageManager != null){
							dos.writeObject(messageFromMessageManager);
							dos.flush();
						//}
			}

			catch (IOException e) { }
			try {
			}
			catch (Exception e) { }
		}
	}
}
