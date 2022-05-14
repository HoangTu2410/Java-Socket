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
import model.Friend;
import model.Game;
import model.ObjectWrapper;
import model.PlayedGame;
import model.User;

/**
 *
 * @author hoang
 */
public class ListFriendFrm extends javax.swing.JPanel {
    private ClientCtr myControl;
    private ArrayList<User> listFriends;
    /**
     * Creates new form ListFriendFrm
     */
    public ListFriendFrm(ClientCtr control) {
        initComponents();
        myControl = control;
        listFriends = new ArrayList<User>();
        
        tblResult.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int column = tblResult.getColumnModel().getColumnIndexAtX(e.getX()); // get the coloum of the button
                int row = e.getY() / tblResult.getRowHeight(); // get the row of the button
 
                // *Checking the row or column is valid or not
                if (row < tblResult.getRowCount() && row >= 0 && column < tblResult.getColumnCount() && column >= 0) {
                    boolean online = false;
                    for(User user: myControl.getListOnline())
                        if(user.getId() == listFriends.get(row).getId()) online = true;
                    if(online){
                        Object[] options = {"Challenge", "Delete", "Cancel"};
                        int result = JOptionPane.showOptionDialog(null,
                            "Friend: "+listFriends.get(row).getName(), "Friend",
                            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
                        User user1 = myControl.getMyUser();
                        User user2 = listFriends.get(row);
                        if(result == JOptionPane.YES_OPTION){
                            PlayedGame player1 = new PlayedGame(); player1.setUser(user1);
                            PlayedGame player2 = new PlayedGame(); player2.setUser(user2);
                            Game game =  new Game(player1, player2, Game.REQUEST_CHALLENGE);
                            myControl.sendData(new ObjectWrapper(ObjectWrapper.CHALLENGE, game));
                            return;
                        }else if(result == JOptionPane.NO_OPTION){
                            Friend friends = new Friend(user1,user2);
                            myControl.sendData(new ObjectWrapper(ObjectWrapper.DELETE_FRIEND, friends));
                        } else
                            return;
                    } else {
                        Object[] options = {"Delete", "Cancel"};
                        int result = JOptionPane.showOptionDialog(null,
                            "Friend: "+listFriends.get(row).getName(), "Friend",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                        if(result == JOptionPane.YES_OPTION){
                            User user1 = myControl.getMyUser();
                            User user2 = listFriends.get(row);
                            Friend friends = new Friend(user1,user2);
                            myControl.sendData(new ObjectWrapper(ObjectWrapper.DELETE_FRIEND, friends));
                        } else
                            return;
                    }
                    listFriends.remove(row);
                    tblResult.removeAll();
                    setTable();
                }
            }
        });
        myControl.sendData(new ObjectWrapper(ObjectWrapper.FRIENDS, myControl.getMyUser()));
        myControl.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_FRIENDS, this));
    }
    
    private void setTable(){
        String[] columnNames = {"Number", "Name", "Date of birth", "Gender", "Status"};
        String[][] value = new String[listFriends.size()][columnNames.length];
        for(int i=0; i<listFriends.size(); i++){
            value[i][0] = i + 1 + "";
            value[i][1] = listFriends.get(i).getName();
            value[i][2] = listFriends.get(i).getDateOfBirth().toString();
            value[i][3] = listFriends.get(i).getGender()==true?"Male":"Female";
            boolean online = false;
            for(User user: myControl.getListOnline())
                if(user.getId() == listFriends.get(i).getId()) online = true;
            if(online) value[i][4] = "Online";
            else value[i][4] = "Offline";
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
            listFriends = (ArrayList<User>) data.getData();
            setTable();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblResult = new javax.swing.JTable();

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setText("List Friend");

        tblResult.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblResult.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Number", "Name", "Date of birth", "Gender", "Status"
            }
        ));
        jScrollPane1.setViewportView(tblResult);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 790, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
            .addGroup(layout.createSequentialGroup()
                .addGap(347, 347, 347)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblResult;
    // End of variables declaration//GEN-END:variables
}
