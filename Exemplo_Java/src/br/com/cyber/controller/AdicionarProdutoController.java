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
import br.com.cyber.view.panel.PanelAdicionarProduto;

/**
 *
 * @author Daniel M. Kuhn
 */

public class AdicionarProdutoController {
    
    ListagemProdutoController listagemprodutocontroller; // atualizar a tabela quando inserir
    
    PanelAdicionarProduto paneladicionarproduto;
    
    CategoriaController categoriacontroller; // acessar o array de categorias
    
    CyberShowAlert csa = new CyberShowAlert(); // para usar o método ShowAlert
    
    
    // valida os campos obrigatórios e emite alertas
    public boolean verificarFormulario(String titulo, String descricao, Integer categoria_id) 
    {
        if (!titulo.isEmpty()) {
            if (!descricao.isEmpty()) {    
                return true;
            } else {
                // usando o método CyberShowAlert 
                csa.showAlert(paneladicionarproduto.pnAlert, paneladicionarproduto.lbIconAlert, paneladicionarproduto.lbAlert, false, "Informe uma descrição");
            }
        } else {
            csa.showAlert(paneladicionarproduto.pnAlert, paneladicionarproduto.lbIconAlert, paneladicionarproduto.lbAlert, false, "Informe um título");
        }
        return false;
    }
    
    
    public void limparCampos() {
        
        paneladicionarproduto.etTitulo.setText("");
        paneladicionarproduto.etDescricao.setText("");
        
        // dá o foco no campo titulo novamente, caso o usuário queira adicionar outro
        paneladicionarproduto.etTitulo.requestFocus(); 
    }
    
    
    public boolean cadastrarProduto() {
    
        String titulo        = paneladicionarproduto.etTitulo.getText();
        String descricao     = paneladicionarproduto.etDescricao.getText();
        Integer categoria_id = categoriacontroller.getCategoriaPorIndice(paneladicionarproduto.cbCategoria.getSelectedIndex());
        
        ProdutoDAO pdao = new ProdutoDAO();
        
        if (verificarFormulario(titulo, descricao, categoria_id)) 
        {
            Integer insercao_id = pdao.adicionarProduto(new Produto(categoria_id, titulo, descricao, new Categoria(categoria_id)));
                
            if (insercao_id != null) {
                limparCampos();
                // atualiza a tabela da janela de listagem dos produtos
                listagemprodutocontroller.atualizaTabela();
                csa.showAlert(paneladicionarproduto.pnAlert, paneladicionarproduto.lbIconAlert, paneladicionarproduto.lbAlert, true, "Cadastrado com sucesso");
                return true;
            }
        }
        return false;
    }
}
