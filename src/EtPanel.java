/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author madso
 */
public class EtPanel extends javax.swing.JPanel {
	Database dtb;
	
    /**
     * Creates new form EtPanel
     */
    public EtPanel() {
    	dtb = new Database();
        initComponents();
    }
    
    //Metode, der sætter max temp grænse
    public void setgMaxTemp(String temp){
        gMaxTemp.setText(""+temp);
        
    }
    
    //Metode, der sætter min temp grænse
    public void setgMinTemp(String temp){
        gMinTemp.setText(""+temp);
        
    }
    
    //Metode, der sætter max puls grænse
    public void setgMaxPuls(String puls){
        gMaxPuls.setText(""+puls);
        
    }
    
    //Metode, der sætter min puls grænse
    public void setgMinPuls(String puls){
        gMinPuls.setText(""+puls);
        
    }
    
    //Metode, der skal tjekke om den aktuelle temperatur er den højest eller laveste målt
    public void måltTemp(int temp){
        
       // if (temp < maxTemp)
    }
    
    //Metode, der skal tjekke om den aktuelle puls er den højest eller laveste målt
    public void måltPuls(int puls){
        
       // maxPuls = Integer.parseInt(maxPuls);
        
        
       /* if (puls > maxTemp){
                maxPuls = "" + puls
                maxPuls.setText();
          } else if(puls < minTemp){
                minPuls = 
        }
        
        */
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        stop = new javax.swing.JButton();
        startKnap = new javax.swing.JButton();
        grafOmraade = new Graf("puls",dtb);
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
        jPanel1 = new Graf("temp",dtb);
        minTemp = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        gMaxTemp = new javax.swing.JTextField();
        gMinTemp = new javax.swing.JTextField();

        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        setPreferredSize(new java.awt.Dimension(1200, 700));

        stop.setText("Stop");
        stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopActionPerformed(evt);
            }
        });

        startKnap.setText("Start");

        javax.swing.GroupLayout grafOmraadeLayout = new javax.swing.GroupLayout(grafOmraade);
        grafOmraade.setLayout(grafOmraadeLayout);
        grafOmraadeLayout.setHorizontalGroup(
            grafOmraadeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        grafOmraadeLayout.setVerticalGroup(
            grafOmraadeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 268, Short.MAX_VALUE)
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel3.setText("Temp");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        aktuelPuls.setEditable(false);
        aktuelPuls.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        aktuelPuls.setForeground(new java.awt.Color(0, 204, 0));
        aktuelPuls.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        aktuelPuls.setText("183");
        aktuelPuls.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel1.setText("Puls");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        maxPuls.setBackground(new java.awt.Color(240, 240, 240));
        maxPuls.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        maxPuls.setText("200");
        maxPuls.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel2.setText("Max målt");

        jLabel4.setText("Min målt");

        jLabel5.setText("Max grænse");

        jLabel6.setText("Min grænse");

        jTextField12.setEditable(false);
        jTextField12.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTextField12.setForeground(new java.awt.Color(0, 204, 0));
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
        gMinPuls.setText("180");
        gMinPuls.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        minPuls.setBackground(new java.awt.Color(240, 240, 240));
        minPuls.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        minPuls.setText("10");
        minPuls.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        gMaxPuls.setBackground(new java.awt.Color(240, 240, 240));
        gMaxPuls.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        gMaxPuls.setText("20");
        gMaxPuls.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        gMaxPuls.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gMaxPulsActionPerformed(evt);
            }
        });

        maxTemp.setBackground(new java.awt.Color(240, 240, 240));
        maxTemp.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        maxTemp.setText("36.7");
        maxTemp.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        aktuelTemp.setEditable(false);
        aktuelTemp.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        aktuelTemp.setForeground(new java.awt.Color(51, 51, 255));
        aktuelTemp.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        aktuelTemp.setText("37.1");
        aktuelTemp.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        aktuelTemp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aktuelTempActionPerformed(evt);
            }
        });

        jTextField16.setEditable(false);
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

        //jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 105));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 818, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 222, Short.MAX_VALUE)
        );

        minTemp.setBackground(new java.awt.Color(240, 240, 240));
        minTemp.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        minTemp.setText("35.1");
        minTemp.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        minTemp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minTempActionPerformed(evt);
            }
        });

        jLabel7.setText("Max målt");

        jLabel8.setText("Min målt");

        jLabel9.setText("Max grænse");

        jLabel10.setText("Min grænse");

        gMaxTemp.setBackground(new java.awt.Color(240, 240, 240));
        gMaxTemp.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        gMaxTemp.setText("35.1");
        gMaxTemp.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        gMaxTemp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gMaxTempActionPerformed(evt);
            }
        });

        gMinTemp.setBackground(new java.awt.Color(240, 240, 240));
        gMinTemp.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        gMinTemp.setText("35.1");
        gMinTemp.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(261, 261, 261)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(48, 48, 48))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(22, 22, 22))))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(maxTemp, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(aktuelTemp, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel10))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(aktuelPuls, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(maxPuls, javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(minPuls, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addComponent(gMaxPuls, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(gMinPuls, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(gMaxTemp, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(gMinTemp, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(minTemp, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(82, 82, 82)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 822, Short.MAX_VALUE)
                            .addComponent(grafOmraade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(startKnap, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(stop)))
                .addGap(40, 40, 40))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(aktuelTemp, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(maxTemp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(minTemp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(gMaxTemp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(gMinTemp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(grafOmraade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(stop)
                            .addComponent(startKnap))
                        .addGap(41, 41, 41))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(aktuelPuls, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(10, 10, 10)
                                .addComponent(jLabel4))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(maxPuls, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(minPuls, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(gMaxPuls, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(gMinPuls, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents

    private void stopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopActionPerformed
        /* Afslut måling */ 
    }//GEN-LAST:event_stopActionPerformed

    private void jTextField12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField12ActionPerformed

    private void jTextField16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField16ActionPerformed

    private void minTempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minTempActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_minTempActionPerformed

    private void gMaxTempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gMaxTempActionPerformed
        
    }//GEN-LAST:event_gMaxTempActionPerformed

    private void gMaxPulsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gMaxPulsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_gMaxPulsActionPerformed

    private void aktuelTempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aktuelTempActionPerformed
        
    }//GEN-LAST:event_aktuelTempActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField aktuelPuls;
    private javax.swing.JTextField aktuelTemp;
    private javax.swing.JTextField gMaxPuls;
    private javax.swing.JTextField gMaxTemp;
    private javax.swing.JTextField gMinPuls;
    private javax.swing.JTextField gMinTemp;
    private javax.swing.JPanel grafOmraade;
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
    private javax.swing.JPanel jPanel1;
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

