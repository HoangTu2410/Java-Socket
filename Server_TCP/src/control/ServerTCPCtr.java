/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import model.Friend;
import model.Game;
import model.Group;
import model.IPAddress;
import model.ObjectWrapper;
import model.User;
import model.Character;
import model.UserStat;
import view.ServerMainFrm;

/**
 *
 * @author hoang
 */
public class ServerTCPCtr {
    private ServerMainFrm view;
    private ServerSocket myServer;
    private ServerListening myListening;
    private ArrayList<ServerProcessing> myProcess;
    private ArrayList<User> listUser;
    private ArrayList<Game> listGame;
    private IPAddress myAddress = new IPAddress("localhost",8888);  //default server host and port
    private IPAddress serverAddress = new IPAddress("192.168.0.106", 5555); //default server UDP address
    private DatagramSocket myClient;
    
    public ServerTCPCtr(ServerMainFrm view){
        listUser = new ArrayList<User>();
        listGame = new ArrayList<Game>();
        myProcess = new ArrayList<ServerProcessing>();
        this.view = view;
        openServer();       
    }
     
    public ServerTCPCtr(ServerMainFrm view, int serverPort){
        listUser = new ArrayList<User>();
        listGame = new ArrayList<Game>();
        myProcess = new ArrayList<ServerProcessing>();
        this.view = view;
        myAddress.setPort(serverPort);
        openServer();       
    }
    
