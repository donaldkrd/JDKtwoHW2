package server;

public interface Repository {
    void saveHistory(String text);
    String loadHistory();
}
