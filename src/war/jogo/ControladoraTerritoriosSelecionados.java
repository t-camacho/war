/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war.jogo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author thais, rafael
 */
public class ControladoraTerritoriosSelecionados {
    private final String imagem_verde = "/war/imagens/territorio_verde.jpg";
    private final String imagem_vermelha = "/war/imagens/territorio_vermelha.jpg";
    private HashMap<LayoutTerritorio, Boolean> referenciaTerritorios;
    private HashMap<LayoutTerritorio, Integer> referenciaExercitoAereoTerritorio;
    private LayoutTerritorio primeiroSelecionado;
    private LayoutTerritorio segundoSelecionado;
    //CONSTRUTOR
    public ControladoraTerritoriosSelecionados() {
        this.referenciaTerritorios = new HashMap<>();
        this.referenciaExercitoAereoTerritorio = new HashMap<>();
    }

    public LayoutTerritorio getPrimeiroSelecionado() {
        return primeiroSelecionado;
    }

    public LayoutTerritorio getSegundoSelecionado() {
        return segundoSelecionado;
    }
    
    public Boolean verificarSelecao(LayoutTerritorio t){
        return referenciaTerritorios.get(t);
    }
    
    public void setPrimeiroTerritorioSelecionado(LayoutTerritorio t){
        this.primeiroSelecionado = t;
    }
    
    public void setSegundoTerritorioSelecionado(LayoutTerritorio t){
        this.segundoSelecionado = t;
    }
    
    //USADO NO ATAQUE AEREO
    public void executarAcoesSelecionar(LayoutTerritorio territorio, Boolean selecionado, Integer i){
        alterarSelecao(territorio, selecionado);
        alterarVisualizacaoBotao(territorio);
        alterarQuantidade(territorio, i);
    }

    //ALTERA O ESTADO DE SELECAO PARA selecionado DO BOTAO botao 
    //E A SUA VISUALIZACAO DE ACORDO COM O ESTADO selecionado
    //VERDE: selecionado true AZUL/VERMELHO: selecionado FALSE
    public void executarAcoesSelecionar(LayoutTerritorio territorio, Boolean selecionado){
        alterarSelecao(territorio, selecionado);
        alterarVisualizacaoBotao(territorio);
    }
    
    //ADICIONA UM NOVO BOTAO A HASHMAP COM O ESTADO DE SELECAO FALSE
    public void adiconarTerritorio(LayoutTerritorio territorio){
        this.referenciaTerritorios.put(territorio, false);
    }
    
    //ALTERA O ESTADO DE SELECAO PARA O ESTADO PASSADO PELO PARAMETRO
    private void alterarSelecao(LayoutTerritorio territorio, Boolean selecionado){
        this.referenciaTerritorios.put(territorio, selecionado);
    }
    //USADO NO ATAQUE AEREO PARA ALTERAR A QUNATIDADE DE EXERCITO NO ATAQUE
    private void alterarQuantidade(LayoutTerritorio territorio, Integer i){
        this.referenciaExercitoAereoTerritorio.put(territorio, i);
    }
    
    public void zerarQtdAereo(){
        this.referenciaExercitoAereoTerritorio.clear();
    }
    
    //ALTERA O LAYOUT BAKCGROUND DO BOTAO DE ACORDO COM O SEU ESTADO DE SELECO ATUAL
    private void alterarVisualizacaoBotao(LayoutTerritorio botao){
        Boolean selecionado = this.referenciaTerritorios.get(botao);
        if(selecionado){
            botao.setLblBackgroundVerde();
        }else{
            if(botao.getCor().equals("Vermelho")){
                botao.setLblBackgroundVermelho();
            }else{
                botao.setLblBackgroundAzul();
            }
        }
    }
    
    //ZERAR TODOS OS BOTOES SELECIONADOS
    //ZERA OS ESTADOS SÓ DE SELECIONADO
    public void zerarSelecoes(){
        for(LayoutTerritorio territorio: this.referenciaTerritorios.keySet()){
            if(referenciaTerritorios.get(territorio)){
                executarAcoesSelecionar(territorio, false);
            }
        }
    }
    //ZERA OS ESTADO, DE SELECIONADO E SEL_REMANEJAMENTO
    
    //VERIFICAR SE EXISTE ALGUM SELECIONADO
    public boolean verificarExistenciaDeSelecionados(){
        for(Boolean territorio : this.referenciaTerritorios.values()){
            //return true;
            if(territorio){
                return true;
            }
        }
        return false;
    }
    
    public LayoutTerritorio getUmTerritorioSelecionado(){
        for(LayoutTerritorio territorio: this.referenciaTerritorios.keySet()){
            if(referenciaTerritorios.get(territorio)){
                return(territorio);
            }
        }
        return null;
    }
    
    public int getQtdExercitoAereoDeTerritorioSelecionado(LayoutTerritorio t){
        return this.referenciaExercitoAereoTerritorio.get(t);
    }
    
    public ArrayList getTerritoriosSelecionados(){
        ArrayList<LayoutTerritorio> aux = new ArrayList();
        for(LayoutTerritorio territorio: this.referenciaTerritorios.keySet()){
            if(referenciaTerritorios.get(territorio)){
                aux.add(territorio);
            }
        }
        return aux;
    }

    //VERIFICAR SE EXISTE DOIS SELECIONADO
    public boolean verificarDoisSelecionados(){
        int aux = 0;
        for(Boolean b : this.referenciaTerritorios.values()){
            if(b){
                aux++;
            }
            if(aux > 1){
                return true;
            }
        }
        return false;
    }
    //VERIFICA SE JÁ SELECIONOU 4
    public boolean verificarQuatroSelecionados(){
        int aux = 0;
        for(Boolean b : this.referenciaTerritorios.values()){
            if(b){
                aux++;
            }
            if(aux > 3){
                return true;
            }
        }
        return false;
    }
}
