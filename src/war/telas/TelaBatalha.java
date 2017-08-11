/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war.telas;

import war.jogo.IEventosTelaBatalha;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import war.jogo.ControladoraTerritoriosSelecionados;
import war.jogo.Jogador;
import war.jogo.LayoutTerritorio;
import war.jogo.Mapa;
import war.jogo.Territorio;

/**
 *
 * @author thais
 */
public class TelaBatalha extends JFrame{
    
    private JPanel painel;//PAINEL PRINCIPAL DA JFRAME
    private ControladoraTerritoriosSelecionados controleBotoes;
    private LayoutTerritorio paineis[][];//PAINEIS SERIAM COMO BOTOES QUE REPRESENTAM UM TERRITORIO
    private IEventosTelaBatalha interfaceTela;
    
    private JLabel lblCorJogador;
    
    private JButton btnAtacar;
    private JButton btnDefender;
    private JButton btnAtacarA;
    
    private JButton btnConfirmarDistribuicao;
    private JButton btnFAtaques;
    private JButton btnFRemanejamentos;
    private JButton btnRemanejamentos;
    private JLabel estado;
    
    private JSlider slider;
    private JLabel sliderValor;
    
    private JSlider JSAtaque;
    private JLabel terrestre;
    private JLabel aereo;
    
    private Mapa mapa;//TODOS OS TERRITORIOS DO JOGO
    private Jogador j1;//USUARIO
    private Jogador j2;//MAQUINA*/
    
