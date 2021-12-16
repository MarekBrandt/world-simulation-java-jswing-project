import java.util.Vector;

abstract public class Organizm {

    public enum KierunekRuchu {
        wLewo,
        wGore,
        wPrawo,
        wDol,
        zostan
    }

    private int sila;
    private int inicjatywa;
    private int id;
    private Wspolrzedne pozycja;
    private String nazwaObiektu;
    private String nazwaTypu;
    private char symbol;
    private boolean mozeRuszyc;
    protected Swiat swiat;

    public Organizm(int x, int y, Swiat swiat, String nazwaObiektu, String nazwaTypu, int sila, int inicjatywa){
        this.pozycja = new Wspolrzedne(x,y);
        this.swiat = swiat;
        this.nazwaObiektu = nazwaObiektu;
        this.nazwaTypu = nazwaTypu;
        this.sila = sila;
        this.inicjatywa = inicjatywa;
        this.id = swiat.getLiczbaOrganizmow();
        this.swiat.zainkrementujLiczbeOrganizmow();
        this.mozeRuszyc = false;
        this.swiat.dodajOrganizmNaPlansze(x, y, this);
        this.swiat.dodajOrganizmNaListeDoDodania(this);
    }

    abstract void akcja();
    abstract void kolizja(int x, int y, int xnast, int ynast);
    abstract Organizm stworzPotomka(int x, int y);

    public KierunekRuchu losujDostepneSasiedniePole(Wspolrzedne polozenie) {
        Vector<KierunekRuchu> mozliweRuchy = zwrocWektorDostepnychPol(polozenie);

        int ktoryZWektora = this.swiat.generator.nextInt(mozliweRuchy.size());

        return mozliweRuchy.get(ktoryZWektora);
    }

    public KierunekRuchu losujDostepnePusteSasiedniePole(Wspolrzedne polozenie) {
        Vector <KierunekRuchu> mozliweRuchy = zwrocWektorDostepnychPol(polozenie);
        Vector <KierunekRuchu> znaleznionoPuste = new Vector<KierunekRuchu>();

        int x = polozenie.x; int y = polozenie.y;
        int dx, dy;

        for (int i = 0; i < mozliweRuchy.size(); i++) {
            dx = wektorPrzesuniecia(mozliweRuchy.get(i)).x;
            dy = wektorPrzesuniecia(mozliweRuchy.get(i)).y;

            if(swiat.getWskZPlanszy(x+dx, y+dy) == null) znaleznionoPuste.add(mozliweRuchy.get(i));
        }

        if(znaleznionoPuste.isEmpty()) return KierunekRuchu.zostan;

        int ktoryZWektora = this.swiat.generator.nextInt(znaleznionoPuste.size());

        return znaleznionoPuste.get(ktoryZWektora);
    }

    public Vector <KierunekRuchu> zwrocWektorDostepnychPol(Wspolrzedne polozenie) {
        Vector <KierunekRuchu> mozliweRuchy = new Vector<KierunekRuchu>();
        int x = polozenie.x;
        int y = polozenie.y;

        if(x != 0) mozliweRuchy.add(0, KierunekRuchu.wLewo);
        if(x != swiat.getWidth()-1) mozliweRuchy.add(0, KierunekRuchu.wPrawo);
        if(y != 0) mozliweRuchy.add(0, KierunekRuchu.wGore);
        if(y != swiat.getHeight()-1) mozliweRuchy.add(0, KierunekRuchu.wDol);

        return mozliweRuchy;
    }

    public Wspolrzedne wektorPrzesuniecia(KierunekRuchu gdzie) {
        int dx = 0, dy = 0;
        switch (gdzie) {
            case wLewo:
                dx = -1; dy = 0;
                break;
            case wPrawo:
                dx = 1; dy = 0;
                break;
            case wGore:
                dx = 0; dy = -1;
                break;
            case wDol:
                dx = 0; dy = 1;
                break;
            case zostan:
                dx = 0; dy = 0;
                break;
        }

        return new Wspolrzedne(dx, dy);
    }

    public String obiektToString() {
        String znakObiektu = String.valueOf(getSymbol());
        String xpoz = String.valueOf(getPozycja().x);
        String ypoz = String.valueOf(getPozycja().y);
        String silaVal = String.valueOf(sila);
        String inicjatywaVal = String.valueOf(inicjatywa);
        return  znakObiektu + " " + xpoz + " " + ypoz + " " + silaVal + " " + inicjatywaVal;
    }

    Wspolrzedne getPozycja() {
        return pozycja;
    }

    String getNazwa() {
        return nazwaObiektu;
    }

    String getTyp() {
        return nazwaTypu;
    }

    int getSila() {
        return sila;
    }

    int getId() {
        return id;
    }

    boolean getMogeRuszyc() {
        return mozeRuszyc;
    }

    char getSymbol() {
        return symbol;
    }

    int getInicjatywa() {
        return inicjatywa;
    }

    void setSymbol(char znak) {
        this.symbol = znak;
    }

    void setPozycja(int x, int y) {
        this.pozycja.x = x;
        this.pozycja.y = y;
    }

    void setMogeRuszyc(boolean ruszy) {
        this.mozeRuszyc = ruszy;
    }

    void increaseSila(int x) {
        this.sila += x;
    }

    void setSila(int x) {
        this.sila = x;
    }

    void setInicjatywa(int x) {
        this.inicjatywa = x;
    }
}
