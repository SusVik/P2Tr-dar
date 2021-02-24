import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MessageProducerServer implements Runnable{

    private MessageProducerInput messageProducerInput;
    private Thread thread = new Thread(this);
    private ServerSocket serverSocket;

    public MessageProducerServer(MessageProducerInput messageProducerInput, int port) {
        try {
            this.messageProducerInput = messageProducerInput;
            serverSocket = new ServerSocket(port);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


        public void run() {
                while (true)
                try (Socket socket = serverSocket.accept();
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())){
                    messageProducerInput.addMessageProducer((MessageProducer) objectInputStream.readObject());
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
        }
    }

