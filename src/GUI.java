
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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
public class GUI extends javax.swing.JPanel {

	Database dtb;
	private double latestTemp = 0;
	private double minTemperatur = 2000;
	private double maxTemperatur = 0;
	private double minPulse = 2000;
	private double maxPulse = 0;
	private double latestPuls = 0;
	private double oevreTemp = 0;
	private double nedreTemp = 0;
	private double oevrePuls = 0;
	private double nedrePuls = 0;
	private boolean isRunning = false;
	private Timer timer = new Timer();
        private ImageIcon icon = new ImageIcon(getClass().getResource("exclamation-mark.png"));

	/**
	 * Creates new form EtPanel
	 */
	public GUI(Database dtb) {
		this.dtb = dtb;
		initComponents();
                isRunning = true;
                dangerLabelPuls.setIcon(icon);
                dangerLabelPuls.setVisible(false);
                dangerLabelTemp.setIcon(icon);
                dangerLabelTemp.setVisible(false);
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
		// JMenuItem menu22 = new JMenuItem("Puls");
		menu1.add(menu11);
		menu1.add(menu12);
		menu1.add(menu13);
		menu2.add(menu21);
		// menu2.add(menu22);
		menu.add(menu1);
		menu.add(menu2);
		menu.add(menu3);
		hej.setJMenuBar(menu);
		hej.setVisible(true);

		menu13.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				 * Der skal nok også ske noget mere her..
				 */
				System.exit(0);
			}
		});
		// Indtast tempgrænser

		menu21.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setGraense();
			}
		});

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
		// gMaxTemp.setText("" + temp);

		JTextField oevreTField = new JTextField(5);
		JTextField nedreTField = new JTextField(5);
		JTextField oevrePField = new JTextField(5);
		JTextField nedrePField = new JTextField(5);

		JPanel gTempBoks = new JPanel();
		gTempBoks.setPreferredSize(new Dimension(200, 300));
		gTempBoks.add(new JLabel("Temperatur: "));
		gTempBoks.add(Box.createVerticalStrut(15)); // Burde lave verticalt
													// space
		gTempBoks.add(new JLabel("Indtast øvre grænseværdi: "));
		gTempBoks.add(oevreTField);
		gTempBoks.add(Box.createHorizontalStrut(15)); // Laver mellemrum
		gTempBoks.add(new JLabel("Indtast nedre grænseværdi:"));
		gTempBoks.add(nedreTField);

		gTempBoks.add(new JLabel("Puls: "));
		gTempBoks.add(Box.createVerticalStrut(15)); // Burde lave verticalt
													// space
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
			 */
			String nedreT = nedreTField.getText();
			String oevreT = oevreTField.getText();
			String nedreP = nedrePField.getText();
			String oevreP = oevrePField.getText();
			if (!nedreT.isEmpty() && !nedreT.equals("")) {
				double buff = Double.parseDouble(nedreT);
				// Må ikke være negativ og ikke være 0.
				if (buff < 40 && buff > 0)
					setgMinTemp(buff);
			}

			if (!oevreT.isEmpty() && !oevreT.equals("")) {
				double buff = Double.parseDouble(oevreT);
				if (buff > 30 && buff > 0)
					setgMaxTemp(buff);
			}
			if (!oevreP.isEmpty() && !oevreP.equals("")) {
				double buff = Double.parseDouble(oevreP);
				if (buff < 200 && buff > 0)
					setgMaxPuls(buff);
			}

			if (!nedreP.isEmpty() && !nedreP.equals("")) {
				double buff = Double.parseDouble(nedreP);
				if (buff < 60 && buff > 0)
					setgMinPuls(buff);
			}
		}
	}

	// Metode, der sætter max temp grænse
	public void setgMaxTemp(double temp) {
		gMaxTemp.setText("" + temp);
		oevreTemp = temp;
	}

	// Metode, der sætter min temp grænse
	public void setgMinTemp(double temp) {
		gMinTemp.setText("" + temp);
		nedreTemp = temp;
	}

	// Metode, der sætter max puls grænse
	public void setgMaxPuls(double puls) {
		gMaxPuls.setText("" + puls);
		oevrePuls = puls;
	}

	// Metode, der sætter min puls grænse
	public void setgMinPuls(double puls) {
		gMinPuls.setText("" + puls);
		nedrePuls = puls;
	}

	public void setAktuelTemp(double aktTemp) {
		latestTemp = aktTemp;
		DecimalFormat df = new DecimalFormat("#.0");
		aktuelTemp.setText(df.format(latestTemp));
	}

	public void setMinMaxTemp() {
		if (latestTemp > 0) {
			minTemperatur = (latestTemp < minTemperatur) ? latestTemp : minTemperatur;
			maxTemperatur = (latestTemp > maxTemperatur) ? latestTemp : maxTemperatur;
			DecimalFormat df = new DecimalFormat("#.00");
			minTemp.setText(df.format(minTemperatur));
			maxTemp.setText(df.format(maxTemperatur));
		}
	}

	public void setAktuelPuls(double aktPuls) {
		latestPuls = aktPuls;
		aktuelPuls.setText("" + aktPuls);
	}

	public void setMinMaxPuls() {
		if (latestPuls > 0) {
			minPulse = (latestPuls < minPulse) ? latestPuls : minPulse;
			maxPulse = (latestPuls > maxPulse) ? latestPuls : maxPulse;
			maxPuls.setText("" + maxPulse);
			minPuls.setText("" + minPulse);
		}
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

	public void start() {
		this.isRunning = true;
		graf_PulseObj.begin();
		graf_TempObj.begin();
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				ArrayList<Double> buffer = dtb.getValueSet("Temperatur");
				if (buffer.size() > 0)
					setAktuelTemp(buffer.get(0));
				setMinMaxTemp();
				buffer = dtb.getValueSet("Pulsslag");
				if (buffer.size() > 0)
					setAktuelPuls(buffer.get(0));
				setMinMaxPuls();
			}

		}, 0, 1000);

	}

	public void stop() {
		this.isRunning = false;
		timer.cancel();
		graf_PulseObj.stop();
		graf_TempObj.stop();
	}

	/*
	 * FORM: t: temp u: upper, l: lover p: pulse [tl tu pl pu]
	 */
	public double[] getBound() {
		double[] bounds = new double[4];
		bounds[0] = nedreTemp;
		bounds[1] = oevreTemp;
		bounds[2] = nedrePuls;
		bounds[3] = oevrePuls;
		return bounds;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// <editor-fold defaultstate="collapsed" desc="Generated
	// <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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

        jLabel2.setText("Max målt");

        jLabel4.setText("Min målt");

        jLabel5.setText("Maxgrænseværdi");

        jLabel6.setText("Mingrænseværdi");

        jTextField12.setBackground(new java.awt.Color(240, 240, 240));
        jTextField12.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTextField12.setForeground(new java.awt.Color(255, 0, 51));
        jTextField12.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField12.setText("bpm");
        jTextField12.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

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

        minTemp.setBackground(new java.awt.Color(240, 240, 240));
        minTemp.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        minTemp.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel7.setText("Max målt");

        jLabel8.setText("Min målt");

        jLabel9.setText("Maxgrænseværdi");

        jLabel10.setText("Mingrænseværdi");

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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(startKnap, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(graenseKnap, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(dangerLabelPuls, javax.swing.GroupLayout.DEFAULT_SIZE, 876, Short.MAX_VALUE))
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
                            .addComponent(dangerLabelTemp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(graf_PulseObj, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(graf_TempObj, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                            .addComponent(gMinTemp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(graf_TempObj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(dangerLabelTemp, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
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
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dangerLabelPuls, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(startKnap, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(graenseKnap)
                .addGap(70, 70, 70))
        );

        getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents

	private void startKnapActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_startKnapActionPerformed
                if(isRunning){
                    startKnap.setText("Afbryd måling");
                    start();
                }
                else{
                    stop(); startKnap.setText("Start måling");
                }
                
	}// GEN-LAST:event_startKnapActionPerformed

	private void graenseKnapActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_graenseKnapActionPerformed
            setGraense();
	}// GEN-LAST:event_graenseKnapActionPerformed


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
    // End of variables declaration//GEN-END:variables
}
