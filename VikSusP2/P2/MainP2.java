import javax.swing.ImageIcon;
import java.io.IOException;
import java.io.Serializable;
//TODO ändra variabelnamn etc.....
public class MainP2 {
	public static void main(String[] args) throws IOException {
		Buffer<Message> messageBuffer = new Buffer<Message>();
		Buffer<MessageProducer> producerBuffer	= new Buffer<MessageProducer>();

		MessageManager messageManager = new MessageManager(messageBuffer);
		P1Viewer v1 = new P1Viewer(messageManager, 300, 200);
		P1Viewer v2 = new P1Viewer(messageManager, 320, 320);
		Viewer.showPanelInFrame(v1.getViewer(), "Viewer 1", 100, 50);
		Viewer.showPanelInFrame(v2.getViewer(), "Viewer 2", 450, 50);

	  MessageServer messageServer = new MessageServer(messageManager, 2343); // start av server



		MessageClient messageClient1 = new MessageClient("127.0.0.1", 2343); // start av client1





		P2Viewer v3 = new P2Viewer(messageClient1, 300, 200);
		P2Viewer v4 = new P2Viewer(messageClient1, 320, 320);
		Viewer.showPanelInFrame(v3, "Viewer 3", 100, 400);
		Viewer.showPanelInFrame(v4, "Viewer 4", 450, 400);


		//MessageClient messageClient2 = new MessageClient("127.0.0.1", 2343); // start av client2

		MessageClient messageClient2 = new MessageClient("127.0.0.1", 2343); // start av client2
		P2Viewer v5 = new P2Viewer(messageClient2, 250, 320);
		Viewer.showPanelInFrame(v5, "Viewer 5", 800, 400);


		//SKICKAR Till p1viewer och klienten
		messageManager.start();

		//tar från producerBuffer, lägger i MessAGEbUFFER
		Producer producer = new Producer(producerBuffer,messageBuffer);
		producer.start();

        MessageProducerInput mpInput = new MessageProducerInput(producerBuffer);
//        mpInput.addMessageProducer(new TextfileProducer("files/new.txt"));

        MessageProducerServer mpServer = new MessageProducerServer(mpInput,3343);
        //mpServer.startServer();

		//Startar server och kopplar klient mot server, skickar messproduces-instans till server
        MessageProducerClient mpClient1 = new MessageProducerClient("127.0.0.1",3343);
		mpClient1.send(TestP2Input.getArrayProducer(10,100));
        mpClient1.send(new ShowGubbe(5000));
        mpClient1.send(new TextfileProducer("files/new.txt"));

	}
}

class ShowGubbe implements MessageProducer, Serializable {
	private int delay;

	public ShowGubbe(int delay) {
		this.delay = delay;
	}

	@Override
	public int delay() {
		return delay;
	}

	@Override
	public int times() {
		return 1;
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public Message nextMessage() {
		return new Message("Hi folks...",new ImageIcon("images/gubbe.jpg"));
	}
}