    private void openServer(){
        try {
            myServer = new ServerSocket(myAddress.getPort());
            myClient = new DatagramSocket(myAddress.getPort());
            myListening = new ServerListening();
            myListening.start();
            myAddress.setHost(InetAddress.getLocalHost().getHostAddress());
            view.showServerInfor(myAddress, serverAddress);
            view.showMessage("Server is running at the port " + myAddress.getPort() +"...");
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
     
    public void stopServer() {
        try {
            for(ServerProcessing sp:myProcess)
                sp.stop();
            myListening.stop();
            myServer.close();
            view.showMessage("Server is stopped!");
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
     
    public void publicClientOnline() {
        for(ServerProcessing sp : myProcess) {
            ObjectWrapper data = new ObjectWrapper(ObjectWrapper.SERVER_CLIENT_ONLINE, listUser);
            sp.sendData(data);
        }
    }
    
    private boolean sendDataServer(ObjectWrapper data){
        try {
            //prepare the buffer and write the data to send into the buffer
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(data);
            oos.flush();            
             
            //create data package and send
            byte[] sendData = baos.toByteArray();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(serverAddress.getHost()), serverAddress.getPort());
            myClient.send(sendPacket);
             
        } catch (Exception e) {
            e.printStackTrace();
            view.showMessage("Error in sending data package");
            return false;
        }
        return true;
    }
    
    public ObjectWrapper receiveDataServer(){
        ObjectWrapper result = null;
        try {   
            //prepare the buffer and fetch the received data into the buffer
            byte[] receiveData = new byte[102400];
            DatagramPacket receivePacket = new  DatagramPacket(receiveData, receiveData.length);
            myClient.receive(receivePacket);
             
            //read incoming data from the buffer 
            ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
            ObjectInputStream ois = new ObjectInputStream(bais);
            result = (ObjectWrapper)ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            view.showMessage("Error in receiving data package");
        }
        return result;
    }
    
    class ServerListening extends Thread{ 
        //The class to listen the connections from client, avoiding the blocking of accept connection
        public ServerListening() {
            super();
        }
         
        public void run() {
            view.showMessage("Server is listening... ");
            try {
                while(true) {
                    Socket clientSocket = myServer.accept();
                    ServerProcessing sp = new ServerProcessing(clientSocket);
                    sp.start();
                    myProcess.add(sp);
                    view.showMessage("Number of client connecting to the server: " + myProcess.size());
                }
            }catch (Exception e) {
//                e.printStackTrace();
            }
        }
    }
    
    class ServerProcessing extends Thread{
        private Socket mySocket;
        private User myUser; 
         
        public ServerProcessing(Socket s) {
            super();
            mySocket = s;
            myUser = new User();
        }

        public User getMyUser() {
            return myUser;
        }
        
        public void sendData(Object obj) {
            try {
                ObjectOutputStream oos= new ObjectOutputStream(mySocket.getOutputStream());
                oos.writeObject(obj);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        
        public void run() { 
            try {
                while(true) {
                    ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
                    ObjectOutputStream oos= new ObjectOutputStream(mySocket.getOutputStream());
                    Object o = ois.readObject();
                    if(o instanceof ObjectWrapper){
                        ObjectWrapper data = (ObjectWrapper)o;
 
                        ObjectWrapper dataReceived;
                        switch(data.getPerformative()) {
                        case ObjectWrapper.LOGIN_USER:
                            sendDataServer(data);
                            dataReceived = receiveDataServer();
                            if(dataReceived.getPerformative() == ObjectWrapper.REPLY_LOGIN_USER){
                                if(dataReceived.getData() instanceof User){
                                    myUser = (User) dataReceived.getData();
                                    listUser.add(myUser);
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LOGIN_USER,myUser));
                                } else
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LOGIN_USER,"false"));
                            }
                            break;
                        case ObjectWrapper.ADD_USER:;
                            sendDataServer(data);
                            dataReceived = receiveDataServer();
                            if(dataReceived.getPerformative() == ObjectWrapper.REPLY_ADD_USER){
                                if(dataReceived.getData().equals("oke")){
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_ADD_USER,"oke"));
                                } else
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_ADD_USER,"false"));
                            }
                            break;
                        case ObjectWrapper.EDIT_USER:
                            sendDataServer(data);
                            dataReceived = receiveDataServer();
                            if(dataReceived.getPerformative() == ObjectWrapper.REPLY_EDIT_USER){
                                if(dataReceived.getData().equals("oke")){
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_EDIT_USER,"oke"));
                                } else
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_EDIT_USER,"false"));
                            } 
                            break;
                        case ObjectWrapper.SEARCH_USER:
                            sendDataServer(data);
                            dataReceived = receiveDataServer();
                            if(dataReceived.getPerformative() == ObjectWrapper.REPLY_SEARCH_USER){
                                if(dataReceived.getData() instanceof ArrayList<?>){
                                    ArrayList<User> result = (ArrayList<User>) dataReceived.getData();
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_SEARCH_USER,result));
                                } else
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_SEARCH_USER,"false"));
                            }
                            break;
                        case ObjectWrapper.FRIENDS:
                            sendDataServer(data);
                            dataReceived = receiveDataServer();
                            if(dataReceived.getPerformative() == ObjectWrapper.REPLY_FRIENDS){
                                if(dataReceived.getData() instanceof ArrayList<?>){
                                    ArrayList<User> result = (ArrayList<User>) dataReceived.getData();
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_FRIENDS,result));
                                } else
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_FRIENDS,"false"));
                            } 
                            break;
                        case ObjectWrapper.RANKING:
                            sendDataServer(data);
                            dataReceived = receiveDataServer();
                            if(dataReceived.getPerformative() == ObjectWrapper.REPLY_RANKING){
                                if(dataReceived.getData() instanceof ArrayList<?>){
                                    ArrayList<UserStat> result = (ArrayList<UserStat>) dataReceived.getData();
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_RANKING,result));
                                } else
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_RANKING,"false"));
                            } 
                            break;
                        case ObjectWrapper.REQUEST_FRIEND:
                            sendDataServer(data);
                            dataReceived = receiveDataServer();
                            if(dataReceived.getPerformative() == ObjectWrapper.REPLY_REQUEST_FRIEND){
                                if(dataReceived.getData().equals("oke")){
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_REQUEST_FRIEND,"oke"));
                                } else
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_REQUEST_FRIEND,"false"));
                            } 
                            break;
                        case ObjectWrapper.SEARCH_REQUEST_FRIEND:
                            sendDataServer(data);
                            dataReceived = receiveDataServer();
                            if(dataReceived.getPerformative() == ObjectWrapper.REPLY_SEARCH_REQUEST_FRIEND){
                                if(dataReceived.getData() instanceof ArrayList<?>){
                                    ArrayList<User> result = (ArrayList<User>) dataReceived.getData();
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_SEARCH_REQUEST_FRIEND,result));
                                } else
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_SEARCH_REQUEST_FRIEND,"false"));
                            }
                            break;
                        case ObjectWrapper.ADD_FRIEND:
                            sendDataServer(data);
                            dataReceived = receiveDataServer();
                            if(dataReceived.getPerformative() == ObjectWrapper.REPLY_ADD_FRIEND){
                                if(dataReceived.getData().equals("oke")){
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_ADD_FRIEND,"oke"));
                                } else
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_ADD_FRIEND,"false"));
                            } 
                            break;
                        case ObjectWrapper.DELETE_FRIEND:
                            sendDataServer(data);
                            dataReceived = receiveDataServer();
                            if(dataReceived.getPerformative() == ObjectWrapper.REPLY_DELETE_FRIEND){
                                if(dataReceived.getData().equals("oke")){
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_DELETE_FRIEND,"oke"));
                                } else
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_DELETE_FRIEND,"false"));
                            } 
                            break;
                        case ObjectWrapper.CHECK_FRIEND:
                            sendDataServer(data);
                            dataReceived = receiveDataServer();
                            if(dataReceived.getPerformative() == ObjectWrapper.REPLY_CHECK_FRIEND){
                                if(dataReceived.getData().equals("oke")){
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CHECK_FRIEND,"oke"));
                                } else
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CHECK_FRIEND,"false"));
                            } 
                            break;
                        case ObjectWrapper.GROUP:
                            sendDataServer(data);
                            dataReceived = receiveDataServer();
                            if(dataReceived.getPerformative() == ObjectWrapper.REPLY_GROUP){
                                if(dataReceived.getData() instanceof ArrayList<?>){
                                    ArrayList<Group> result = (ArrayList<Group>) dataReceived.getData();
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_GROUP,result));
                                } else
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_GROUP,"false"));
                            } 
                            break;
                        case ObjectWrapper.SEARCH_GROUP:
                            sendDataServer(data);
                            dataReceived = receiveDataServer();
                            if(dataReceived.getPerformative() == ObjectWrapper.REPLY_SEARCH_GROUP){
                                if(dataReceived.getData() instanceof ArrayList<?>){
                                    ArrayList<Group> result = (ArrayList<Group>) dataReceived.getData();
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_SEARCH_GROUP,result));
                                } else
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_SEARCH_GROUP,"false"));
                            } 
                            break;
                        case ObjectWrapper.CREATE_GROUP:
                            sendDataServer(data);
                            dataReceived = receiveDataServer();
                            if(dataReceived.getPerformative() == ObjectWrapper.REPLY_CREATE_GROUP){
                                if(dataReceived.getData().equals("oke")){
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CREATE_GROUP,"oke"));
                                } else
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CREATE_GROUP,"false"));
                            } 
                            break;
                        case ObjectWrapper.REQUEST_GROUP:
                            sendDataServer(data);
                            dataReceived = receiveDataServer();
                            if(dataReceived.getPerformative() == ObjectWrapper.REPLY_REQUEST_GROUP){
                                if(dataReceived.getData().equals("oke")){
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_REQUEST_GROUP,"oke"));
                                } else
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_REQUEST_GROUP,"false"));
                            } 
                            break;
                        case ObjectWrapper.DELETE_MEMBER:
                            sendDataServer(data);
                            dataReceived = receiveDataServer();
                            if(dataReceived.getPerformative() == ObjectWrapper.REPLY_DELETE_MEMBER){
                                if(dataReceived.getData().equals("oke")){
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_DELETE_MEMBER,"oke"));
                                } else
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_DELETE_MEMBER,"false"));
                            }
                            break;
                        case ObjectWrapper.ADD_MEMBER:
                            sendDataServer(data);
                            dataReceived = receiveDataServer();
                            if(dataReceived.getPerformative() == ObjectWrapper.REPLY_ADD_MEMBER){
                                if(dataReceived.getData().equals("oke")){
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_ADD_MEMBER,"oke"));
                                } else
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_ADD_MEMBER,"false"));
                            } 
                            break;
                        case ObjectWrapper.EDIT_GROUP:
                            sendDataServer(data);
                            dataReceived = receiveDataServer();
                            if(dataReceived.getPerformative() == ObjectWrapper.REPLY_EDIT_GROUP){
                                if(dataReceived.getData().equals("oke")){
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_EDIT_GROUP,"oke"));
                                } else
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_EDIT_GROUP,"false"));
                            } 
                            break;
                        case ObjectWrapper.CHALLENGE:
                            Game game = (Game) data.getData();
                            for(ServerProcessing sp : myProcess) {
                                if(sp.getMyUser().getId() == game.getPlayer2().getUser().getId()){
                                    ObjectWrapper challenge = new ObjectWrapper(ObjectWrapper.SERVER_SEND_CLIENT_CHALLENGE, game);
                                    sp.sendData(challenge);
                                }
                            }
                            oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CHALLENGE,"oke"));
                            break;
                        case ObjectWrapper.CLIENT_SEND_SERVER_REQUEST_CHALLENGE:
                            game = (Game) data.getData();
                            oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CLIENT_SEND_SERVER_REQUEST_CHALLENGE,"oke"));
                            for(ServerProcessing sp : myProcess) {
                                if(sp.getMyUser().getId() == game.getPlayer1().getUser().getId()){
                                    ObjectWrapper challenge = new ObjectWrapper(ObjectWrapper.SERVER_SEND_CLIENT_REQUEST_CHALLENGE, game);
                                    sp.sendData(challenge);
                                }
                            }
                            if(game.getStatus() == Game.ACCEPT_CHALLENGE){
                                game.setStatus(Game.PLAYING);
                                ObjectWrapper saveGame = new ObjectWrapper(ObjectWrapper.SAVE_GAME, game);
                                sendDataServer(saveGame);
                                dataReceived = receiveDataServer();
                                if(dataReceived.getPerformative() == ObjectWrapper.REPLY_SAVE_GAME){
                                    if(dataReceived.getData() instanceof Game){
                                        Game gameReceived = (Game) dataReceived.getData();
                                        listGame.add(gameReceived);
                                    }
                                } 
                            }
                            break;
                        case ObjectWrapper.SEND_CHARACTER:
                            sendDataServer(data);
                            dataReceived = receiveDataServer();
                            oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_SEND_CHARACTER,"oke"));
                            if(dataReceived.getPerformative() == ObjectWrapper.REPLY_SEND_CHARACTER){
                                if(dataReceived.getData() instanceof Character){
                                    Character character = (Character) dataReceived.getData();
                                    for(Game i: listGame){
                                        if(i.getPlayer1().getUser().getId()== myUser.getId()){
                                            i.getPlayer1().setCharacter(character);
                                        } else if(i.getPlayer2().getUser().getId()== myUser.getId()){
                                            i.getPlayer2().setCharacter(character);
                                        }
                                    }
                                    for(Game i: listGame){
                                        try{
                                            if(i.getPlayer1().getCharacter().getId()!=0 && i.getPlayer2().getCharacter().getId()!=0){
                                                sendDataServer(new ObjectWrapper(ObjectWrapper.SAVE_CHARACTER, i));
                                                dataReceived = receiveDataServer();
                                                if(dataReceived.getPerformative() == ObjectWrapper.REPLY_SAVE_CHARACTER){
                                                    if(dataReceived.getData().equals("oke")){
                                                        System.out.println("Save character successful!");
                                                    } else
                                                        System.out.println("Error when save character!");
                                                } 
                                                for(ServerProcessing sp : myProcess) {
                                                    if(sp.getMyUser().getId()==i.getPlayer1().getUser().getId() || sp.getMyUser().getId()==i.getPlayer2().getUser().getId()){
                                                        ObjectWrapper playGame = new ObjectWrapper(ObjectWrapper.SERVER_SEND_CLIENT_JOIN_GAME, i);
                                                        sp.sendData(playGame);
                                                    }
                                                }
                                            }
                                        } catch(NullPointerException e){
                                            System.out.println("Null");
                                        }
                                    }
                                }
                            }
                            break;
                        } 
                    }
                    publicClientOnline();
                }
            }catch (EOFException | SocketException e) {             
//                e.printStackTrace();
                listUser.remove(myUser);
                myProcess.remove(this);
                view.showMessage("Number of client connecting to the server: " + myProcess.size());
                publicClientOnline();
                try {
                    mySocket.close();
                }catch(Exception ex) {
                    ex.printStackTrace();
                }
                this.stop();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
}
