public class Zolw extends Zwierze{
    public Zolw(int x, int y, Swiat swiat){
        super(x, y , swiat, "Zolw", 2, 1);
        setSymbol('Z');
    }

    public Organizm stworzPotomka(int x, int y) {
        return new Zolw(x,y,swiat);
    }

    public void akcja() {
        int los = swiat.generator.nextInt(100)+1;
        if(los >= 75) {
            super.akcja();
        }
    }

    public void walka(Organizm wskNaNowyObiekt, int xpoprz, int ypoprz, int xnast, int ynast) {
        if(wskNaNowyObiekt.getSila() < 5) {
            this.swiat.komunikaty.add(this.getNazwa() + this.getId() + " odparl atak " + wskNaNowyObiekt.getNazwa() + wskNaNowyObiekt.getId() +"\n");
        }
        else super.walka(wskNaNowyObiekt, xpoprz, ypoprz, xnast, ynast);
    }
}
