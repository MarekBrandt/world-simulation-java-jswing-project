abstract public class Zwierze extends Organizm{
    public Zwierze(int x, int y, Swiat swiat, String nazwaObiektu, int sila, int inicjatywa){
        super(x, y, swiat, nazwaObiektu, "Zwierze", sila, inicjatywa);
    }

    public void wykonajRuch(KierunekRuchu gdzie) {
        int x = getPozycja().x;
        int y = getPozycja().y;
        int dx, dy;

        dx = wektorPrzesuniecia(gdzie).x;
        dy = wektorPrzesuniecia(gdzie).y;

        if(swiat.getWskZPlanszy(x + dx,y + dy) == null) {
            setPozycja(x + dx , y + dy);
            this.swiat.komunikaty.add(this.getNazwa()+this.getId()+" poszedl na pole ("+this.getPozycja().x+","+this.getPozycja().y+")\n");
            swiat.usunOrganizmZPlanszy(x, y);
            swiat.dodajOrganizmNaPlansze(this.getPozycja().x, this.getPozycja().y, this);
        }
        else swiat.getWskZPlanszy(x + dx,y + dy).kolizja(x, y, x + dx, y + dy);
    }

    public void akcja() {
        KierunekRuchu dostepnePole = losujDostepneSasiedniePole(this.getPozycja());
        if(dostepnePole != KierunekRuchu.zostan) {
            wykonajRuch(dostepnePole);
        }
    }

    public void kolizja(int xpoprz, int ypoprz, int xnast, int ynast) {

        Organizm wskNaObiektKtoryWchodzi = swiat.getWskZPlanszy(xpoprz, ypoprz);

        if(wskNaObiektKtoryWchodzi.getNazwa().equals(this.getNazwa())) {
            reprodukcja(wskNaObiektKtoryWchodzi, xpoprz, ypoprz, xnast, ynast);
        }
    else {
            walka(wskNaObiektKtoryWchodzi, xpoprz, ypoprz, xnast, ynast);
        }
    }

    public void walka(Organizm wskNaNowyObiekt, int xpoprz, int ypoprz, int xnast, int ynast) {
        int sila = this.getSila();
        if( wskNaNowyObiekt.getSila() >= sila) {
            wskNaNowyObiekt.setMogeRuszyc(false);
            this.swiat.komunikaty.add(wskNaNowyObiekt.getNazwa()+wskNaNowyObiekt.getId()+" idzie na pole (" + xnast + "," + ynast + ") i ");
            this.swiat.komunikaty.add("zabija "+this.getNazwa()+this.getId()+"\n");
            swiat.usunOrganizmZPlanszy(xnast, ynast);
            swiat.usunOrganizmZPlanszy(xpoprz, ypoprz);
            swiat.dodajOrganizmNaListeDoUsuniecia(this);
            swiat.dodajOrganizmNaPlansze(xnast, ynast, wskNaNowyObiekt);
            wskNaNowyObiekt.setPozycja(xnast, ynast);
        }
        else {
            this.swiat.komunikaty.add(wskNaNowyObiekt.getNazwa() + wskNaNowyObiekt.getId() + " idzie na pole (" + xnast + "," + ynast + ") i ");
            this.swiat.komunikaty.add("zostaje zabity przez "+ this.getNazwa()+this.getId()+"\n");
            swiat.usunOrganizmZPlanszy(xpoprz, ypoprz);
            swiat.dodajOrganizmNaListeDoUsuniecia(wskNaNowyObiekt);
        }
    }

    public void reprodukcja(Organizm wskNaNowyObiekt, int xpoprz, int ypoprz, int xnast, int ynast) {
        this.setMogeRuszyc(false);
        wskNaNowyObiekt.setMogeRuszyc(false);
        this.swiat.komunikaty.add(wskNaNowyObiekt.getNazwa() + wskNaNowyObiekt.getId() + " i " + this.getNazwa() + this.getId() + " zostaja rodzicami\n");
        if(losujDostepnePusteSasiedniePole(new Wspolrzedne(xpoprz, ypoprz)) != KierunekRuchu.zostan) {
            KierunekRuchu pole = losujDostepnePusteSasiedniePole(new Wspolrzedne(xpoprz, ypoprz));
            int dx = wektorPrzesuniecia(pole).x;
            int dy = wektorPrzesuniecia(pole).y;
            Organizm potomek = this.stworzPotomka(xpoprz + dx, ypoprz + dy);
            this.swiat.komunikaty.add(potomek.getNazwa() + potomek.getId() + " urodzil sie na polu ("  + potomek.getPozycja().x + "," + potomek.getPozycja().y + ")\n");
        }
        else if (losujDostepnePusteSasiedniePole(new Wspolrzedne(xnast, ynast)) != KierunekRuchu.zostan) {
            KierunekRuchu pole = losujDostepnePusteSasiedniePole(new Wspolrzedne(xnast, ynast));
            int dx = wektorPrzesuniecia(pole).x;
            int dy = wektorPrzesuniecia(pole).y;
            Organizm potomek = this.stworzPotomka(xnast + dx, ynast + dy);
            this.swiat.komunikaty.add(potomek.getNazwa() + potomek.getId() + " urodzil sie na polu ("  + potomek.getPozycja().x + "," + potomek.getPozycja().y + ")\n");
        }
        else this.swiat.komunikaty.add("Niestety, dziecko nie mialo miejsca i zmarlo\n");
    }

}
