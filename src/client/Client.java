package client;

import server.Server;
import server.ServerWindow;

public class Client {
    private String name;
    private ClientView clientView;
    private Server server;
    private boolean connected;

    public Client(ClientView clientView, Server server) {
        this.clientView = clientView;
        this.server = server;
    }

    public boolean connectToServer(String name){
        this.name = name;
        if (server.connectUser(this)){
            clientView.setNameChat(getName());
            printText("You connected to server!\n");
            connected = true;
            String log = server.getHistory();
            if (log != null){
                printText(log);
            }
            return true;
        } else {
            printText("Not connected to server...");
            return false;
        }
    }

    //мы посылаем
    public void sendMessage(String message){
        if (connected) {
            if (!message.isEmpty()) {
                server.message(name + ": " + message);
            }
        } else {
            printText("Not connect to server...");
        }
    }
    //нам посылают
    public void serverAnswer(String answer){
        printText(answer);
    }

    public void disconnect(){
        if (connected) {
            connected = false;
            clientView.disconnectFromServer();
            server.disconnectUser(this);
            printText("You are disconnect to server!");
        }
    }

    public String getName() {
        return name;
    }

    private void printText(String text){
        clientView.showMessage(text);
    }
}
