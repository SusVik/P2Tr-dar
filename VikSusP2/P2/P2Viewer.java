import javax.management.ObjectInstance;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class P2Viewer extends Viewer implements PropertyChangeListener {

    private MessageClient messageClient;
    public P2Viewer(MessageClient messageClient, int width, int height){ //P1Viewer har Viewer
        super(width, height);
        this.messageClient = messageClient;
        messageClient.addListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getPropertyName().equals("mh") && evt.getNewValue() instanceof Message){
            setMessage((Message) evt.getNewValue());
        }
    }
}
