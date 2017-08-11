/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war.jogo;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author thais, rafael
 */
public class Mapa {
    private static final int LINHA = 5;
    private static final int COLUNA = 8;
    
    private final Territorio territorios[][] = new Territorio[5][8];
    private final int QTD_TERRITORIO = 33;
    
    //CONSTRUTOR
    public Mapa() {
        territorios[0][0] = new Territorio("Alasca", Continente.AMERICA_DO_NORTE);
        territorios[0][1] = new Territorio("Vancouver", Continente.AMERICA_DO_NORTE);
        territorios[0][2] = new Territorio("Groenlândia", Continente.AMERICA_DO_NORTE);
        territorios[0][3] = new Territorio("Inglaterra", Continente.EUROPA);
        territorios[0][4] = new Territorio("Itália", Continente.EUROPA);
        territorios[0][5] = new Territorio("Suécia", Continente.EUROPA);
        territorios[0][7] = new Territorio("Vladivostok", Continente.ASIA);
  
        territorios[1][1] = new Territorio("Califórnia", Continente.AMERICA_DO_NORTE);
        territorios[1][2] = new Territorio("Otawa", Continente.AMERICA_DO_NORTE);
        territorios[1][4] = new Territorio("Alemanha", Continente.EUROPA);
        territorios[1][5] = new Territorio("Moscou", Continente.EUROPA);
        territorios[1][6] = new Territorio("Omsk", Continente.ASIA);
        territorios[1][7] = new Territorio("Sibéria", Continente.ASIA);
        
        territorios[2][1] = new Territorio("México", Continente.AMERICA_DO_NORTE);
        territorios[2][2] = new Territorio("Nova York", Continente.AMERICA_DO_NORTE);
        territorios[2][3] = new Territorio("Nigéria", Continente.AFRICA);
        territorios[2][4] = new Territorio("Egito", Continente.AFRICA);
        territorios[2][5] = new Territorio("Oriente Médio", Continente.ASIA);
        territorios[2][6] = new Territorio("Índia", Continente.ASIA);
        territorios[2][7] = new Territorio("China", Continente.ASIA);
        
        territorios[3][0] = new Territorio("Chile", Continente.AMERICA_DO_SUL);
        territorios[3][1] = new Territorio("Colômbia", Continente.AMERICA_DO_SUL);
        territorios[3][3] = new Territorio("Congo", Continente.AFRICA);
        territorios[3][4] = new Territorio("Sudão", Continente.AFRICA);
        territorios[3][5] = new Territorio("Sumatra", Continente.OCEANIA);
        territorios[3][6] = new Territorio("Bornéu", Continente.OCEANIA);
        territorios[3][7] = new Territorio("Japão", Continente.ASIA);
        
        territorios[4][0] = new Territorio("Argentina", Continente.AMERICA_DO_SUL);
        territorios[4][1] = new Territorio("Brasil", Continente.AMERICA_DO_SUL);
        territorios[4][3] = new Territorio("África do Sul", Continente.AFRICA);
        territorios[4][4] = new Territorio("Madagascar", Continente.AFRICA);
        territorios[4][6] = new Territorio("Austrália", Continente.OCEANIA);
        territorios[4][7] = new Territorio("Nova Guiné", Continente.OCEANIA);
        
        adicionarFronteiras();
    }
 
    //METODO PARA DIVIDIR OS 33 TERRITORIOS ENTRE OS 2 JOGADORES
    public void dividirTabuleiro(Jogador j1, Jogador j2){
        Random gerador = new Random();        
        //CALCULAR A QUATIDADE QUE CADA JOGADOR VAI RECEBER DE TERRITORIO
        int qtd_j1 = QTD_TERRITORIO/2;
        int qtd_j2 = (QTD_TERRITORIO/2) + 1;
        //DIVIDIR OS TERRITORIOS
        
            for(int i = 0; i < LINHA; i++){
                for(int j = 0; j < COLUNA; j++){
                    if(territorios[i][j] != null){
                        //SORTEAR UM DOS JOGADORES PARA RECEBER O TERRITÓRIO DA VEZ 
                        int jogador = gerador.nextInt(2);
                        //DAR O TERRITORIO PARA O JOGADOR SORTEADO
                        //APENAS SE ELE AINDA NÃO RECEBEU A QUANTIDADE MAXIMA PERMITIDA
                        if(jogador == 0 && qtd_j1 > 0){
                            territorios[i][j].setJogador(j1);
                            j1.ganharTerritorio(territorios[i][j]);
                            qtd_j1--;
                        }else if(jogador == 1 && qtd_j2 > 0){
                            territorios[i][j].setJogador(j2);
                            j2.ganharTerritorio(territorios[i][j]);
                            qtd_j2--;
                        }else if(qtd_j1 > 0){
                            territorios[i][j].setJogador(j1);
                            j1.ganharTerritorio(territorios[i][j]);
                            qtd_j1--;
                        }else{
                            territorios[i][j].setJogador(j2);
                            j2.ganharTerritorio(territorios[i][j]);
                            qtd_j2--;
                        }
                    }
                }
            }
            if(verificarVitoria(j1) || verificarVitoria(j2)){
                for(int i = 0; i < LINHA; i++){
                    for(int j = 0; j < COLUNA; j++){
                        if(territorios[i][j] != null){
                           territorios[i][j].setJogador(null);
                           j1.limparTerritorios();
                           j2.limparTerritorios();
                        }
                    }
                }
                dividirTabuleiro(j1, j2);
            }
    }
    
