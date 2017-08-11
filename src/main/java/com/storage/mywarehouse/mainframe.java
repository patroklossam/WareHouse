/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 *
 * Copyright ownership: Patroklos Samaras
 */


package com.storage.mywarehouse;

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
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;


/**
 *
 * @author Patroklos
 */

public final class mainframe extends javax.swing.JFrame implements Observer{

    /**
     * Creates new form mainframe
     */
    
    private ClientFrame clframe;
    private DataInputStream idx;
    private ObjectInputStream cus_in;
    private List<ImmutableTriple> customers;
    private DoubleList customer_dc;
    private List<storagepanel> panels;
    private DefaultTableModel tableModel;
    private DefaultTableModel tableModel_rep;
    private TreeMap<MyTriple<String,String,String>, MyTriple<Integer,String,Double>> tires;
    
    @Override // Observer interface's implemented method
    public void update(Observable o, Object data) {	
        switch ((String) data) {
            case "refresh":
                refreshIndex();
                fillInReporter();
                break;
            case "refresh_clients":
                refreshCustomers();
                break;
        }
    }
    
    public void refreshCustomers(){
        customers = new ArrayList<>();
        
        customer_dc = new ArrayDoubleList();
        customer_dc.add(0.0);
        clientCombo.addItem("Επιλέξτε Πελάτη");
        
        try {
            cus_in = new ObjectInputStream(new BufferedInputStream(new FileInputStream("df_csr.bdf")));
            
            customers = (ArrayList<ImmutableTriple>) cus_in.readObject();
            cus_in.close();
            
            if(Globals.ClientsFrame)
                clframe.refreshClients(customers);
            
            clientCombo.removeAllItems();
            customer_dc = new ArrayDoubleList();
            customer_dc.add(0.0);
            clientCombo.addItem("Επιλέξτε Πελάτη");
            
            ImmutableTriple<String,String,Double> csmr;
            for (Iterator<ImmutableTriple> it = customers.iterator(); it.hasNext();) {
                csmr = it.next();
                customer_dc.add(Double.parseDouble(csmr.getRight().toString()));
                clientCombo.addItem(csmr.getLeft()+" - "+csmr.getMiddle());
            }
             
            
            
        } catch (IOException | ClassNotFoundException ex) {
            // nothing to do -- customer list is empty
        }
    }
    
