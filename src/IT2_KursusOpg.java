
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.*;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author madso
 */
public class IT2_KursusOpg {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame vindue = new JFrame();
        EtPanel gui = new EtPanel();
        vindue.add(gui);
        vindue.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vindue.pack();
        vindue.setVisible(true);
        JMenuBar menu = new JMenuBar();
        JMenu menu1 = new JMenu("Fil");
        JMenu menu2 = new JMenu("Temperaturmåler");
        JMenu menu3 = new JMenu("Pulsmåler");
        JMenu menu4 = new JMenu("Hjælp");
        menu1.setMnemonic(KeyEvent.VK_F);
        JMenuItem menu11 = new JMenuItem("Start måling");
        JMenuItem menu12 = new JMenuItem("Stop måling");
        JMenuItem menu13 = new JMenuItem("Afslut");
        JMenuItem menu21 = new JMenuItem("Indstil grænseværdier");
        JMenuItem menu31 = new JMenuItem("Indstil grænseværdier");
        menu1.add(menu11);
        menu1.add(menu12);
        menu1.add(menu13);
        menu2.add(menu21);
        menu3.add(menu31);
        menu.add(menu1);
        menu.add(menu2);
        menu.add(menu3);
        menu.add(menu4);
        vindue.setJMenuBar(menu);
        menu13.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }
        );
        vindue.setVisible(true);

        //Indtast tempgrænser
        menu21.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                JTextField xField = new JTextField(5);
                JTextField yField = new JTextField(5);

                JPanel gTempBoks = new JPanel();
                gTempBoks.add(new JLabel("Indtast øvre grænseværdi: "));
                gTempBoks.add(xField);
                gTempBoks.add(Box.createHorizontalStrut(15)); // a spacer
                gTempBoks.add(new JLabel("Indtast nedre grænseværdi:"));
                gTempBoks.add(yField);

                int gTemp = JOptionPane.showConfirmDialog(null, gTempBoks, "Temperatur ", JOptionPane.OK_CANCEL_OPTION);
                if (gTemp == JOptionPane.OK_OPTION) {
                    System.out.println("Indtast øvre grænseværdi: " + xField.getText());
                    System.out.println("Indtast nedre grænseværdi: " + yField.getText());
                    String nedre = yField.getText();
                    String oevre = xField.getText();
                    
                    gui.setgMaxTemp(oevre);
                    gui.setgMinTemp(nedre);
                }
            }

        }
        );
        
         //Indtast pulsgrænser
        menu31.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTextField xField = new JTextField(5);
                JTextField yField = new JTextField(5);

                JPanel gPulsBoks = new JPanel();
                gPulsBoks.add(new JLabel("Indtast øvre grænseværdi: "));
                gPulsBoks.add(xField);
                gPulsBoks.add(Box.createHorizontalStrut(15)); // a spacer
                gPulsBoks.add(new JLabel("Indtast nedre grænseværdi:"));
                gPulsBoks.add(yField);

                int gTemp = JOptionPane.showConfirmDialog(null, gPulsBoks, "Puls: ", JOptionPane.OK_CANCEL_OPTION);
                if (gTemp == JOptionPane.OK_OPTION) {
                    System.out.println("Øvre grænseværdi: " + xField.getText());
                    System.out.println("Nedre grænseværdi: " + yField.getText());
                    
                    String nedre = yField.getText();
                    String oevre = xField.getText();
                    gui.setgMaxPuls(oevre);
                    gui.setgMinPuls(nedre);
                }
            }

        }
        );
    }
}
