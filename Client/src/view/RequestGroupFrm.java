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
public class RequestGroupFrm extends javax.swing.JFrame {
    private ClientCtr myControl;
    private Group myGroup;
    ArrayList<JoinedGroup> listRequest;
    /**
     * Creates new form RequestGroupFrm
     */
    public RequestGroupFrm(ClientCtr control, Group group) {
        initComponents();
        this.setTitle(group.getName());
        myControl = control;
        myGroup = group;
        listRequest = new ArrayList<JoinedGroup>();
        for(JoinedGroup jg: myGroup.getListJoined()){
            if(jg.getPosition()==JoinedGroup.IS_REQUEST) listRequest.add(jg);
        }
        setTable();
        
        tblResult.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int column = tblResult.getColumnModel().getColumnIndexAtX(e.getX()); // get the coloum of the button
                int row = e.getY() / tblResult.getRowHeight(); // get the row of the button
 
                // *Checking the row or column is valid or not
                if (row < tblResult.getRowCount() && row >= 0 && column < tblResult.getColumnCount() && column >= 0) {
                    Object[] options = {"Accept", "Refuse", "Cancel"};
                    int result = JOptionPane.showOptionDialog(null,
                        "This person want to join group!", "Request to join group",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
                    if(result == JOptionPane.YES_OPTION){
                        int k = -1;
                        for(int j=0; j<myGroup.getListJoined().size(); j++){
                            if(myGroup.getListJoined().get(j).getUser().getId() == listRequest.get(row).getUser().getId()){
                                myGroup.getListJoined().get(j).setPosition(JoinedGroup.UPDATE_MEMBER);
                                k = j;
                            }
                        }
                        myControl.sendData(new ObjectWrapper(ObjectWrapper.ADD_MEMBER, myGroup));
                        myControl.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_ADD_MEMBER, this));
                        myGroup.getListJoined().get(k).setPosition(1);
                        listRequest.remove(row);
                        setTable();
                    }else if(result == JOptionPane.NO_OPTION){
                        int k = -1;
                        for(int j=0; j<myGroup.getListJoined().size(); j++){
                            if(myGroup.getListJoined().get(j).getUser().getId() == listRequest.get(row).getUser().getId()){
                                myGroup.getListJoined().get(j).setPosition(JoinedGroup.DELETE_MEMBER);
                                k = j;
                            }
                        }
                        myControl.sendData(new ObjectWrapper(ObjectWrapper.DELETE_MEMBER, myGroup));
                        myControl.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_DELETE_MEMBER, this));
                        myGroup.getListJoined().remove(k);
                        listRequest.remove(row);
                        setTable();
                    } else
                        return;
                }
            }
        });
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
    }
    
    private void setTable(){
        String[] columnNames = {"Name", "Date of birth", "Gender", "Email"};
        String[][] value = new String[listRequest.size()][columnNames.length];
        for(int i=0; i<listRequest.size(); i++){
            value[i][0] = listRequest.get(i).getUser().getName();
            value[i][1] = listRequest.get(i).getUser().getDateOfBirth().toString();
            value[i][2] = listRequest.get(i).getUser().getGender()==true?"Male":"Female";
            value[i][3] = listRequest.get(i).getUser().getEmail();
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblResult = new javax.swing.JTable();
        btnReturn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setText("Request to join group");

        tblResult.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblResult.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Date of birth", "Gender", "Email"
            }
        ));
        jScrollPane1.setViewportView(tblResult);

        btnReturn.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jScrollPane1)
                .addGap(22, 22, 22))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(283, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(280, 280, 280))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(357, 357, 357)
                .addComponent(btnReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
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
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(2, 2, 2))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnActionPerformed
        // TODO add your handling code here:
        new DetailsGroupFrm(myControl, myGroup).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnReturnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReturn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblResult;
    // End of variables declaration//GEN-END:variables
}
