/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 *
 * Copyright ownership: Patroklos Samaras
 */
package com.storage.mywarehouse;

import com.storage.mywarehouse.Entity.Customer;
import com.storage.mywarehouse.Entity.Warehouse;
import com.storage.mywarehouse.Hibernate.NewHibernateUtil;
import com.storage.mywarehouse.View.WarehouseProduct;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.collections.primitives.ArrayDoubleList;
import org.apache.commons.collections.primitives.DoubleList;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Patroklos
 */
public final class mainframe extends javax.swing.JFrame implements Observer {

    /**
     * Creates new form mainframe
     */
    
    private ProductForm prframe;
    private ClientFrame clframe;
    private List customers;
    private DoubleList customer_dc;
    private List<storagepanel> panels;
    private List<Warehouse> warehouseList;
    private DefaultTableModel tableModel;
    private DefaultTableModel tableModel_rep;
    private List productList;
    private List reporterProductList;

    @Override // Observer interface's implemented method
    public void update(Observable o, Object data) {
        switch ((String) data) {
            case "refresh":
                fillInReporter();
                break;
            case "refresh_clients":
                refreshCustomers();
                break;
        }
    }

    public void refreshCustomers() {
        customers = new ArrayList<>();

        customer_dc = new ArrayDoubleList();
        customer_dc.add(0.0);
        clientCombo.addItem("Select Customer");

        Session session = NewHibernateUtil.getSessionFactory().openSession();

        Transaction tx = session.beginTransaction();

        customers = session.createQuery("FROM Customer").list();
        
        tx.commit();
        session.close();

        if (Globals.ClientsFrame) {
            clframe.refreshClients(customers);
        }

        clientCombo.removeAllItems();
        customer_dc = new ArrayDoubleList();
        customer_dc.add(0.0);
        clientCombo.addItem("Customer Selection");

        for (Iterator it = customers.iterator(); it.hasNext();) {
            Customer cust = (Customer) it.next();
            customer_dc.add(cust.getDiscount());
            clientCombo.addItem(cust.getLastName() + " - " + cust.getName());
        }

    }


    public mainframe() {
//        addWindowListener(new java.awt.event.WindowAdapter() {
//            @Override
//            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
//                
//            }
//        });

        panels = new ArrayList<>();
        warehouseList = new ArrayList<>();
        customer_dc = new ArrayDoubleList();
        customer_dc.add(0.0);
        initComponents();

        setTitle("StoreHouse");

        //
        tableModel = new DefaultTableModel(new Object[]{"Code", "Brand", "Type", "Total Amount", "Warehouse", "Price", "Price after Discount"}, 0);
        jTable1.setModel(tableModel);
        tableModel_rep = new DefaultTableModel(new Object[]{"Code", "Brand", "Type", "Quantity", "Warehouse"}, 0);
        reporter.setModel(tableModel_rep);

        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.setRowSelectionAllowed(true);

        jTable1.getColumnModel().getColumn(0).setMinWidth(150);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(150);
        jTable1.getColumnModel().getColumn(1).setMinWidth(150);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(1).setMaxWidth(150);
        jTable1.getColumnModel().getColumn(2).setMinWidth(150);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(2).setMaxWidth(150);
        jTable1.getColumnModel().getColumn(4).setMinWidth(300);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(300);
        jTable1.getColumnModel().getColumn(4).setMaxWidth(300);
        jTable1.getColumnModel().getColumn(5).setMinWidth(150);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(5).setMaxWidth(150);
        jTable1.getColumnModel().getColumn(6).setMinWidth(150);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(6).setMaxWidth(150);

        reporter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        reporter.setRowSelectionAllowed(true);

        reporter.getColumnModel().getColumn(0).setMinWidth(150);
        reporter.getColumnModel().getColumn(0).setPreferredWidth(150);
        reporter.getColumnModel().getColumn(0).setMaxWidth(150);
        reporter.getColumnModel().getColumn(1).setMinWidth(150);
        reporter.getColumnModel().getColumn(1).setPreferredWidth(150);
        reporter.getColumnModel().getColumn(1).setMaxWidth(150);
        reporter.getColumnModel().getColumn(2).setMinWidth(150);
        reporter.getColumnModel().getColumn(2).setPreferredWidth(150);
        reporter.getColumnModel().getColumn(2).setMaxWidth(150);
        reporter.getColumnModel().getColumn(4).setMinWidth(300);
        reporter.getColumnModel().getColumn(4).setPreferredWidth(300);
        reporter.getColumnModel().getColumn(4).setMaxWidth(300);

        TabTitleEditListener l = new TabTitleEditListener(tab, panels, this);
        tab.addChangeListener(l);
        tab.addMouseListener(l);

        
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        
        List warehouseList = session.createQuery("FROM Warehouse").list();
        
        if(warehouseList.isEmpty() ){
            JOptionPane.showMessageDialog(this, "No previous configuration found. The program will initiate in a clean state.");
        }
        
        for (Iterator it = warehouseList.iterator(); it.hasNext();) {
            Warehouse w = (Warehouse) it.next();
            
            panels.add(new storagepanel(w, this));
            panels.get(panels.size()-1).Load();
            
            tab.add(w.getName(), panels.get(panels.size()-1));
        }
        
        tx.commit();
        session.close();
        
        
        fillInReporter();

        //load customers
        refreshCustomers();

        setLocationRelativeTo(null);
    }

