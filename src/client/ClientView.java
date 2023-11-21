package client;

public interface ClientView {
    void showMessage(String text);
    void disconnectFromServer();
    void setNameChat(String name);
}