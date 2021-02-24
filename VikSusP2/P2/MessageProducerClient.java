import java.io.*;
import java.net.*;

public class MessageProducerClient {

    private String IPaddress;
    private int port;


    public MessageProducerClient(String ipAdress, int port){
        this.IPaddress = ipAdress;
        this.port = port;
    }

    public void send(MessageProducer messageProducer) throws IOException {
        try (Socket socket = new Socket(IPaddress, port);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {

            objectOutputStream.writeObject(messageProducer);
            objectOutputStream.flush();
        }
        catch (IOException e ){

        }
    }
}
