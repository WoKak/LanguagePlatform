import javax.swing.*;
import java.awt.*;

/**
 * Created by Michał on 2016-11-24.
 */
public class GUI {
    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                MyFrame frame = new MyFrame();
                frame.setTitle("plang");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setResizable(true);
                frame.setVisible(true);

                KnowledgeBase knowledgeBase = new KnowledgeBase("sources.txt");
            }
        });
    }
}