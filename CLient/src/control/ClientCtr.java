/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.Game;
import model.IPAddress;
import model.ObjectWrapper;
import model.User;
import view.AddMemberFrm;
import view.ClientMainFrm;
import view.DetailsFrm;
import view.DetailsGroupFrm;
import view.EditGroupFrm;
import view.EditInformationFrm;
import view.GameMarioFrm;
import view.GroupFrm;
import view.ListFriendFrm;
import view.LoginFrm;
import view.RankFrm;
import view.RegisterFrm;
import view.RequestFriendFrm;
import view.JoinGroupFrm;
import view.RequestGroupFrm;
import view.SearchUserFrm;
import view.PlayGameFrm;

/**
 *
 * @author hoang
 */
public class ClientCtr {
    private Socket mySocket;
    private ClientMainFrm view;
    private ClientListening myListening;               // thread to listen the data from the server
    private ArrayList<ObjectWrapper> myFunction;       // list of active client functions
    private IPAddress serverAddress = new IPAddress("localhost",8888); // default server host and port
    private ArrayList<User> listOnline;
    private User myUser;
    public static Boolean inMatch;
    
    public ClientCtr(){
        super();
        myFunction = new ArrayList<ObjectWrapper>();
        listOnline = new ArrayList<User>();
        inMatch = false;
    }

    public ClientCtr(ClientMainFrm view) {
        super();
        this.view = view;
        myFunction = new ArrayList<ObjectWrapper>();
        listOnline = new ArrayList<User>();
    }
    
    public ClientCtr(ClientMainFrm view, IPAddress serverAddr) {
        super();
        this.view = view;
        this.serverAddress = serverAddr;
        this.myFunction = new ArrayList<ObjectWrapper>();
        listOnline = new ArrayList<User>();
    }
    
