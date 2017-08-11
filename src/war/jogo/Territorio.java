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
public class Territorio {
    private String nome;
    private Continente continente;
    private Jogador jogador;
    private ArrayList<Exercito> exercitos;
    private ArrayList<Territorio> fronteiras;
    
    //CONSTRUTOR
    public Territorio(String nome, Continente continente) {
        this.exercitos = new ArrayList();
        exercitos.add(new Terrestre(false));
        exercitos.add(new Aereo(false));
        this.nome = nome;
        this.continente = continente;
    }
    //ACESSORES E MODIFICADORES
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Continente getContinente() {
        return continente;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }
    
    public ArrayList<Exercito> getExercitos(){
        return this.exercitos;
    }

    public ArrayList<Exercito> getExercTerrestre() {
        ArrayList<Exercito> t = new ArrayList<>();
        for(Exercito exercito: this.exercitos){
            if(exercito instanceof Terrestre){
                t.add(exercito);
            }
        }
        return t;
    }
    
    public ArrayList<Exercito> getExercAereo() {
        ArrayList<Exercito> t = new ArrayList<>();
        for(Exercito exercito: exercitos){
            if(exercito instanceof Aereo){
                t.add(exercito);
            }
        }
        return t;
    }
    
    public Exercito getUmExercAereo(){
        for(Exercito exercito: exercitos){
            if(exercito instanceof Aereo){
                return exercito;
            }
        } 
        return null;
    }

     public int getQuantExercTerrestre(){
        int aux = 0;
        for(Exercito exercito: this.exercitos){
            if(exercito instanceof Terrestre){
                aux++;
            }
        }
        return aux;
    }
    
    public int getQuantExercAereo(){
        int aux = 0;
        for(Exercito exercito: exercitos){
            if(exercito instanceof Aereo){
                aux++;
            }
        }
        return aux;
    }

    public ArrayList<Territorio> getFronteiras() {
        return fronteiras;
    }

    public void setFronteiras(ArrayList<Territorio> fronteiras) {
        this.fronteiras = fronteiras;
    }
    
    
    
    public void addExercito(Exercito t){
        this.exercitos.add(t);
    }
    
    public void removerExercitoTerrestre(){
        for(int i = 0; i < exercitos.size(); i++){
            if(exercitos.get(i) instanceof Terrestre){
                exercitos.remove(i);
                break;
            }
        }
    }
    //SO REMOVE SE O EXERCITO N FOI REMANEJADO NAQUELA JOGADA
    public void removerExercitoTerrestreRemanejado(){
        for(int i = 0; i < exercitos.size(); i++){
            if(exercitos.get(i) instanceof Terrestre && !exercitos.get(i).isRemanejado()){
                exercitos.remove(i);
                break;
            }
        }
    }
    
    public void removerExercitoAereoRemanejado(){
        for(int i = 0; i < exercitos.size(); i++){
            if(exercitos.get(i) instanceof Aereo && !exercitos.get(i).isRemanejado()){
                exercitos.remove(i);
                break;
            }
        }
    }
    
    public void removerExercitoAereo(){
        for(int i = 0; i < exercitos.size(); i++){
            if(exercitos.get(i) instanceof Aereo){
                exercitos.remove(i);
                break;
            }
        }
    }
    
    public int qtdExercitoTerrestreNRemanejados(){
        int aux = 0;
        for(Exercito exercito: exercitos){
            if(exercito instanceof Terrestre && !exercito.isRemanejado()){
                aux++;
            }
        }
        return aux;
    }
    
    public int qtdExercitoAereoNRemanejados(){
        int aux = 0;
        
        for(Exercito exercito: exercitos){
            if(exercito instanceof Aereo && !exercito.isRemanejado()){
                aux++;
            }
        }
        return aux;
    }
    
    public int qtdExercitoTerrestreRemanejados(){
        int aux = 0;
        
        for(Exercito exercito: exercitos){
            if(exercito instanceof Terrestre && exercito.isRemanejado()){
                aux++;
            }
        }
        return aux;
    }
    
    public int qtdExercitoAereoRemanejados(){
        int aux = 0;
        
        for(Exercito exercito: exercitos){
            if(exercito instanceof Aereo && exercito.isRemanejado()){
                aux++;
            }
        }
        return aux;
    }
    
    public void resetExercitosRemanejdos(){
        for(Exercito exercito: exercitos){
            exercito.setRemanejado(false);
        }
    }
    
    public boolean verificarContinenteAdjacente(Territorio t){
        if(this.getContinente().verificarContinenteAdjacentes(t.getContinente())){
            return true;
        }else if(this.getContinente().equals(t.getContinente())){
            return true;
        }
        return false;
    }
    
    public boolean verificarSeFazFronteira(Territorio territorio){
        for(Territorio t: fronteiras){
            if(t.getNome().equals(territorio.getNome())){
                return true;
            }
        }
        return false;
    }
    
    public boolean verificarFronteiraComUmDoAdversário(){
        for(Territorio t: fronteiras){
            if(!t.getJogador().getCor().equals(this.getJogador().getCor())){
                return true;
            }
        }
        return false;
    }
}
