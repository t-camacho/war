/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war.jogo;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author thais, rafael
 */
public class LayoutTerritorio extends JPanel{
    
    private JLabel lblBackground;//IMAGEM QUE DIZ O ESTADO DO BOTAO(SELECIONADO(VERDE) OU NORMAL(AZUL OU VERMELHO))
    private JLabel lblNome;//NOME DO TERRITORIO
    private JLabel lblAereo;//QUANTIDADE DE EXERCITO AEREO DO TERRITORIO
    private JLabel lblTerrestre;//QUANTIDADE DE EXERCITO TERRESTRE DO TERRITORIO
    private JLabel lblContinente;
    private String cor;
    private Territorio territorio;
    
    //CONSTRUTOR
    public LayoutTerritorio(int i, int j, Territorio territorio){
        this.setLayout(null);
        this.setBounds(120*j,50*i,120,50);
        this.territorio = territorio;
        lblBackground = new JLabel();
        lblBackground.setBounds(0, 0, 120, 50);

        lblNome = new JLabel();
        lblNome.setBounds(5, 15, 80, 20);

        lblTerrestre = new JLabel();
        lblTerrestre.setBounds(100, 10, 20, 10);

        lblAereo = new JLabel();
        lblAereo.setBounds(100, 30, 20, 10);
        
        lblContinente = new JLabel();
        lblContinente.setBounds(3, 3, 10, 10);
        
        //ADICIONANDO COMPONENTES AOS PAINEIS
        this.add(lblNome);
        this.add(lblTerrestre);
        this.add(lblAereo);
        this.add(lblContinente);
        this.add(lblBackground);
        //SETANDO VALORES DOS PAINEIS
        if(territorio.getJogador().getCor().equals("Vermelho")){
            setLblBackgroundVermelho();
        }else{
            setLblBackgroundAzul();
        }
        lblNome.setText(territorio.getNome());
        lblTerrestre.setText(Integer.toString(territorio.getQuantExercTerrestre()));
        lblAereo.setText(Integer.toString(territorio.getQuantExercAereo()));
        this.cor = territorio.getJogador().getCor();
        if(territorio.getContinente().getNome().equals("America do Sul")){
            this.lblContinente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/war/imagens/continentes/verde.png")));
        }else if(territorio.getContinente().getNome().equals("America do Norte")){
            this.lblContinente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/war/imagens/continentes/amarela.png")));
        }else if(territorio.getContinente().getNome().equals("Oceania")){
            this.lblContinente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/war/imagens/continentes/rosa.png")));
        }else if(territorio.getContinente().getNome().equals("Europa")){
            this.lblContinente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/war/imagens/continentes/verdeagua.png")));
        }else if(territorio.getContinente().getNome().equals("Asia")){
            this.lblContinente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/war/imagens/continentes/laranja.png")));
        }else{
            this.lblContinente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/war/imagens/continentes/cinza.png")));
        }
            
    }

    public JLabel getLblNome() {
        return lblNome;
    }

    public void setLblNome(JLabel lblNome) {
        this.lblNome = lblNome;
    }

    public JLabel getLblBackground() {
        return lblBackground;
    }

    public void setLblBackgroundVermelho() {
        this.lblBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/war/imagens/geral/territorio_vermelho.jpg")));
    }
    
    public void setLblBackgroundAzul() {
        this.lblBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/war/imagens/geral/territorio_azul.jpg")));
    }
    
    public void setLblBackgroundVerde() {
        this.lblBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/war/imagens/geral/territorio_verde.jpg")));
    }

    public JLabel getLblAereo() {
        return lblAereo;
    }

    public void atualizarLblAereo() {
        this.lblAereo.setText(Integer.toString(territorio.getQuantExercAereo()));
    }

    public JLabel getLblTerrestre() {
        return lblTerrestre;
    }

    private void atualizarLblTerrestre() {
        this.lblTerrestre.setText(Integer.toString(territorio.getQuantExercTerrestre()));
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Territorio getTerritorio() {
        return territorio;
    }

    public void atualizar(){
        this.cor = this.territorio.getJogador().getCor();
        
        if(cor.equals("Vermelho")){
            setLblBackgroundVermelho();
        }else{
            setLblBackgroundAzul();
        }
        atualizarLblTerrestre();
        atualizarLblAereo();
    }
}
