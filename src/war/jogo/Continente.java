/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war.jogo;

import java.util.ArrayList;

/**
 *
 * @author thais, rafael
 */
public enum Continente {
    AFRICA("Africa", 6), AMERICA_DO_NORTE("America do Norte", 7),
    AMERICA_DO_SUL("America do Sul", 4), ASIA("Asia", 7),
    EUROPA("Europa", 5), OCEANIA("Oceania", 4);
    
    //ATRIBUTOS
    private String nome;
    private int qtdTerritorio;
    
    //CONSTRUTOR
    Continente(String nome, int qtd){
        this.nome = nome;
        this.qtdTerritorio = qtd;
    }
    
    //ACESSORES E MODIFICADORES
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQtdTerritorio() {
        return qtdTerritorio;
    }

    private void setQtdTerritorio(int qtd_territorio) {
        this.qtdTerritorio = qtd_territorio;
    }
    
    public boolean verificarContinenteAdjacentes(Continente c2){
        if(this == AFRICA){
            if(c2 == ASIA || c2 == OCEANIA || c2 == EUROPA || c2 == AMERICA_DO_NORTE){
                return true;
            }
        }else if(this == ASIA){
            if(c2 == AFRICA || c2 == EUROPA || c2 == AMERICA_DO_NORTE || c2 == OCEANIA || c2 == AMERICA_DO_SUL){
                return true;
            }
        }else if(this == OCEANIA){
            if(c2 == AFRICA || c2 == AMERICA_DO_SUL || c2 == ASIA){
                return true;
            }
        }else if(this == EUROPA){
            if(c2 == AFRICA || c2 == ASIA || c2 == AMERICA_DO_NORTE){
                return true;
            }
        }else if(this == AMERICA_DO_SUL){
            if(c2 == ASIA || c2 == AMERICA_DO_NORTE || c2 == OCEANIA){
                return true;
            }
        }else if(this == AMERICA_DO_NORTE){
            if(c2 == ASIA || c2 == AMERICA_DO_SUL || c2 == EUROPA || c2 == AFRICA){
                return true;
            }
        }
        return false;
    }
}
