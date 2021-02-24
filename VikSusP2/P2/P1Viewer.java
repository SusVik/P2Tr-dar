public class P1Viewer {

    private Viewer viewer;
    private MessageManager messageManager;

    public P1Viewer(MessageManager messageManager, int width, int height){
        this.messageManager = messageManager;
        viewer = new Viewer(width, height); //
        messageManager.registerListener(new seeMessage()); //Instanser i arraylist
    }

    public Viewer getViewer() {
        return viewer;
    }

    private class seeMessage implements IManager {

        @Override
        public void sendMessage(Message message) {
            viewer.setMessage(message);
        }
    }
}
