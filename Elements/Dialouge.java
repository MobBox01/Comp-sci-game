package Elements;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

public class Dialouge extends JFrame
{
    @SuppressWarnings("FieldMayBeFinal")
    private JTextArea textBox = new JTextArea();
    private boolean isDialougeBusy = false;

    public Dialouge()
    {
        setBounds(740, 540, 550, 180);
        setTitle("Dialogue");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(Color.black);
        setLayout(new BorderLayout()); 
        
        textBox.setEditable(false);
        textBox.setFocusable(false);
        textBox.setBackground(Color.BLACK);
        textBox.setForeground(Color.WHITE);
        textBox.setFont(new Font("DialogInput", Font.BOLD, 16));
        textBox.setLineWrap(true);
        textBox.setWrapStyleWord(true);
        
        // remove blinking caret
        textBox.setCaret(new DefaultCaret() {@Override public void paint(Graphics g) {}});

        add(textBox, BorderLayout.CENTER);

        revalidate();
        repaint();
        setVisible(true);
    }

    public void setNewText(String newText)
    {
        textBox.setText(newText);
    }

    public void dialouge(String newText)
    {
        isDialougeBusy = true;
        setNewText("");
        int[] i = {0};
        Timer timer = new Timer(10, time -> 
            {
                if(i[0] == newText.length())
                {
                    ((Timer)time.getSource()).stop();
                    isDialougeBusy = false;
                }
                else
                {
                    textBox.append(newText.substring(i[0],i[0]+1));
                    i[0] += 1;
                }
            }
        );

        timer.start();
    }

    public boolean isDialougeActive()
    {
        return isDialougeBusy;
    }

}