package engine;

public class Message {
    private String chatString;
    private String username;
    private long time;

    public Message(String chatString, String username) {
        this.chatString = chatString;
        this.username = username;
        this.time = System.currentTimeMillis();;
    }
}
