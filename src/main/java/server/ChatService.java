package server;


import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChatService {
    private Set<ChatWebSocket> webSockets;

    public ChatService() {
        this.webSockets = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }

    public void sendOnMessage(String data){
        for(ChatWebSocket socket : webSockets){
            try{
                socket.sendString(data);
            }catch (Exception e){e.printStackTrace();}
        }
    }

    public void add(ChatWebSocket socket){
        //System.out.println("Hello, initial ChatService");
        webSockets.add(socket);
    }

    public void remove(ChatWebSocket socket){
        webSockets.remove(socket);
    }

}
