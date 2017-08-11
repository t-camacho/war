/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war.jogo;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author thais, rafael
 */
public class Jogador {
    protected String cor;
    protected ArrayList<Territorio> territorios;
    protected int exercTerrestreDistr;
    protected int exercAereoDistr;
    
    //CONSTRUTOR
    public Jogador(String cor) {
        this.territorios = new ArrayList();
        this.cor = cor;
    }
    
    //ACESSORES E MODIFICADORES
    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public ArrayList<Territorio> getTerritorios() {
        return territorios;
    }
    
    public void setTerritorios(ArrayList<Territorio> territorios) {
        this.territorios = territorios;
    }

    public int getExercTerrestreDistr() {
        return exercTerrestreDistr;
    }

    public void setExercTerrestreDistr(int exercTerrestreDistr) {
        this.exercTerrestreDistr = exercTerrestreDistr;
    }

    public int getExercAereoDistr() {
        return exercAereoDistr;
    }

    public void setExercAereoDistr(int exercAereoDistr) {
        this.exercAereoDistr = exercAereoDistr;
    }
    
    //METODOS GERAIS
    
    //METODO ADICIONAR O TERRITORIO T A LISTA DE TERRITORIOS DO JOGADOR
    public void ganharTerritorio(Territorio t){
        this.territorios.add(t);
    }
    
    public void perderTerritorio(Territorio t){
        if (territorios.contains(t)) territorios.remove(t);
    }
    
    //O METODOS CALCULA QUANTOS EXERCITO TERRESTRE O JOGADOR 
    //TEM DIREITO NO INICIO DA JOGADA
    public void ganharExercitoTerrestre(){
        //A QUANTIDADE DE EXERCITO RECEBIDA SERÁ A METADE DA QUANTIDADE
        //DE TERRITORIO QUE O JOGADOR POSSUI NO MOMENTO
        int qtd = Math.floorDiv(territorios.size(), 2);
        this.exercTerrestreDistr = qtd;
    }
    
    //O METODO CALCULA QUANTOS EXERCITO AEREO O JOGADOR 
    //TEM DIREITO NO INICIO DA JOGADA
    public void ganharExercitoAereo(){        
        //A QUANTIDADE DE EXERCITO RECEBIDA SERÁ UM TERÇO DA QUANTIDADE
        //DE TERRITORIO QUE O JOGADOR POSSUI NO MOMENTO
        int qtd = Math.floorDiv(territorios.size(), 3);
        this.exercAereoDistr = qtd;
    }
    
    public void limparTerritorios(){
        this.territorios.clear();
    }
    
    public boolean verificarTerritorioJogador(String nome){
        Iterator it = territorios.iterator();
        for(int i = 0; i < territorios.size(); i++ ){
            if(((Territorio)territorios.get(i)).getNome().equals(nome)){
                return true;
            }
        }
        return false;
    }
}
