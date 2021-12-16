import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class Swiat implements KeyListener, ActionListener {

    final int LICZBAWILKOW = 2;
    final int LICZBAOWIEC = 2;
    final int LiCZBALISOW = 2;
    final int LiCZBAZOLWI = 2;
    final int LiCZBAANTYLOP = 2;
    final int LiCZBATRAWY = 2;
    final int LiCZBACYBEROWIEC = 2;
    final int LICZBAMLECZY = 2;
    final int LICZBAGUARANY = 2;
    final int LICZBAWILCZYCHJAGOD = 2;
    final int LICZBABARSZCZU = 1;

    private int W;
    private int H;
    private int liczbaOrganizmow;
    private Organizm[][] plansza;
    private final MyFrame frame;
    Czlowiek czlowiek;
    private final Vector<Organizm> organizmy;
    private final Vector<Organizm> organizmyDoUsuniecia;
    private final Vector<Organizm> organizmyDoDodania;
    public Vector<String> komunikaty;

    JButton button;
    JTextField textField;
    JPanel tempPanel;

    Random generator;

    public Swiat(String nazwaPliku) throws FileNotFoundException {
        generator = new Random();
        liczbaOrganizmow = 0;
        czlowiek = null;
        this.organizmy = new Vector<>();
        this.organizmyDoUsuniecia = new Vector<>();
        this.organizmyDoDodania = new Vector<>();
        this.komunikaty = new Vector<>();
        Scanner odczyt = new Scanner(new File("saves/"+nazwaPliku));
        String dane;
        String token;
        Vector <String> tokeny = new Vector<>();
        boolean pierwszalinia = true;
        while(odczyt.hasNextLine()){
            dane = odczyt.nextLine();
            Scanner tokenowanie = new Scanner(dane);
            while (tokenowanie.hasNextLine()) {
                token = tokenowanie.next();
                tokeny.add(token);
            }
            if(pierwszalinia) { H = Integer.parseInt(tokeny.get(0)); W = Integer.parseInt(tokeny.get(1));
                this.plansza = generujPustySwiat();}
            else {
                zrobObiektZTokenowZPliku(tokeny);
            }
            pierwszalinia = false;
            tokeny.clear();
        }
        frame = new MyFrame(Stale.frameWidth, Stale.frameHeight, W, H, Stale.panelsRatio, Stale.tileSize, Stale.tileBorderSize);
        frame.addKeyListener(this);
        rysujSwiat();
    }


    private Organizm[][] generujPustySwiat() {
        Organizm[][] tab = new Organizm[this.H][];
        for (int i = 0; i < this.H; i++) {
            tab[i] = new Organizm[this.W];
            for (int j = 0; j < this.W; j++) {
                tab[i][j] = null;
            }
        }
        return tab;
    }

    public Swiat(int W, int H) {
        generator = new Random();

        this.W = W;
        this.H = H;
        this.liczbaOrganizmow = 0;
        this.plansza = generujPustySwiat();
        this.organizmy = new Vector<>();
        this.organizmyDoUsuniecia = new Vector<>();
        this.organizmyDoDodania = new Vector<>();
        this.komunikaty = new Vector<>();

        frame = new MyFrame(Stale.frameWidth, Stale.frameHeight, W, H, Stale.panelsRatio, Stale.tileSize, Stale.tileBorderSize);
        frame.addKeyListener(this);

        stworzOrganizmy();
        rysujSwiat();
    }

    public void rysujSwiat() {
        for (int i = 0; i < this.H; i++) {
            for (int j = 0; j < (this.W); j++) {
                if (plansza[i][j] == null) {
                    frame.tiles[i * W + j].setIcon(null);
                } else {
                    frame.tiles[i * W + j].setIcon(new ImageIcon("images/" + plansza[i][j].getNazwa() + ".png"));
                }
            }
        }
        frame.setFocusable(true);
    }

    public void dodajOrganizmNaPlansze(int x, int y, Organizm organizm) {
        plansza[y][x] = organizm; //dodaje wskaznik na element do tablicy
    }

    public void usunOrganizmZPlanszy(int x, int y) {
        plansza[y][x] = null;
    }

    public void dodajOrganizmyDoWektora() {

        int size = organizmyDoDodania.size();

        for (int i = 0; i < size; i++) {
            boolean dodano = false;
            if (organizmy.isEmpty()) organizmy.add(0, organizmyDoDodania.get(i));
            else {
                int inicjatywaOrganizmu = organizmyDoDodania.get(i).getInicjatywa();
                int organizmowWWektorze = organizmy.size();

                for (int j = 0; j < organizmowWWektorze; j++) {
                    if (inicjatywaOrganizmu > organizmy.get(j).getInicjatywa()) {
                        organizmy.add(j, organizmyDoDodania.get(i));
                        dodano = true;
                        break;
                    }
                }
                if (!dodano) organizmy.add(organizmyDoDodania.get(i));
            }
        }
        organizmyDoDodania.clear();
    }

    public void dodajOrganizmNaListeDoUsuniecia(Organizm organizm) {
        organizmyDoUsuniecia.add(organizm);
        organizm.setMogeRuszyc(false);
    }

    public void dodajOrganizmNaListeDoDodania(Organizm organizm) {
        organizmyDoDodania.add(organizm);
    }

    public void wykonajTure() {
        dodajOrganizmyDoWektora();
        usunOrganizmyDoUsuniecia();
        pozwolWszystkimRuszyc();

        int organizmowWWektorze = organizmy.size();

        for (int i = 0; i < organizmowWWektorze; i++) {
            if (organizmy.get(i).getMogeRuszyc()) {
                organizmy.get(i).akcja();
            }
        }
        for (int i = 0; i < komunikaty.size(); i++) {
            frame.display.append(komunikaty.get(i));
        }
        komunikaty.clear();
    }

    public void pozwolWszystkimRuszyc() {
        int organizmowWWektorze = organizmy.size();

        for (int i = 0; i < organizmowWWektorze; i++) {
            organizmy.get(i).setMogeRuszyc(true);
        }
    }

    public void usunOrganizmyDoUsuniecia() {
        int size = organizmyDoUsuniecia.size();

        for (Organizm organizm : organizmyDoUsuniecia) {
            int size2 = organizmy.size();
            for (int j = 0; j < size2; j++) {
                if (organizm == organizmy.get(j)) {
                    organizmy.remove(j);
                    break;
                }
            }
        }

        organizmyDoUsuniecia.clear();
    }

    public void zapiszDoPliku() {
        JLabel tempLabel = new JLabel("Nazwa pliku save'a");
        tempLabel.setFont(new Font("MV Boli", Font.PLAIN, 19));
        tempPanel = new JPanel();
        tempPanel.setBounds((int) (Stale.frameWidth * 0.375), (int) (Stale.frameHeight * 7/16), Stale.frameWidth / 4, Stale.frameHeight / 6);
        tempPanel.setBackground(new Color(43, 43, 43 ));
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(Stale.frameWidth/5, Stale.frameHeight/20));
        textField.setBackground(new Color(122, 122, 122));
        textField.setForeground(Color.white);
        textField.setFont(new Font("MV Boli", Font.PLAIN, 25));
        tempLabel.setForeground(Color.white);
        button = new JButton("Zapisz");
        button.setPreferredSize(new Dimension(100, 40));
        button.addActionListener(this);
        button.setFocusable(false);
        tempPanel.add(tempLabel);
        tempPanel.add(textField);
        tempPanel.add(button);
        tempPanel.setFocusable(false);

        frame.add(tempPanel);
        tempPanel.validate();
        tempPanel.repaint();
    }

    public void zapiszDoPlikuZNazwa(String nazwaPliku) throws IOException {
        dodajOrganizmyDoWektora();
        usunOrganizmyDoUsuniecia();

        frame.remove(tempPanel);
        frame.validate();
        frame.repaint();
        frame.setFocusable(true);
        frame.requestFocus();

        if(nazwaPliku.isEmpty()) nazwaPliku = "savedWorld";
        nazwaPliku += ".txt";

        try {
            FileWriter myWriter = new FileWriter("saves/"+nazwaPliku);

            myWriter.write(this.H + " " + this.W + "\n");
            for (int i = 0; i < organizmy.size(); i++) {
                myWriter.write(organizmy.get(i).obiektToString() + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Wystapil problem");
            e.printStackTrace();
        }
    }

    public void stworzOrganizmy() {
        if (LICZBAWILKOW + LICZBAOWIEC + LiCZBALISOW + LICZBABARSZCZU + LICZBAWILCZYCHJAGOD + LICZBAMLECZY + LICZBAGUARANY + LiCZBATRAWY
                + LiCZBAANTYLOP + LiCZBAZOLWI + 1 > H * W) {
            System.out.println("Za mala plansza jak na taka liczbe organizmow, koncze program");
            System.exit(1);
        }

        Wspolrzedne losowePole = getWspolrzedneLosowegoPola();

        new Czlowiek(losowePole.x, losowePole.y, this);


        for (int i = 0; i < LICZBAWILKOW; i++) {
            Wspolrzedne losowePolozenie = getWspolrzedneLosowegoPola();
            new Wilk(losowePolozenie.x, losowePolozenie.y, this);
        }

        for (int i = 0; i < LICZBAOWIEC; i++) {
            Wspolrzedne losowePolozenie = getWspolrzedneLosowegoPola();
            new Owca(losowePolozenie.x, losowePolozenie.y, this);
        }

        for (int i = 0; i < LiCZBALISOW; i++) {
            Wspolrzedne losowePolozenie = getWspolrzedneLosowegoPola();
            new Lis(losowePolozenie.x, losowePolozenie.y, this);
        }

        for (int i = 0; i < LiCZBAZOLWI; i++) {
            Wspolrzedne losowePolozenie = getWspolrzedneLosowegoPola();
            new Zolw(losowePolozenie.x, losowePolozenie.y, this);
        }

        for (int i = 0; i < LiCZBAANTYLOP; i++) {
            Wspolrzedne losowePolozenie = getWspolrzedneLosowegoPola();
            new Antylopa(losowePolozenie.x, losowePolozenie.y, this);
        }

        for (int i = 0; i < LiCZBACYBEROWIEC; i++) {
            Wspolrzedne losowePolozenie = getWspolrzedneLosowegoPola();
            new CyberOwca(losowePolozenie.x, losowePolozenie.y, this);
        }

        for (int i = 0; i < LiCZBATRAWY; i++) {
            Wspolrzedne losowePolozenie = getWspolrzedneLosowegoPola();
            new Trawa(losowePolozenie.x, losowePolozenie.y, this);
        }

        for (int i = 0; i < LICZBAMLECZY; i++) {
            Wspolrzedne losowePolozenie = getWspolrzedneLosowegoPola();
            new Mlecz(losowePolozenie.x, losowePolozenie.y, this);
        }

        for (int i = 0; i < LICZBAGUARANY; i++) {
            Wspolrzedne losowePolozenie = getWspolrzedneLosowegoPola();
            new Guarana(losowePolozenie.x, losowePolozenie.y, this);
        }

        for (int i = 0; i < LICZBAWILCZYCHJAGOD; i++) {
            Wspolrzedne losowePolozenie = getWspolrzedneLosowegoPola();
            new WilczeJagody(losowePolozenie.x, losowePolozenie.y, this);
        }

        for (int i = 0; i < LICZBABARSZCZU; i++) {
            Wspolrzedne losowePolozenie = getWspolrzedneLosowegoPola();
            new BarszczSosnowskiego(losowePolozenie.x, losowePolozenie.y, this);
        }
    }

    void zrobObiektZTokenowZPliku(Vector<String> tokeny) {
    int x = Integer.parseInt(tokeny.get(1));
    int y = Integer.parseInt(tokeny.get(2));
    int sila = Integer.parseInt(tokeny.get(3));
    int inicjatywa = Integer.parseInt(tokeny.get(4));

    char symbol = (tokeny.get(0).substring(0,1)).charAt(0);
    Organizm wskNaOrganizm;
    switch(symbol) {
        case 'W':
            wskNaOrganizm = new Wilk(x, y, this); break;
        case 'O':
            wskNaOrganizm = new Owca(x, y, this); break;
        case 'L':
            wskNaOrganizm = new Lis(x, y, this); break;
        case 'A':
            wskNaOrganizm = new Antylopa(x, y, this); break;
        case 'Z':
            wskNaOrganizm = new Zolw(x, y, this); break;
        case 'C':
            wskNaOrganizm = new Czlowiek(x, y, this);
            czlowiek = (Czlowiek) wskNaOrganizm;
            break;
        case 'T':
            wskNaOrganizm = new Trawa(x, y, this); break;
        case 'M':
            wskNaOrganizm = new Mlecz(x, y, this); break;
        case 'G':
            wskNaOrganizm = new Guarana(x, y, this); break;
        case 'B':
            wskNaOrganizm = new BarszczSosnowskiego(x, y, this); break;
        case 'J':
            wskNaOrganizm = new WilczeJagody(x, y, this); break;
        case 'S':
            wskNaOrganizm = new CyberOwca(x, y ,this); break;
        default:
            throw new IllegalStateException("Unexpected value: " + symbol);
    }
    wskNaOrganizm.setSila(sila); wskNaOrganizm.setInicjatywa(inicjatywa);
}

    int getWidth() {
        return W;
    }

    int getHeight() {
        return H;
    }

    Wspolrzedne getWspolrzedneLosowegoPola() {
        int x = generator.nextInt(getWidth());
        int y = generator.nextInt(getHeight());

        if (getWidth() * getHeight() <= organizmy.size()) return new Wspolrzedne(0, 0);

        while (plansza[y][x] != null) {
            x = generator.nextInt(getWidth());
            y = generator.nextInt(getHeight());
        }

        return new Wspolrzedne(x, y);
    }

    public Organizm getWskZPlanszy(int x, int y) {
        return plansza[y][x];
    }

    public int getLiczbaOrganizmow() {
        return liczbaOrganizmow;
    }

    public void zainkrementujLiczbeOrganizmow() {
        liczbaOrganizmow++;
    }

    public void setCzlowiek(Czlowiek czlowiek) {
        this.czlowiek = czlowiek;
    }

    public Czlowiek getCzlowiek() {
        return czlowiek;
    }

    public Vector<Organizm> getOrganizmy() {
        return organizmy;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(czlowiek != null){
            switch (e.getKeyCode()) {
                case Stale.K_LEFT:
                    czlowiek.setGdziePojdzie(Organizm.KierunekRuchu.wLewo);
                    break;
                case Stale.K_RIGHT:
                    czlowiek.setGdziePojdzie(Organizm.KierunekRuchu.wPrawo);
                    break;
                case Stale.K_UP:
                    czlowiek.setGdziePojdzie(Organizm.KierunekRuchu.wGore);
                    break;
                case Stale.K_DOWN:
                    czlowiek.setGdziePojdzie(Organizm.KierunekRuchu.wDol);
                    break;
                case Stale.K_S:
                    czlowiek.setUmiejSpecjalna();
                    break;
                case Stale.K_Z:
                    zapiszDoPliku();
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == Stale.K_DOWN || e.getKeyCode() == Stale.K_UP || e.getKeyCode() == Stale.K_LEFT
                || e.getKeyCode() == Stale.K_RIGHT || e.getKeyCode() == Stale.K_S) {
            wykonajTure();
            rysujSwiat();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            try {
                zapiszDoPlikuZNazwa(textField.getText());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}

