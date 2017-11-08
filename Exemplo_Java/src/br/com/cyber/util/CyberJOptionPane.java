/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cyber.util;

import br.daniel.componente.KButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Daniel M. Kuhn
 */
public class CyberJOptionPane {

    private static final Logger LOG = Logger.getLogger(CyberJOptionPane.class.getName());
    
    
    protected static JOptionPane getOptionPane(JComponent parent) {
        JOptionPane pane = null;
        if (!(parent instanceof JOptionPane)) {
            pane = getOptionPane((JComponent) parent.getParent());
        } else {
            pane = (JOptionPane) parent;
        }
        return pane;
    }
    
    
    public static boolean showMyOptionPane(String title, String msg, String label_ok, String label_cancel) 
    {
        /**
        * https://stackoverflow.com/questions/14591089/joptionpane-passing-custom-buttons
        */
        try {
            final KButton okay = new KButton();
            okay.setText(label_ok);
            okay.setBackground(new Color(77,202,121));
            okay.setForeground(Color.WHITE);
            okay.setFont(new Font("Century Gothic", Font.PLAIN, 15));
            okay.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JOptionPane pane = getOptionPane((JComponent)e.getSource());
                    // set the value of the option pane
                    pane.setValue(okay);
                }
            });

            okay.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        
                        JOptionPane pane = getOptionPane((JComponent)e.getSource());
                        // set the value of the option pane
                        pane.setValue(okay);
                    }
                }
            });
            

            KButton cancel = new KButton();
            cancel.setText(label_cancel);
            cancel.setBackground(new Color(254,135,112));
            cancel.setForeground(Color.WHITE);
            cancel.setFont(new Font("Century Gothic", Font.PLAIN, 15));
            cancel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    JOptionPane pane = getOptionPane((JComponent)e.getSource());
                    // set the value of the option pane
                    pane.setValue(cancel);
                }
            });

            cancel.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        
                        JOptionPane pane = getOptionPane((JComponent)e.getSource());
                        // set the value of the option pane
                        pane.setValue(cancel);
                    }
                }
            });

            JLabel label = new JLabel(msg);
            label.setFont(new Font("Century Gothic", Font.PLAIN, 15));

            JOptionPane jop = new JOptionPane();

            int dialogResult = jop.showOptionDialog(null, 
                label, 
                title, 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                new Object[]{okay, cancel},
                null);

            if (dialogResult == JOptionPane.YES_OPTION) 
            {   
               return true;
            }
            return false;
        } catch (HeadlessException e) {
            System.out.println(LOG+" "+e.getMessage());
            return false;
        }
    }
}
