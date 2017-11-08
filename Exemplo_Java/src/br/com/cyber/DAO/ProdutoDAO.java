/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cyber.DAO;

import br.com.cyber.entity.Categoria;
import br.com.cyber.entity.Produto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;
import br.com.cyber.persistence.Conexao;
import br.com.cyber.util.Termos;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel M. Kuhn 
 */
public class ProdutoDAO {

    private static final Logger LOG = Logger.getLogger(ProdutoDAO.class.getName());

    public Integer adicionarProduto(Produto p) {
         try {
            String sql = "INSERT INTO produto (titulo, descricao, categoria_id) VALUES (?, ?, ?)";
            
            PreparedStatement pstmt = Conexao.getConnection()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            pstmt.setString(1, p.getTitulo());
            pstmt.setString(2, p.getDescricao());
            pstmt.setInt(3, p.getCategoria().getId());
            
            pstmt.executeUpdate();
            
            Integer produto_id = null;
            
            ResultSet rs = pstmt.getGeneratedKeys();
            
            if (rs.next()) {
                produto_id = rs.getInt(1);
            }
            
            pstmt.close();
            return produto_id;
            
        } catch(SQLException e) {
            System.out.println(LOG+" - "+e.getMessage());
            return null;
        }
    }
    
    
    public List<Produto> selecionaTodosProdutos() {
    
        Connection conn = Conexao.getConnection();
        
        String sql = "SELECT * FROM produto p";
        
        try 
        {   
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            List<Produto> produtos = new ArrayList<>();
            
            while (rs.next()) 
            {
                Produto p = new Produto();

                p.setId(rs.getInt("id"));
                p.setTitulo(rs.getString("titulo"));
                p.setDescricao(rs.getString("descricao"));
                p.setCategoria(new Categoria(rs.getInt("categoria_id")));
                
                produtos.add(p);
            }
            conn.close();
            stmt.close();
            return produtos;
            
        } catch (SQLException e) {
            System.out.println(LOG+" "+e.getMessage());
            return null;
        }
    }
    
    
    public Produto selecionaProdutosPorId(Integer id) {        
       
        String sql = " SELECT * FROM produto p WHERE p.id = ? ";
    
        try 
        {   
            PreparedStatement pstmt = Conexao.getConnection().prepareStatement(sql);

            pstmt.setInt(1, id);
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) 
            {    
                Produto p = new Produto();

                p.setId(rs.getInt("id"));
                p.setTitulo(rs.getString("titulo"));
                p.setDescricao(rs.getString("descricao"));
                p.setCategoria(new Categoria(rs.getInt("categoria_id")));
                
                return p;
            }
            
            return null;
                        
        } catch(SQLException e) {
            System.out.println(LOG+" "+e.getMessage());
            return null;
        }
    }
    
    
    public List<Produto> selecionaProdutoPorTituloDescricao(String termo) 
    {
        String sql = " SELECT * "  
                   + "   FROM produto "     
                   + "  WHERE unaccent(titulo) "
                   + "  ILIKE unaccent('%"+Termos.limpaTermos(termo)+"%') ";
                    
        try {
            Connection conn = Conexao.getConnection();

            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            List<Produto> produtos = new ArrayList<>();
            
            while (rs.next()) 
            {
                Produto p = new Produto();

                p.setId(rs.getInt("id"));
                p.setTitulo(rs.getString("titulo"));
                p.setDescricao(rs.getString("descricao"));
                p.setCategoria(new Categoria(rs.getInt("categoria_id")));
                
                produtos.add(p);
            }

            stmt.close();
            conn.close();
            return produtos;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    
    public boolean deleteProduto(Integer id) {
        try 
        {
            String sql = " DELETE FROM produto WHERE id = ? ";
            
            PreparedStatement pstmt = Conexao.getConnection().prepareStatement(sql);
            
            pstmt.setInt(1, id);
            
            if (pstmt.executeUpdate() == 1) {
                return true;
            }
            return false;
            
        } catch(SQLException e) {
            System.out.println(LOG+" "+e.getMessage());
            return false;
        }
    }
    
     public boolean alterarProduto(Produto p) {
        try 
        {
            String sql = " UPDATE produto "
                       + "    SET titulo = ?, "
                       + "        descricao = ?, "
                       + "        categoria_id = ? "
                       + "  WHERE id = ? ";

            PreparedStatement pstmt = Conexao.getConnection().prepareStatement(sql);
            
            pstmt.setString(1, p.getTitulo());
            pstmt.setString(2, p.getDescricao());
            pstmt.setInt(3, p.getCategoria().getId());
            pstmt.setInt(4, p.getId());

            if (pstmt.executeUpdate() == 1) {
                return true;
            }
            return false;
            
        } catch(SQLException e) {
            System.out.println(LOG+" "+e.getMessage());
            return false;
        }
    }
     
    public static void main(String[] args) {
        ProdutoDAO pdao = new ProdutoDAO();
        pdao.selecionaProdutoPorTituloDescricao("prod");
    }
}
