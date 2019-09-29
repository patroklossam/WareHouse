/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 *
 * Copyright ownership: Patroklos Samaras
 */
package com.storage.mywarehouse;

import com.storage.mywarehouse.Dao.EntryDAO;
import com.storage.mywarehouse.Dao.ProductDAO;
import com.storage.mywarehouse.Dao.WarehouseEntryDAO;
import com.storage.mywarehouse.Entity.Entry;
import com.storage.mywarehouse.Entity.Product;
import com.storage.mywarehouse.Entity.QuantityHistory;
import com.storage.mywarehouse.Entity.Warehouse;
import com.storage.mywarehouse.Hibernate.NewHibernateUtil;
import com.storage.mywarehouse.View.WarehouseEntry;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.collections.primitives.ArrayIntList;
import org.apache.commons.collections.primitives.IntList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

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

    private HashMap<Integer, Integer> DbToRow;
    private List rows, rows_entry;
    private final MyObservable observable = new MyObservable();

    public void setName(String n) {
        st_name = n;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public storagepanel(Warehouse wh, mainframe frame) {

        //set observer
        observable.addObserver(frame);

        rows = new ArrayList<>();
        rows_entry = new ArrayList<>();
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
                int rowId = jTable1.getSelectedRow();

                SaveEntry(rowId);
                observable.changeData("refresh");
            }
        });

    }

    private void cleanEmptyRows() {
        IntList ids = new ArrayIntList();
        int rows = tableModel.getRowCount();
        int cols = tableModel.getColumnCount();

        int sum = 0;

        //get empty lines
        for (int i = 0; i < rows; i++) {
            sum = 0;
            for (int j = 0; j < cols; j++) {
                if (tableModel.getValueAt(i, j).equals("") || tableModel.getValueAt(i, j).equals(" ")) {
                    sum++;
                }
            }

            if (sum == cols) {
                ids.add(i);
            }
        }

        // list to array to get elements reversed
        int rev[] = ids.toArray();

        // remove rows reversly
        for (int i = rev.length - 1; i >= 0; i--) {
            tableModel.removeRow(rev[i]);
        }
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void SaveEntry(int rowId) {

        if (rowId != -1) {
            Entry e = (Entry) rows_entry.get(rowId);

            if (e != null) {

                Session session = NewHibernateUtil.getSessionFactory().openSession();
                Transaction tx = session.beginTransaction();
                e.setQuantity(Integer.parseInt(jTable1.getValueAt(rowId, 3).toString()));
                session.update(e);
                
                QuantityHistory qh = new QuantityHistory();
                QuantityHistory pqh = (QuantityHistory) session.createCriteria(QuantityHistory.class).addOrder(Order.desc("id")).setMaxResults(1).uniqueResult();
                
                qh.setId(pqh == null ? 0 : pqh.getId() + 1);
                qh.setDate(new Date());
                qh.setWareHouseEntryId(e.getEntryId());
                qh.setQuantity(e.getQuantity());
                
                session.save(qh);
                
                tx.commit();
                session.close();
            }
        }
    }

    public void Load() {
        DbToRow = new HashMap<>();
        rows = WarehouseEntryDAO.findByWarehouseId(st_id);
        rows_entry = EntryDAO.findByWarehouseId(st_id);
        jTable1.setModel(tableModel);
        for (Iterator it = rows.iterator(); it.hasNext();) {
            WarehouseEntry we = (WarehouseEntry) it.next();
            tableModel.addRow(new Object[]{we.getProductId(), we.getBrand(), we.getType(), we.getQuantity(), we.getPrice()});
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
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
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
        List<Product> products = ProductDAO.findAll();
        String[] prodArray = new String[products.size()];
        int i = 0;
        for (Product prod : products) {
            prodArray[i] = prod.getBrand() + "_" + prod.getType() + "_" + prod.getProductId();
            i++;
        }

        String selectedProduct = (String) JOptionPane.showInputDialog(this,
                "PLease select your Product?",
                "Product:",
                JOptionPane.QUESTION_MESSAGE,
                null,
                prodArray,
                prodArray[0]);

        int prodId = Integer.parseInt(selectedProduct.substring(selectedProduct.lastIndexOf('_') + 1));

        int warehouseId = this.st_id;
        Entry e = EntryDAO.save(new Entry(warehouseId, prodId, 0));
        Product pr = ProductDAO.findById(prodId);
        tableModel.addRow(new Object[]{pr.getProductId(), pr.getBrand(), pr.getType(), 0, pr.getPrice()});
        rows_entry.add(e);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyTyped
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            int rid = jTable1.getSelectedRow();
            SaveEntry(rid);
        }
    }//GEN-LAST:event_jTable1KeyTyped

    private void formKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyTyped
        jTable1KeyTyped(evt);
        //System.out.println(evt.getKeyChar());
    }//GEN-LAST:event_formKeyTyped

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        cleanEmptyRows();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int row = jTable1.getSelectedRow();
        if (row != -1) {
            int sel = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this entry?", "Confirm", JOptionPane.OK_CANCEL_OPTION);
            if (sel == 0) {
                tableModel.removeRow(row);
            }

            Entry e = (Entry) rows_entry.get(row);
            rows_entry.remove(e);

            EntryDAO.delete(e);
        } else {
            JOptionPane.showMessageDialog(this, "First click on the row you want to delete.");
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
