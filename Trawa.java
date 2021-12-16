public class Trawa extends Roslina{
    public Trawa(int x, int y, Swiat swiat) {
        super(x, y, swiat, "Trawa", 0);
        setSymbol('T');
    }

    Organizm stworzPotomka(int x, int y) {
        return new Trawa(x,y,swiat);
    }
}
