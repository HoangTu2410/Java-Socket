/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.ClientCtr;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Group;
import model.JoinedGroup;
import model.ObjectWrapper;
/**
 *
 * @author hoang
 */
public class GroupFrm extends javax.swing.JPanel {
    private ClientCtr myControl;
    private ArrayList<Group> listGroups;
    /**
     * Creates new form GroupFrm
     */
    public GroupFrm(ClientCtr control) {
        initComponents();
        myControl = control;
        listGroups = new ArrayList<Group>();
        tblResult.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int column = tblResult.getColumnModel().getColumnIndexAtX(e.getX()); // get the coloum of the button
                int row = e.getY() / tblResult.getRowHeight(); // get the row of the button
 
                // *Checking the row or column is valid or not
                if (row < tblResult.getRowCount() && row >= 0 && column < tblResult.getColumnCount() && column >= 0) {
                    Group group = listGroups.get(row);
                    new DetailsGroupFrm(myControl, group).setVisible(true);
                }
            }
        });
        
        myControl.sendData(new ObjectWrapper(ObjectWrapper.GROUP, myControl.getMyUser()));
        myControl.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_GROUP, this));
    }

    private void setTable(){
        String[] columnNames = {"Number", "Name", "Number of members"};
        String[][] value = new String[listGroups.size()][columnNames.length];
        for(int i=0; i<listGroups.size(); i++){
            int count = 0;
            for(JoinedGroup j: listGroups.get(i).getListJoined()){
                if(j.getPosition()>JoinedGroup.IS_REQUEST) count++;
            }
            value[i][0] = i+1+"";
            value[i][1] = listGroups.get(i).getName();
            value[i][2] = count+"";
        }
        DefaultTableModel tableModel = new DefaultTableModel(value, columnNames) {
        @Override
            public boolean isCellEditable(int row, int column) {
                //unable to edit cells
                return false;
            }
        };
        tblResult.setModel(tableModel);
    }
    
    public void receivedDataProcessing(ObjectWrapper data) {
        if(data.getData() instanceof ArrayList<?>) {
            ArrayList<Group> list = (ArrayList<Group>) data.getData();
            for(Group i: list){
                for(JoinedGroup j: i.getListJoined()){
                    if(j.getUser().getId() == myControl.getMyUser().getId() && j.getPosition()>JoinedGroup.IS_REQUEST){
                        listGroups.add(i);
                    }
                }
            }
            setTable();
        }
        else if(data.getData().equals("oke")){
            JOptionPane.showMessageDialog(null, "Create group successful!");
        }
        else
            JOptionPane.showMessageDialog(null, "Error!");
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblResult = new javax.swing.JTable();
        btnCreate = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setText("Group");

        tblResult.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblResult.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Number", "Name", "Number of members"
            }
        ));
        jScrollPane1.setViewportView(tblResult);

        btnCreate.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnCreate.setText("Create");
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 721, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(379, 379, 379)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCreate)))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
        // TODO add your handling code here:
        String name = JOptionPane.showInputDialog(null, "Enter group name, please!", "Create group", JOptionPane.QUESTION_MESSAGE);
        if(name == null) return;
        if(name.trim().equals("")) JOptionPane.showMessageDialog(null, "Invalid name!");
        else{
            ArrayList<JoinedGroup> listJoin = new ArrayList<JoinedGroup>();
            listJoin.add(new JoinedGroup(myControl.getMyUser(), JoinedGroup.IS_ADMIN));
            Group group = new Group(name, "",listJoin);
            myControl.sendData(new ObjectWrapper(ObjectWrapper.CREATE_GROUP, group));
            myControl.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_CREATE_GROUP, this));
        }
    }//GEN-LAST:event_btnCreateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblResult;
    // End of variables declaration//GEN-END:variables
}