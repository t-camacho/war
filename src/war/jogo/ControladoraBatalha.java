/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war.jogo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import war.telas.TelaBatalha;

/**
 *
 * @author thais, rafael
 * 
 */
public class ControladoraBatalha implements IEventosTelaBatalha{
    private TelaBatalha telaPrincipal;
    private Jogador j1;//HUMANO
    private Jogador j2;//MAQUINA
    private Mapa mapa;
    private ControladoraTerritoriosSelecionados controleBotoes;
    private int cont;
    private int fase;//0 - subfase onde os dois ganham território | 1 - as outras subfases
    private int exercAereo;
    private int faseAtaque;
    //CONSTRUTOR
    public ControladoraBatalha(TelaBatalha telaPrincipal){
        this.telaPrincipal = telaPrincipal;
        this.telaPrincipal.setInterfaceTela(this);
        this.desabilitarBotoes();
        this.j1 = this.telaPrincipal.getJ1();
        this.j2 = this.telaPrincipal.getJ2();
        
        this.mapa = this.telaPrincipal.getMapa();
        controleBotoes = this.telaPrincipal.getControleBotoes();
        this.faseAtaque = 0;
        partida();
    }
    
    private void desabilitarBotoes(){
        this.telaPrincipal.desabilitarBotaoConfirmarDistribuicao();
        this.telaPrincipal.desabilitarBotaoAtacar();
        this.telaPrincipal.desabilitarBotaoFAtaques();
        this.telaPrincipal.desabilitarBotaoFRemanejamentos();
        this.telaPrincipal.desabilitarBotaoRemanejamentos();
    }
    
    //ACESSORES E MODIFICADORES
    public TelaBatalha getTelaPrincipal() {
        return telaPrincipal;
    }
    
    //METODOS GERAIS
    
    /*private boolean verificarTerritorioJogador(String nome){
        ArrayList<Territorio> e = j1.getTerritorios();
        Iterator it = e.iterator();
        for(int i = 0; i < e.size(); i++ ){
            if(((Territorio)e.get(i)).getNome().equals(nome)){
                return true;
            }
        }
        return false;
    }*/
    
    private void partida(){
        fase = 0;
        inicializacao();
    }
    
    private void inicializacao(){
        j2.ganharExercitoTerrestre();
        distribuirExercitoTerrestreMaquina();
        j2.ganharExercitoAereo();
        distribuirExercitoAereoMaquina();

        if(fase == 1){
            ataqueMaquina();
            remanejamentoMaquina();
        }
        j1.ganharExercitoTerrestre();
        j1.ganharExercitoAereo();
        distribuicaoExercitoTerrestre();
    }
    
    public void distribuirExercitoTerrestreMaquina(){
        Random random = new Random();
        ArrayList<Territorio> territorios = j2.getTerritorios();
        
        int intervalo_randomico = 0;
        while(j2.getExercTerrestreDistr() > 0){
            intervalo_randomico = random.nextInt(territorios.size());
            territorios.get(intervalo_randomico).addExercito(new Terrestre(false));
            j2.setExercTerrestreDistr(j2.getExercTerrestreDistr() - 1);
        }
        JOptionPane.showMessageDialog(null, "Seu adversário irá realizar distribuições terrestres!");
        this.telaPrincipal.atualizarMapa();
    }
    
    public void distribuirExercitoAereoMaquina(){
        Random random = new Random();  
        ArrayList<Territorio> territorios = j2.getTerritorios();
        int intervalo_randomico = 0;
        while(j2.getExercAereoDistr() > 0){
            intervalo_randomico = random.nextInt(territorios.size());
            territorios.get(intervalo_randomico).addExercito(new Aereo(false));
            j2.setExercAereoDistr(j2.getExercAereoDistr() - 1);
        }
        JOptionPane.showMessageDialog(null, "Seu adversário irá realizar distribuições aereas!");
        this.telaPrincipal.atualizarMapa();
    }
    
    public void distribuicaoExercitoTerrestre(){
        this.telaPrincipal.resetarSliderAtaque();
        cont = 0;
        this.telaPrincipal.alterarMaximoSlider(j1.getExercTerrestreDistr());
        this.telaPrincipal.alterarValorSlider();
        this.telaPrincipal.exibirMensagemDistribuicaoTerrestre();
    }
    
    public void distribuicaoExercitoAereo(){
        cont = 1;
        this.telaPrincipal.alterarMaximoSlider(j1.getExercAereoDistr());
        this.telaPrincipal.alterarValorSlider();
        this.telaPrincipal.exibirMensagemDistribuicaoAereo();
    }
    
    public void ataqueTerrestre(){
        faseAtaque = 0;
        this.telaPrincipal.resetarSliderAtaque();
        cont = 2;
        this.telaPrincipal.habilitarBotaoFAtaques();
        this.telaPrincipal.exibirMensagemAtaqueTerrestre();
    }
    
    public void ataqueHumano(){
        this.telaPrincipal.habilitarBotaoFAtaques();
        if(faseAtaque == 0){
            ataqueTerrestre();
        }else{
            exercAereo = 0;
            ataqueAereo();
        }
    }
    
    public void ataqueAereo(){
        exercAereo = 0;
        this.telaPrincipal.exibirMensagemAtaqueAereo();
    }
    
    public void remanejamentosTerrestre(){
        cont = 3;
        this.telaPrincipal.exibirMensagemRemanejamentoTerrestre();
        this.telaPrincipal.habilitarBotaoFRemanejamentos();
    }
    
    public void remanejamentosAereo(){
        cont = 4;
        this.telaPrincipal.exibirMensagemRemanejamentoAereo();
        this.telaPrincipal.habilitarBotaoFRemanejamentos();
    }
    //JSLIDER DA JOPTIONPANE
    static JSlider getSlider(final JOptionPane optionPane, int x) {
        JSlider slider = new JSlider();
        slider.setMinimum(1);
        slider.setMaximum(x);
        return slider;
    }
    
    private void ordenar(Integer vetor[]){
        int aux = 0;
        for (int i = 0; i < vetor.length; i++)
            {
                for (int j = 0; j < vetor.length; j++)
                {
                    if (vetor[i] > vetor[j])
                    {
                        aux = vetor[i];
                        vetor[i] = vetor[j];
                        vetor[j] = aux;
                    }
                }
            }
    }
    
