/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 *
 * Copyright ownership: Patroklos Samaras
 */

package com.storage.mywarehouse;

import com.storage.mywarehouse.Entity.Warehouse;
import com.storage.mywarehouse.Hibernate.NewHibernateUtil;
import com.storage.mywarehouse.View.WarehouseProduct;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.collections.primitives.ArrayIntList;
import org.apache.commons.collections.primitives.IntList;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Patroklos
 */
public class storagepanel extends javax.swing.JPanel {

    /**
     * Creates new form storagepanel
     */
    
    
    //flag to check older version files
    private boolean noprice;
    
    private DefaultTableModel tableModel;
    private Warehouse warehouse;
    private String st_name;
    private int st_id;
    
    private HashMap<Integer,Integer> DbToRow;
    private List rows;
    private final MyObservable observable = new MyObservable();
    
    public void setName(String n){
        st_name = n;
    }
    
    public Warehouse getWarehouse(){
        return warehouse;
    }
    
    public storagepanel(Warehouse wh ,mainframe frame) {
        
        
        //set observer
        observable.addObserver(frame);
        
        st_id = wh.getWarehouseId();
        st_name = wh.getName();
        warehouse = wh;
        initComponents();

        tableModel = new DefaultTableModel(new Object[]{"Code", "Brand", "Type", "Quantity", "Price"}, 0);
        jTable1.setModel(tableModel);
        
        jTable1.getDefaultEditor(String.class).addCellEditorListener(
                new CellEditorListener() {
                    public void editingCanceled(ChangeEvent e) {
                        System.out.println("editingCanceled");
                    }

                    public void editingStopped(ChangeEvent e) {
                        System.out.println("editingStopped: apply additional action");
                        SaveStorage();
                        
                        observable.changeData("refresh");
                    }
                });

    }
    
    private void cleanEmptyRows(){
        IntList ids = new ArrayIntList();
        int rows = tableModel.getRowCount();
        int cols = tableModel.getColumnCount();
        
        int sum =0;
        
        //get empty lines
        for(int i=0;i<rows;i++){
            sum=0;
            for(int j=0;j<cols;j++)
                if(tableModel.getValueAt(i, j).equals("") || tableModel.getValueAt(i, j).equals(" "))
                    sum++;
            
            if(sum == cols)
                ids.add(i);
        }
        
        // list to array to get elements reversed
        
        int rev[] = ids.toArray();
        
        
        // remove rows reversly
        for(int i=rev.length-1;i>=0;i--)
            tableModel.removeRow(rev[i]);
    }
    
    public DefaultTableModel getTableModel(){
        return tableModel;
    }

    public boolean SaveStorage() {
        
        
        try {
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("df_" + st_name + ".bdf")));
            out.writeInt(jTable1.getRowCount());  // to know the count of rows to read
            out.writeInt(jTable1.getColumnCount()); // for the count of columns
            
            
            // write out column names
            for (int col = 0; col < jTable1.getColumnCount(); col++) {
                 out.writeUTF(jTable1.getColumnName(col));
                //System.out.print(jTable1.getColumnName(col));
            }
            
            // write out all cells by row 
            // easy load by row
            for (int row = 0; row < jTable1.getRowCount(); row++) {
                for (int col = 0; col < jTable1.getColumnCount(); col++) {
                    out.writeUTF(jTable1.getValueAt(row, col).toString());
                }
            }
            out.close();
            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(storagepanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(storagepanel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return false;
    }

    public void Load() {
        
        DbToRow = new HashMap<>();
        
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        
        rows = session.createQuery("FROM Entry E where E.warehouseId = " + st_id).list();
        
        jTable1.setModel(tableModel);
        int r = 0;
        for (Iterator it = rows.iterator(); it.hasNext();) {
            WarehouseProduct wp = (WarehouseProduct) it.next();
            tableModel.addRow(new Object[]{wp.getProductId(), wp.getBrand(), wp.getType(), wp.getQuantity(), wp.getPrice()});
            
            DbToRow.put(r, wp.getId());
        }
        
        
        
        tx.commit();
        session.close();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null}
            },
            new String [] {
                "Code", "Brand", "Type", "Quantity", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTable1KeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Add Row");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("Remove empty rows");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Delete Row");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap(95, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        tableModel.addRow(new Object[]{"", "", "", "", ""});
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyTyped
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER)
            SaveStorage();
    }//GEN-LAST:event_jTable1KeyTyped

    private void formKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyTyped
        jTable1KeyTyped(evt);
        //System.out.println(evt.getKeyChar());
    }//GEN-LAST:event_formKeyTyped

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        cleanEmptyRows();
        SaveStorage();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int row = jTable1.getSelectedRow();
        if(row !=-1){
            int sel = JOptionPane.showConfirmDialog(this, "Θέλετε σίγουρα να διαγράψετε αυτή τη γραμμή;", "Επιβεβαίωση", JOptionPane.OK_CANCEL_OPTION);
            if (sel == 0)
                tableModel.removeRow(row);

            SaveStorage();
        }else{
            JOptionPane.showMessageDialog(this, "Πρέπει πρώτα να πιλέξετε με κλικ την γραμμή που θέλετε να διαγράψετε!");
        }
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
