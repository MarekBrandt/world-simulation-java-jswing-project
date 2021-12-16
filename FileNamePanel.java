import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class FileNamePanel extends JPanel implements ActionListener {
    JFrame parentframe;
    JTextField textField;
    public FileNamePanel(JFrame frame) {
        parentframe = frame;
        JLabel tempLabel = new JLabel("Nazwa pliku save'a", SwingConstants.CENTER);
        tempLabel.setFont(new Font("MV Boli", Font.PLAIN, 25));
        this.setBackground(new Color(43, 43, 43 ));
        this.setLayout(new GridLayout(3,1));
        textField = new JTextField();
        textField.setFont(new Font("MV Boli", Font.PLAIN, 25));
        textField.setPreferredSize(new Dimension(Stale.frameWidth/5, 30));
        textField.setBackground(new Color(122, 122, 122));
        tempLabel.setForeground(Color.white);
        textField.setForeground(Color.white);
        JButton button = new JButton("Otworz");
        button.setFont(new Font("MV Boli", Font.PLAIN, 20));
        button.setPreferredSize(new Dimension(100, 40));
        button.addActionListener(this);
        button.setFocusable(false);
        this.add(tempLabel);
        this.add(textField);
        this.add(button);
        this.setFocusable(false);
        parentframe.add(this);
        parentframe.pack();
        this.validate();
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            parentframe.dispose();
            new Swiat(textField.getText()+".txt");
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }
}
