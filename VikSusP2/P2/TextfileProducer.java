import javax.swing.*;
import java.io.*;

public class TextfileProducer implements MessageProducer, Serializable{
    private int times;
    private int delay;
    private int size;
    private int currentIndex = -1;
    Message[] messages;

    public TextfileProducer(String filename) throws FileNotFoundException, IOException {

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(filename), "UTF-8"))) {// konvertera till tecken

            times = Integer.parseInt(br.readLine());
            delay = Integer.parseInt(br.readLine());
            size = Integer.parseInt(br.readLine());
            messages = new Message[size];
            int x =0;
               while (size > 0 && size < 10){
                   String text, icon;
                   text = br.readLine();
                   icon = br.readLine();
                 messages[x] = new Message(text, new ImageIcon(icon));
                 x++;
                }
            }
            catch (Exception e){//Catch exception if any
                System.err.println("Error: " + e.getMessage());
            }
        }


    @Override
    public int delay() {
        return delay;
    }

    @Override
    public int times() {
        return times;
    }


    @Override
    public int size() {
        return size;
    }


    @Override
    public Message nextMessage() {
        if (size() == 0) return null;
        currentIndex = (currentIndex + 1) % messages.length;
        return messages[currentIndex];
    }
}
