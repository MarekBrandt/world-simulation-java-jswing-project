import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class MyFrame extends JFrame {
    JLabel [] tiles;
    JTextArea display;
    MyFrame(int frameWidth, int frameHeight, int W, int H, double panelsRatio, int tileSize, int tileBorderSize) {

        final int fullTileSize = tileBorderSize*2 + tileSize;
        final int boardWidth = fullTileSize * W;
        final int boardHeight = fullTileSize * H;
        final int panelSymulacjiHeight = (int) (frameHeight*panelsRatio);
        final int panelLogowHeight = frameHeight-panelSymulacjiHeight;

        JPanel panelSymulacji = new JPanel();
        JPanel panelLogow = new JPanel();

        panelSymulacji.setBounds(0, 0, frameWidth, panelSymulacjiHeight);
        panelSymulacji.setBackground(new Color(57, 215, 230));
        panelSymulacji.setLayout(null);
        panelSymulacji.setFocusable(false);

        panelLogow.setBounds(0, panelSymulacjiHeight, frameWidth, panelLogowHeight);
        panelLogow.setBackground(new Color(17, 133, 27));
        panelLogow.setBorder(new TitledBorder(new EtchedBorder(), "Logs"));
        panelLogow.setFocusable(false);
        display = new JTextArea((panelLogowHeight)/40, frameWidth/20);
        display.setFocusable(false);
        display.setFont(new Font("MV Boli", Font.PLAIN, 20));
        display.setEditable(false);
        display.setLineWrap(true);
        display.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(display);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panelLogow.add(scroll);

        JPanel board = new JPanel();
        board.setBounds(frameWidth/2-boardWidth/2, (int) (frameHeight * panelsRatio /2 - boardHeight/2), boardWidth, boardHeight);
        board.setBackground(Color.cyan);
        board.setLayout(null);

        Border border = BorderFactory.createLineBorder(Color.white, tileBorderSize);

        tiles = new JLabel[W*H];
        for(int i = 0; i < W*H; i++) {
            tiles[i] = new JLabel();
            tiles[i].setBackground(Color.black);
            tiles[i].setOpaque(true);
            tiles[i].setIcon(null);
            tiles[i].setBorder(border);
            tiles[i].setBounds(i%W*fullTileSize, i/W*fullTileSize, fullTileSize, fullTileSize);
        }

        this.setTitle("Marek Brandt, 184590");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(frameWidth+16, frameHeight+38);


        this.add(panelLogow);
        this.add(panelSymulacji);
        panelSymulacji.add(board);
        for (int i = 0; i < W*H; i++) {
            board.add(tiles[i]);
        }
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