    public void saveCustomers(){
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("df_csr.bdf")));
            oos.writeObject(customers);
            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                oos.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public mainframe() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                save();
            }
        });
        
        
        
        
        
        panels = new ArrayList<>();
        tires = new TreeMap<>();
        customer_dc = new ArrayDoubleList();
        customer_dc.add(0.0);
        initComponents();
        
        
        
        setTitle("StoreHouse");
        
        //
        tableModel = new DefaultTableModel(new Object[]{"Μάρκα", "Τύπος", "Διάσταση", "Σύνολο", "Αποθήκες","Τιμή","Τιμή_πελάτη"}, 0);
        jTable1.setModel(tableModel);
        tableModel_rep = new DefaultTableModel(new Object[]{"Μάρκα", "Τύπος", "Διάσταση", "Σύνολο", "Αποθήκες"}, 0);
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
        
        
        TabTitleEditListener l = new TabTitleEditListener(tab,panels,this);
        tab.addChangeListener(l);
        tab.addMouseListener(l);
                
    
               
        
        try {
            idx = new DataInputStream(new BufferedInputStream(new FileInputStream("df0000.idx")));
        } catch (FileNotFoundException ex) {
            try {

                JOptionPane.showMessageDialog(this, "Δεν βρέθηκαν ρυθμίσεις οπότε πραγματοποιείται επαναφορά στην αρχική κατάσταση");
                DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("df0000.idx")));

                out.writeInt(0);

                out.close();

                idx = new DataInputStream(new BufferedInputStream(new FileInputStream("df0000.idx")));

            } catch (FileNotFoundException ex1) {
                Logger.getLogger(mainframe.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (IOException ex1) {
                Logger.getLogger(mainframe.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        
        try {
            int num_of_tabs = idx.readInt();

            for(int i=0;i<num_of_tabs;i++){
                String nam = idx.readUTF();
                panels.add(new storagepanel(nam,this));
                panels.get(i).Load();
                
                tab.add(nam, panels.get(i));
                
                //System.out.println("added");
            }

            
            idx.close();

        } catch (IOException ex1) {
            Logger.getLogger(mainframe.class.getName()).log(Level.SEVERE, null, ex1);
        }
        
        cleanEmptyUntitledTabs();
        
        
        refreshIndex();
        fillInReporter();
        
        
        //load customers
        refreshCustomers();
        
        
        setLocationRelativeTo(null);
    }
    
    private void cleanEmptyUntitledTabs(){
        
        for(int i=0; i<panels.size();i++){
            if(tab.getTitleAt(i+2).length() < 2){
                if(panels.get(i).getTableModel().getRowCount() == 0){
                    tab.remove(i+2);
                    panels.remove(i--);
                }
            }       
        }
        
        CleanIndexSave();
    }
    
    
    
    public void refreshIndex(){
        tires = new TreeMap<>();
        for(int i=0; i<panels.size();i++){
            int rows =  panels.get(i).getTableModel().getRowCount();
            String[] temp;
            
            int q;
            for(int j=0;j<rows;j++){
                temp = new String[3];
                q =0;
                
                temp[0] = (String) panels.get(i).getTableModel().getValueAt(j, 0);
                temp[1] = (String) panels.get(i).getTableModel().getValueAt(j, 1);
                temp[2] = (String) panels.get(i).getTableModel().getValueAt(j, 2);
                
                MyTriple<String,String,String> tpl = new MyTriple<>(temp[0],temp[1],temp[2]);
                q = Integer.parseInt((String) panels.get(i).getTableModel().getValueAt(j, 3));
                
                // me apothikes
                if(tires.containsKey(tpl)){
                    MyTriple<Integer, String, Double> tp= new MyTriple<>(tires.get(tpl).getLeft()+q,tires.get(tpl).getMiddle()+", "+tab.getTitleAt(i+2),Double.parseDouble(panels.get(i).getTableModel().getValueAt(j, 4).toString()));
                    tires.put(tpl, tp);
                }
                else{
                    MyTriple<Integer, String, Double> tp= new MyTriple<>(q,tab.getTitleAt(i+2),Double.parseDouble( panels.get(i).getTableModel().getValueAt(j, 4).toString()));
                    tires.put(tpl, tp);
                }
                
                
                // xwris apothikes
                /*if(tires.containsKey(tpl)){
                    tires.put(tpl, tires.get(tpl) + q);
                }
                else
                    tires.put(tpl, q);*/
            }
            
        }
        //int rows = tableModel.getRowCount();
        
        
        // empty table;
        /*for(int i=0;i<rows;i++){
            //System.out.println("removed row: "+i);
            tableModel.removeRow(0);
        }*/
        //tableModel.fireTableDataChanged();
        
        // fill table with All tires 
        /*for(MyTriple<String,String,String> tripl : tires.keySet())
            tableModel.addRow(new Object[]{tripl.getLeft(),tripl.getMiddle(),tripl.getRight(),tires.get(tripl).getLeft(),tires.get(tripl).getRight()});
        */
        
        
    }
    
    public void fillInReporter(){
        
        //clean reporter
        
        int rows = tableModel_rep.getRowCount();
        for(int i=0;i<rows;i++){
            tableModel_rep.removeRow(0);
        }
        
        //fill table with ZERO quantities
        
        for(MyTriple<String,String,String> tripl : tires.keySet()){
                if(tires.get(tripl).getLeft() == 0)
                    tableModel_rep.addRow(new Object[]{tripl.getLeft(),tripl.getMiddle(),tripl.getRight(),tires.get(tripl).getLeft(),tires.get(tripl).getMiddle()});
            }
        
    }
    
    
    public boolean save(){
        refreshIndex();
        
        if(panels.size()>1)
            for(int i=1; i<panels.size();i++){
                panels.get(i).SaveStorage();
            }
        return true;
    }
    
    private void CleanIndexSave(){
        try{
                DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("df0000.idx")));


                out.writeInt(panels.size());
                for(int i=0;i<panels.size();i++)
                    out.writeUTF(tab.getTitleAt(i+2));

                out.close();
            } catch (FileNotFoundException ex1) {
                Logger.getLogger(mainframe.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (IOException ex1) {
                Logger.getLogger(mainframe.class.getName()).log(Level.SEVERE, null, ex1);
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
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Μάρκα", "Τύπος", "Διάσταση", "Σύνολο", "Αποθήκες", "Τιμή", "Τιμή Πελάτη"
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

        jLabel2.setText("Εισάγετε Διάσταση");

        jButton2.setText("Αναζήτηση");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Επιλέξτε Πελάτη");

        clientCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientComboActionPerformed(evt);
            }
        });

        jLabel3.setText("Ποσοστό Έκπτωσης");

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
                "Brand", "Type", "Description", "Total Quantity", "WareHouses"
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

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Add WareHouse");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Customer Management");

        jMenuItem3.setText("Customer Window");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        jMenuBar1.add(jMenu3);

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
        save();
        dispose();
    }//GEN-LAST:event_closeActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        String name = JOptionPane.showInputDialog(this, "Δώστε ένα όνομα για την αποθήκη: (πχ Πατάρι1)");
        
        if(name != null ){
            panels.add(new storagepanel(name,this));
            tab.add(name, panels.get(panels.size()-1));

            CleanIndexSave();
        }  
        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void searchfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchfieldActionPerformed
    }//GEN-LAST:event_searchfieldActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        
        if(searchfield.getText().length() > 2){
            
            //first we clean the whole generic table
            
            int rows = tableModel.getRowCount();
            for(int i=0;i<rows;i++){
            //System.out.println("removed row: "+i);
                tableModel.removeRow(0);
            }
            
            //get the desired dimension
            String dimension = searchfield.getText();
            
            // fill the table with rows that contain this dimension ONLY
            for(MyTriple<String,String,String> tripl : tires.keySet()){
                if(tripl.getRight().contains(dimension)){
                    double init_pr = tires.get(tripl).getRight();
                    double dc  = Double.parseDouble(dc_label.getText());
                    double dc_pr = init_pr * dc / 100;
                    
                    tableModel.addRow(new Object[]{tripl.getLeft(),tripl.getMiddle(),tripl.getRight(),tires.get(tripl).getLeft(),tires.get(tripl).getMiddle(),init_pr,init_pr - dc_pr});
            
                }
            }
            
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        if(!Globals.ClientsFrame){
            clframe = new ClientFrame(this,customers);
            Globals.ClientsFrame = true;
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void clientComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientComboActionPerformed
        if (evt.getActionCommand().equalsIgnoreCase("comboBoxChanged")) {
            int index = clientCombo.getSelectedIndex();
            if(index>=0)
                dc_label.setText(customer_dc.get(index)+"");
            else
                dc_label.setText("");
            
            
            for(int i=0;i<tableModel.getRowCount();i++){
                if(tableModel.getValueAt(i, 5).toString().length()>0){
                    double initial_v = (double) tableModel.getValueAt(i, 5);
                    double dc = Double.parseDouble(dc_label.getText());
                    tableModel.setValueAt(initial_v - (initial_v*dc/100) , i, 6);
                }
            }
        }
    }//GEN-LAST:event_clientComboActionPerformed
    
    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
       /* try {

            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
            
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mainframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }*/
        //</editor-fold>

        /* Create and display the form */
  /*      java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mainframe().setVisible(true);
            }
        });
    }*/

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
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem3;
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


class MyTriple<L,M,R> extends Triple<L,M,R>{
    
    private static final long serialVersionUID = 1L;
    
    /** Left object */
    public final L left;
    /** Middle object */
    public final M middle;
    /** Right object */
    public final R right;
    
    public static <L, M, R> MyTriple<L, M, R> of(final L left, final M middle, final R right) {
        return new MyTriple<L, M, R>(left, middle, right);
    }
    
    public MyTriple(L left, M middle, R right) {
        super();
        this.left = left;
        this.middle = middle;
        this.right = right;
    }
    public L getLeft() {
        return left;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M getMiddle() {
        return middle;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public R getRight() {
        return right;
    }
    
    @Override
    public int compareTo(final Triple<L, M, R> other) {
      return new CompareToBuilder().append(getRight(), other.getRight())
              .append(getMiddle(), other.getMiddle())
              .append(getLeft(), other.getLeft()).toComparison();
    }
    
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
            @Override public void focusLost(FocusEvent e) {
                renameTabTitle();
            }
        });
        editor.addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER) {
                    renameTabTitle();
                }else if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
                    cancelEditing();
                }else{
                    editor.setPreferredSize(editor.getText().length()>len ? null : dim);
                    tabbedPane.revalidate();
                }
            }
        });
        tabbedPane.getInputMap(JComponent.WHEN_FOCUSED).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "start-editing");
        tabbedPane.getActionMap().put("start-editing", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                startEditing();
            }
        });
    }
    @Override public void stateChanged(ChangeEvent e) {
        renameTabTitle();
    }
    @Override public void mouseClicked(MouseEvent me) {
        Rectangle rect = tabbedPane.getUI().getTabBounds(tabbedPane, tabbedPane.getSelectedIndex());
        if(rect!=null && rect.contains(me.getPoint()) && me.getClickCount()==2) {
            startEditing();
        }else{
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
        if(editing_idx>=0) {
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
        if(editing_idx>=0 && !title.isEmpty()) {
            tabbedPane.setTitleAt(editing_idx, title);
            panels.get(editing_idx-2).setName(title);
            panels.get(editing_idx-2).SaveStorage();
            
            try{
                DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("df0000.idx")));


                out.writeInt(panels.size());
                for(int i=0;i<panels.size();i++)
                    out.writeUTF(tabbedPane.getTitleAt(i+2));

                out.close();
            } catch (FileNotFoundException ex1) {
                Logger.getLogger(mainframe.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (IOException ex1) {
                Logger.getLogger(mainframe.class.getName()).log(Level.SEVERE, null, ex1);
            } 
        }
        cancelEditing();
        observable.changeData("refresh");
    }
}
