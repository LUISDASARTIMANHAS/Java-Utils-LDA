/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing;

import java.awt.Component;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author LUIS DAS ARTIMANHAS
 */
public class LDASwingUtils {

    public static void toggleEditable(JTextField txt) {
        if (txt.isEditable()) {
            txt.setEditable(false);
        } else {
            txt.setEditable(true);
        }

    }

    public static void toggleEnabled(JTextField txt) {
        if (txt.isEnabled()) {
            txt.setEnabled(false);
        } else {
            txt.setEnabled(true);
        }

    }

    public static void toggleEnabledAndEditable(JTextField txt) {
        toggleEnabled(txt);
        toggleEditable(txt);

    }

    public static void ChangeLookAndFeel(String NameClass, String type, Component comp) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if (type.equals(info.getName())) {
                System.out.println("Changed Look And Feel to: " + info.getName());
                UpdateLookAndFeel(info.getClassName(), comp);
                break;
            }
        } //</editor-fold>
        if (type.equals("com.formdev.flatlaf.FlatDarkLaf") || type.equals("com.formdev.flatlaf.FlatLightLaf") ) {
            System.out.println("Changed Personal Look And Feel to: " + type);
            UpdateLookAndFeel(type, comp);
        }

    }

    public static void UpdateLookAndFeel(String className, Component comp) {
        try {
            javax.swing.UIManager.setLookAndFeel(className);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LDASwingUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(LDASwingUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(LDASwingUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(LDASwingUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        SwingUtilities.updateComponentTreeUI(comp);
    }

}
