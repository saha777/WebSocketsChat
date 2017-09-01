package client;

import javax.websocket.*;
import java.io.IOException;

@ClientEndpoint
public class ChatClientEndPoint {
    private Session session;
    private ClientForm form;

    public ChatClientEndPoint(ClientForm form) {
        this.form = form;
    }

    @OnOpen
    public void open(Session session){
        this.session = session;
        form.onConnect();
    }

    @OnClose
    public void close(CloseReason reason){
        System.out.println(reason);
        form.onClose();
    }

    @OnError
    public void error(Throwable t){
        System.out.println(t.toString());
        form.onClose();
    }

    @OnMessage
    public void receiveMessage(String message, Session session){
        System.out.println(message);
        form.receiveMessage(message);
    }

    public void sendMessage(String message){
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}