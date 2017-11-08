/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cyber.controller;

import br.com.cyber.DAO.ProdutoDAO;
import br.com.cyber.util.ButtonColumnDelete;
import br.com.cyber.util.ButtonColumnUpdate;
import br.com.cyber.util.CyberJOptionPane;
import br.com.cyber.entity.Produto;
import br.com.cyber.view.AlterarProduto;
import br.com.cyber.view.ListagemProduto;
import br.com.cyber.view.panel.PanelListagemProduto;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Daniel M. Kuhn
 */
public class ListagemProdutoController {

    private static final Logger LOG = Logger.getLogger(ListagemProdutoController.class.getName());
    
    
    private static ListagemProduto listagemproduto;
    
    private List<Produto> produtos; // lista referente a tabela
        
    private static PanelListagemProduto panellistagemproduto;
    
   

    // Remove as linhas antigas da tabela, seleciona no banco e adiciona novamente
    public static void atualizaTabela() {
        removeLinhas();
        ListagemProdutoController lpc = new ListagemProdutoController();
        
        String termos_pesquisa = panellistagemproduto.etSearch.getText();
        
        // verifica se existe algo no campo de busca
        if (termos_pesquisa.isEmpty()) {
            lpc.addLinhaTabela(false, null);
        } else {
            lpc.addLinhaTabela(true, termos_pesquisa);
        }
    }
    
    
    /**
     * Adiciona linhas na tabela
     * 
     * @fontes https://www.youtube.com/watch?v=GAl1FSKvoFY
     */
    public void addLinhaTabela(boolean pesquisa, String termos) {
        try {
            DefaultTableModel model = (DefaultTableModel) panellistagemproduto.jtProduto.getModel();
            
            ProdutoDAO pdao = new ProdutoDAO();

            // caso seja uma pesquisa por termos chave
            if (pesquisa) {
                produtos = pdao.selecionaProdutoPorTituloDescricao(termos);
            } else {
                // listagem de todos os produtos
                produtos = pdao.selecionaTodosProdutos();
            }
            Object rowData[] = new Object[4];
            
            if (!produtos.isEmpty()) 
            {
                for (Produto produto : produtos) 
                {
                    rowData[0] = produto.getTitulo();
                    rowData[1] = produto.getDescricao();
                    rowData[2] = "Alterar";
                    rowData[3] = "Excluir";
                    model.addRow(rowData);
                }

                Action update;
                update = new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int modelRow = Integer.valueOf(e.getActionCommand());

                        AlterarProduto ap = new AlterarProduto(listagemproduto, true, produtos.get(modelRow).getId());
                        ap.setVisible(true);
                    }
                };

                Action delete;
                delete = new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        String title = "Excluir";
                        String msg = "Tem certeza que deseja excluir?";
                        boolean result = CyberJOptionPane.showMyOptionPane(title, msg, "Sim", "Não");

                        if (result == true) 
                        {
                            int modelRow = Integer.valueOf(e.getActionCommand());

                            if (pdao.deleteProduto(produtos.get(modelRow).getId())) {
                                atualizaTabela();
                            }
                        }
                    }
                };
                
                // aqui as colunas de índice 2 e 3 são alteradas para serem botões
                
                ButtonColumnUpdate buttonColumn2 = new ButtonColumnUpdate(panellistagemproduto.jtProduto, update, 2);
                buttonColumn2.setMnemonic(KeyEvent.VK_D);

                ButtonColumnDelete buttonColumn3 = new ButtonColumnDelete(panellistagemproduto.jtProduto, delete, 3);
                buttonColumn3.setMnemonic(KeyEvent.VK_D);
            }

        } catch (Exception e) {
            System.out.println(LOG+" "+e.getMessage());
        }
    }
    
    
    /**
     * 
     * Remove todas as linhas da tabela
     * 
     * @fontes
     * https://stackoverflow.com/questions/6232355/deleting-all-the-rows-in-a-jtable
     */
    public static void removeLinhas() {
        try {
            DefaultTableModel dtm = (DefaultTableModel) panellistagemproduto.jtProduto.getModel();
            dtm.setRowCount(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
   
    // Limpa o campo de busca
    public static void limpaCampoBusca() {
        panellistagemproduto.etSearch.setText("");
    }
}
