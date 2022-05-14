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
public class JoinGroupFrm extends javax.swing.JPanel {
    private ClientCtr myControl;
    private ArrayList<Group> listGroup;
    /**
     * Creates new form SearchUserFrm
     */
    public JoinGroupFrm(ClientCtr control) {
        initComponents();
        myControl = control;
        listGroup = new ArrayList<Group>();
        
        tblResult.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int column = tblResult.getColumnModel().getColumnIndexAtX(e.getX()); // get the coloum of the button
                int row = e.getY() / tblResult.getRowHeight(); // get the row of the button
 
                // *Checking the row or column is valid or not
                if (row < tblResult.getRowCount() && row >= 0 && column < tblResult.getColumnCount() && column >= 0) {
                    Group group = listGroup.get(row);
                    int result = JOptionPane.showConfirmDialog(null, "Do you want send a request to join the group?", "Request to join", JOptionPane.YES_NO_OPTION);
                    if(result == JOptionPane.YES_OPTION){
                        JoinedGroup jg = new JoinedGroup(myControl.getMyUser(), JoinedGroup.IS_REQUEST);
                        group.getListJoined().add(jg);
                        listGroup.set(row, group);
                        myControl.sendData(new ObjectWrapper(ObjectWrapper.REQUEST_GROUP, group));
                    }else{
                        return;
                    }
                }
            }
        });
        myControl.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_SEARCH_GROUP, this));
    }
    
    private void setTable(){
        String[] columnNames = {"Number", "Name","Number of member", "Description"};
        String[][] value = new String[listGroup.size()][columnNames.length];
        for(int i=0; i<listGroup.size(); i++){
            int count = 0;
            for(JoinedGroup j: listGroup.get(i).getListJoined()){
                if(j.getPosition()>JoinedGroup.IS_REQUEST) count++;
            }
            value[i][0] = i + 1 + "";
            value[i][1] = listGroup.get(i).getName();
            value[i][2] = count+"";
            value[i][3] = listGroup.get(i).getDescription();
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
            for(Group i:list){
                Boolean check = false;
                for(JoinedGroup j: i.getListJoined()){
                    if(j.getUser().getId() == myControl.getMyUser().getId()){
                        check = true;
                    }
                }
                if(!check) listGroup.add(i);
            }
            setTable();
        }
        else if(data.getData().equals("false")){
            JOptionPane.showMessageDialog(null, "Error!");
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

        jLabel1 = new javax.swing.JLabel();
        txtKey = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblResult = new javax.swing.JTable();

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setText("Search Group");

        txtKey.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel2.setText("Name:");

        btnSearch.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        tblResult.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblResult.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Number", "Name", "Number of member", "Description"
            }
        ));
        jScrollPane1.setViewportView(tblResult);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(318, 318, 318)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 641, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(55, 55, 55)
                                .addComponent(txtKey, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(72, 72, 72)
                                .addComponent(btnSearch)))))
                .addContainerGap(92, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKey, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        if((txtKey.getText() == null)||(txtKey.getText().length() == 0)){
            return;
        }
        myControl.sendData(new ObjectWrapper(ObjectWrapper.SEARCH_GROUP, txtKey.getText().trim()));
    }//GEN-LAST:event_btnSearchActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblResult;
    private javax.swing.JTextField txtKey;
    // End of variables declaration//GEN-END:variables
}
