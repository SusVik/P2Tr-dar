import java.util.ArrayList;

public class MessageManager {
    private Buffer<Message> messageBuffer;
    private Thread thread;
    ArrayList<IManager> arrayList = new ArrayList<>();

    public MessageManager(Buffer<Message> messageBuffer) {
        this.messageBuffer = messageBuffer;
    }

    public void registerListener(IManager IManager){
        this.arrayList.add(IManager);
    }

    public void start() {
        thread = new Counter();
        thread.start();
    }

    class Counter extends Thread {

        @Override
        public void run() {
            Message message;
            while (!Thread.interrupted()) {
                try {
                    message = messageBuffer.get();
                    transfer(message);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        public void transfer(Message message){ // Message objektet kommer skickas
            for (IManager l : arrayList) {
                l.sendMessage(message);
            }
        }
    }
}
