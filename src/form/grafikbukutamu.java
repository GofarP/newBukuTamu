/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import koneksi.koneksi;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
  


/**
 *
 * @author LENOVO
 */
public class grafikbukutamu extends javax.swing.JFrame {

    /**
     * Creates new form adminbukutamu
     */
    
    int tahun = Calendar.getInstance().get(Calendar.YEAR);
    
    public grafikbukutamu() {
        initComponents();
        this.setLocationRelativeTo(null);
        pnChart.setLayout(new java.awt.BorderLayout());
        pie();
        pnBarChart.setLayout(new java.awt.BorderLayout());
        bar();
       
    }
    
    

    
    public void bar() 
    {
        
        try 
        {
            
            Date date = new Date();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int bulan = localDate.getMonthValue();
            int tahun=localDate.getYear();
            
            ArrayList<String> arrbulan=new ArrayList<>();
            
            switch(bulan)
            {
                case 1:
                case 2:
                case 3:
                case 4:
                    arrbulan.add("Januari");
                    arrbulan.add("Februari");
                    arrbulan.add("Maret");
                    arrbulan.add("April");
                    
                    break;
                
                case 5:
                case 6:
                case 7:
                case 8:
                    arrbulan.add("May");
                    arrbulan.add("Juni");
                    arrbulan.add("Juli");
                    arrbulan.add("August");
                    break;
                    
                case 9:
                case 10:
                case 11:
                case 12:
                    arrbulan.add("September");
                    arrbulan.add("October");
                    arrbulan.add("November");
                    arrbulan.add("December");
                break;
                   
            }
            
            
            ArrayList<String>blnangka=new ArrayList<>();
            
            for(int i=0;i<arrbulan.size();i++)
            {
                try 
                {
                    String monthName = arrbulan.get(i); // German for march
                    date = new SimpleDateFormat("MMMM", getLocale()).parse(monthName);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    blnangka.add(String.valueOf(cal.get(Calendar.MONTH)+1));
                    
                } 
                
                catch (Exception e) 
                {
                    System.out.println(e.getMessage());
                }
            }
            
            java.sql.Connection conn=(Connection)koneksi.configDB();
            String sql="SELECT COUNT(mahasiswa.nim),mahasiswa.`jurusan`,MONTH(bukutamu.`tanggal`) AS bulan FROM bukutamu \n" +
            "INNER JOIN  mahasiswa ON bukutamu.`nim`=mahasiswa.`nim` \n" +
            "WHERE MONTH(bukutamu.`tanggal`)  BETWEEN ? AND ? \n" +
            "AND YEAR(bukutamu.`tanggal`) = ? \n" +
            "GROUP BY mahasiswa.`jurusan`,MONTH(bukutamu.`tanggal`) \n" +
            "ORDER BY MONTH(tanggal),mahasiswa.`jurusan`";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,blnangka.get(0));
            pst.setString(2, blnangka.get(3));
            pst.setInt(3, tahun);
            ResultSet rs=pst.executeQuery();
            
            
            

            ArrayList<String>periode1=new ArrayList<>();
            ArrayList<String>periode2=new ArrayList<>();
            ArrayList<String>periode3=new ArrayList<>();
            ArrayList<String>periode4=new ArrayList<>();
            List<String> waktu = Arrays.asList( "0", "0", "0","0");

            
            
            while (rs.next()) 
            {
                
                if(rs.getString(3).equals(blnangka.get(0)))
                {
                   
                    periode1.add(rs.getString(1));
                }
                
                
                else if(rs.getString(3).equals(blnangka.get(1)))
                {
                    periode2.add(rs.getString(1));
                }
                
                else if(rs.getString(3).equals(blnangka.get(2)))
                {
                    periode3.add(rs.getString(1));
                    
                }
                
                else if(rs.getString(3).equals(blnangka.get(3)))
                {
                    periode4.add(rs.getString(1));
                } 
                
                
            }
            
            
            
            
            if(periode1.size()<1)
            {
                periode1.addAll(waktu);
            }
            
            if(periode2.size()<1)
            {
                periode2.addAll(waktu);
            }
            
            if(periode3.size()<1)
            {
                periode3.addAll(waktu);
            }
            
            if(periode4.size()<1)
            {
                periode4.addAll(waktu);
            }
            
            
            
            
            
            for (int i=0;i<periode1.size();i++)
            {
                  
                if(periode1.size()<3||periode1.isEmpty())
                {
                   periode1.add("0");
                }
                
                
            }

            
            for (int i=0;i<periode2.size();i++)
            {
                 
                if(periode2.size()<3||periode2.isEmpty())
                {
                      periode2.add("0");
                }
                
                 
            }

            
             for (int i=0;i<periode3.size();i++)
              {
                     
                  if(periode3.size()<3||periode3.isEmpty())
                  {
                      periode3.add("0");
                  }
              }
             
             
              
              for (int i=0;i<periode4.size();i++)
              {
              
                 if(periode4.size()<3||periode4.isEmpty())
                 {
                      periode4.add("0");
                 }
              }
                  
//                  
//                   
//              }
              
            
            DefaultCategoryDataset dataset=new DefaultCategoryDataset();
            
            dataset.addValue(Integer.valueOf(periode1.get(0)) , "Komputer Akuntansi" , arrbulan.get(0));
            dataset.addValue(Integer.valueOf(periode1.get(1)) , "Sistem Informasi" ,  arrbulan.get(0));
            dataset.addValue(Integer.valueOf(periode1.get(2)), "Teknik Informatika" , arrbulan.get(0) );
            
            dataset.addValue(Integer.valueOf(periode2.get(0)) , "Komputer Akuntansi" , arrbulan.get(1));
            dataset.addValue(Integer.valueOf(periode2.get(1)) , "Sistem Informasi" , arrbulan.get(1));
            dataset.addValue(Integer.valueOf(periode2.get(2)), "Teknik Informatika" , arrbulan.get(1));
           
            dataset.addValue(Integer.valueOf(periode3.get(0)) , "Komputer Akuntansi" , arrbulan.get(2) );
            dataset.addValue(Integer.valueOf(periode3.get(1)), "Sistem Informasi" , arrbulan.get(2) );
            dataset.addValue(Integer.valueOf(periode3.get(2)), "Teknik Informatika" , arrbulan.get(2));
            
            dataset.addValue(Integer.valueOf(periode4.get(0)) , "Komputer Akuntansi" , arrbulan.get(3) );
            dataset.addValue(Integer.valueOf(periode4.get(1)), "Sistem Informasi" , arrbulan.get(3) );
            dataset.addValue(Integer.valueOf(periode4.get(2)) , "Teknik Informatika" , arrbulan.get(3) );

            JFreeChart barChart = ChartFactory.createBarChart(
             "Pengunjung bulan "+ arrbulan.get(0)+" - "+ arrbulan.get(3)+" "+tahun, 
             "Bulan", "Pengunjung", 
             dataset,PlotOrientation.VERTICAL, 
             true, true, false);

            int width = 680;    /* Width of the image */
            int height = 480;   /* Height of the image */ 
            File BarChart = new File( "BarChart.jpeg" ); 
            ChartUtilities.saveChartAsJPEG( BarChart , barChart , width , height );
            ChartPanel chartPanel = new ChartPanel(barChart);
            CategoryPlot plot=(CategoryPlot)barChart.getPlot();
            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            renderer.setSeriesPaint(0, Color.yellow);
            renderer.setSeriesPaint(1, Color.green);
            renderer.setSeriesPaint(2, Color.blue);
           
            pnBarChart.removeAll();        // clear panel before add new chart
            pnBarChart.add(chartPanel, BorderLayout.CENTER);
            pnBarChart.validate();         // refresh panel to display new chart
            
        } 
        
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
        }
        
    }
    
    public void pie()
    {   
        int tif=0;
        int si=0;
        int ka=0;
        String jurusan="";
        
        try 
        {
             java.sql.Connection conn=(Connection)koneksi.configDB();
             String sql="select jurusan from mahasiswa";
             PreparedStatement pst=conn.prepareStatement(sql);
             ResultSet rs=pst.executeQuery();
             
             
             while(rs.next())
             {
                
                switch(rs.getString("jurusan")) 
                {
                    case "Teknik Informatika":
                       tif+=1;
                      break;
                    
                    case "Sistem Informasi":
                       si+=1;
                      break;
                    
                    case "Komputer Akuntansi":
                        ka+=1;
                     break;
                  }

             }
             
        } 
        
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
        }
       
        
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Teknik Informatika", new Double(tif));
        dataset.setValue("Sistem Informasi", new Double(si));
        dataset.setValue("Komputer Akuntansi", new Double(ka));
        // create pir chart
        JFreeChart chart = ChartFactory.createPieChart3D(
                null, // chart title                   
                dataset, // data 
                true, // include legend                   
                true,
                false);
        // set chart properties
        final PiePlot plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(270);
        plot.setForegroundAlpha(0.60f);
        plot.setInteriorGap(0.02);
        plot.setSectionPaint(0,Color.blue);
        plot.setSectionPaint(1,Color.green);
        plot.setSectionPaint(2,Color.yellow);
        plot.setSimpleLabels(true);
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} {2}"));
        // create chart panel the add it to swing panel in  jframe
        ChartPanel chartPanel = new ChartPanel(chart);
        pnChart.removeAll();        // clear panel before add new chart
        pnChart.add(chartPanel, BorderLayout.CENTER);
        pnChart.validate();         // refresh panel to display new chart
    }
    
   
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        rSPanelImage1 = new rojerusan.RSPanelImage();
        jLabel2 = new javax.swing.JLabel();
        rSPanelImage2 = new rojerusan.RSPanelImage();
        jLabel1 = new javax.swing.JLabel();
        pnChart = new javax.swing.JPanel();
        pnBarChart = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(33, 152, 207));

        jPanel3.setBackground(new java.awt.Color(29, 83, 109));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel3MouseExited(evt);
            }
        });
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rSPanelImage1.setImagen(new javax.swing.ImageIcon(getClass().getResource("/icon/book.png"))); // NOI18N
        rSPanelImage1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSPanelImage1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout rSPanelImage1Layout = new javax.swing.GroupLayout(rSPanelImage1);
        rSPanelImage1.setLayout(rSPanelImage1Layout);
        rSPanelImage1Layout.setHorizontalGroup(
            rSPanelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );
        rSPanelImage1Layout.setVerticalGroup(
            rSPanelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel3.add(rSPanelImage1, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 9, -1, 30));

        jLabel2.setFont(new java.awt.Font("Baskerville Old Face", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Data Buku Tamu");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 17, 100, -1));

        rSPanelImage2.setImagen(new javax.swing.ImageIcon(getClass().getResource("/icon/back.png"))); // NOI18N
        rSPanelImage2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSPanelImage2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout rSPanelImage2Layout = new javax.swing.GroupLayout(rSPanelImage2);
        rSPanelImage2.setLayout(rSPanelImage2Layout);
        rSPanelImage2Layout.setHorizontalGroup(
            rSPanelImage2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );
        rSPanelImage2Layout.setVerticalGroup(
            rSPanelImage2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 33, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rSPanelImage2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(rSPanelImage2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(186, 186, 186)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Baskerville Old Face", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(33, 152, 207));
        jLabel1.setText("Grafik Buku Tamu & Mahasiswa");

        pnChart.setBackground(new java.awt.Color(255, 255, 255));
        pnChart.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mahasiswa Terdaftar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(33, 152, 207))); // NOI18N
        pnChart.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnBarChart.setBackground(new java.awt.Color(255, 255, 255));
        pnBarChart.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pengunjung perpustakaan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(33, 152, 207))); // NOI18N
        pnBarChart.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setBackground(new java.awt.Color(33, 152, 207));
        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(33, 152, 207));
        jLabel6.setText("x");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        jLabel7.setBackground(new java.awt.Color(33, 152, 207));
        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(33, 152, 207));
        jLabel7.setText("-");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(pnChart, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(51, 51, 51)
                                .addComponent(pnBarChart, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)))
                        .addGap(0, 0, 0)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnChart, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnBarChart, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(189, Short.MAX_VALUE))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void rSPanelImage1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSPanelImage1MouseClicked
        // TODO add your handling code here:
        dispose();
        new admin().setVisible(true);
    }//GEN-LAST:event_rSPanelImage1MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        // TODO add your handling code here:
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jPanel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseEntered
        // TODO add your handling code here:
        jPanel3.setBackground(new Color(36, 116, 169));
    }//GEN-LAST:event_jPanel3MouseEntered

    private void jPanel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseExited
        // TODO add your handling code here:
        jPanel3.setBackground(new Color(29, 83, 109));
    }//GEN-LAST:event_jPanel3MouseExited

    private void rSPanelImage2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSPanelImage2MouseClicked
        // TODO add your handling code here:
        new admin().setVisible(true);
        dispose();
    }//GEN-LAST:event_rSPanelImage2MouseClicked

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        // TODO add your handling code here:
       
        new databukutamu().setVisible(true);
         dispose();
                
    }//GEN-LAST:event_jPanel3MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(grafikbukutamu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(grafikbukutamu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(grafikbukutamu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(grafikbukutamu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new grafikbukutamu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel pnBarChart;
    private javax.swing.JPanel pnChart;
    private rojerusan.RSPanelImage rSPanelImage1;
    private rojerusan.RSPanelImage rSPanelImage2;
    // End of variables declaration//GEN-END:variables
}
