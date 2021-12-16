public class Mlecz extends Roslina{
    public Mlecz(int x, int y, Swiat swiat){
        super(x, y, swiat, "Mlecz", 0);
        setSymbol('M');
    }

    public Organizm stworzPotomka(int x, int y) {
        return new Mlecz(x,y,swiat);
    }

    public void akcja() {
        int liczbaProb = 3;
        for ( int i = 0; i < liczbaProb; i++) {
            if(this.getMogeRuszyc()) super.akcja();
        }
    }
}
