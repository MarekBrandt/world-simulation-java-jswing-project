public class Owca extends Zwierze{
    public Owca(int x, int y, Swiat swiat) {
        super(x, y, swiat, "Owca", 4, 4);
        this.setSymbol('O');
    }

    public Organizm stworzPotomka(int x, int y) {
        return new Owca(x,y,swiat);
    }
}