    //METODO PARA VERIFICA SE O TERRITORIO ESCOLHIDO É DO JOGADOR
    //METODOS DA INTERFACE
    @Override
    public void eventoBotaoConfirmarDistribuicao() {
        if(cont == 0){
            if(j1.getExercTerrestreDistr() > 0){
                int add = this.telaPrincipal.getValorSlider();

                j1.setExercTerrestreDistr(j1.getExercTerrestreDistr() - add);

                LayoutTerritorio selecionado = controleBotoes.getUmTerritorioSelecionado();

                for(int i = 0; i < add; i++){
                    Terrestre t = new Terrestre(false);
                    (selecionado.getTerritorio()).addExercito(t);
                }
                controleBotoes.zerarSelecoes();
                selecionado.atualizar();

                this.telaPrincipal.desabilitarBotaoConfirmarDistribuicao();
            }
            if(j1.getExercTerrestreDistr() > 0){
                distribuicaoExercitoTerrestre();
            }else{
                    distribuicaoExercitoAereo();
            }
        }else{//CONT = 1
            if(j1.getExercAereoDistr() > 0){
                int add = this.telaPrincipal.getValorSlider();

                j1.setExercAereoDistr(j1.getExercAereoDistr() - add);

                LayoutTerritorio selecionado = controleBotoes.getUmTerritorioSelecionado();

                for(int i = 0; i < add; i++){
                    Aereo t = new Aereo(false);
                    (selecionado.getTerritorio()).addExercito(t);
                }
                controleBotoes.zerarSelecoes();
                selecionado.atualizar();

                this.telaPrincipal.desabilitarBotaoConfirmarDistribuicao();
            }
            if(j1.getExercAereoDistr() > 0){
                distribuicaoExercitoAereo();
            }else{
                if(fase == 0){
                    fase = 1;
                    inicializacao();
                }else{
                    ataqueTerrestre();
                }
            }
        }
    }
    
    @Override
    public void eventoFinalizarAtaque() {
        this.controleBotoes.zerarSelecoes();
        this.telaPrincipal.desabilitarBotaoAtacar();
        this.telaPrincipal.desabilitarBotaoFAtaques();
        remanejamentosTerrestre();
    }

