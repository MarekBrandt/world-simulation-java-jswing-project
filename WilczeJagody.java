public class WilczeJagody extends Roslina{
    public WilczeJagody(int x, int y, Swiat swiat){
        super(x, y, swiat, "Wilcza_jagoda", 99);
        setSymbol('J');
    }

    public Organizm stworzPotomka(int x, int y) {
        return new WilczeJagody(x, y, swiat);
    }

    public void kolizja(int x, int y, int xnast, int ynast) {
        Organizm wskNaObiektKtoryWejdzie = swiat.getWskZPlanszy(x,y);
        this.swiat.komunikaty.add(wskNaObiektKtoryWejdzie.getNazwa() + wskNaObiektKtoryWejdzie.getId() + " ginie, bo zjadl " + this.getNazwa() + this.getId() + "\n");
        swiat.usunOrganizmZPlanszy(x, y);
        swiat.usunOrganizmZPlanszy(xnast, ynast);
        swiat.dodajOrganizmNaListeDoUsuniecia(wskNaObiektKtoryWejdzie);
        swiat.dodajOrganizmNaListeDoUsuniecia(this);
    }
}
