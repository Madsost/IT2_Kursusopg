
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author madso
 */
public class GUI extends javax.swing.JPanel {

    Database dtb;
    private double latestTemp = 0;
    private double minTemperatur = 2000;
    private double maxTemperatur = 0;
    private int minPulse = 2000;
    private int maxPulse = 0;
    private int latestPuls = 0;
    static boolean isActive = false;
    private Timer timer = new Timer();

    /**
     * Creates new form EtPanel
     */
    public GUI(Database dtb) {
        this.dtb = dtb;
        initComponents();
        JFrame vindue = new JFrame();
        vindue.add(this);
        vindue.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vindue.pack();
        setMenu(vindue);
    }

    public void setMenu(JFrame hej) {
        JMenuBar menu = new JMenuBar();
        JMenu menu1 = new JMenu("Fil");
        JMenu menu2 = new JMenu("Grænseværdier");
        JMenu menu3 = new JMenu("Hjælp");
        menu1.setMnemonic(KeyEvent.VK_F);
        JMenuItem menu11 = new JMenuItem("Start måling");
        JMenuItem menu12 = new JMenuItem("Stop måling");
        JMenuItem menu13 = new JMenuItem("Afslut");
        JMenuItem menu21 = new JMenuItem("Indstil");
        //JMenuItem menu22 = new JMenuItem("Puls");
        menu1.add(menu11);
        menu1.add(menu12);
        menu1.add(menu13);
        menu2.add(menu21);
        //menu2.add(menu22);
        menu.add(menu1);
        menu.add(menu2);
        menu.add(menu3);
        hej.setJMenuBar(menu);
        hej.setVisible(true);

        menu13.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /*
                Der skal nok også ske noget mere her..
                */
                System.exit(0);
            }
        });
        // Indtast tempgrænser

        menu21.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setGraense();
                /*

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

                    setgMaxTemp(oevre);
                    setgMinTemp(nedre);
                } */
            }

        });

        /*
        // Indtast pulsgrænser
        menu22.addActionListener(new ActionListener() {
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
                    setgMaxPuls(oevre);
                    setgMinPuls(nedre);
                }
            }

        });
        */

        menu11.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                start();
            }

        });
        menu12.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                stop();
            }

        });
    }

    // Metode, der sætter grænseværdier
    public void setGraense() {
        //gMaxTemp.setText("" + temp);

        JTextField oevreTField = new JTextField(5);
        JTextField nedreTField = new JTextField(5);
        JTextField oevrePField = new JTextField(5);
        JTextField nedrePField = new JTextField(5);

        JPanel gTempBoks = new JPanel();
        gTempBoks.add(new JLabel("Temperatur: "));
        gTempBoks.add(Box.createVerticalStrut(15)); // Burde lave verticalt space
        gTempBoks.add(new JLabel("Indtast øvre grænseværdi: "));
        gTempBoks.add(oevreTField);
        gTempBoks.add(Box.createHorizontalStrut(15)); // Laver mellemrum
        gTempBoks.add(new JLabel("Indtast nedre grænseværdi:"));
        gTempBoks.add(nedreTField);
        
        gTempBoks.add(new JLabel("Puls: "));
        gTempBoks.add(Box.createVerticalStrut(15)); // Burde lave verticalt space
        gTempBoks.add(new JLabel("Indtast øvre grænseværdi: "));
        gTempBoks.add(oevrePField);
        gTempBoks.add(Box.createHorizontalStrut(15)); // Laver mellemrum
        gTempBoks.add(new JLabel("Indtast nedre grænseværdi:"));
        gTempBoks.add(nedrePField);

        int gTemp = JOptionPane.showConfirmDialog(null, gTempBoks, "Grænseværdier ", JOptionPane.OK_CANCEL_OPTION);
        if (gTemp == JOptionPane.OK_OPTION) {
            System.out.println("Indtast øvre grænseværdi: " + oevreTField.getText());
            System.out.println("Indtast nedre grænseværdi: " + nedreTField.getText());
            
            /*
             * Her skal der være noget validering af input.
             * */
            String nedreT = nedreTField.getText();
            String oevreT = oevreTField.getText();
            String nedreP = nedrePField.getText();
            String oevreP = oevrePField.getText();

            setgMaxTemp(oevreT);
            setgMinTemp(nedreT);
            setgMaxPuls(oevreP);
            setgMinPuls(nedreP);
        }

    }

    
    
    
    // Metode, der sætter max temp grænse
    public void setgMaxTemp(String temp) {
        gMaxTemp.setText("" + temp);
    }

    // Metode, der sætter min temp grænse
    public void setgMinTemp(String temp) {
        gMinTemp.setText("" + temp);
    }

    // Metode, der sætter max puls grænse
    public void setgMaxPuls(String puls) {
        gMaxPuls.setText("" + puls);
    }

    // Metode, der sætter min puls grænse
    public void setgMinPuls(String puls) {
        gMinPuls.setText("" + puls);
    }

    // Metode, der skal tjekke om den aktuelle temperatur er den højest eller
    // laveste målt
    public void måltTemp(int temp) {

        // if (temp < maxTemp)
    }

    // Metode, der skal tjekke om den aktuelle puls er den højest eller laveste
    // målt
    public void måltPuls(int puls) {

        // maxPuls = Integer.parseInt(maxPuls);

        /*
		 * if (puls > maxTemp){ maxPuls = "" + puls maxPuls.setText(); } else
		 * if(puls < minTemp){ minPuls = }
		 * 
         */
    }

    public static boolean isActive() {
        isActive = false;
        return isActive;
    }

    public void setAktuelTemp() {
        latestTemp = (Database.genererMaaling() * 4 / 50) + 24;
        DecimalFormat df = new DecimalFormat("#.0");
        aktuelTemp.setText(df.format(latestTemp));
    }

    public void setMinMaxTemp() {
        minTemperatur = (latestTemp < minTemperatur) ? latestTemp : minTemperatur;
        maxTemperatur = (latestTemp > maxTemperatur) ? latestTemp : maxTemperatur;
        DecimalFormat df = new DecimalFormat("#.00");
        minTemp.setText(df.format(minTemperatur));
        maxTemp.setText(df.format(maxTemperatur));
    }

    public void setAktuelPuls(int aktPuls) {
        latestPuls = aktPuls;
        aktuelPuls.setText("" + aktPuls);
    }

    public void setMinMaxPuls() {
        minPulse = (latestPuls < minPulse) ? latestPuls : minPulse;
        maxPulse = (latestPuls > maxPulse) ? latestPuls : maxPulse;
        maxPuls.setText("" + maxPulse);
        maxPuls.setText("" + minPulse);
    }

    public void advarsel(String parameter) {
        String tester = "Puls";
        dangerLabelTemp.setText("Temperatur: Målinger uden for grænseområdet");
        dangerLabelPuls.setText("Puls: Målinger uden for grænseområdet");
        if (tester.equals(parameter)) {
            dangerLabelPuls.setVisible(true);
        } else {
            dangerLabelTemp.setVisible(true);
        }
    }

    public void tjek() {
        dangerLabelTemp.setVisible(false);
        dangerLabelPuls.setVisible(false);
        if (latestPuls > (Integer.parseInt(gMaxPuls.getText()))
                || latestPuls < (Integer.parseInt(gMinPuls.getText()))) {
            advarsel("Puls");
        }
        if (latestTemp > (Double.parseDouble(gMaxTemp.getText()))
                || latestTemp < (Double.parseDouble(gMinTemp.getText()))) {
            advarsel("Temp");
        }
    }

    public void start() {
        isActive = true;
        //grafOmraade.setName("Hej");
        
        //System.out.println(grafOmraade.getName());
        graf_PulseObj.begin();
        graf_TempObj.begin();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                setAktuelTemp();
                setMinMaxTemp();
                setAktuelPuls(Main.calcPulse());
                setMinMaxPuls();
                //tjek(); - skal ske i Main...
            }

        }, 0, 1000);
        
    }

    public void stop() {
        isActive = false;
        timer.cancel();
        graf_PulseObj.stop();
        graf_TempObj.stop();
    }
    
    public void update(/* skal der stå noget her? */){
    	
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        stop = new javax.swing.JButton();
        startKnap = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        aktuelPuls = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        maxPuls = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        gMinPuls = new javax.swing.JTextField();
        minPuls = new javax.swing.JTextField();
        gMaxPuls = new javax.swing.JTextField();
        maxTemp = new javax.swing.JTextField();
        aktuelTemp = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        minTemp = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        gMaxTemp = new javax.swing.JTextField();
        gMinTemp = new javax.swing.JTextField();
        dangerLabelTemp = new javax.swing.JLabel();
        dangerLabelPuls = new javax.swing.JLabel();
        graenseKnap = new javax.swing.JButton();
        graf_TempObj = new Graf_Temp();
        graf_TempObj.setDTB(dtb);
        graf_PulseObj = new Graf_Pulse();
        graf_PulseObj.setDTB(dtb);

        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        setPreferredSize(new java.awt.Dimension(1200, 700));

        stop.setText("Stop måling");
        stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopActionPerformed(evt);
            }
        });

        startKnap.setForeground(new java.awt.Color(0, 153, 51));
        startKnap.setText("Start måling");
        startKnap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startKnapActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel3.setText("Temp");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        aktuelPuls.setBackground(new java.awt.Color(240, 240, 240));
        aktuelPuls.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        aktuelPuls.setForeground(new java.awt.Color(255, 0, 51));
        aktuelPuls.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        aktuelPuls.setText("--");
        aktuelPuls.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel1.setText("Puls");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        maxPuls.setBackground(new java.awt.Color(240, 240, 240));
        maxPuls.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        maxPuls.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel2.setText("Max");

        jLabel4.setText("Min");

        jLabel5.setText("Maxgrænse");

        jLabel6.setText("Mingrænse");

        jTextField12.setBackground(new java.awt.Color(240, 240, 240));
        jTextField12.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTextField12.setForeground(new java.awt.Color(255, 0, 51));
        jTextField12.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField12.setText("bpm");
        jTextField12.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTextField12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField12ActionPerformed(evt);
            }
        });

        gMinPuls.setBackground(new java.awt.Color(240, 240, 240));
        gMinPuls.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        gMinPuls.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        minPuls.setBackground(new java.awt.Color(240, 240, 240));
        minPuls.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        minPuls.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        gMaxPuls.setBackground(new java.awt.Color(240, 240, 240));
        gMaxPuls.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        gMaxPuls.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        maxTemp.setBackground(new java.awt.Color(240, 240, 240));
        maxTemp.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        maxTemp.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        maxTemp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maxTempActionPerformed(evt);
            }
        });

        aktuelTemp.setBackground(new java.awt.Color(240, 240, 240));
        aktuelTemp.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        aktuelTemp.setForeground(new java.awt.Color(51, 51, 255));
        aktuelTemp.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        aktuelTemp.setText("--.-");
        aktuelTemp.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jTextField16.setBackground(new java.awt.Color(240, 240, 240));
        jTextField16.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTextField16.setForeground(new java.awt.Color(51, 51, 255));
        jTextField16.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField16.setText("C");
        jTextField16.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTextField16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField16ActionPerformed(evt);
            }
        });

        minTemp.setBackground(new java.awt.Color(240, 240, 240));
        minTemp.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        minTemp.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel7.setText("Max");

        jLabel8.setText("Min");

        jLabel9.setText("Maxgrænse");

        jLabel10.setText("Mingrænse");

        gMaxTemp.setBackground(new java.awt.Color(240, 240, 240));
        gMaxTemp.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        gMaxTemp.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        gMinTemp.setBackground(new java.awt.Color(240, 240, 240));
        gMinTemp.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        gMinTemp.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        dangerLabelTemp.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        dangerLabelTemp.setForeground(new java.awt.Color(255, 0, 0));
        dangerLabelTemp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dangerLabelTemp.setToolTipText("");
        dangerLabelTemp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        dangerLabelPuls.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        dangerLabelPuls.setForeground(new java.awt.Color(255, 0, 0));
        dangerLabelPuls.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dangerLabelPuls.setToolTipText("");
        dangerLabelPuls.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        graenseKnap.setText("Indtast grænseværdier");
        graenseKnap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                graenseKnapActionPerformed(evt);
            }
        });

        graf_TempObj.setBackground(new java.awt.Color(224, 221, 221));

        javax.swing.GroupLayout graf_TempObjLayout = new javax.swing.GroupLayout(graf_TempObj);
        graf_TempObj.setLayout(graf_TempObjLayout);
        graf_TempObjLayout.setHorizontalGroup(
            graf_TempObjLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        graf_TempObjLayout.setVerticalGroup(
            graf_TempObjLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 182, Short.MAX_VALUE)
        );

        graf_PulseObj.setBackground(new java.awt.Color(224, 221, 221));

        javax.swing.GroupLayout graf_PulseObjLayout = new javax.swing.GroupLayout(graf_PulseObj);
        graf_PulseObj.setLayout(graf_PulseObjLayout);
        graf_PulseObjLayout.setHorizontalGroup(
            graf_PulseObjLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        graf_PulseObjLayout.setVerticalGroup(
            graf_PulseObjLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 156, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(aktuelPuls, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(maxPuls, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(minPuls, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(gMaxPuls, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(gMinPuls, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(maxTemp, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(minTemp, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel10))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(gMaxTemp, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(gMinTemp, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(aktuelTemp, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(graf_PulseObj, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(graf_TempObj, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(stop, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(startKnap, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(graenseKnap, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dangerLabelPuls, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dangerLabelTemp, javax.swing.GroupLayout.DEFAULT_SIZE, 876, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(aktuelTemp, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(maxTemp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(minTemp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(gMaxTemp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(gMinTemp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(graf_TempObj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(aktuelPuls, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(29, 29, 29)
                                        .addComponent(jLabel5)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(maxPuls, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(minPuls, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(gMaxPuls, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(gMinPuls, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(graf_PulseObj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(startKnap, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dangerLabelPuls, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(graenseKnap, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dangerLabelTemp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(stop)
                .addGap(51, 51, 51))
        );

        getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents

    private void startKnapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startKnapActionPerformed
        start();
    }//GEN-LAST:event_startKnapActionPerformed

    private void maxTempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maxTempActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maxTempActionPerformed

    private void graenseKnapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_graenseKnapActionPerformed
        // TODO add your handling code here:
        setGraense();
    }//GEN-LAST:event_graenseKnapActionPerformed

    private void stopActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_stopActionPerformed
        stop();
    }// GEN-LAST:event_stopActionPerformed

    private void jTextField12ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField12ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jTextField12ActionPerformed

    private void jTextField16ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField16ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jTextField16ActionPerformed

    private void minTempActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_minTempActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_minTempActionPerformed

    private void gMaxTempActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_gMaxTempActionPerformed

    }// GEN-LAST:event_gMaxTempActionPerformed

    private void gMaxPulsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_gMaxPulsActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_gMaxPulsActionPerformed

    private void aktuelTempActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_aktuelTempActionPerformed

    }// GEN-LAST:event_aktuelTempActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField aktuelPuls;
    private javax.swing.JTextField aktuelTemp;
    private javax.swing.JLabel dangerLabelPuls;
    private javax.swing.JLabel dangerLabelTemp;
    private javax.swing.JTextField gMaxPuls;
    private javax.swing.JTextField gMaxTemp;
    private javax.swing.JTextField gMinPuls;
    private javax.swing.JTextField gMinTemp;
    private javax.swing.JButton graenseKnap;
    private Graf_Pulse graf_PulseObj;
    private Graf_Temp graf_TempObj;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField maxPuls;
    private javax.swing.JTextField maxTemp;
    private javax.swing.JTextField minPuls;
    private javax.swing.JTextField minTemp;
    private javax.swing.JButton startKnap;
    private javax.swing.JButton stop;
    // End of variables declaration//GEN-END:variables
}