    //METODO PARA VERIFICAR SE EXISTE ALGUM JOGADOR QUE JÁ COMPRIU COM O OBJETIVO
    public boolean verificarVitoria(Jogador j1){
        //VARIAVEIS AUXILIARES
        int africa = 0, america_n = 0, america_s = 0, asia = 0, oceania = 0, europa = 0;
        int aux = 0;
        
        //CALCULAR QUANTIDADE DE TERRITORIOS POR CONTINENTE DO JOGADO J1
        for(int i = 0; i < LINHA; i++){
            for(int j = 0; j < COLUNA; j++){
                if(territorios[i][j] != null && territorios[i][j].getJogador().getCor().equals(j1.getCor())){
                    if(territorios[i][j].getContinente().getNome().equals("Europa")){
                        europa++;
                    }else if(territorios[i][j].getContinente().getNome().equals("America do Norte")){
                        america_n++;
                    }else if(territorios[i][j].getContinente().getNome().equals("Asia")){
                        asia++;
                    }else if(territorios[i][j].getContinente().getNome().equals("Africa")){
                        africa++;
                    }else if(territorios[i][j].getContinente().getNome().equals("Oceania")){
                        oceania++;
                    }else if(territorios[i][j].getContinente().getNome().equals("America do Sul")){
                        america_s++;
                    }  
                }
            }
        }
      
        //VERIFICAR QUANTOS CONTINENTES O JOGADOR POSSUI COMPLETO
        if(africa == Continente.AFRICA.getQtdTerritorio()){
            aux++;
        }
        if(america_n == Continente.AMERICA_DO_NORTE.getQtdTerritorio()){
            aux++;
        }
        if(america_s == Continente.AMERICA_DO_SUL.getQtdTerritorio()){
            aux++;
        }
        if(asia == Continente.ASIA.getQtdTerritorio()){
            aux++;
        }
        if(europa == Continente.EUROPA.getQtdTerritorio()){
            aux++;
        }
        if(oceania == Continente.OCEANIA.getQtdTerritorio()){
            aux++;
        }
        //CONCLUINDO: SE AUX > 1 ENTÃO O JOGADOR POSSUI MAIS DE UM 
        //CONTINENTE COMPLETO
        return aux > 1;
    }

    public Territorio[][] getTerritorios() {
        return territorios;
    }
    
    private ArrayList fronteiras(Territorio territorio){
        ArrayList<Territorio> aux = new ArrayList();
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 8; j++){
                if(territorios[i][j] != null){
                    if(territorios[i][j].getNome().equals(territorio.getNome())){
                        if(i == 0){
                            if(j == 0){
                                aux.add(territorios[0][1]);
                                aux.add(territorios[0][7]);
                            }else if(j == 7){
                                aux.add(territorios[0][0]);
                                aux.add(territorios[1][7]);
                            }else{
                                if(territorios[0][j+1] != null){
                                    aux.add(territorios[0][j+1]);
                                }
                                if(territorios[0][j-1] != null){
                                    aux.add(territorios[0][j-1]);
                                }
                                if(territorios[1][j] != null){
                                    aux.add(territorios[1][j]);
                                }
                            }
                        }else if(i == 4){
                            if(j == 0){
                                aux.add(territorios[4][1]);
                                aux.add(territorios[3][0]);
                                aux.add(territorios[4][7]);
                            }else if(j == 7){
                                aux.add(territorios[4][6]);
                                aux.add(territorios[3][7]);
                                aux.add(territorios[4][0]);
                            }else{
                                if(territorios[3][j] != null){
                                    aux.add(territorios[3][j]);
                                }
                                if(territorios[4][j+1] != null){
                                    aux.add(territorios[4][j+1]);
                                }
                                if(territorios[4][j-1] != null){
                                    aux.add(territorios[4][j-1]);
                                }
                            }
                        }else if(j == 0){
                            if(territorios[i+1][j] != null){
                                    aux.add(territorios[i+1][j]);
                            }
                            if(territorios[i-1][j] != null){
                                    aux.add(territorios[i-1][j]);
                            }
                            if(territorios[i][j+1] != null){
                                    aux.add(territorios[i][j+1]);
                            }
                            if(territorios[i][7] != null){
                                    aux.add(territorios[i][7]);
                            }
                        }else if(j == 7){
                            if(territorios[i+1][j] != null){
                                    aux.add(territorios[i+1][j]);
                            }
                            if(territorios[i-1][j] != null){
                                    aux.add(territorios[i-1][j]);
                            }
                            if(territorios[i][j-1] != null){
                                    aux.add(territorios[i][j-1]);
                            }
                            if(territorios[i][0] != null){
                                    aux.add(territorios[i][0]);
                            }
                        }else{
                            if(territorios[i+1][j] != null){
                                    aux.add(territorios[i+1][j]);
                            }
                            if(territorios[i-1][j] != null){
                                    aux.add(territorios[i-1][j]);
                            }
                            if(territorios[i][j+1] != null){
                                    aux.add(territorios[i][j+1]);
                            }
                            if(territorios[i][j-1] != null){
                                    aux.add(territorios[i][j-1]);
                            }
                        }
                    }
                }
            }
        }
        return aux;
    }
    
    private void adicionarFronteiras(){
        ArrayList<Territorio> f;
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 8; j++){
                if(territorios[i][j] != null){
                    f = fronteiras(territorios[i][j]);
                    territorios[i][j].setFronteiras(f);
                }
            }
        }
    }
}
