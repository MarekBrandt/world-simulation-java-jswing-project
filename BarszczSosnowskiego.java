import java.util.Vector;

public class BarszczSosnowskiego extends Roslina{
    public BarszczSosnowskiego(int x, int y, Swiat swiat) {
        super(x, y, swiat, "Barszcz_sosnowskiego", 10);
        setSymbol('B');
    }

    public Organizm stworzPotomka(int x, int y) {
        return new BarszczSosnowskiego(x, y, swiat);
    }

    public void akcja() {
        Vector<KierunekRuchu> wektorSasiadow = zwrocWektorDostepnychPol(getPozycja());

        int x = getPozycja().x;
        int y = getPozycja().y;
        int dx, dy;

        int size = wektorSasiadow.size();
        for ( int i = 0; i < size; i++) {
            dx = wektorPrzesuniecia(wektorSasiadow.get(i)).x;
            dy = wektorPrzesuniecia(wektorSasiadow.get(i)).y;

            Wspolrzedne wspSasiada = new Wspolrzedne(x + dx, y + dy);

            Organizm wskNaSasiada = swiat.getWskZPlanszy(wspSasiada.x, wspSasiada.y);

            if(wskNaSasiada != null && wskNaSasiada.getTyp() == "Zwierze" && wskNaSasiada.getNazwa() != "CyberOwca" && !(wskNaSasiada.getNazwa() == "Czlowiek" && swiat.getCzlowiek().getAktywnoscSpecjalnej() == true)) {
                this.swiat.komunikaty.add(this.getNazwa() + this.getId() + " zabija " + wskNaSasiada.getNazwa() + wskNaSasiada.getId() + "\n");
                swiat.usunOrganizmZPlanszy( wspSasiada.x, wspSasiada.y);
                swiat.dodajOrganizmNaListeDoUsuniecia(wskNaSasiada);
            }
        }
        super.akcja();
    }

    public void kolizja(int x, int y, int xnast, int ynast) {
        Organizm wskNaObiektKtoryWejdzie = swiat.getWskZPlanszy(x,y);

        if(wskNaObiektKtoryWejdzie.getNazwa() != "CyberOwca") {
            this.swiat.komunikaty.add( wskNaObiektKtoryWejdzie.getNazwa() + wskNaObiektKtoryWejdzie.getId() + " ginie, bo zjadl "
                    + this.getNazwa() + this.getId() + "\n");
            swiat.dodajOrganizmNaListeDoUsuniecia(wskNaObiektKtoryWejdzie);
            swiat.usunOrganizmZPlanszy(x, y);
            if(wskNaObiektKtoryWejdzie.getSila() >= this.getSila()){
            swiat.usunOrganizmZPlanszy(xnast, ynast);
            swiat.dodajOrganizmNaListeDoUsuniecia(this);}
        }
        else {
            this.swiat.komunikaty.add( wskNaObiektKtoryWejdzie.getNazwa() + wskNaObiektKtoryWejdzie.getId() + " idzie na (" + xnast + ","
                    + ynast + ") i zjada " + this.getNazwa() + this.getId() + "\n");
            wskNaObiektKtoryWejdzie.setPozycja(xnast, ynast);
            swiat.dodajOrganizmNaPlansze(xnast, ynast, wskNaObiektKtoryWejdzie);
            swiat.usunOrganizmZPlanszy(x, y);
            swiat.dodajOrganizmNaListeDoUsuniecia(this);
        }

    }
}
