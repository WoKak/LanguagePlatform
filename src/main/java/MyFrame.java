import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Created by Michał on 2016-11-24.
 */
public class MyFrame extends JFrame {

    JLabel wordToTranslateLabel;
    JTextArea answerTextArea;
    JButton okButton;
    JButton nextButton;

    public MyFrame() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = 1080;
        int screenWidth = 1920;

        setSize(screenWidth / 4 - 200, screenHeight / 4 - 100);
        setLocationByPlatform(true);
        setLayout(new BorderLayout(100, 10));

        wordToTranslateLabel = new JLabel("Aby rozpocząć kliknij przycisk Next");
        answerTextArea = new JTextArea(1, 20);
        okButton =  new JButton("OK");
        nextButton = new JButton("Next");

        wordToTranslateLabel.setSize(new Dimension(screenWidth / 4 - 50, 100));
        answerTextArea.setSize(new Dimension(100, 100));
        okButton.setSize(new Dimension(100, 100));
        nextButton.setSize(new Dimension(100, 100));

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String answer = getAnswerTextArea().getText();
                String result = KnowledgeBase.findWordInPl(answer);

                if(result == null) {
                    getWordToTranslateLabel().setText("Błąd! poprawna odpowiedź: " +
                            KnowledgeBase.findWordInEng(getWordToTranslateLabel().getText()));
                    getWordToTranslateLabel().setForeground(Color.RED);
                } else {
                    getWordToTranslateLabel().setText("Dobrze!");
                    getWordToTranslateLabel().setForeground(new Color(0,150, 0));
                }
            }
        });


        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Random random = new Random();
                int idx = random.nextInt(KnowledgeBase.getKnowledge().size());
                getWordToTranslateLabel().setText(KnowledgeBase.getKnowledge().get(idx).getWordInPolish());
                getWordToTranslateLabel().setForeground(Color.BLACK);
                getAnswerTextArea().setText("");
            }
        });

        JPanel pane = new JPanel();
        pane.setLayout(new GridLayout(4, 1));

        JPanel paneTop = new JPanel();
        JPanel paneCen = new JPanel();
        JPanel paneBot = new JPanel();

        paneTop.add(wordToTranslateLabel);
        paneCen.add(answerTextArea);
        paneBot.add(okButton);
        paneBot.add(nextButton);

        pane.setLayout(new BorderLayout());
        pane.add(paneTop, BorderLayout.NORTH);
        pane.add(paneCen, BorderLayout.CENTER);
        pane.add(paneBot, BorderLayout.SOUTH);


        add(pane, BorderLayout.CENTER);

        setIconImage(new ImageIcon("pug.png").getImage());
    }

    public JLabel getWordToTranslateLabel() {
        return wordToTranslateLabel;
    }

    public JTextArea getAnswerTextArea() {
        return answerTextArea;
    }
}