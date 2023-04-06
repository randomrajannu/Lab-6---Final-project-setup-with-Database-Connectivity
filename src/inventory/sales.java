/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package inventory;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 
 */
public class sales extends javax.swing.JFrame {

    /**
     * Creates new form sales
     */
    public sales() {
        initComponents();
        Connect();
    }
    
    Connection con;
    PreparedStatement pst;
    PreparedStatement pst1;
    PreparedStatement pst2;
     
    DefaultTableModel df;
    ResultSet rs;
    
    public void Connect()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/inventorymanagement", "root","");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(vendor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(vendor.class.getName()).log(Level.SEVERE, null, ex);
        }
   
    }

    public void barcode()
    {
        try {
            String pcode = txtPCode.getText();

            pst = con.prepareStatement("select * from product where UPC = ?");
            pst.setString(1, pcode);
            rs = pst.executeQuery();
            
           if(rs.next() == false)
           {
               JOptionPane.showMessageDialog(this, "THis Barcode Is Not Found");
               txtPCode.setText("");

           }
           
           else
               
           {
               String pname = rs.getString("pname");
               String price = rs.getString("rprice");
               
                 txtPName.setText(pname.trim());
                 txtPrice.setText(price.trim());
                 txtQty.requestFocus();

           }

        } catch (SQLException ex) {
            Logger.getLogger(purchase.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
     public void Sales()
    {
        
        try {
            String pcode = txtPCode.getText();

            pst = con.prepareStatement("select * from product where UPC = ?");
            pst.setString(1, pcode);
            rs = pst.executeQuery();
            
            while(rs.next())
            {
               int currentqty;
               currentqty = rs.getInt("qty");
               int price = Integer.parseInt(txtPrice.getText());
               int qty = Integer.parseInt(txtQty.getText());
               int tot = price * qty;
            
            
            if(qty >= currentqty)
            {
                JOptionPane.showMessageDialog(this, "Please Enter Correct Quantity.");
            }
            
            else
            {
                 df = (DefaultTableModel)jTable1.getModel();
                 df.addRow(new Object[]
                    
            {
                txtPCode.getText(),
                txtPName.getText(),
                txtPrice.getText(),
                txtQty.getText(),
                tot
      
            });

            }
                
            }

            int sum = 0;
            
            for(int i = 0; i<jTable1.getRowCount(); i++)
            {
                
                sum = sum + Integer.parseInt(jTable1.getValueAt(i, 4).toString());

            }
            
            txtTCost.setText(String.valueOf(sum));
            
            txtPCode.setText("");
            txtPName.setText("");
            txtPrice.setText("");
            txtQty.setText("");
            
        } catch (SQLException ex) {
            Logger.getLogger(sales.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
     
      public void add()
    {
        try {
            DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDateTime now = LocalDateTime.now();
            String date = dt.format(now);
           
            String subtotal = txtTCost.getText();
            String pay = txtPayment.getText();
            String bal = txtBalance.getText();
            int lastid = 0;

            String query1 = "insert into sales(date,subtotal,pay,balance)values(?,?,?,?)" ;
            pst = con.prepareStatement(query1,Statement.RETURN_GENERATED_KEYS);
            
            pst.setString(1, date);
            pst.setString(2, subtotal);
            pst.setString(3, pay);
            pst.setString(4, bal);
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            
            if(rs.next())
            {
                
                lastid = rs.getInt(1);
            }
            
            
            String query2 = "insert into sales_product(salesID,productID,price,quantity,total)values(?,?,?,?,?) ";
            pst1 = con.prepareStatement(query2);
            
          
              String productid;
              String price;
              String qty;
              
              int total = 0;

             for(int i=0; i<jTable1.getRowCount(); i++)
             {
                 productid = (String)jTable1.getValueAt(i, 0);
                 price = (String)jTable1.getValueAt(i, 2);
                 qty = (String)jTable1.getValueAt(i, 3);
                 total = (int)jTable1.getValueAt(i, 4);

                 pst1.setInt(1, lastid);
                 pst1.setString(2, productid);
                 pst1.setString(3, price);
                 pst1.setString(4, qty);
                 pst1.setInt(5, total);
                 pst1.executeUpdate();        
             }

             String query3 = "update product set qty = qty- ?   where UPC = ?";
             pst2 = con.prepareStatement(query3);

             for(int i=0; i<jTable1.getRowCount(); i++)
             {
                 productid = (String)jTable1.getValueAt(i, 0);
                 qty = (String)jTable1.getValueAt(i, 3);
                 
                 pst2.setString(1, qty);
                 pst2.setString(2, productid);
                 pst2.executeUpdate();

             }

             JOptionPane.showMessageDialog(this, "Sales Has Been Successfully Completed.");

             HashMap p = new HashMap();
             p.put("invo", lastid);
             
        } catch (SQLException ex) {
            Logger.getLogger(purchase.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
     

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel9 = new javax.swing.JLabel();
        txtPayment = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtBalance = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtPCode = new javax.swing.JTextField();
        txtPName = new javax.swing.JTextField();
        txtPrice = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtQty = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        txtTCost = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        jLabel9.setText("Payment");

        jLabel10.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        jLabel10.setText("Balance");

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        jLabel1.setText("Sales Details");

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        jLabel2.setText("Product Code");

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        jLabel3.setText("Product Name");

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        jLabel5.setText("Price");

        txtPCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPCodeKeyPressed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        jLabel7.setText("Qty");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPCode))
                .addGap(62, 62, 62)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPName))
                .addGap(70, 70, 70)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(86, 86, 86)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(54, Short.MAX_VALUE))
        );

        jButton2.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        jButton2.setText("Add to Databse");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        jButton3.setText("Close");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTable1.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product Code", "Product Name", "Product Price", "Quantity", "Total Cost"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        jLabel8.setText("Total Cost");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10))
                                .addGap(41, 41, 41)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTCost)
                                    .addComponent(txtPayment)
                                    .addComponent(txtBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(380, 380, 380)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(179, 179, 179)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addGap(34, 34, 34)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtTCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(txtBalance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(txtPayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtPCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPCodeKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {

            barcode();

        }

    }//GEN-LAST:event_txtPCodeKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Sales();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

        int pay = Integer.parseInt(txtPayment.getText());
        int subtotal = Integer.parseInt(txtTCost.getText());
        int bal = subtotal - pay;

        txtBalance.setText(String.valueOf(bal));

      //  add();

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:

        this.setVisible(false);
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(sales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(sales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(sales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(sales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new sales().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtBalance;
    private javax.swing.JTextField txtPCode;
    private javax.swing.JTextField txtPName;
    private javax.swing.JTextField txtPayment;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtQty;
    private javax.swing.JTextField txtTCost;
    // End of variables declaration//GEN-END:variables
}
