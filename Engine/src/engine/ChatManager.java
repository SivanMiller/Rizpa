package engine;

import java.util.List;

public class ChatManager {
    private  List<Message> chatDataList;

    public void addMessage(Message message)
    {
        chatDataList.add(message);
    }

    public List<Message> getChatDataList() {
        return chatDataList;
    }
}
