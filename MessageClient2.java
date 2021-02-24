import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *  Klient klassen som kopplas upp mot {@link MessageServer} samt tar emot {@link Message} objekt från den,
 *  för att skicka de vidare till {@link P2Viewer} genom {@link PropertyChangeListener} metoden.
 * @author Mohanad Oweidat
 */
public class MessageClient
{

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	/**
	 * Konstruktoren som tar emot en ipAddress och en port för att kunna koppla upp mot en server {@link MessageServer}
	 * @param ipAddress En ipadress till {@link MessageProducerServer}
	 * @param port Uppkopplingsport till {@link MessageProducerServer}
	 */
	public MessageClient(String ipAddress, int port)
	{
		 new con(ipAddress,port).start();
	}
	/**
	 * En funktion för att registera Listiner som är ansvarig för att skicka {@link Message} objektet vidare till
	 * {@link P2Viewer}
	 * @param listener Parametern för Listinern som ska registerars och används i {@link P2Viewer}
	 */
	public void addListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}


	/**
	 * EN inre klass som användar sig av trådar (Ärvar) {@link Thread}
	 * Dess huvuduppgift är att koppla {@link MessageClient} klassen mot {@link MessageServer} klassen för att kunna
	 * läsa in {@link Message} objekt från den, samt skicka dem vidare till {@link P2Viewer} genom
	 * {@link PropertyChangeSupport}
	 */
	private class con extends Thread{
		private String ipAdd;
		private int port;

		/**
		 *
		 Konstruktoren som tar emot en ipAddress och en port för att kunna koppla upp mot en server {@link MessageServer}
		 * @param ipAddr En ipadress till {@link MessageProducerServer}
		 * @param port Uppkopplingsport till {@link MessageProducerServer}
		 */
		public con(String ipAddr, int port){
			this.ipAdd = ipAddr;
			this.port = port;
		}

		/**
		 * En {@link Override} metod från {@link Thread} som körs automatiskt när man skapar instancen av
		 * konstruktoren {@link con} i konstruktoren för klassen {@link MessageClient}
		 * Dess huvuduppgift är att koppla {@link MessageClient} klassen mot {@link MessageServer} klassen
		 * för attkunna läsa in {@link Message} objekt från den, samt skicka dem vidare till {@link P2Viewer} genom
		 *  {@link PropertyChangeSupport}
		 */
		public void run(){

			while (true){

				try(Socket socket = new Socket(ipAdd, port);
				    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
				    ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream()))
				{

					System.out.println("--------------------------------");
					System.out.println("Message Client Connected!");
					Message messageFromClient = (Message) inputStream.readObject();
                    propertyChangeSupport.firePropertyChange("msg", null, messageFromClient);


                    //Det står i instruktionerna att man bör hålla MessageClient uppkoplad mot MessageServer under
					// hela körningen.
					/*
					//Koppla ner
					outputStream.close();
					socket.close();
					if (socket.isClosed()){
						System.out.println("Message Client Disconnected");
					}
					else {
						socket.close();
					}
					 */
				}
				catch (IOException | ClassNotFoundException  e){
					System.out.println(e.getMessage());
				}
			}
		}
	}
}