    public boolean openConnection(){        
        try {
            mySocket = new Socket(serverAddress.getHost(), serverAddress.getPort());  
            myListening = new ClientListening();
            myListening.start();
        } catch (Exception e) {
//            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean sendData(Object obj){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(obj);   
             
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean closeConnection(){
         try {
             if(myListening != null)
                 myListening.stop();
             if(mySocket != null) {
                 mySocket.close();
             }
            myFunction.clear();             
         } catch (Exception e) {
             e.printStackTrace();
             return false;
         }
         return true;
    }

    public static void setInMatch(Boolean inMatch) {
        ClientCtr.inMatch = inMatch;
    }
    
    public ArrayList<ObjectWrapper> getActiveFunction() {
        return myFunction;
    }

    public User getMyUser() {
        return myUser;
    }

    public void setMyUser(User myUser) {
        this.myUser = myUser;
    }

    public ArrayList<User> getListOnline() {
        return listOnline;
    }
    
    class ClientListening extends Thread{

        public ClientListening() {
            super();
        }
        
        public void run(){
            try{
                while(true){
                    ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
                    Object obj = ois.readObject();
                    if(obj instanceof ObjectWrapper){
                        ObjectWrapper data = (ObjectWrapper) obj;
                        if(data.getPerformative() == ObjectWrapper.SERVER_CLIENT_ONLINE){
                            listOnline = (ArrayList<User>) data.getData();
                        }
                        else if(data.getPerformative() == ObjectWrapper.SERVER_SEND_CLIENT_CHALLENGE){
                            Game game = (Game) data.getData();
                            if(inMatch){
                                game.setStatus(Game.PLAYING);
                                sendData(new ObjectWrapper(ObjectWrapper.CLIENT_SEND_SERVER_REQUEST_CHALLENGE, game));
                            } else{
                                Object[] options = {"Accept", "Refuse"};
                                int result = JOptionPane.showOptionDialog(null,
                                    game.getPlayer1().getUser().getName()+" wants to challenge you", "Challenge",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                                if(result == JOptionPane.YES_OPTION){
                                    game.setStatus(Game.ACCEPT_CHALLENGE);
                                    sendData(new ObjectWrapper(ObjectWrapper.CLIENT_SEND_SERVER_REQUEST_CHALLENGE, game));
                                    inMatch = true;
                                    new GameMarioFrm(mySocket, game, myUser).setVisible(true);
                                } else{
                                    game.setStatus(Game.REFUSE_CHALLENGE);
                                    sendData(new ObjectWrapper(ObjectWrapper.CLIENT_SEND_SERVER_REQUEST_CHALLENGE, game));
                                }
                            }
                        }
                        else if(data.getPerformative() == ObjectWrapper.SERVER_SEND_CLIENT_REQUEST_CHALLENGE){
                            Game game = (Game) data.getData();
                            if(game.getStatus() == Game.PLAYING){
                                JOptionPane.showMessageDialog(null, game.getPlayer2().getUser().getName()+ " is in another match!");
                            }else if(game.getStatus() == Game.REFUSE_CHALLENGE){
                                JOptionPane.showMessageDialog(null, game.getPlayer2().getUser().getName()+ " refuses to challenge!");
                            } else if(game.getStatus() == Game.ACCEPT_CHALLENGE){
                                new GameMarioFrm(mySocket, game, myUser).setVisible(true);
                            }
                        }
                        else if(data.getPerformative() == ObjectWrapper.SERVER_SEND_CLIENT_JOIN_GAME){
                            Game game = (Game) data.getData();
                            new PlayGameFrm(mySocket, game, myUser).setVisible(true);
                        }
                        else {
                        for(ObjectWrapper fto: myFunction)
                            if(fto.getPerformative() == data.getPerformative()) {
                                switch(data.getPerformative()) {
                                case ObjectWrapper.REPLY_LOGIN_USER:
                                    LoginFrm loginView = (LoginFrm)fto.getData();
                                    loginView.receivedDataProcessing(data); 
                                    break;
                                case ObjectWrapper.REPLY_ADD_USER:
                                    RegisterFrm reg = (RegisterFrm)fto.getData();
                                    reg.receivedDataProcessing(data); 
                                    break;
                                case ObjectWrapper.REPLY_EDIT_USER:
                                    EditInformationFrm edit = (EditInformationFrm)fto.getData();
                                    edit.receivedDataProcessing(data); 
                                    break;
                                case ObjectWrapper.REPLY_SEARCH_USER:
                                    if(fto.getData() instanceof SearchUserFrm){
                                        SearchUserFrm suf = (SearchUserFrm)fto.getData();
                                        suf.receivedDataProcessing(data); 
                                    } else if(fto.getData() instanceof AddMemberFrm){
                                        AddMemberFrm amf = (AddMemberFrm)fto.getData();
                                        amf.receivedDataProcessing(data);
                                    }
                                    break;
                                case ObjectWrapper.REPLY_SEARCH_REQUEST_FRIEND:
                                    RequestFriendFrm rqf = (RequestFriendFrm)fto.getData();
                                    rqf.receivedDataProcessing(data); 
                                    break;
                                case ObjectWrapper.REPLY_CHECK_FRIEND:
                                    DetailsFrm dtf = (DetailsFrm)fto.getData();
                                    dtf.receivedDataProcessing(data); 
                                    break;
                                case ObjectWrapper.REPLY_REQUEST_FRIEND:
                                    dtf = (DetailsFrm)fto.getData();
                                    dtf.receivedDataProcessing(data); 
                                    break;
                                case ObjectWrapper.REPLY_FRIENDS:
                                    ListFriendFrm lff = (ListFriendFrm)fto.getData();
                                    lff.receivedDataProcessing(data); 
                                    break;
                                case ObjectWrapper.REPLY_RANKING:
                                    RankFrm rf = (RankFrm)fto.getData();
                                    rf.receivedDataProcessing(data); 
                                    break;
                                case ObjectWrapper.REPLY_GROUP:
                                    GroupFrm gf = (GroupFrm)fto.getData();
                                    gf.receivedDataProcessing(data); 
                                    break;
                                case ObjectWrapper.REPLY_SEARCH_GROUP:
                                    JoinGroupFrm jgf = (JoinGroupFrm)fto.getData();
                                    jgf.receivedDataProcessing(data); 
                                    break;
                                case ObjectWrapper.REPLY_CREATE_GROUP:
                                    gf = (GroupFrm)fto.getData();
                                    gf.receivedDataProcessing(data); 
                                    break;
                                case ObjectWrapper.REPLY_DELETE_MEMBER:
                                    if(fto.getData() instanceof DetailsGroupFrm){
                                        DetailsGroupFrm dgf = (DetailsGroupFrm)fto.getData();
                                        dgf.receivedDataProcessing(data); 
                                    } else if(fto.getData() instanceof  RequestGroupFrm){
                                        RequestGroupFrm rgf = (RequestGroupFrm)fto.getData();
                                        rgf.receivedDataProcessing(data);
                                    }
                                    break;
                                case ObjectWrapper.REPLY_ADD_MEMBER:
                                    if(fto.getData() instanceof AddMemberFrm){
                                        AddMemberFrm amf = (AddMemberFrm)fto.getData();
                                        amf.receivedDataProcessing(data); 
                                    } else if(fto.getData() instanceof  RequestGroupFrm){
                                        RequestGroupFrm rgf = (RequestGroupFrm)fto.getData();
                                        rgf.receivedDataProcessing(data);
                                    }
                                    break;
                                case ObjectWrapper.REPLY_EDIT_GROUP:
                                    EditGroupFrm egf = (EditGroupFrm)fto.getData();
                                    egf.receivedDataProcessing(data); 
                                    break;
                                }
                                
                            }
                        myFunction.clear();
                        }
                    }
                }
            } catch(Exception e){
//                e.printStackTrace();
            }
        }
        
    }
    
}