    private void cleanEmptyUntitledTabs() {

        for (int i = 0; i < panels.size(); i++) {
            if (tab.getTitleAt(i + 2).length() < 2) {
                if (panels.get(i).getTableModel().getRowCount() == 0) {
                    tab.remove(i + 2);
                    panels.remove(i--);
                }
            }
        }
    }

//    public void refreshIndex() {
//        for (int i = 0; i < panels.size(); i++) {
//            int rows = panels.get(i).getTableModel().getRowCount();
//            String[] temp;
//
//            int q;
//            for (int j = 0; j < rows; j++) {
//                temp = new String[3];
//                q = 0;
//
//                temp[0] = (String) panels.get(i).getTableModel().getValueAt(j, 0);
//                temp[1] = (String) panels.get(i).getTableModel().getValueAt(j, 1);
//                temp[2] = (String) panels.get(i).getTableModel().getValueAt(j, 2);
//
//                MyTriple<String, String, String> tpl = new MyTriple<>(temp[0], temp[1], temp[2]);
//                q = Integer.parseInt((String) panels.get(i).getTableModel().getValueAt(j, 3));
//
//                // me apothikes
//                if (products.containsKey(tpl)) {
//                    MyTriple<Integer, String, Double> tp = new MyTriple<>(products.get(tpl).getLeft() + q, products.get(tpl).getMiddle() + ", " + tab.getTitleAt(i + 2), Double.parseDouble(panels.get(i).getTableModel().getValueAt(j, 4).toString()));
//                    products.put(tpl, tp);
//                } else {
//                    MyTriple<Integer, String, Double> tp = new MyTriple<>(q, tab.getTitleAt(i + 2), Double.parseDouble(panels.get(i).getTableModel().getValueAt(j, 4).toString()));
//                    products.put(tpl, tp);
//                }
//            }
//
//        }
//        
//    }

    public void fillInReporter() {

        //clean reporter
        int rows = tableModel_rep.getRowCount();
        for (int i = 0; i < rows; i++) {
            tableModel_rep.removeRow(0);
        }
        
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        
        
            reporterProductList = session.createQuery("FROM WarehouseProduct WP WHERE Quantity = 0").list();
            
        tx.commit();
        session.close();
            
            // fill in table with zero quantities
            
            for (Iterator it = reporterProductList.iterator(); it.hasNext();) {
                WarehouseProduct pr = (WarehouseProduct) it.next();

                tableModel_rep.addRow(new Object[]{pr.getProductId(), pr.getBrand(), pr.getType(), pr.getQuantity(), pr.getWarehouse()});
                
            }

    }

    

    private Warehouse AddNewWareHouse(String name) {
        
        
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        
        List whs = session.createQuery("FROM Warehouse W ORDER BY W.warehouseId DESC").list();
        
        int nextId = 0;
        
        if(whs.size() > 0 ){
            Warehouse w = (Warehouse) whs.get(0);
            nextId = w.getWarehouseId() +1;
        }
        
        Warehouse wh = new Warehouse(nextId, name);
        session.save(wh);
        tx.commit();
        session.close();
        
        return(wh);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tab = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        searchfield = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        clientCombo = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        dc_label = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        reporter = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        close = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Code", "Brand", "Type", "Total Amount", "Warehouses", "Price", "Price after Discount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(170);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(170);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(170);
            jTable1.getColumnModel().getColumn(1).setMinWidth(170);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(170);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(170);
            jTable1.getColumnModel().getColumn(2).setMinWidth(170);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(170);
            jTable1.getColumnModel().getColumn(2).setMaxWidth(170);
            jTable1.getColumnModel().getColumn(3).setMinWidth(170);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(170);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(170);
            jTable1.getColumnModel().getColumn(4).setMinWidth(170);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(170);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(170);
            jTable1.getColumnModel().getColumn(5).setMinWidth(150);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(5).setMaxWidth(150);
            jTable1.getColumnModel().getColumn(6).setMinWidth(150);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(6).setMaxWidth(150);
        }

        searchfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchfieldActionPerformed(evt);
            }
        });

        jLabel2.setText("Enter code:");

        jButton2.setText("Search");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Select Customer");

        clientCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientComboActionPerformed(evt);
            }
        });

        jLabel3.setText("Discount Percentage");

        jLabel5.setText("%");

        dc_label.setColumns(6);
        dc_label.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 775, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(searchfield, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(dc_label, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5)
                            .addGap(237, 237, 237))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1)
                                .addComponent(clientCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(147, 147, 147)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(88, 88, 88))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addComponent(clientCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(dc_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(79, 79, 79))
        );

        tab.addTab("General", jPanel1);

        reporter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Code", "Brand", "Type", "Total Quantity", "WareHouses"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        reporter.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(reporter);
        if (reporter.getColumnModel().getColumnCount() > 0) {
            reporter.getColumnModel().getColumn(0).setMinWidth(170);
            reporter.getColumnModel().getColumn(0).setPreferredWidth(170);
            reporter.getColumnModel().getColumn(0).setMaxWidth(170);
            reporter.getColumnModel().getColumn(1).setMinWidth(170);
            reporter.getColumnModel().getColumn(1).setPreferredWidth(170);
            reporter.getColumnModel().getColumn(1).setMaxWidth(170);
            reporter.getColumnModel().getColumn(2).setMinWidth(170);
            reporter.getColumnModel().getColumn(2).setPreferredWidth(170);
            reporter.getColumnModel().getColumn(2).setMaxWidth(170);
            reporter.getColumnModel().getColumn(3).setMinWidth(170);
            reporter.getColumnModel().getColumn(3).setPreferredWidth(170);
            reporter.getColumnModel().getColumn(3).setMaxWidth(170);
            reporter.getColumnModel().getColumn(4).setMinWidth(170);
            reporter.getColumnModel().getColumn(4).setPreferredWidth(170);
            reporter.getColumnModel().getColumn(4).setMaxWidth(170);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 775, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
                .addContainerGap())
        );

        tab.addTab("Shortage", jPanel2);

        jMenu1.setText("File");

        close.setText("Exit");
        close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeActionPerformed(evt);
            }
        });
        jMenu1.add(close);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jMenu4.setText("Management");

        jMenu6.setText("Product");

        jMenuItem4.setText("Add Product");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem4);

        jMenu4.add(jMenu6);

        jMenu3.setText("Customer");

        jMenuItem3.setText("Open Customer Window");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        jMenu4.add(jMenu3);

        jMenu5.setText("Warehouse");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Add WareHouse");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Delete Selected Warehouse");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem2);

        jMenu4.add(jMenu5);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tab)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tab, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeActionPerformed
        dispose();
        System.exit(0);
    }//GEN-LAST:event_closeActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        String name = JOptionPane.showInputDialog(this, "Enter name for the new warehouse");

        if (name != null) {
            Warehouse w  = AddNewWareHouse(name);
            panels.add(new storagepanel(w, this));
            warehouseList.add(w);
            tab.add(name, panels.get(panels.size() - 1));
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void searchfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchfieldActionPerformed
    }//GEN-LAST:event_searchfieldActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        if (searchfield.getText().length() > 2) {

            //first we clean the whole generic table
            int rows = tableModel.getRowCount();
            for (int i = 0; i < rows; i++) {
                tableModel.removeRow(0);
            }

            //get the desired code
            String search_code = searchfield.getText();

            // fill the table with rows that contain this code ONLY
            
            Session session = NewHibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            
            productList = session.createQuery("FROM WarehouseProduct WP WHERE Code = "+ search_code).list();
            
            
            for (Iterator it = productList.iterator(); it.hasNext();) {
                WarehouseProduct pr = (WarehouseProduct) it.next();
                
                double init_pr = pr.getPrice();
                double dc = Double.parseDouble(dc_label.getText());
                double dc_pr = init_pr * dc / 100;

                tableModel.addRow(new Object[]{pr.getProductId(), pr.getBrand(), pr.getType(), pr.getQuantity(), pr.getWarehouse(), init_pr, init_pr - dc_pr});
                
            }
            
            tx.commit();
            session.close();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        if (!Globals.ClientsFrame) {
            clframe = new ClientFrame(this, customers);
            Globals.ClientsFrame = true;
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void clientComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientComboActionPerformed
        if (evt.getActionCommand().equalsIgnoreCase("comboBoxChanged")) {
            int index = clientCombo.getSelectedIndex();
            if (index >= 0) {
                dc_label.setText(customer_dc.get(index) + "");
            } else {
                dc_label.setText("");
            }

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 5).toString().length() > 0) {
                    double initial_v = (double) tableModel.getValueAt(i, 5);
                    double dc = Double.parseDouble(dc_label.getText());
                    tableModel.setValueAt(initial_v - (initial_v * dc / 100), i, 6);
                }
            }
        }
    }//GEN-LAST:event_clientComboActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        
        //
        
        int tabId = tab.getSelectedIndex();
        if(tabId > 1){
            
            Session session = NewHibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            
            
            
            session.delete(panels.get(tabId-2).getWarehouse());
            tx.commit();
            session.close();
            
            tab.remove(tabId);
            panels.remove(tabId-2);

        }
        
        // get tab id to remove
                
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        if (!Globals.ProductsFrame) {
            prframe = new ProductForm(this);
            Globals.ProductsFrame = true;
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox clientCombo;
    private javax.swing.JMenuItem close;
    private javax.swing.JTextField dc_label;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable reporter;
    private javax.swing.JTextField searchfield;
    private javax.swing.JTabbedPane tab;
    // End of variables declaration//GEN-END:variables
}


