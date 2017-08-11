/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war.jogo;
/**
 *
 * @author thais, rafael
 */
public abstract class Exercito {
    private boolean remanejado;
    
    public Exercito(boolean c){
        this.remanejado = c;
    }
    
    public abstract int combater();

    public boolean isRemanejado() {
        return remanejado;
    }

    public void setRemanejado(boolean remanejado) {
        this.remanejado = remanejado;
    }
}
