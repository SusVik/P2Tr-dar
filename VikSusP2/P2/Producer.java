/**
 * Den här klassen tar ett MessageProducerBuffer-objekt och lägger i en MessageBuffer-objekt
 * @author Susanne Vikström
 */


public class Producer {
    private Buffer<MessageProducer> producerBuffer; //lägg från dennna
    private Buffer<Message> messageBuffer; //lägg i denna
    private Thread thread;

    /**
     * Tar emot två buffer-objekt
     * @param producerBuffer en messageProducerBuffer-instans
     * @param messageBuffer en MessageBuffer-instans
     */
    public Producer(Buffer<MessageProducer> producerBuffer, Buffer<Message> messageBuffer) {
        this.producerBuffer = producerBuffer;
        this.messageBuffer = messageBuffer;
    }

    /**
     * Exekverar tråden
     */
    public void start() { //inre klass implements/extendar runnable eller thread
        thread = new Counter();
        thread.start();
    }

    /**
     * En klass för användning av trådar, hämtar instans av MessageProducerBuffer och lägger till i MessageBuffer med delay
     */
    class Counter extends Thread {
        @Override
        public void run() {
            MessageProducer producer;
            Message message;
            while (!Thread.interrupted()) {
                try {
                    producer = producerBuffer.get();
                    for(int times=0; times<producer.times(); times++){
                        for(int index = 0; index<producer.size(); index++){
                            message = producer.nextMessage();
                            //System.out.println(message.getIcon() + ", " + message.getText());
                            messageBuffer.put(message);
                            Thread.sleep(producer.delay());
                        }
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }
}