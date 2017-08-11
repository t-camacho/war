/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war.jogo;

import java.util.Random;

/**
 *
 * @author thais, rafael
 */
public class Aereo extends Exercito{
    public Aereo(boolean b){
        super(b);
    }

    @Override
    public int combater() {
        Random gerador = new Random();
        int face = gerador.nextInt(4);
        return face;
    }
    
}
