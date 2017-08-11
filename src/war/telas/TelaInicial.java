/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war.telas;

import war.jogo.ControladoraBatalha;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author thais
 */
public class TelaInicial extends JFrame{
   
   JPanel painel;
   private ButtonGroup cores;
   private JRadioButton RBVermelho;
   private JRadioButton RBAzul;
   private JButton btnIniciarJogo;
   private JLabel lblBackground;
    
    public TelaInicial(){
        super("War");
        this.setBounds(0, 0, 500, 430);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        
        
        this.painel = new JPanel();
        this.add(painel);
        painel.setLayout(null);
        
        
        cores = new ButtonGroup();
        RBVermelho = new JRadioButton();
        RBAzul = new JRadioButton();
        btnIniciarJogo = new JButton("Iniciar Jogo");
        this.lblBackground = new JLabel();
        
        painel.add(RBVermelho);
        RBVermelho.setText("Vermelho");
        RBVermelho.setBounds(150, 65, 80, 15);
        RBVermelho.setSelected(true);

        painel.add(RBAzul);
        RBAzul.setText("Azul");
        RBAzul.setBounds(300, 65, 50, 15);
        
        cores.add(RBAzul);
        cores.add(RBVermelho);
        
        painel.add(btnIniciarJogo);
        btnIniciarJogo.setBounds(200, 100, 100, 25);
        
        painel.add(lblBackground);
        lblBackground.setBounds(0, -50, 500, 500);
        this.lblBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/war/imagens/geral/background.jpg")));
        
        btnIniciarJogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                boolean aux = RBVermelho.isSelected();
                ControladoraBatalha c = new ControladoraBatalha(new TelaBatalha(aux));
            }
        });

    }
    
}
