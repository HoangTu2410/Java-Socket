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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Group;
import model.JoinedGroup;
import model.ObjectWrapper;
import model.User;

/**
 *
 * @author hoang
 */
public class DetailsGroupFrm extends javax.swing.JFrame {
    private ClientCtr myControl;
    private Group myGroup;
    ArrayList<JoinedGroup> listMember;
    /**
     * Creates new form DetailsGroupFrm
     */
    public DetailsGroupFrm(ClientCtr control, Group group) {
        initComponents();
        this.setTitle("Details Group");
        myControl = control;
        myGroup = group;
        jLabelName.setText(myGroup.getName());
        listMember = new ArrayList<JoinedGroup>();
        for(JoinedGroup jg: myGroup.getListJoined()){
            if(jg.getPosition()>JoinedGroup.IS_REQUEST) listMember.add(jg);
        }
        setTable();
        
        int myPosition = JoinedGroup.IS_REQUEST;
        for(JoinedGroup i: listMember){
            if(i.getUser().getId() == myControl.getMyUser().getId())
                myPosition = i.getPosition();
        }
        if(myPosition == JoinedGroup.IS_ADMIN){
            tblResult.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int column = tblResult.getColumnModel().getColumnIndexAtX(e.getX()); // get the coloum of the button
                    int row = e.getY() / tblResult.getRowHeight(); // get the row of the button
 
                    // *Checking the row or column is valid or not
                    if (row < tblResult.getRowCount() && row >= 0 && column < tblResult.getColumnCount() && column >= 0) {
                        if(listMember.get(row).getUser().getId()==myControl.getMyUser().getId()) return;
                        int result = JOptionPane.showConfirmDialog(null, "Do you want to delete this member?", "Delete member", JOptionPane.YES_NO_OPTION);
                        if(result == JOptionPane.YES_OPTION){
                            int k = -1;
                            for(int j=0; j<myGroup.getListJoined().size(); j++){
                                if(myGroup.getListJoined().get(j).getUser().getId() == listMember.get(row).getUser().getId()){
                                    myGroup.getListJoined().get(j).setPosition(JoinedGroup.DELETE_MEMBER);
                                    k = j;
                                }
                            }
                            myControl.sendData(new ObjectWrapper(ObjectWrapper.DELETE_MEMBER, myGroup));
                            myControl.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_DELETE_MEMBER, this));
                            myGroup.getListJoined().remove(k);
                            listMember.remove(row);
                            setTable();
                        }else{
                            return;
                        }
                    }
                }
            });
        } else {
            btnEdit.setEnabled(false);
            btnRequestJoin.setEnabled(false);
        }
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    private void setTable(){
        String[] columnNames = {"Name", "Position", "Status"};
        String[][] value = new String[listMember.size()][columnNames.length];
        for(int i=0; i<listMember.size(); i++){
            value[i][0] = listMember.get(i).getUser().getName();
            int position = listMember.get(i).getPosition();
            if(position == JoinedGroup.IS_ADMIN) value[i][1] = "Admin";
            else value[i][1] = "Member";
            boolean online = false;
            for(User user: myControl.getListOnline())
                if(user.getId() == listMember.get(i).getUser().getId()) online = true;
            if(online) value[i][2] = "Online";
            else value[i][2] = "Offline";
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
        if(data.getData().equals("oke")) {
            setTable();
        }
        else
            JOptionPane.showMessageDialog(null, "Error!");
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabelName = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblResult = new javax.swing.JTable();
        btnAddMember = new javax.swing.JButton();
        btnRequestJoin = new javax.swing.JButton();
        btnLeave = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnReturn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelName.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabelName.setText("Group's name");

        tblResult.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblResult.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Position", "Status"
            }
        ));
        jScrollPane1.setViewportView(tblResult);

        btnAddMember.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnAddMember.setText("Add Member");
        btnAddMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMemberActionPerformed(evt);
            }
        });

        btnRequestJoin.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnRequestJoin.setText("Request Join");
        btnRequestJoin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRequestJoinActionPerformed(evt);
            }
        });

        btnLeave.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnLeave.setText("Leave");
        btnLeave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeaveActionPerformed(evt);
            }
        });

        btnEdit.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnReturn.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnReturn.setText("Return");
        btnReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddMember, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRequestJoin, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLeave, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(342, 342, 342)
                .addComponent(jLabelName)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabelName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(btnAddMember, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(btnRequestJoin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(btnLeave, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        new EditGroupFrm(myControl, myGroup).setVisible(true);
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnAddMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMemberActionPerformed
        // TODO add your handling code here:
        new AddMemberFrm(myControl, myGroup).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnAddMemberActionPerformed

    private void btnRequestJoinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRequestJoinActionPerformed
        // TODO add your handling code here:
        new RequestGroupFrm(myControl, myGroup).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRequestJoinActionPerformed

    private void btnLeaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeaveActionPerformed
        // TODO add your handling code here:
        if(listMember.size()<2){
            JOptionPane.showMessageDialog(null, "The group doesn't have a new admin yet!");
            return;
        }
        int result = JOptionPane.showConfirmDialog(null, "Do you really want to leave the group?", "Confirm", JOptionPane.YES_NO_OPTION);
        if(result == JOptionPane.YES_OPTION){
            for(JoinedGroup i: myGroup.getListJoined()){
                if(i.getUser().getId() == myControl.getMyUser().getId()){
                    if(i.getPosition()==1) i.setPosition(JoinedGroup.DELETE_MEMBER);
                    else if(i.getPosition()==2) i.setPosition(JoinedGroup.ADMIN_LEAVE_GROUP);
                }
            }
            myControl.sendData(new ObjectWrapper(ObjectWrapper.DELETE_MEMBER, myGroup));
            this.dispose();
        }else{
            return;
        }
    }//GEN-LAST:event_btnLeaveActionPerformed

    private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnReturnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddMember;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnLeave;
    private javax.swing.JButton btnRequestJoin;
    private javax.swing.JButton btnReturn;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblResult;
    // End of variables declaration//GEN-END:variables
}
