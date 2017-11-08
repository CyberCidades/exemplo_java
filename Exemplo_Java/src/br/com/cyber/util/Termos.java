/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cyber.util;

import org.apache.commons.lang3.StringUtils;
/**
 *
 * @author Daniel M. Kuhn
 */
public class Termos {
    
    
    public static String limpaTermos(String input) 
    {
        String output = StringUtils.lowerCase(input);
        output = StringUtils.stripAccents(output);
        
        return output;
    }
    
    public static void main (String args[]) {
    
    Termos t = new Termos();
    t.limpaTermos("Tĥïŝ ĩš â fůňķŷ Šťŕĭńġ");
    }
}
