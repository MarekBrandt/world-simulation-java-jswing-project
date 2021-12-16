public class Antylopa extends Zwierze{
    public Antylopa(int x, int y, Swiat swiat) {
        super(x, y, swiat, "Antylopa", 4, 4);
        setSymbol('A');
    }

    public Organizm stworzPotomka(int x, int y) {
        return new Antylopa(x,y,swiat);
    }

    public void akcja() {
        int zasieg = 2;
        for( int i = 0; i < zasieg; i++) {
            if(getMogeRuszyc())
                super.akcja();
        }
    }

    public void walka(Organizm wskNaNowyObiekt, int x, int y, int xnast, int ynast) {
        boolean los = swiat.generator.nextBoolean();
        if(los) {
            super.walka(wskNaNowyObiekt, x, y, xnast, ynast);
        }
        else {
            KierunekRuchu gdzie = losujDostepnePusteSasiedniePole(new Wspolrzedne(xnast, ynast));
            if(gdzie == KierunekRuchu.zostan) super.walka(wskNaNowyObiekt, x, y, xnast, ynast);
            else {
                int xnowy = xnast + wektorPrzesuniecia(gdzie).x;
                int ynowy = ynast + wektorPrzesuniecia(gdzie).y;
                this.setPozycja(xnowy, ynowy);
                swiat.dodajOrganizmNaPlansze(xnowy, ynowy, this);
                wskNaNowyObiekt.setPozycja(xnast, ynast);
                swiat.usunOrganizmZPlanszy(x,y);
                swiat.dodajOrganizmNaPlansze(xnast,ynast, wskNaNowyObiekt);
                this.swiat.komunikaty.add(wskNaNowyObiekt.getNazwa()+wskNaNowyObiekt.getId() + " idzie na pole ("+wskNaNowyObiekt.getPozycja().x
                        +","+wskNaNowyObiekt.getPozycja().y+") ale "+this.getNazwa()+this.getId()+" ucieka\n");
            }
        }

    }
}
