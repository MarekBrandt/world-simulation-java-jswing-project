public class Guarana extends Roslina{
    public Guarana(int x, int y, Swiat swiat) {
        super(x, y, swiat, "Guarana", 0);
        setSymbol('G');
    }

    public Organizm stworzPotomka(int x, int y) {
        return new Guarana(x, y, swiat);
    }

    public void kolizja(int x, int y, int xnast, int ynast) {
        Organizm wskNaObiektKtoryWejdzie = swiat.getWskZPlanszy(x,y);
        wskNaObiektKtoryWejdzie.increaseSila(3);
        super.kolizja(x, y, xnast, ynast);
    }
}
