/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cyber.controller;

import br.com.cyber.DAO.ProdutoDAO;
import br.com.cyber.util.CyberShowAlert;
import br.com.cyber.entity.Categoria;
import br.com.cyber.entity.Produto;
import br.com.cyber.view.panel.PanelAlterarProduto;

/**
 *
 * @author Daniel M. Kun
 */
public class AlterarProdutoController {

    ListagemProdutoController listagemprodutocontroller; // atualizar a tabela quando inserir

    PanelAlterarProduto panelalterarproduto;

    CategoriaController categoriacontroller; // acessar o array de categorias

    CyberShowAlert csa = new CyberShowAlert(); // para usar o método ShowAlert

    
    // preenche os campos do formulário
    public void setFormulario(Integer produto_id) {
    
        ProdutoDAO pdao = new ProdutoDAO();
        
        Produto p = pdao.selecionaProdutosPorId(produto_id);
        
        panelalterarproduto.etTitulo.setText(p.getTitulo());
        panelalterarproduto.etDescricao.setText(p.getDescricao());
        panelalterarproduto.cbCategoria.setSelectedIndex(CategoriaController.getIndicePorCategoria(p.getCategoria().getId()));
    }
    
    
    // valida os campos obrigatórios e emite alertas
    public boolean verificarFormulario(String titulo, String descricao, Integer categoria_id) {
        if (!titulo.equals("")) {
            if (!descricao.equals("")) {
                return true;
            } else {
                // usando o método CyberShowAlert 
                csa.showAlert(panelalterarproduto.pnAlert, panelalterarproduto.lbIconAlert, panelalterarproduto.lbAlert, false, "Informe uma descrição");
            }
        } else {
            csa.showAlert(panelalterarproduto.pnAlert, panelalterarproduto.lbIconAlert, panelalterarproduto.lbAlert, false, "Informe um título");
        }
        return false;
    }
    

    public void limparCampos() {

        panelalterarproduto.etTitulo.setText("");
        panelalterarproduto.etDescricao.setText("");
        
        // dá o foco no campo titulo novamente, caso o usuário queira adicionar outro
        panelalterarproduto.etTitulo.requestFocus(); 
    }

    
    public boolean alterarProduto(Integer produto_id) {

        String titulo = panelalterarproduto.etTitulo.getText();
        String descricao = panelalterarproduto.etDescricao.getText();
        Integer categoria_id = categoriacontroller.getCategoriaPorIndice(panelalterarproduto.cbCategoria.getSelectedIndex());

        ProdutoDAO pdao = new ProdutoDAO();

        if (verificarFormulario(titulo, descricao, categoria_id)) {

            boolean alteracao = pdao.alterarProduto(new Produto(produto_id, titulo, descricao, new Categoria(categoria_id)));

            if (alteracao) {
                limparCampos();
                // atualiza a tabela da janela de listagem dos produtos
                listagemprodutocontroller.atualizaTabela();
                csa.showAlert(panelalterarproduto.pnAlert, panelalterarproduto.lbIconAlert, panelalterarproduto.lbAlert, true, "Alterado com sucesso");
                return true;
            }
        }
        return false;
    }
}
