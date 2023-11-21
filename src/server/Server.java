package server;

import client.Client;

import java.util.ArrayList;
import java.util.List;

public class Server {
    private boolean isWork;
    private ServerView serverView;
    private List<Client> listClient;
    private Repository repository;

    public Server(ServerView view, Repository repo) {
        this.serverView = view;
        this.repository = repo;
        listClient = new ArrayList<>();
    }
    public void start(){
        if (isWork){
            serverView.sendMessage("Server is running...\n");
        } else {
            isWork = true;
            serverView.sendMessage("Server run!\n");
        }
    }
    public void stop(){
        if (!isWork){
            serverView.sendMessage("Server not run!\n");
        } else {
//            for (Client cl : listClient) { // Не верная реализация, так как
//                disconnectUser(cl); // отключает 2 раза одного и того же клиента
//            }
            for (int i = listClient.size() - 1; i >= 0; i--) {
                disconnectUser(listClient.get(i));
            }
            isWork = false;
            serverView.sendMessage("Server is stopped!\n");
        }
    }
    public void disconnectUser(Client client){
        listClient.remove(client);
        if (client != null){
            client.disconnect();
            serverView.sendMessage(client.getName() + " disconnect from server\n");
        }
    }
    public boolean connectUser(Client client){
        if (isWork) {
            listClient.add(client);
            serverView.sendMessage(client.getName() + " connected to server\n");
            return true;
        }
        return false;
    }
    public void message(String message){
        if (!isWork){
            return;
        }
        serverView.sendMessage(message + "\n");
        repository.saveHistory(message);
        sendAllClient(message);
    }
    private void sendAllClient(String message){
        for (Client cl : listClient) {
            cl.serverAnswer(message);
        }
    }
    public String getHistory() {
        return repository.loadHistory();
    }
}