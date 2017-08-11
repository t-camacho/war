/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war.jogo;

import war.jogo.Jogador;
import war.jogo.LayoutTerritorio;

/**
 *
 * @author thais
 */
public interface IEventosTelaBatalha {
    public void eventoBotaoAtacar();
    public void eventoBotaoConfirmarDistribuicao();
    public void eventoFinalizarAtaque();
    public void eventoFinalizarRemanejamento();
    public void eventoSelecionarTerritorio(String nome, LayoutTerritorio territorio);
    public void eventoRemanejamento();
    public void eventoAlterarAtaque(int v);
}
