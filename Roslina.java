abstract public class Roslina extends Organizm{
    final int SZANSZANAZASIANIE = 5;

    public Roslina(int x, int y, Swiat swiat, String nazwaObiektu, int sila) {
        super(x, y, swiat, nazwaObiektu, "Roslina", sila, 0);
    }

    public void akcja() {
        int los = this.swiat.generator.nextInt(100) + 1;
        if(los <= SZANSZANAZASIANIE) {
            KierunekRuchu ktorePole = losujDostepnePusteSasiedniePole(this.getPozycja());
            if(ktorePole != KierunekRuchu.zostan) {
                rozprzestrzeniajSie(ktorePole);
                this.setMogeRuszyc(false);
            }
        }
    }

    public void rozprzestrzeniajSie(KierunekRuchu gdzie) {
        int x = getPozycja().x;
        int y = getPozycja().y;
        int dx, dy;

        dx = wektorPrzesuniecia(gdzie).x;
        dy = wektorPrzesuniecia(gdzie).y;

        Organizm potomek = this.stworzPotomka(x + dx, y + dy);
        this.swiat.komunikaty.add(this.getNazwa() + this.getId() + " rozprzestrzenia sie" + "\n");
        this.swiat.komunikaty.add("Powstaje nowa " + potomek.getNazwa() + potomek.getId()
                + " na polu (" + (x+dx) + "," + (y+dy) + ")" + "\n");
    }

    public void kolizja(int x, int y, int xnast, int ynast) {
        Organizm wskNaObiektKtoryWejdzie = swiat.getWskZPlanszy(x,y);

        if(wskNaObiektKtoryWejdzie.getSila() >= this.getSila()) {
            wskNaObiektKtoryWejdzie.setMogeRuszyc(false);
            this.swiat.komunikaty.add(wskNaObiektKtoryWejdzie.getNazwa() + wskNaObiektKtoryWejdzie.getId() + " idzie na pole (" + xnast + "," + ynast + ") i ");
            this.swiat.komunikaty.add("zjada "+this.getNazwa()+this.getId()+"\n");
            swiat.usunOrganizmZPlanszy(xnast, ynast);
            swiat.usunOrganizmZPlanszy(x, y);
            swiat.dodajOrganizmNaListeDoUsuniecia(this);
            swiat.dodajOrganizmNaPlansze(xnast, ynast, wskNaObiektKtoryWejdzie);
            wskNaObiektKtoryWejdzie.setPozycja(xnast, ynast);
        }
    else {
            this.swiat.komunikaty.add(wskNaObiektKtoryWejdzie.getNazwa() + wskNaObiektKtoryWejdzie.getId() + " idzie na pole (" + xnast + "," + ynast + ") i ");
            this.swiat.komunikaty.add("zostaje zabity przez "+ this.getNazwa()+this.getId()+"\n");
            swiat.usunOrganizmZPlanszy(x, y);
            swiat.dodajOrganizmNaListeDoUsuniecia(wskNaObiektKtoryWejdzie);
        }
    }
}
