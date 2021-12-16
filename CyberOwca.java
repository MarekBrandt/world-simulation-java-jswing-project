import java.util.Vector;

public class CyberOwca extends Zwierze {
    public CyberOwca(int x, int y, Swiat swiat) {
        super(x, y, swiat, "CyberOwca", 11, 4);
        this.setSymbol('S');
    }

    public Organizm stworzPotomka(int x, int y) {
        return new CyberOwca(x,y,swiat);
    }

    public void akcja() {
        int d = 1000000, dx = 0, dy = 0;
        int index = -10;
        Vector<Organizm>organizmy = this.swiat.getOrganizmy();
        for(int i = 0; i < organizmy.size(); i++) {
            if(organizmy.get(i) instanceof BarszczSosnowskiego) {
                dx = organizmy.get(i).getPozycja().x - this.getPozycja().x;
                dy = organizmy.get(i).getPozycja().y- this.getPozycja().y;
                int d1 = Math.abs(dx) + Math.abs(dy);
                if(d1 < d) {
                    d = d1;
                    index = i;
                }
            }
        }
        if(index < 0) super.akcja();
        else {
            if(dx != 0) {
                if(dx >0) {
                    super.wykonajRuch(KierunekRuchu.wPrawo);
                }
                else super.wykonajRuch(KierunekRuchu.wLewo);
            }
            else if(dy != 0) {
                if(dy <0) {
                    super.wykonajRuch(KierunekRuchu.wGore);
                }
                else super.wykonajRuch(KierunekRuchu.wDol);
            }
        }
    }
}
