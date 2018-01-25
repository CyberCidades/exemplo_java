/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cyber.DAO;

import br.com.cyber.entity.Categoria;
import br.com.cyber.persistence.Conexao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Daniel M. Kuhn
 */
public class CategoriaDAO {

    private static final Logger LOG = Logger.getLogger(CategoriaDAO.class.getName());
    
    
    public List<Categoria> selecionaTodasCategorias() {
    
        Connection conn = Conexao.getConnection();
        
        String sql = "SELECT * FROM categoria c";
        
        try 
        {   
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            List<Categoria> categorias = new ArrayList<>();
            
            while (rs.next()) 
            {    
                Categoria c = new Categoria();

                c.setId(rs.getInt("id"));
                c.setTitulo(rs.getString("titulo"));
                
                categorias.add(c);
            }
            stmt.close();
            return categorias;
            
        } catch (SQLException e) {
            System.out.println(LOG+" "+e.getMessage());
            return null;
        }
    }
}
