import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;

public class MenuFrame extends JFrame implements ActionListener {
    JButton startButton;
    JButton loadButton;
    JButton exitButton;
    JButton button;
    JTextField textField;
    JPanel tempPanel;
    JLabel label;
    public MenuFrame() {
        Dimension buttonSize = new Dimension(400, 100);
        label = new JLabel("Menu", SwingConstants.CENTER);
        label.setPreferredSize(buttonSize);
        label.setFont(new Font("MV Boli", Font.PLAIN, 40));
        startButton = new JButton("Rozpocznij");
        loadButton = new JButton("Zaladuj z pliku");
        exitButton = new JButton("Wyjdz");
        startButton.addActionListener(this);
        startButton.setPreferredSize(buttonSize);
        loadButton.addActionListener(this);
        loadButton.setPreferredSize(buttonSize);
        exitButton.addActionListener(this);
        exitButton.setPreferredSize(buttonSize);

        startButton.setFocusable(false);
        exitButton.setFocusable(false);
        loadButton.setFocusable(false);

        startButton.setFont(new Font("MV Boli", Font.PLAIN, 20));
        loadButton.setFont(new Font("MV Boli", Font.PLAIN, 20));
        exitButton.setFont(new Font("MV Boli", Font.PLAIN, 20));

        this.setTitle("Marek Brandt, s184590");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        this.setLayout(new GridLayout(4,1));
        this.setResizable(false);

        this.add(label);
        this.add(startButton);
        this.add(loadButton);
        this.add(exitButton);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == startButton) {
            this.remove(label);
            this.remove(startButton);
            this.remove(exitButton);
            this.remove(loadButton);
            this.setLayout(new GridLayout(1,1));
            new SlidersPanel(this);

        }
        else if (e.getSource() == loadButton) {
                this.remove(label);
                this.remove(startButton);
                this.remove(loadButton);
                this.remove(exitButton);
                this.setLayout(new GridLayout(1,1));
                FileNamePanel fileNamePanel = new FileNamePanel(this);
            }
        else if(e.getSource() == exitButton) {
            System.exit(0);
        }
    }
}
