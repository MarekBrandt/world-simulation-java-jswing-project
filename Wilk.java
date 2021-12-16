public class Wilk extends Zwierze{
    public Wilk(int x, int y, Swiat swiat) {
        super(x, y, swiat, "Wilk", 9, 5);
        this.setSymbol('W');
    }

    public Organizm stworzPotomka(int x, int y) {
        return new Wilk(x,y,swiat);
    }
}
