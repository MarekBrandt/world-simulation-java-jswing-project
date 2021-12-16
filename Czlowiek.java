import java.util.Vector;

public class Czlowiek extends Zwierze{
    private KierunekRuchu gdziePojdzie;
    private int umiejSpecjalnaAktywna;

    public Czlowiek(int x, int y, Swiat swiat){
        super(x, y, swiat, "Czlowiek", 5,4);
        swiat.setCzlowiek(this);
        umiejSpecjalnaAktywna = -5;
        gdziePojdzie = KierunekRuchu.zostan;
        setSymbol('C');
    }

    public void akcja() {
        umiejSpecjalnaAktywna--;
        Vector<KierunekRuchu> mozliweRuchy = zwrocWektorDostepnychPol(this.getPozycja());
        boolean znaleziono = false;
        int size = mozliweRuchy.size();
        for ( int i = 0; i < size; i++) {
            if(mozliweRuchy.get(i) == gdziePojdzie) znaleziono = true;
        }
        if(!znaleziono) gdziePojdzie = KierunekRuchu.zostan;

        if (gdziePojdzie != KierunekRuchu.zostan) {
            int x = getPozycja().x;
            int y = getPozycja().y;
            int dx, dy;

            dx = wektorPrzesuniecia(gdziePojdzie).x;
            dy = wektorPrzesuniecia(gdziePojdzie).y;

            Organizm wskNaObiektNastepny = swiat.getWskZPlanszy(x + dx, y + dy);

            if (wskNaObiektNastepny == null) {
                setPozycja(x + dx, y + dy);
                this.swiat.komunikaty.add( this.getNazwa() + this.getId() + " poszedl na pole (" + this.getPozycja().x + "," + this.getPozycja().y + ")\n");
                swiat.usunOrganizmZPlanszy(x, y);
                swiat.dodajOrganizmNaPlansze(this.getPozycja().x, this.getPozycja().y, this);
            }
            else {
                if (umiejSpecjalnaAktywna > 0) {
                    Organizm zaatakowany = swiat.getWskZPlanszy(x+dx,y+dy);
                    this.swiat.komunikaty.add( this.getNazwa() + this.getId() + " niszczy, dzieki umiejetnosci specjalnej "
                            + zaatakowany.getNazwa() + zaatakowany.getId() + "\n");
                    swiat.usunOrganizmZPlanszy(x, y);
                    swiat.dodajOrganizmNaPlansze(x + dx, y + dy, this);
                    setPozycja(x + dx, y + dy);
                    swiat.dodajOrganizmNaListeDoUsuniecia(wskNaObiektNastepny);
                }
                else {
                    wskNaObiektNastepny.kolizja(x, y, x + dx, y + dy);
                }
            }
        }
    }

    public void kolizja(int x, int y, int xnast, int ynast) {
        Organizm wskNaAtakujacego = swiat.getWskZPlanszy(x, y);
        if (umiejSpecjalnaAktywna <= 0) {
            super.kolizja(x, y, xnast, ynast);
        }
        else {
            if (wskNaAtakujacego.getSila() < this.getSila()) super.kolizja(x, y, xnast, ynast);
        else {
                KierunekRuchu wylosowanyKierunek = losujDostepneSasiedniePole(new Wspolrzedne(xnast, ynast));
                int dx = wektorPrzesuniecia(wylosowanyKierunek).x;
                int dy = wektorPrzesuniecia(wylosowanyKierunek).y;

                Organizm obiektNaWylosowanym = swiat.getWskZPlanszy(xnast + dx, ynast + dy);

                if (obiektNaWylosowanym != null) {
                    swiat.dodajOrganizmNaListeDoUsuniecia(obiektNaWylosowanym);
                }

                swiat.dodajOrganizmNaPlansze(xnast + dx, ynast + dy, this);
                swiat.usunOrganizmZPlanszy(x, y);
                swiat.dodajOrganizmNaPlansze(xnast, ynast, wskNaAtakujacego);
            }
        }
    }

    void setGdziePojdzie(KierunekRuchu gdzie) {
        gdziePojdzie = gdzie;
    }

    void setUmiejSpecjalna() {
        gdziePojdzie = KierunekRuchu.zostan;
        if (umiejSpecjalnaAktywna < -3)
        {
            umiejSpecjalnaAktywna = 7;
            this.swiat.komunikaty.add( "Niesmiertelnosc odpalona\n");
        }
        else
            this.swiat.komunikaty.add( "Nie minelo jeszcze 5 tur od ostatniego uzycia\n");
    }

    Organizm stworzPotomka(int x, int y) {
        return new Czlowiek(x,y,swiat);
    }

    boolean getAktywnoscSpecjalnej() {
        return umiejSpecjalnaAktywna > 0;
    }
}
