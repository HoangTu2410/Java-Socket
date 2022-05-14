/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.FriendDAO;
import dao.GameDAO;
import dao.GroupDAO;
import dao.RankDAO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
 
import dao.UserDAO;
import model.Friend;
import model.Game;
import model.Group;
import model.IPAddress;
import model.ObjectWrapper;
import model.User;
import model.Character;
import view.ServerMainFrm;
 
 
public class ServerUDPCtr {
    private ServerMainFrm view;
    private DatagramSocket myServer;    
    private IPAddress myAddress = new IPAddress("localhost", 5555); //default server address
    private UDPListening myListening;
     
    public ServerUDPCtr(ServerMainFrm view){
        this.view = view;       
    }
     
    public ServerUDPCtr(ServerMainFrm view, int port){
        this.view = view;
        myAddress.setPort(port);
    }
     
     
    public boolean open(){
        try {
            myServer = new DatagramSocket(myAddress.getPort());
            myAddress.setHost(InetAddress.getLocalHost().getHostAddress());
            view.showServerInfo(myAddress);
            myListening = new UDPListening();
            myListening.start();
            view.showMessage("UDP server is running at the host: " + myAddress.getHost() + ", port: " + myAddress.getPort());
        }catch(Exception e) {
            e.printStackTrace();
            view.showMessage("Error to open the datagram socket!");
            return false;
        }
        return true;
    }
     
    public boolean close(){
        try {
            myListening.stop();
            myServer.close();
        }catch(Exception e) {
            e.printStackTrace();
            view.showMessage("Error to close the datagram socket!");
            return false;
        }
        return true;
    }
     
    class UDPListening extends Thread{
        public UDPListening() {
             
        }
         
