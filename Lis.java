import java.util.Vector;

public class Lis extends Zwierze{

    public Lis(int x, int y, Swiat swiat){
        super(x, y, swiat, "Lis", 3, 7);
        setSymbol('L');
    }

    public Organizm stworzPotomka(int x, int y) {
        return new Lis(x,y,swiat);
    }

    public KierunekRuchu losujDostepneSasiedniePole(Wspolrzedne polozenie) {
        Vector<KierunekRuchu> dostepnePola = zwrocWektorDostepnychPol(polozenie);
        Vector <KierunekRuchu> polaDostepneDlaLisa = new Vector<>();

        int size = dostepnePola.size();
        for ( int i = 0; i < size; i++) {
            Wspolrzedne przesuniecie = wektorPrzesuniecia(dostepnePola.get(i));
            Organizm tempOrganizm = swiat.getWskZPlanszy(polozenie.x + przesuniecie.x, polozenie.y + przesuniecie.y);
            if(tempOrganizm == null || tempOrganizm.getSila() <= this.getSila()) {
                polaDostepneDlaLisa.add(0, dostepnePola.get(i));
            }
        }

        if(polaDostepneDlaLisa.isEmpty()) return KierunekRuchu.zostan;

        size = polaDostepneDlaLisa.size();

        int ktorePole = this.swiat.generator.nextInt(size);

        return polaDostepneDlaLisa.get(ktorePole);
    }
}
