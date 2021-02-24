public class MessageProducerInput {
    private Buffer<MessageProducer> buffer;

    //Servern anropar metoden addmessageProducer i MessageProducerInput
    public MessageProducerInput(Buffer<MessageProducer> buffer){
        this.buffer = buffer;
    }

    public void addMessageProducer(MessageProducer m){
        this.buffer.put(m);
    }
}