    @Override
    public void eventoFinalizarRemanejamento() {
        LayoutTerritorio t[][] = this.telaPrincipal.getPaineis();
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 8; j++){
                if(t[i][j] != null){
                    t[i][j].getTerritorio().resetExercitosRemanejdos();
                }
            }
        }
        if(cont == 3){
            this.telaPrincipal.desabilitarBotaoRemanejamentos();
            this.controleBotoes.zerarSelecoes();
            this.remanejamentosAereo();
        }else{
            this.telaPrincipal.desabilitarBotaoFRemanejamentos();
            this.telaPrincipal.desabilitarBotaoRemanejamentos();
            inicializacao();
        }
    }
 
    @Override
    public void eventoSelecionarTerritorio(String nome, LayoutTerritorio territorio) {
            if(cont == 0 || cont == 1){
                if( j1.verificarTerritorioJogador(nome)){//VERIFICA SE O TERRITORIO CLICADO É DO JOGADOR
                    if(controleBotoes.verificarExistenciaDeSelecionados()){//VERIFICA SE NÃO EXISTE NENHUM JÁ SELECIONADO
                        controleBotoes.zerarSelecoes();
                    }
                    controleBotoes.executarAcoesSelecionar(territorio, true);
                    if(cont == 0){
                        this.telaPrincipal.habilitarBotaoConfirmarDistribuicao();
                    }else if(cont == 1){
                        this.telaPrincipal.habilitarBotaoConfirmarDistribuicao();
                    }
                }
            }else if(cont == 2){
                if(faseAtaque == 0){
                    if(controleBotoes.verificarDoisSelecionados()){
                        controleBotoes.zerarSelecoes();
                        this.telaPrincipal.desabilitarBotaoAtacar();
                    }
                }
                if(faseAtaque == 1){
                    if(controleBotoes.verificarQuatroSelecionados()){
                        controleBotoes.zerarSelecoes();
                        exercAereo = 0;
                        this.telaPrincipal.desabilitarBotaoAtacar();
                    }
                }
                if(!controleBotoes.verificarExistenciaDeSelecionados()){//SE N TIVR NENHUM SELECIONADO ENTRA NO IF
                    if(faseAtaque == 0){//O SELECIONADO DEVE SER DO JOGADOR
                        if(j1.verificarTerritorioJogador(nome)){//SE O SELECIONADO E DO JOGADOR, ENTRA NO IF
                            controleBotoes.executarAcoesSelecionar(territorio, true);
                            controleBotoes.setPrimeiroTerritorioSelecionado(territorio);
                            //REGRAS DE ATAQUE
                            if(territorio.getTerritorio().getQuantExercTerrestre() >= 4){
                                this.telaPrincipal.alterarMaximoSlider(3);
                                this.telaPrincipal.alterarValorSlider();
                            }else if(territorio.getTerritorio().getQuantExercTerrestre() == 1){
                                controleBotoes.zerarSelecoes();
                                this.telaPrincipal.alterarValorSlider();
                            }else if(territorio.getTerritorio().getQuantExercTerrestre() == 2){
                                this.telaPrincipal.alterarMaximoSlider(1);
                                this.telaPrincipal.alterarValorSlider();
                            }else{
                                this.telaPrincipal.alterarMaximoSlider(2);
                                this.telaPrincipal.alterarValorSlider();
                            }
                        }
                    }else{//O SELECIONADO DEVE SER DO ADVERSÁRIO CONT == 3
                        if(!j1.verificarTerritorioJogador(nome)){//SE O SELECIONADO N E DO JOGADOR, ENTRA NO IF
                            //REGRAS DE ATAQUE
                            if((territorio.getTerritorio().getQuantExercTerrestre() >= 4 && territorio.getTerritorio().getQuantExercAereo() >= 1)
                                    && (territorio.getTerritorio().verificarFronteiraComUmDoAdversário())){
                                controleBotoes.executarAcoesSelecionar(territorio, true);
                                controleBotoes.setPrimeiroTerritorioSelecionado(territorio);
                            }
                        }
                    }
                }else{//VAI TER QUE TER UM JÁ SELECIONADO
                    if(faseAtaque == 0){
                        if(!j1.verificarTerritorioJogador(nome)){//COMO E O SEGUNDO SELECINADO, N DEVE SER DO JOGADOR E SIM DO INIMIGO
                            if(territorio.getTerritorio().verificarSeFazFronteira(this.controleBotoes.getPrimeiroSelecionado().getTerritorio())){//PARA ATACAR DEVE SER FRONTIRA
                                controleBotoes.executarAcoesSelecionar(territorio, true);
                                this.telaPrincipal.habilitarBotaoAtacar();
                            }
                        }else{
                            this.controleBotoes.zerarSelecoes();
                        }
                    }else{
                        if(j1.verificarTerritorioJogador(nome) && !this.controleBotoes.verificarSelecao(territorio)){
                            if(this.controleBotoes.getPrimeiroSelecionado().getTerritorio().verificarContinenteAdjacente(territorio.getTerritorio()) &&
                                     territorio.getTerritorio().getQuantExercAereo() != 0){
                                //exercito aereo inimigo
                                //só pode ser no max 3 aereo inimigo >= 3
                                if(exercAereo < 3){
                                    switch(territorio.getTerritorio().getQuantExercAereo()){
                                        case 1:
                                            controleBotoes.executarAcoesSelecionar(territorio, true, 1);
                                            this.exercAereo++;
                                            break;
                                        case 2:
                                            if(this.exercAereo == 0){
                                                JOptionPane optionPane = new JOptionPane();
                                                JSlider slider = getSlider(optionPane, 2);
                                                optionPane.setMessage(new Object[] { "Quantos exercitos você deseja pegar desse territorio para realizar o ataque aéreo: ", slider });
                                                optionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
                                                JDialog dialog = optionPane.createDialog("Ataque Aéreo");
                                                dialog.setVisible(true);
                                                int valor = slider.getValue();
                                                controleBotoes.executarAcoesSelecionar(territorio, true, valor);
                                                this.exercAereo += valor;
                                            }else {
                                                controleBotoes.executarAcoesSelecionar(territorio, true, 1);
                                                this.exercAereo++;
                                            }
                                            break;
                                        default:
                                            if(this.exercAereo == 0){
                                                JOptionPane optionPane = new JOptionPane();
                                                JSlider slider = getSlider(optionPane, 3);
                                                optionPane.setMessage(new Object[] { "Quantos exercitos você deseja pegar desse territorio para realizar o ataque aéreo: ", slider });
                                                optionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
                                                JDialog dialog = optionPane.createDialog("Ataque Aéreo");
                                                dialog.setVisible(true);
                                                int valor = slider.getValue();
                                                controleBotoes.executarAcoesSelecionar(territorio, true, valor);
                                                this.exercAereo += valor;
                                            }else if(this.exercAereo == 1){
                                                JOptionPane optionPane = new JOptionPane();
                                                JSlider slider = getSlider(optionPane, 2);
                                                optionPane.setMessage(new Object[] { "Quantos exercitos você deseja pegar desse territorio para realizar o ataque aéreo: ", slider });
                                                optionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
                                                JDialog dialog = optionPane.createDialog("Ataque Aéreo");
                                                dialog.setVisible(true);
                                                int valor = slider.getValue();
                                                controleBotoes.executarAcoesSelecionar(territorio, true, valor);
                                                this.exercAereo += valor;
                                            }else{
                                                controleBotoes.executarAcoesSelecionar(territorio, true,1);
                                                this.exercAereo++;
                                            }
                                    }
                                }else{
                                    JOptionPane.showMessageDialog(null, "Você não pode atacar com mais de três exercitos aéreos!");
                                }
                                if(this.exercAereo > 0){
                                    this.telaPrincipal.habilitarBotaoAtacar();
                                }else{
                                    this.telaPrincipal.desabilitarBotaoAtacar();
                                }
                            }
                        }else{
                            this.controleBotoes.zerarSelecoes();
                            exercAereo = 0;
                            this.telaPrincipal.desabilitarBotaoAtacar();
                        }
                    }
                }
            }else if(cont == 4 || cont == 3){//REMANEJAMENTO
                if(controleBotoes.verificarDoisSelecionados()){//VERIFICA SE NÃO EXISTE NENHUM JÁ SELECIONADO
                        controleBotoes.zerarSelecoes();
                        this.telaPrincipal.desabilitarBotaoRemanejamentos();
                }
                if(!controleBotoes.verificarExistenciaDeSelecionados()){//SE NENHUM JÁ ESTIVER SELECIONADO ENTRA AQUI
                    if(cont == 3){
                            if(j1.verificarTerritorioJogador(nome) && territorio.getTerritorio().getQuantExercTerrestre() > 1){//VERIFICA SE O TERRITORIO CLICADO É DO JOGADOR
                                controleBotoes.executarAcoesSelecionar(territorio, true);
                                controleBotoes.setPrimeiroTerritorioSelecionado(territorio);
                                this.telaPrincipal.alterarValorSlider();

                                if(this.controleBotoes.getPrimeiroSelecionado().getTerritorio().getQuantExercTerrestre() ==
                                        this.controleBotoes.getPrimeiroSelecionado().getTerritorio().qtdExercitoTerrestreRemanejados()){
                                    this.controleBotoes.zerarSelecoes();
                                }else if(this.controleBotoes.getPrimeiroSelecionado().getTerritorio().qtdExercitoTerrestreRemanejados() > 0){
                                    if(this.controleBotoes.getPrimeiroSelecionado().getTerritorio().getQuantExercTerrestre() >
                                        this.controleBotoes.getPrimeiroSelecionado().getTerritorio().qtdExercitoTerrestreRemanejados()){
                                        this.telaPrincipal.alterarMaximoSlider(this.controleBotoes.getPrimeiroSelecionado().getTerritorio().qtdExercitoTerrestreNRemanejados());
                                    }
                                }else if(this.controleBotoes.getPrimeiroSelecionado().getTerritorio().qtdExercitoTerrestreRemanejados() == 0){
                                    this.telaPrincipal.alterarMaximoSlider(this.controleBotoes.getPrimeiroSelecionado().getTerritorio().qtdExercitoTerrestreNRemanejados() - 1);
                                }
                            }
                    }else{
                        if(j1.verificarTerritorioJogador(nome) && territorio.getTerritorio().getQuantExercAereo() > 1){//VERIFICA SE O TERRITORIO CLICADO É DO JOGADOR
                                controleBotoes.executarAcoesSelecionar(territorio, true);
                                controleBotoes.setPrimeiroTerritorioSelecionado(territorio);
                                this.telaPrincipal.alterarValorSlider();

                                if(this.controleBotoes.getPrimeiroSelecionado().getTerritorio().getQuantExercAereo() ==
                                        this.controleBotoes.getPrimeiroSelecionado().getTerritorio().qtdExercitoAereoRemanejados()){
                                    this.controleBotoes.zerarSelecoes();
                                }else{
                                    if(this.controleBotoes.getPrimeiroSelecionado().getTerritorio().getQuantExercAereo() >
                                        this.controleBotoes.getPrimeiroSelecionado().getTerritorio().qtdExercitoAereoRemanejados()){
                                        this.telaPrincipal.alterarMaximoSlider(this.controleBotoes.getPrimeiroSelecionado().getTerritorio().qtdExercitoAereoNRemanejados());
                                    }
                                }
                            }
                    }
                }else{//UM JA ESTA SELECIONADO
                    if(j1.verificarTerritorioJogador(nome) &&
                        territorio.getTerritorio().verificarSeFazFronteira(this.controleBotoes.getPrimeiroSelecionado().getTerritorio())){//ENTAO O SELECIONADO NO MOMENTO DEVE SER DO JOGADOR TBM
                        controleBotoes.executarAcoesSelecionar(territorio, true);
                        this.controleBotoes.setSegundoTerritorioSelecionado(territorio);
                        this.telaPrincipal.habilitarBotaoRemanejamentos();
                    }else{
                        this.controleBotoes.zerarSelecoes();
                    }

                }
            }
    }

    @Override
    public void eventoRemanejamento() {
        int aux = this.telaPrincipal.getValorSlider();
        if(cont == 3){
            while(aux > 0){
                this.controleBotoes.getPrimeiroSelecionado().getTerritorio().removerExercitoTerrestreRemanejado();
                this.controleBotoes.getSegundoSelecionado().getTerritorio().addExercito(new Terrestre(true));
                aux--;
            }
            this.controleBotoes.getPrimeiroSelecionado().atualizar();
            this.controleBotoes.getSegundoSelecionado().atualizar();
            this.telaPrincipal.desabilitarBotaoRemanejamentos();
        }else{
            while(aux > 0){
                this.controleBotoes.getPrimeiroSelecionado().getTerritorio().removerExercitoAereoRemanejado();
                this.controleBotoes.getSegundoSelecionado().getTerritorio().addExercito(new Aereo(true));
                aux--;
            }
            this.controleBotoes.getPrimeiroSelecionado().atualizar();
            this.controleBotoes.getSegundoSelecionado().atualizar();
            this.telaPrincipal.desabilitarBotaoRemanejamentos();
        }
    }
    
    private void ataqueTerrestreMaquina(){
        ArrayList<Territorio> territorio = j2.getTerritorios();
        ArrayList<Territorio> fronteira = null;
        
        Random random = new Random();  
        int randT = 0;
        int randA = 0;
        int rand;
        int aux = 0;
        
        Integer dadosAtacante[] = new Integer[3];
        Integer dadosDefensor[] = new Integer[3];
        
        inicializarVetor(dadosAtacante);
        inicializarVetor(dadosDefensor);
        
        boolean b = true;
        
        while(b){
            randT = random.nextInt(territorio.size());
            while(territorio.get(randT).getQuantExercTerrestre() < 2){
                randT = random.nextInt(territorio.size());
            }
            fronteira = territorio.get(randT).getFronteiras();
            for(int i = 0; i < fronteira.size(); i++){
                rand = random.nextInt(fronteira.size());
                if(!(fronteira.get(rand).getJogador().getCor().equals(territorio.get(randT).getJogador().getCor()))){
                    b = false;
                    aux = rand;
                    break;
                }
            }
        }
        
        Territorio atacante = territorio.get(randT);
        Territorio defensor = fronteira.get(aux);
        
        JOptionPane.showMessageDialog(null, "Seu inimigo vai te atacar no território " + defensor.getNome() + " com " 
                + atacante.getNome() + ".");
        
        ArrayList<Exercito> exercitosA = territorio.get(randT).getExercTerrestre();
        ArrayList<Exercito> exercitosD = fronteira.get(aux).getExercTerrestre();
        
        int qtdExercitoA, qtdExercitoD;
        
        if(exercitosA.size() > 3){
            qtdExercitoA = 3;
            this.jogarDados(3, dadosAtacante, exercitosA);
        }else if(exercitosA.size() == 3){
            qtdExercitoA = 2;
            this.jogarDados(2, dadosAtacante, exercitosA);
        }else{
            qtdExercitoA = 1;
            this.jogarDados(1, dadosAtacante, exercitosA);
        }
        
        if(exercitosD.size() == 1){
            qtdExercitoD = 1;
            this.jogarDados(1, dadosDefensor, exercitosD);
        }else if(exercitosD.size() == 2){
            qtdExercitoD = 2;
            this.jogarDados(2, dadosDefensor, exercitosD);
        }else{
            qtdExercitoD = 3;
            this.jogarDados(3, dadosDefensor, exercitosD);
        }
        
        ordenar(dadosAtacante);
        ordenar(dadosDefensor);
        
        this.combateAtaqueTerrestre(atacante, defensor, dadosAtacante, dadosDefensor, qtdExercitoA, qtdExercitoD);
    }
  
    private void combateAtaqueTerrestre(Territorio atacante, Territorio defensor, Integer dadoA[], Integer dadoD[], int qtdExercitoA, 
            int qtdExercitoD){
        Random random = new Random();
        switch(qtdExercitoA){
                case 1:
                        if(dadoA[0] > dadoD[0]){
                            defensor.removerExercitoTerrestre();
                            if(cont == 2){
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você ganhou!\n"
                                        + "Você tirou " + dadoA[0] + "\n"
                                        + "Seu adversário tirou " + dadoD[0]);
                            }else{
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você perdeu!\n"
                                        + "Seu adversário tirou " + dadoA[0] + "\n"
                                        + "Você tirou " + dadoD[0]);
                            }
                        }else{
                            atacante.removerExercitoTerrestre();
                            if(cont == 2){
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você perdeu!\n"
                                        + "Você tirou " + dadoA[0] + "\n"
                                        + "Seu adversário tirou " + dadoD[0]);
                            }else{
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você ganhou!\n"
                                        + "Seu adversário tirou " + dadoA[0] + "\n"
                                        + "Você tirou " + dadoD[0]);
                            }
                        }
                    if(defensor.getQuantExercTerrestre() == 0 && cont == 2){
                        defensor.setJogador(j1);
                        j2.perderTerritorio(defensor);
                        j1.ganharTerritorio(defensor);
                        defensor.addExercito(new Terrestre(false));
                        atacante.removerExercitoTerrestre();
                    }
                    if(defensor.getQuantExercTerrestre() == 0){
                        j1.perderTerritorio(defensor);
                        defensor.setJogador(j2);
                        j2.ganharTerritorio(defensor);
                        defensor.addExercito(new Terrestre(false));
                        atacante.removerExercitoTerrestre();
                    }
                    break;
                case 2:
                    if(qtdExercitoD == 1){
                        if(dadoA[0] > dadoD[0]){
                            defensor.removerExercitoTerrestre();
                            if(cont == 2){
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você ganhou!\n"
                                        + "Você tirou " + dadoA[0] + "\n"
                                        + "Seu adversário tirou " + dadoD[0]);
                            }else{
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você perdeu!\n"
                                        + "Seu adversário tirou " + dadoA[0] + "\n"
                                        + "Você tirou " + dadoD[0]);
                            }
                        }else{
                            atacante.removerExercitoTerrestre();
                            if(cont == 2){
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você perdeu!\n"
                                        + "Você tirou " + dadoA[0] + "\n"
                                        + "Seu adversário tirou " + dadoD[0]);
                            }else{
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você ganhou!\n"
                                        + "Seu adversário tirou " + dadoA[0] + "\n"
                                        + "Você tirou " + dadoD[0]);
                            }
                        }
                    }else{
                        if(dadoA[0] > dadoD[0]){
                            defensor.removerExercitoTerrestre();
                            if(cont == 2){
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você ganhou!\n"
                                        + "Você tirou " + dadoA[0] + "\n"
                                        + "Seu adversário tirou " + dadoD[0]);
                            }else{
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você perdeu!\n"
                                        + "Seu adversário tirou " + dadoA[0] + "\n"
                                        + "Você tirou " + dadoD[0]);
                            }
                        }else{
                            atacante.removerExercitoTerrestre();
                            if(cont == 2){
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você perdeu!\n"
                                        + "Você tirou " + dadoA[0] + "\n"
                                        + "Seu adversário tirou " + dadoD[0]);
                            }else{
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você ganhou!\n"
                                        + "Seu adversário tirou " + dadoA[0] + "\n"
                                        + "Você tirou " + dadoD[0]);
                            }
                        }
                        if(dadoA[1] > dadoD[1]){
                            defensor.removerExercitoTerrestre();
                            if(cont == 2){
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você ganhou!\n"
                                        + "Você tirou " + dadoA[1] + "\n"
                                        + "Seu adversário tirou " + dadoD[1]);
                            }else{
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você perdeu!\n"
                                        + "Seu adversário tirou " + dadoA[1] + "\n"
                                        + "Você tirou " + dadoD[1]);
                            }
                        }else{
                            atacante.removerExercitoTerrestre();
                            if(cont == 2){
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você perdeu!\n"
                                        + "Você tirou " + dadoA[1] + "\n"
                                        + "Seu adversário tirou " + dadoD[1]);
                            }else{
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você ganhou!\n"
                                        + "Seu adversário tirou " + dadoA[1] + "\n"
                                        + "Você tirou " + dadoD[1]);
                            }
                        }
                    }
                    
                    if(defensor.getQuantExercTerrestre() == 0 && cont == 2){
                        JOptionPane optionPane = new JOptionPane();
                        JSlider slider = getSlider(optionPane, 2);
                        optionPane.setMessage(new Object[] { "Você conquistou o território. Quantos exércitos deseja distribuir: ", slider });
                        optionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
                        //optionPane.setOptionType(JOptionPane.OK_OPTION);
                        JDialog dialog = optionPane.createDialog("Conquistou o território");
                        dialog.setVisible(true);
                        int valor = slider.getValue();
                        
                        j2.perderTerritorio(defensor);
                        defensor.setJogador(j1);
                        j1.ganharTerritorio(defensor);
                        while(valor > 0){
                            defensor.addExercito(new Terrestre(false));
                            atacante.removerExercitoTerrestre();
                            valor--;
                        }
                    }else if(defensor.getQuantExercTerrestre() == 0){
                        int valor = random.nextInt(2) + 1;
                        j1.perderTerritorio(defensor);
                        defensor.setJogador(j2);
                        j2.ganharTerritorio(defensor);
                        while(valor > 0){
                            defensor.addExercito(new Terrestre(false));
                            atacante.removerExercitoTerrestre();
                            valor--;
                        }
                    }
                    break;
                case 3:
                    if(qtdExercitoD == 1){
                        if(dadoA[0] > dadoD[0]){
                            defensor.removerExercitoTerrestre();
                            if(cont == 2){
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você ganhou!\n"
                                        + "Você tirou " + dadoA[0] + "\n"
                                        + "Seu adversário tirou " + dadoD[0]);
                            }else{
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você perdeu!\n"
                                        + "Seu adversário tirou " + dadoA[0] + "\n"
                                        + "Você tirou " + dadoD[0]);
                            }
                        }else{
                            atacante.removerExercitoTerrestre();
                            if(cont == 2){
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você perdeu!\n"
                                        + "Você tirou " + dadoA[0] + "\n"
                                        + "Seu adversário tirou " + dadoD[0]);
                            }else{
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você ganhou!\n"
                                        + "Seu adversário tirou " + dadoA[0] + "\n"
                                        + "Você tirou " + dadoD[0]);
                            }
                        }
                    }else if(qtdExercitoD == 2){
                        if(dadoA[0] > dadoD[0]){
                            defensor.removerExercitoTerrestre();
                            if(cont == 2){
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você ganhou!\n"
                                        + "Você tirou " + dadoA[0] + "\n"
                                        + "Seu adversário tirou " + dadoD[0]);
                            }else{
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você perdeu!\n"
                                        + "Seu adversário tirou " + dadoA[0] + "\n"
                                        + "Você tirou " + dadoD[0]);
                            }
                        }else{
                            atacante.removerExercitoTerrestre();
                            if(cont == 2){
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você perdeu!\n"
                                        + "Você tirou " + dadoA[0] + "\n"
                                        + "Seu adversário tirou " + dadoD[0]);
                            }else{
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você ganhou!\n"
                                        + "Seu adversário tirou " + dadoA[0] + "\n"
                                        + "Você tirou " + dadoD[0]);
                            }
                        }
                        if(dadoA[1] > dadoD[1]){
                            defensor.removerExercitoTerrestre();
                            if(cont == 2){
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você ganhou!\n"
                                        + "Você tirou " + dadoA[1] + "\n"
                                        + "Seu adversário tirou " + dadoD[1]);
                            }else{
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você perdeu!\n"
                                        + "Seu adversário tirou " + dadoA[1] + "\n"
                                        + "Você tirou " + dadoD[1]);
                            }
                        }else{
                            atacante.removerExercitoTerrestre();
                            if(cont == 2){
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você perdeu!\n"
                                        + "Você tirou " + dadoA[1] + "\n"
                                        + "Seu adversário tirou " + dadoD[1]);
                            }else{
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você ganhou!\n"
                                        + "Seu adversário tirou " + dadoA[1] + "\n"
                                        + "Você tirou " + dadoD[1]);
                            }
                        }
                    }else if(qtdExercitoD == 3){
                        if(dadoA[0] > dadoD[0]){
                            defensor.removerExercitoTerrestre();
                            if(cont == 2){
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você ganhou!\n"
                                        + "Você tirou " + dadoA[0] + "\n"
                                        + "Seu adversário tirou " + dadoD[0]);
                            }else{
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você perdeu!\n"
                                        + "Seu adversário tirou " + dadoA[0] + "\n"
                                        + "Você tirou " + dadoD[0]);
                            }
                        }else{
                            atacante.removerExercitoTerrestre();
                            if(cont == 2){
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você perdeu!\n"
                                        + "Você tirou " + dadoA[0] + "\n"
                                        + "Seu adversário tirou " + dadoD[0]);
                            }else{
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você ganhou!\n"
                                        + "Seu adversário tirou " + dadoA[0] + "\n"
                                        + "Você tirou " + dadoD[0]);
                            }
                        }
                        if(dadoA[1] > dadoD[1]){
                            defensor.removerExercitoTerrestre();
                            if(cont == 2){
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você ganhou!\n"
                                        + "Você tirou " + dadoA[1] + "\n"
                                        + "Seu adversário tirou " + dadoD[1]);
                            }else{
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você perdeu!\n"
                                        + "Seu adversário tirou " + dadoA[1] + "\n"
                                        + "Você tirou " + dadoD[1]);
                            }
                        }else{
                            atacante.removerExercitoTerrestre();
                            if(cont == 2){
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você perdeu!\n"
                                        + "Você tirou " + dadoA[1] + "\n"
                                        + "Seu adversário tirou " + dadoD[1]);
                            }else{
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você ganhou!\n"
                                        + "Seu adversário tirou " + dadoA[1] + "\n"
                                        + "Você tirou " + dadoD[1]);
                            }
                        }
                        if(dadoA[2] > dadoD[2]){
                            defensor.removerExercitoTerrestre();
                            if(cont == 2){
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você ganhou!\n"
                                        + "Você tirou " + dadoA[2] + "\n"
                                        + "Seu adversário tirou " + dadoD[2]);
                            }else{
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você perdeu!\n"
                                        + "Seu adversário tirou " + dadoA[2] + "\n"
                                        + "Você tirou " + dadoD[2]);
                            }
                        }else{
                            atacante.removerExercitoTerrestre();
                            if(cont == 2){
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você perdeu!\n"
                                        + "Você tirou " + dadoA[2] + "\n"
                                        + "Seu adversário tirou " + dadoD[2]);
                            }else{
                                JOptionPane.showMessageDialog(this.telaPrincipal, "Você ganhou!\n"
                                        + "Seu adversário tirou " + dadoA[2] + "\n"
                                        + "Você tirou " + dadoD[2]);
                            }
                        }
                    }
                    if(defensor.getQuantExercTerrestre() == 0 && cont == 2){
                        JOptionPane optionPane = new JOptionPane();
                        JSlider slider = getSlider(optionPane, 3);
                        optionPane.setMessage(new Object[] { "Você conquistou o território. Quantos exércitos deseja distribuir: ", slider });
                        optionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
                        //optionPane.setOptionType(JOptionPane.OK_OPTION);
                        JDialog dialog = optionPane.createDialog("Conquistou o território");
                        dialog.setVisible(true);
                        
                        int valor = slider.getValue();
                        j2.perderTerritorio(defensor);
                        defensor.setJogador(j1);
                        j1.ganharTerritorio(defensor);
                        while(valor > 0){
                            defensor.addExercito(new Terrestre(false));
                            atacante.removerExercitoTerrestre();
                            valor--;
                        }
                    }else if(defensor.getQuantExercTerrestre() == 0 && cont != 2){
                        int valor = random.nextInt(2) + 1;
                        j1.perderTerritorio(defensor);
                        defensor.setJogador(j2);
                        j2.ganharTerritorio(defensor);
                        while(valor > 0){
                            defensor.addExercito(new Terrestre(false));
                            atacante.removerExercitoTerrestre();
                            valor--;
                        }
                    }
            }
            this.telaPrincipal.desabilitarBotaoAtacar();
            this.telaPrincipal.atualizarMapa();
            if(this.mapa.verificarVitoria(j1)){
                JOptionPane.showMessageDialog(this.telaPrincipal, "Você ganhou o jogo! Parabéns!");
                this.telaPrincipal.dispose();
            }else if(this.mapa.verificarVitoria(j2)){
                JOptionPane.showMessageDialog(this.telaPrincipal, "Você perdeu o jogo! Seu adversário é o campeão!");
                this.telaPrincipal.dispose();
            }
    }
    
    @Override
    public void eventoBotaoAtacar() {
        if(faseAtaque == 0){
            int qtdExercitoA = this.telaPrincipal.getValorSlider();
            int qtdExercitoD;
            
            Integer dadosA[] = new Integer[3];
            Integer dadosD[] = new Integer[3];
            
            inicializarVetor(dadosA);
            inicializarVetor(dadosD);
            
            Territorio atacante;
            Territorio defensor;
            ArrayList<LayoutTerritorio> aux = new ArrayList();
            aux = controleBotoes.getTerritoriosSelecionados();
            
            if(j1.verificarTerritorioJogador(aux.get(0).getTerritorio().getNome())){
                atacante = aux.get(0).getTerritorio();
                defensor = aux.get(1).getTerritorio();
            }else{
                atacante = aux.get(1).getTerritorio();
                defensor = aux.get(0).getTerritorio();
            }
            
            ArrayList<Exercito> exercitoA = atacante.getExercTerrestre();
            ArrayList<Exercito> exercitoD = defensor.getExercTerrestre();
            
            if(exercitoD.size() >=3){
                qtdExercitoD = 3;
            }else if(exercitoD.size() == 2){
                qtdExercitoD = 2;
            }else{
                qtdExercitoD = 1;
            }
            
            jogarDados(qtdExercitoA, dadosA, exercitoA);
            jogarDados(qtdExercitoD, dadosD, exercitoD);
           
            ordenar(dadosA);
            ordenar(dadosD);
            
            this.combateAtaqueTerrestre(atacante, defensor, dadosA, dadosD, qtdExercitoA, qtdExercitoD);
        }else{
            //O QUE FAZER NO ATAQUE AEREO
            Integer qtd[] = new Integer[3];
            inicializarVetor(qtd);
            /* PEGANDO OS EXERCITOS DO ATACANTE E O QUE IRÁ SER ATACADO */
            ArrayList<LayoutTerritorio> auxT = this.controleBotoes.getTerritoriosSelecionados();
            Territorio defensor = null;
            ArrayList<Territorio> atacante = new ArrayList<>();
            ArrayList<LayoutTerritorio> atacanteAux = new ArrayList<>();
            for(LayoutTerritorio lTerritorio: auxT){
                if(lTerritorio.getTerritorio().getJogador().getCor().equals(j1.getCor())){
                    atacanteAux.add(lTerritorio);
                }else{
                    defensor = lTerritorio.getTerritorio();
                }
            }
            /* ATACANTEAUX POSSUI LAYOUTTERRITORIO, ATACANTE TERRITORIO
            PARA PADRONIZAR COM O ATAQUE DA MAQUINA*/
            for( int i = 0; i < atacanteAux.size(); i++){
                atacante.add(atacanteAux.get(i).getTerritorio());
            }
            
            for(int i = 0; i < atacanteAux.size(); i++){
                    qtd[i] = this.controleBotoes.getQtdExercitoAereoDeTerritorioSelecionado(atacanteAux.get(i));
            }
            
            int qtdExercitoAtaque = exercAereo;
            combateAtaqueAereo(defensor, atacante, qtd);
            this.controleBotoes.zerarQtdAereo();
        }
    }
    
    private void jogarDados(int qtd, Integer vet[], ArrayList<Exercito> a){
        switch (qtd) {
                case 1:
                    vet[0] = a.get(0).combater();
                    break;
                case 2:
                    vet[0] = a.get(0).combater();
                    vet[1] = a.get(1).combater();
                    break;
                case 3:
                    vet[0] = a.get(0).combater();
                    vet[1] = a.get(1).combater();
                    vet[2] = a.get(2).combater();
                    break;
            }
    }
    
    private void ataqueMaquina(){
        Random r = new Random();
        int aux2;
        int aux1 = r.nextInt(5);//QUANTIDADE DE ATAQUES NA JOGADA
        while(aux1 > 0){
            aux2 = r.nextInt(2);
            if(aux2 == 0){
                ataqueTerrestreMaquina();
            }else{
                ataqueAereoMaquina();
            }
            aux1--;
        }
    }
    
    private void combateAtaqueAereo(Territorio defensor, ArrayList<Territorio> atacante, Integer qtd[]){
        Random random = new Random();
        ArrayList<Exercito> exercAtacante = new ArrayList<>();
        
        ArrayList<Exercito> exercAdv = defensor.getExercAereo();
        Integer dadosA[] = new Integer[3];
        inicializarVetor(dadosA);
        int dadoD = exercAdv.get(0).combater();
        if(cont == 2){
            JOptionPane.showMessageDialog(null, "Adversário abateu " + dadoD + " exercito(s) aéreo(s).");
        }else{
            JOptionPane.showMessageDialog(null, "Você abateu " + dadoD + " exercito(s) aéreo(s).");
        }
        
        for(int i = 0; i < atacante.size(); i++){
            while(dadoD > 0 && qtd[i] > 0){
                atacante.get(i).removerExercitoAereo();
                qtd[i] -= 1;
                dadoD--;
            }
        }
        this.telaPrincipal.atualizarMapa();
        //RECUPERANDO OS EXERCITOS QUE SOBRARAM PARA COMBATER
        for(int i = 0; i < atacante.size(); i++){
            if(qtd[i] > 0){
                for(int j = 0; j < qtd[i]; j++){
                    exercAtacante.add(atacante.get(i).getUmExercAereo());
                }
            }
        }
        
        JOptionPane.showMessageDialog(null, "Faltam " + exercAtacante.size() + " exercito(s) aéreo(s) sem abater!");
        if(exercAtacante.size() > 0){
            jogarDados(exercAtacante.size(), dadosA, exercAtacante);
            ordenar(dadosA);
            for(int i = 0; i < exercAtacante.size(); i++){
                if(defensor.getQuantExercTerrestre() > 1){
                    if(dadosA[i] == 0){
                        if(cont == 2){
                            JOptionPane.showMessageDialog(null, "Você tirou 0, nada acontece!");
                        }else{
                            JOptionPane.showMessageDialog(null, "Seu adversário tirou 0, nada acontece!");
                        }
                    }else if(dadosA[i] == 1){
                        if(cont == 2){
                            JOptionPane.showMessageDialog(null, "Você tirou 1, seu adversário perde " 
                                    + (defensor.getQuantExercAereo()>0?1:0) + " exército aéreo e 1 terrestre no "
                                    + defensor.getNome() + "!");
                        }else{
                            JOptionPane.showMessageDialog(null, "Seu adversário tirou 1,você perde perde " 
                                    + (defensor.getQuantExercAereo()>0?1:0) + " exército aéreo e 1 terrestre no "
                                    + defensor.getNome() + "!");
                        }
                        if(defensor.getQuantExercAereo() > 0){
                            defensor.removerExercitoAereo();
                        }
                        defensor.removerExercitoTerrestre();
                    }else if(dadosA[i] == 2){
                        if(cont == 2){
                            JOptionPane.showMessageDialog(null, "Você tirou 2, seu adversário perde " 
                                    + (defensor.getQuantExercAereo()>0?1:0) + " exército aéreo e " + (defensor.getQuantExercTerrestre()>2?2:1) + " terrestre no "
                                    + defensor.getNome() + "!");
                        }else{
                            JOptionPane.showMessageDialog(null, "Seu adversário tirou 2,você perde perde " 
                                    + (defensor.getQuantExercAereo()>0?1:0) + " exército aéreo e " + (defensor.getQuantExercTerrestre()>2?2:1)  + " terrestre no "
                                    + defensor.getNome() + "!");
                        }
                        if(defensor.getQuantExercAereo() > 0){
                            defensor.removerExercitoAereo();
                        }
                        defensor.removerExercitoTerrestre();
                        if(defensor.getQuantExercTerrestre() > 1){
                            defensor.removerExercitoTerrestre();
                        }
                    }else if(dadosA[i] == 3){
                        
                        if(defensor.getQuantExercTerrestre() > 3){
                            if(cont == 2){
                                JOptionPane.showMessageDialog(null, "Você tirou 3, seu adversário perde " 
                                    + (defensor.getQuantExercAereo()>0?1:0) + " exército aéreo e 3 terrestre no "
                                    + defensor.getNome() + "!");
                            }else{
                                JOptionPane.showMessageDialog(null, "Seu adversário tirou 3,você perde perde " 
                                    + (defensor.getQuantExercAereo()>0?1:0) + " exército aéreo e 3 terrestre no "
                                    + defensor.getNome() + "!");
                            }    
                        }else if(defensor.getQuantExercTerrestre() == 3){
                            if(cont == 2){
                                JOptionPane.showMessageDialog(null, "Você tirou 3, seu adversário perde " 
                                    + (defensor.getQuantExercAereo()>0?1:0) + " exército aéreo e 2 terrestre no "
                                    + defensor.getNome() + "!");
                            }else{
                                JOptionPane.showMessageDialog(null, "Seu adversário tirou 3,você perde perde " 
                                    + (defensor.getQuantExercAereo()>0?1:0) + " exército aéreo e 2 terrestre no "
                                    + defensor.getNome() + "!");
                            } 
                        }else{
                            if(cont == 2){
                                JOptionPane.showMessageDialog(null, "Você tirou 3, seu adversário perde " 
                                    + (defensor.getQuantExercAereo()>0?1:0) + " exército aéreo e 1 terrestre no "
                                    + defensor.getNome() + "!");
                            }else{
                                JOptionPane.showMessageDialog(null, "Seu adversário tirou 3,você perde perde " 
                                    + (defensor.getQuantExercAereo()>0?1:0) + " exército aéreo e 1 terrestre no "
                                    + defensor.getNome() + "!");
                            }     
                        }
                        if(defensor.getQuantExercAereo() > 0){
                            defensor.removerExercitoAereo();
                        }
                        defensor.removerExercitoTerrestre();
                        if(defensor.getQuantExercTerrestre() > 1){
                            defensor.removerExercitoTerrestre();
                        }
                        if(defensor.getQuantExercTerrestre() > 1){
                            defensor.removerExercitoTerrestre();
                        }
                    }
                    this.telaPrincipal.atualizarMapa();
                }
    
            }
        }
        this.exercAereo = 0;
        this.telaPrincipal.desabilitarBotaoAtacar();
        this.controleBotoes.zerarSelecoes();
        this.telaPrincipal.atualizarMapa();
    }
    
    //ATAQUE AEREO
    private void ataqueAereoMaquina(){
        //DEFENSOR = HUMANO
        //ATACANTE = MAQUINA
        ArrayList<Territorio> territoriosDefensor = j1.getTerritorios();
        ArrayList<Territorio> territoriosAtacante = j2.getTerritorios();
        Territorio defensor;
        ArrayList<Territorio> atacante = new ArrayList<>();
        
        ArrayList<Territorio> advRespRegras = new ArrayList<>();//CONTEM TERRITORIOS DO ADVERSÁRIO QUE RESPEITAM A REGRA DO ATAQUE AEREO
                                                                 //4 OU MAIS EXERCITOS TERRESTRE E 1 OU MAIS AEREO
        ArrayList<Territorio> territoriosContFronteira = new ArrayList<>();//CONTEM TODOS OS TERRITORIO CUJO CONTINENTE FAZ FRONTEIRA COM O TERRITORIO
                                                                 //DO ADVERSARIO QUE DESEJA ATACAR
        
        Integer dadosD[] = new Integer[3];
        int randDefensor, aux, qtdExercitoAtaque = 0;
        
        inicializarVetor(dadosD);
        
        Random random = new Random();
        /* SELECIONANDO OS TERRITIROS DO DEFENSOR/HUMANO QUE RESPEITAM AS REGRAS */
        for(int i = 0; i < territoriosDefensor.size(); i++){
            if(territoriosDefensor.get(i).getQuantExercTerrestre() >= 4 && territoriosDefensor.get(i).getQuantExercAereo() >= 1 &&
                    territoriosDefensor.get(i).verificarFronteiraComUmDoAdversário()){
                advRespRegras.add(territoriosDefensor.get(i));
            }
        }
        
        if(!advRespRegras.isEmpty()){
            randDefensor = random.nextInt(advRespRegras.size());
            defensor = advRespRegras.get(randDefensor);
            
            //PEGAR TODOS OS CONTINENTES DO JOGADOR ATACANTE DO MESMO CONTINENTE OU
            //DO CONTINENTE VIZINHO
            for(int i = 0; i < territoriosAtacante.size(); i++){
                if(defensor.getContinente().verificarContinenteAdjacentes(territoriosAtacante.get(i).getContinente()) ||
                        territoriosAtacante.get(i).getContinente().equals(defensor.getContinente())){
                    territoriosContFronteira.add(territoriosAtacante.get(i));
                }
            }
            
            //SORTEANDO OS TERRITORIO QUE IRÁ FAZER O ATAQUE
            int k = 0;
            aux = 3;
            
            Integer qtd[] = new Integer[3];
            inicializarVetor(qtd);
            
            while(!territoriosContFronteira.isEmpty() && aux > 0){
                int j = random.nextInt(territoriosContFronteira.size());
                if(territoriosContFronteira.get(j).getQuantExercAereo() > 0){
                    atacante.add(territoriosContFronteira.get(j));
                    if(territoriosContFronteira.get(j).getQuantExercAereo() < aux){
                        qtd[k] = territoriosContFronteira.get(j).getQuantExercAereo();
                    }else{
                        qtd[k] = aux;
                    }
                    k++;
                    aux -= territoriosContFronteira.get(j).getQuantExercAereo();
                    territoriosContFronteira.remove(territoriosContFronteira.get(j));
                }
            }
            
            String frase = "";
            for(int i = 0; i < atacante.size(); i++){
                if(i < atacante.size() - 1){
                    frase +=  atacante.get(i).getNome() + " ( com " + qtd[i] 
                            + " exercito(s))" + " e ";
                }else{
                    frase +=  atacante.get(i).getNome() + " ( com " + qtd[i] 
                            + " exercito(s)) ";
                }
            }
            
            for(int i = 0; i < atacante.size(); i++){
                qtdExercitoAtaque += atacante.get(i).getQuantExercAereo();
            }
            if(qtdExercitoAtaque > 3){
                qtdExercitoAtaque = 3;
            }
            
            JOptionPane.showMessageDialog(null, "Seu adversário irá realizar um ataque aéreo no território " 
                    + defensor.getNome() + " pelo(s) território(s) "+ frase + "!");
            
            combateAtaqueAereo(defensor, atacante, qtd);
        }
    }
    
    
    private void inicializarVetor(Integer v[]){
        for(int i = 0; i < v.length; i++){
                v[i] = -1;
        }
    }

    @Override
    public void eventoAlterarAtaque(int v) {
        if(cont == 2){
            if( v == 0){
                this.controleBotoes.zerarSelecoes();
                this.faseAtaque = 0;
            }else if(cont == 2){
                this.controleBotoes.zerarSelecoes();
                this.faseAtaque = 1;
            }
            ataqueHumano();
        }
    }
    
    public void remanejamentoMaquina(){
        Random random = new Random();
        int t = random.nextInt(2), a = random.nextInt(2);
        if(t == 1){
            remanejamentoTerrestreMaquina();
        }
        if(a == 1){
            remanejamentoAereoMaquina();
        }
    }
    
    public void remanejamentoTerrestreMaquina(){
        Random random = new Random();
        ArrayList<Territorio> territorios = j2.getTerritorios();
        Territorio origem, destino = null;
        int indexTerritorio = 0;
        indexTerritorio = random.nextInt(territorios.size());
        if (territorios.get(indexTerritorio).getQuantExercTerrestre() > 1 && 
                    territorios.get(indexTerritorio).qtdExercitoTerrestreRemanejados() < territorios.get(indexTerritorio).getQuantExercTerrestre()) {
            origem = territorios.get(indexTerritorio);
            ArrayList<Territorio> fronteiras = origem.getFronteiras();
            for (Territorio f: fronteiras) {
                if (f.getJogador() == j2) {
                    destino = f;
                    break;
                }
            }
            if (destino != null) {
                int qtd = 0;
                if(origem.qtdExercitoTerrestreRemanejados() > 0){
                    qtd = random.nextInt(origem.qtdExercitoTerrestreNRemanejados()) + 1;
                }else if(origem.qtdExercitoTerrestreRemanejados() == 0){
                    qtd = random.nextInt(origem.getQuantExercTerrestre() - 1) + 1;
                
                }
                JOptionPane.showMessageDialog(this.telaPrincipal,"Seu adversário vai deslocar "
                                + qtd + " exército(s) terrestre(s) de " + origem.getNome() + " para " + destino.getNome());
                while(qtd > 0) {
                    origem.removerExercitoTerrestreRemanejado();
                    destino.addExercito(new Terrestre(true));
                    qtd--;
                }
                this.telaPrincipal.atualizarMapa();
            }
        }
    }
    
    public void remanejamentoAereoMaquina(){
        Random random = new Random();
        ArrayList<Territorio> territorios = j2.getTerritorios();
        Territorio origem, destino = null;
        int indexTerritorio = 0;
        
            
        indexTerritorio = random.nextInt(territorios.size());
        if (territorios.get(indexTerritorio).getQuantExercAereo() > 0
                        && territorios.get(indexTerritorio).qtdExercitoAereoRemanejados() < territorios.get(indexTerritorio).getQuantExercAereo()) {
            origem = territorios.get(indexTerritorio);
            ArrayList<Territorio> fronteiras = origem.getFronteiras();
            for (Territorio f : fronteiras) {
                if (f.getJogador() == j2) {
                    destino = f;
                    break;
                }
            }
            if (destino != null) {
                int qtd = random.nextInt(origem.qtdExercitoAereoNRemanejados()) + 1;
                JOptionPane.showMessageDialog(null, "Seu adversário vai deslocar "
                                + qtd + " exército(s) aéreo(s) de " + origem.getNome() + " para " + destino.getNome());
                while (qtd > 0) {
                    origem.removerExercitoAereoRemanejado();
                    destino.addExercito(new Aereo(true));
                    qtd--;
                }
                this.telaPrincipal.atualizarMapa();
            }
        }
    }
}