        public void run() {
            while(true) {               
                try {   
                    //prepare the buffer and fetch the received data into the buffer
                    byte[] receiveData = new byte[102400];
                    DatagramPacket receivePacket = new  DatagramPacket(receiveData, receiveData.length);
                    myServer.receive(receivePacket);
                     
                    //read incoming data from the buffer 
                    ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    ObjectWrapper receivedData = (ObjectWrapper)ois.readObject();
                     
                    //processing
                    ObjectWrapper resultData = new ObjectWrapper();
                    switch(receivedData.getPerformative()) {
                    case ObjectWrapper.LOGIN_USER:              // login
                        User user = (User)receivedData.getData();
                        resultData.setPerformative(ObjectWrapper.REPLY_LOGIN_USER);
                        if(new UserDAO().checkLogin(user))
                            resultData.setData(user);
                        else
                            resultData.setData("false");
                        break;
                    case ObjectWrapper.ADD_USER:
                        user = (User)receivedData.getData();
                        resultData.setPerformative(ObjectWrapper.REPLY_ADD_USER);
                        if(new UserDAO().addUser(user))
                            resultData.setData("oke");
                        else
                            resultData.setData("false");
                        break;
                    case ObjectWrapper.SEARCH_USER:
                        String key = (String)receivedData.getData();
                        resultData.setPerformative(ObjectWrapper.REPLY_SEARCH_USER);
                        resultData.setData(new UserDAO().searchUser(key));
                        break;
                    case ObjectWrapper.EDIT_USER: 
                        user = (User)receivedData.getData();
                        resultData.setPerformative(ObjectWrapper.REPLY_EDIT_USER);
                        if(new UserDAO().editUser(user))
                            resultData.setData("oke");
                        else
                            resultData.setData("false");
                        break;
                    case ObjectWrapper.REQUEST_FRIEND:
                        Friend friends = (Friend)receivedData.getData();
                        resultData.setPerformative(ObjectWrapper.REPLY_REQUEST_FRIEND);
                        if(new FriendDAO().requestFriend(friends))
                            resultData.setData("oke");
                        else
                            resultData.setData("false");
                        break;
                    case ObjectWrapper.SEARCH_REQUEST_FRIEND: 
                        user = (User)receivedData.getData();
                        resultData.setPerformative(ObjectWrapper.REPLY_SEARCH_REQUEST_FRIEND);
                        resultData.setData(new FriendDAO().searchRequestFriend(user));
                        break;
                    case ObjectWrapper.FRIENDS:
                        user = (User)receivedData.getData();
                        resultData.setPerformative(ObjectWrapper.REPLY_FRIENDS);
                        resultData.setData(new FriendDAO().searchFriends(user));
                        break;
                    case ObjectWrapper.ADD_FRIEND:
                        friends = (Friend)receivedData.getData();
                        resultData.setPerformative(ObjectWrapper.REPLY_ADD_FRIEND);
                        if(new FriendDAO().addFriend(friends))
                            resultData.setData("oke");
                        else
                            resultData.setData("false");
                        break;
                    case ObjectWrapper.DELETE_FRIEND:
                        friends = (Friend)receivedData.getData();
                        resultData.setPerformative(ObjectWrapper.REPLY_DELETE_FRIEND);
                        if(new FriendDAO().deleteFriend(friends))
                            resultData.setData("oke");
                        else
                            resultData.setData("false");
                        break;
                    case ObjectWrapper.CHECK_FRIEND:
                        friends = (Friend)receivedData.getData();
                        resultData.setPerformative(ObjectWrapper.REPLY_CHECK_FRIEND);
                        if(new FriendDAO().checkFriend(friends))
                            resultData.setData("oke");
                        else
                            resultData.setData("false");
                        break;
                    case ObjectWrapper.RANKING:
                        user = (User)receivedData.getData();
                        resultData.setPerformative(ObjectWrapper.REPLY_RANKING);
                        resultData.setData(new RankDAO().statistics());
                        break;
                    case ObjectWrapper.GROUP:
                        user = (User)receivedData.getData();
                        resultData.setPerformative(ObjectWrapper.REPLY_GROUP);
                        resultData.setData(new GroupDAO().searchGroup(user));
                        break;
                    case ObjectWrapper.SEARCH_GROUP:
                        key = (String)receivedData.getData();
                        resultData.setPerformative(ObjectWrapper.REPLY_SEARCH_GROUP);
                        resultData.setData(new GroupDAO().searchGroup(key));
                        break;
                    case ObjectWrapper.CREATE_GROUP:
                        Group group = (Group)receivedData.getData();
                        resultData.setPerformative(ObjectWrapper.REPLY_CREATE_GROUP);
                        if(new GroupDAO().createGroup(group))
                            resultData.setData("oke");
                        else
                            resultData.setData("false");
                        break;
                    case ObjectWrapper.REQUEST_GROUP:
                        group = (Group)receivedData.getData();
                        resultData.setPerformative(ObjectWrapper.REPLY_REQUEST_GROUP);
                        if(new GroupDAO().requestGroup(group))
                            resultData.setData("oke");
                        else
                            resultData.setData("false");
                        break;
                    case ObjectWrapper.DELETE_MEMBER:
                        group = (Group)receivedData.getData();
                        resultData.setPerformative(ObjectWrapper.REPLY_DELETE_MEMBER);
                        if(new GroupDAO().deleteMember(group))
                            resultData.setData("oke");
                        else
                            resultData.setData("false");
                        break;
                    case ObjectWrapper.ADD_MEMBER:
                        group = (Group)receivedData.getData();
                        resultData.setPerformative(ObjectWrapper.REPLY_ADD_MEMBER);
                        if(new GroupDAO().addMember(group))
                            resultData.setData("oke");
                        else
                            resultData.setData("false");
                        break;
                    case ObjectWrapper.EDIT_GROUP:
                        group = (Group)receivedData.getData();
                        resultData.setPerformative(ObjectWrapper.REPLY_EDIT_GROUP);
                        if(new GroupDAO().editGroup(group))
                            resultData.setData("oke");
                        else
                            resultData.setData("false");
                        break;
                    case ObjectWrapper.SAVE_GAME:
                        Game game = (Game)receivedData.getData();
                        resultData.setPerformative(ObjectWrapper.REPLY_SAVE_GAME);
                        resultData.setData(new GameDAO().saveGame(game));
                        break;
                    case ObjectWrapper.SEND_CHARACTER:
                        Character character = (Character)receivedData.getData();
                        resultData.setPerformative(ObjectWrapper.REPLY_SEND_CHARACTER);
                        resultData.setData(new GameDAO().checkCharacter(character));
                        break;
                    case ObjectWrapper.SAVE_CHARACTER:
                        game = (Game)receivedData.getData();
                        resultData.setPerformative(ObjectWrapper.REPLY_SAVE_CHARACTER);
                        if(new GameDAO().saveCharacter(game))
                            resultData.setData("oke");
                        else
                            resultData.setData("false");
                        break;
                    }
                     
                     
                    //prepare the buffer and write the data to send into the buffer
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(resultData);
                    oos.flush();            
                     
                    //create data package and send
                    byte[] sendData = baos.toByteArray();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
                    myServer.send(sendPacket);
                } catch (Exception e) {
//                    e.printStackTrace();
                    view.showMessage("Error when processing an incoming package");
                    this.stop();
                }    
            }
        }
    }
}