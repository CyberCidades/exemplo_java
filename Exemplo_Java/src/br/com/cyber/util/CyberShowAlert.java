/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cyber.util;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Daniel M. Kuhn
 */
public class CyberShowAlert {
    
    public void showAlert(JPanel panel, JLabel icone, JLabel label, Boolean ret, String txt) {
        label.setText(txt);
        panel.setVisible(true);
        label.setVisible(true);
        
        if (ret == true) {
            icone.setIcon(new ImageIcon(getClass().getResource("/br/com/cyber/imagens/check-20.png")));
            label.setForeground(new Color(6, 118, 46));
            
            Timer t = new Timer(3000, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    label.setVisible(false);
                    panel.setVisible(false);
                }
            });
            t.setRepeats(false);
            t.start();
        }else {
            icone.setIcon(new ImageIcon(getClass().getResource("/br/com/cyber/imagens/caution-20.png")));
            label.setForeground(new Color(254, 91, 91));
        }
    }
}
