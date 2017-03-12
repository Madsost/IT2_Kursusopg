
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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
        vindue.add(new EtPanel());
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
    }

}