class TabTitleEditListener extends MouseAdapter implements ChangeListener {

    private final JTextField editor = new JTextField();
    private final JTabbedPane tabbedPane;
    private int editing_idx = -1;
    private int len = -1;
    private Dimension dim;
    private Component tabComponent = null;
    private List<storagepanel> panels;
    private final MyObservable observable = new MyObservable();

    public TabTitleEditListener(final JTabbedPane tabbedPane, List<storagepanel> pan, mainframe frame) {

        super();

        //set observer
        observable.addObserver(frame);

        this.tabbedPane = tabbedPane;
        this.panels = pan;
        editor.setBorder(BorderFactory.createEmptyBorder());
        editor.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                renameTabTitle();
            }
        });
        editor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    renameTabTitle();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    cancelEditing();
                } else {
                    editor.setPreferredSize(editor.getText().length() > len ? null : dim);
                    tabbedPane.revalidate();
                }
            }
        });
        tabbedPane.getInputMap(JComponent.WHEN_FOCUSED).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "start-editing");
        tabbedPane.getActionMap().put("start-editing", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startEditing();
            }
        });
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        renameTabTitle();
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        Rectangle rect = tabbedPane.getUI().getTabBounds(tabbedPane, tabbedPane.getSelectedIndex());
        if (rect != null && rect.contains(me.getPoint()) && me.getClickCount() == 2) {
            startEditing();
        } else {
            renameTabTitle();
        }
    }

    private void startEditing() {
        editing_idx = tabbedPane.getSelectedIndex();
        tabComponent = tabbedPane.getTabComponentAt(editing_idx);
        tabbedPane.setTabComponentAt(editing_idx, editor);
        editor.setVisible(true);
        editor.setText(tabbedPane.getTitleAt(editing_idx));
        editor.selectAll();
        editor.requestFocusInWindow();
        len = editor.getText().length();
        dim = editor.getPreferredSize();
        editor.setMinimumSize(dim);
    }

    private void cancelEditing() {
        if (editing_idx >= 0) {
            tabbedPane.setTabComponentAt(editing_idx, tabComponent);
            editor.setVisible(false);
            editing_idx = -1;
            len = -1;
            tabComponent = null;
            editor.setPreferredSize(null);
            tabbedPane.requestFocusInWindow();
        }
    }

    private void renameTabTitle() {
        String title = editor.getText().trim();
        
        if (editing_idx >= 0 && !title.isEmpty()) {
            tabbedPane.setTitleAt(editing_idx, title);
            
            panels.get(editing_idx - 2).setName(title);
            Warehouse wh = panels.get(editing_idx - 2).getWarehouse();
            wh.setName(title);
            
            Session session = NewHibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.update(wh);
            tx.commit();
            session.close();
            
        }
        cancelEditing();
        observable.changeData("refresh");
    }
}
