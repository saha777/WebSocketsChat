package client;

import javax.swing.*;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ClientForm extends JFrame {
    private JPanel contentPane;
    private JTextArea messageBoard;
    private JTextField usernameTextField;
    private JTextArea msgTextArea;
    private JButton sendBtn;

    private ChatClientEndPoint endpoint;

    public ClientForm() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            final String _uri = "ws://localhost:8080/chat";
            container.connectToServer((endpoint = new ChatClientEndPoint(this)), new URI(_uri));
        } catch (DeploymentException | URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        sendBtn.addActionListener(e -> {
            sendMessage(usernameTextField.getText() + ": " + msgTextArea.getText());
            msgTextArea.setText("");
        });

        setContentPane(contentPane);
        setSize(320, 480);
        setResizable(false);
        setVisible(true);
    }

    public void onConnect(){
        messageBoard.append("New Connection\n");
    }

    public void onClose(){
        messageBoard.append("Close Connection\n");
    }

    public void receiveMessage(String msg){
        messageBoard.append(msg + "\n");
    }

    public void sendMessage(String msg){
        endpoint.sendMessage(msg);
    }

    public static void main(String[] args) {
        ClientForm form = new ClientForm();
    }
}
