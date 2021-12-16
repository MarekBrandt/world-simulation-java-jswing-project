import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SlidersPanel extends JPanel implements ChangeListener, ActionListener {
    JLabel Wlabel;
    JLabel Hlabel;
    JSlider Wslider;
    JSlider Hslider;
    JButton button;
    JFrame frame;
    SlidersPanel(JFrame frame) {
        this.frame = frame;
        int maxW = Stale.frameWidth/(Stale.tileBorderSize*2+Stale.tileSize);
        int maxH = (int)(Stale.frameHeight*Stale.panelsRatio)/(Stale.tileBorderSize*2+Stale.tileSize);
        this.setBackground(new Color(77, 77, 77));
        this.setLayout(new GridLayout(5,1));
        Wlabel = new JLabel();
        Hlabel = new JLabel();
        Wlabel.setFont(new Font("MV Boli", Font.PLAIN, 25));
        Hlabel.setFont(new Font("MV Boli", Font.PLAIN, 25));
        Wlabel.setForeground(Color.white);
        Hlabel.setForeground(Color.white);
        Wslider = new JSlider(1, maxW, (int)(maxW/2));
        Wslider.addChangeListener(this);
        Wslider.setBackground(new Color(77, 77, 77));
        Hslider = new JSlider(1, maxH, (int)(maxH/2));
        Hslider.addChangeListener(this);
        Hslider.setBackground(new Color(77, 77, 77));

        Wslider.setPreferredSize(new Dimension(400, 40));
        Hslider.setPreferredSize(new Dimension(400, 40));

        button = new JButton("Stworz plansze");
        button.setFont(new Font("MV Boli", Font.PLAIN, 20));
        button.setPreferredSize(new Dimension(250, 50));
        button.addActionListener(this);

        this.add(Wslider);
        this.add(Wlabel);
        this.add(Hslider);
        this.add(Hlabel);
        this.add(button);

        Wlabel.setText("Szerokosc planszy:"+Wslider.getValue());
        Hlabel.setText("Wysokosc planszy:"+Hslider.getValue());
        Wlabel.setHorizontalAlignment(SwingConstants.CENTER);
        Hlabel.setHorizontalAlignment(SwingConstants.CENTER);

        this.setVisible(true);

        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(false);
        frame.setVisible(true);
    }
    @Override
    public void stateChanged(ChangeEvent e) {
        if(e.getSource() == Wslider) {
            Wlabel.setText("Szerokosc planszy:"+Wslider.getValue());
        }
        else if (e.getSource() == Hslider) {
            Hlabel.setText("Wysokosc planszy:"+Hslider.getValue());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button) {
            frame.dispose();
            int W = Wslider.getValue();
            int H = Hslider.getValue();
            new Swiat(W, H);
        }
    }
}
