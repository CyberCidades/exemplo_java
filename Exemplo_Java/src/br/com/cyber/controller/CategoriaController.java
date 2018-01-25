/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cyber.controller;

import br.com.cyber.DAO.CategoriaDAO;
import br.com.cyber.componente.KComboBox;
import br.com.cyber.entity.Categoria;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 *
 * @author Daniel M. Kuhn
 */
public class CategoriaController {
    
    private static List<Categoria> categorias; // lista referente ao combobox

    /*
        Popula os Combobox
    */
    public static void setComboBox(KComboBox combobox) 
    {
        CategoriaDAO cdao = new CategoriaDAO();
        
        categorias = cdao.selecionaTodasCategorias();
        
        System.out.println("cat: "+categorias);
        
        String[] array = new String[categorias.size()];
        
        for (int i = 0; i < array.length; i++) {
            array[i] = categorias.get(i).getTitulo();
        }
        combobox.setModel(new DefaultComboBoxModel(array));
    }
    
    public static int getCategoriaPorIndice(int indice) {
        return categorias.get(indice).getId();
    }
    
    public static int getIndicePorCategoria(int categoria_id) {
        return categorias.indexOf(new Categoria(categoria_id));
    }
}
