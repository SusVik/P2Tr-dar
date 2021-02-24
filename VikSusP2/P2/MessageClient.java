import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class MessageClient
{

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	public MessageClient(String ipAddress, int port)
	{
		 new con(ipAddress,port).start();
	}

	public void addListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}


	private class con extends Thread{
		private String ipAdd;
		private int port;


		public con(String ipAddr, int port){
			this.ipAdd = ipAddr;
			this.port = port;
		}


		public void run(){
			while (true){

				try(Socket socket = new Socket(ipAdd, port);
				    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
				    ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream()))
				{
					Message messageFromClient = (Message) inputStream.readObject();
                    propertyChangeSupport.firePropertyChange("mh", null, messageFromClient);
				}
				catch (IOException | ClassNotFoundException  e){
					System.out.println(e.getMessage());
				}
			}
		}
	}
}