    //CONSTRUTOR
    public TelaBatalha(boolean cor){
        //CONFIGURANDO O JFRAME
        super("War");
        this.setBounds(1000, 1000, 966, 375);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        this.painel = new JPanel();
        
        //INSTANCIANDO O MAPA E O CONTROLE DOS BOTOES
        mapa = new Mapa();
        controleBotoes = new ControladoraTerritoriosSelecionados();
            
        //INSTANCIANDO OS JOGADORES E DIVIDINDO OS TERRITORIOS
        if(!cor){
            j1 = new Jogador("Azul");
            j2 = new Jogador("Vermelho");
        }else{
            j1 = new Jogador("Vermelho");
            j2 = new Jogador("Azul");
        }
        
        mapa.dividirTabuleiro(j1, j2);
        adicionarMapaNaTela();

        //BOTOES E SEUS EVENTOS
        btnFRemanejamentos = new JButton();
        painel.add(btnFRemanejamentos);
        btnFRemanejamentos.setText("Finalizar Remanejamentos");
        btnFRemanejamentos.setBounds(735, 285, 220, 25);
        
        btnFRemanejamentos.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(java.awt.event.ActionEvent evt) {
                  interfaceTela.eventoFinalizarRemanejamento();
              }
          });
        
        btnRemanejamentos = new JButton();
        painel.add(btnRemanejamentos);
        btnRemanejamentos.setText("Confirmar Remanejamentos");
        btnRemanejamentos.setBounds(735, 255, 220, 25);
        
        btnRemanejamentos.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(java.awt.event.ActionEvent evt) {
                  interfaceTela.eventoRemanejamento();
              }
          });
        
        btnAtacar = new JButton("Atacar");
        painel.add(btnAtacar);
        btnAtacar.setBounds(230, 285, 220, 25);
        
        btnAtacar.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(java.awt.event.ActionEvent evt) {
                  interfaceTela.eventoBotaoAtacar();
              }
          });
        
        btnConfirmarDistribuicao = new JButton("Confirmar Distribuicao");
        painel.add(btnConfirmarDistribuicao);
        btnConfirmarDistribuicao.setBounds(230, 255, 220, 25);
        
        btnConfirmarDistribuicao.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(java.awt.event.ActionEvent evt) {
                  interfaceTela.eventoBotaoConfirmarDistribuicao();
              }
          });
        
        btnFAtaques = new JButton("Finalizar Ataques");
        painel.add(btnFAtaques);
        btnFAtaques.setBounds(230, 315, 220, 25);
        
        btnFAtaques.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(java.awt.event.ActionEvent evt) {
                  interfaceTela.eventoFinalizarAtaque();
              }
          });
        //SLIDER QUANTIDADE
        sliderValor = new JLabel();
        slider = new JSlider(1, 4, 1);
        this.sliderValor.setText(Integer.toString(1));
        painel.add(sliderValor);
        painel.add(slider);
        
        sliderValor.setBounds(193, 262, 30, 10);
        slider.setBounds(10, 260, 180, 20);
        
        slider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent me) {
                sliderValor.setText(Integer.toString(slider.getValue()));
            }
        });
        //SLIDER ATAQUE E DEFESA
        terrestre = new JLabel("T");
        aereo = new JLabel("A");
        JSAtaque = new JSlider(0, 1, 0);
        painel.add(JSAtaque);
        painel.add(terrestre);
        painel.add(aereo);
        
        terrestre.setBounds(50, 290, 50, 10);
        aereo.setBounds(135, 290, 50, 10);
        JSAtaque.setBounds(75, 288, 50, 20);
        
        JSAtaque.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent me) {
                interfaceTela.eventoAlterarAtaque(JSAtaque.getValue());
            }
        });
        
        //EXIBE A COR DO JOGADOR
        this.lblCorJogador = new JLabel("Você é o Jogador " + (cor?"Vermelho!":"Azul!"));
        painel.add(this.lblCorJogador);
        this.lblCorJogador.setBounds(20, 315, 200, 30);
        
        //MENSAGEM DE ESTADO
        estado = new JLabel();
        painel.add(estado);
        estado.setBounds(470, 250, 300, 100);
        
        
        //ACAO DE SELECIONAR UM BOTAO
        MouseListener selecionarTerritorio = (new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                interfaceTela.eventoSelecionarTerritorio(((LayoutTerritorio)e.getComponent()).getLblNome().getText(), (LayoutTerritorio)e.getSource());
            }
        });
        
        //EVENTO DE SELECIONAR PARA OS TERRITORIOS/BOTOES
        Territorio territorios[][] = mapa.getTerritorios();
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 8; j++){
                if(territorios[i][j] != null){
                    paineis[i][j].addMouseListener(selecionarTerritorio);
                }
            }
        }
    }
    
    
    public void atualizarMapa(){
        LayoutTerritorio t[][] = this.getPaineis();
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 8; j++){
                if(t[i][j] != null){
                    t[i][j].atualizar();
                }
            }
        }
    }
    
    //METODOS GERAIS
    public void adicionarMapaNaTela(){
        Territorio territorios[][] = mapa.getTerritorios();
        
        //CONSTRUCAO DO MAPA NA GUI
        this.add(painel);
        this.painel.setLayout(null);
        
        paineis = new LayoutTerritorio[5][8];
        //INSTANCIANDO CADA TERRITORIO DO MAPA/PAINEIS
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 8; j++){
                if(territorios[i][j] != null){
                    paineis[i][j] = new LayoutTerritorio(i, j, territorios[i][j]);
                    this.painel.add(paineis[i][j]);
                    controleBotoes.adiconarTerritorio(paineis[i][j]);
                }
            }
        }
    }
    
    //ACESSORES E MODIFICADORES
    public IEventosTelaBatalha getInterfaceTela() {
        return interfaceTela;
    }

    public void setInterfaceTela(IEventosTelaBatalha interfaceTela) {
        this.interfaceTela = interfaceTela;
    }

    public Jogador getJ1() {
        return j1;
    }

    public void setJ1(Jogador j1) {
        this.j1 = j1;
    }

    public Jogador getJ2() {
        return j2;
    }

    public Mapa getMapa() {
        return mapa;
    }

    public void setMapa(Mapa mapa) {
        this.mapa = mapa;
    }

    public LayoutTerritorio[][] getPaineis() {
        return paineis;
    }

    public ControladoraTerritoriosSelecionados getControleBotoes() {
        return controleBotoes;
    }

    public void setControleBotoes(ControladoraTerritoriosSelecionados controleBotoes) {
        this.controleBotoes = controleBotoes;
    }
    
    public void desabilitarBotaoAtacar(){
        btnAtacar.setEnabled(false);
    }
    
    public void desabilitarBotaoConfirmarDistribuicao(){
        btnConfirmarDistribuicao.setEnabled(false);
    }

    public void desabilitarBotaoFAtaques(){
        btnFAtaques.setEnabled(false);
    }
    
    public void desabilitarBotaoFRemanejamentos(){
        btnFRemanejamentos.setEnabled(false);
    }
    
    public void desabilitarBotaoRemanejamentos(){
        btnRemanejamentos.setEnabled(false);
    }
    
    public void habilitarBotaoAtacar(){
        btnAtacar.setEnabled(true);
    }

    public void habilitarBotaoConfirmarDistribuicao(){
        btnConfirmarDistribuicao.setEnabled(true);
    }
    
    public void resetarSliderAtaque(){
        JSAtaque.setValue(0);
    }

    public void habilitarBotaoFAtaques(){
        btnFAtaques.setEnabled(true);
    }
    
    public void habilitarBotaoFRemanejamentos(){
        btnFRemanejamentos.setEnabled(true);
    }
    
    public void habilitarBotaoRemanejamentos(){
        btnRemanejamentos.setEnabled(true);
    }
    
    
    public void exibirMensagemDistribuicaoTerrestre(){
        this.estado.setText("É a sua vez de realizar distribuição terrestre!" );
    }
    
    public void exibirMensagemDistribuicaoAereo(){
        this.estado.setText("É a sua vez de realizar distribuição aérea!" );
    }
    
    public void exibirMensagemAtaqueTerrestre(){
        this.estado.setText("É sua vez de realizar ataques terrestre!" );
    }
    
    public void exibirMensagemAtaqueAereo(){
        this.estado.setText("É sua vez de realizar ataques aéreo!" );
    }
    
    public void exibirMensagemRemanejamentoTerrestre(){
        this.estado.setText("É sua vez de realizar remanej. terrestre!" );
    }
    
    public void exibirMensagemRemanejamentoAereo(){
        this.estado.setText("É sua vez de realizar remanejamento aéreo!" );
    }
    
    public void alterarMaximoSlider(int max){
        slider.setMaximum(max);
    }
    
    public void alterarValorSlider(){
        slider.setValue(1);
        this.sliderValor.setText("1");
    }
    
    public int getValorSlider(){
        return this.slider.getValue();
    }
    
}
