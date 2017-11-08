/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cyber.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 *
 * @author Daniel M. Kuhn
 */
public class Conexao {

    private static final Logger LOG = Logger.getLogger(Conexao.class.getName());
    

    private static String HOST     = "localhost";
    private static String BASE     = "exemplo_java";
    private static String USER     = "*";
    private static String PASSWORD = "*";
    
    public static Connection getConnection() {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {

           System.out.println(LOG+" "+e.getMessage());
        }

        try {
            return DriverManager.getConnection("jdbc:postgresql://"+HOST+":5432/"+BASE+"",USER, PASSWORD);	

        } catch (SQLException e) {
           System.out.println(LOG+" "+e.getMessage());
        }
        return null;
    }
    
    public static void main (String[] args) {
        
        Connection conn = Conexao.getConnection();       
    }
}
