package engine;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.ArrayList;
import java.util.List;

public class ChatManager {
    private  List<Message> chatDataList;

    public ChatManager()
    {
        chatDataList = new ArrayList<>();
    }

    public void addMessage(Message message)
    {
        chatDataList.add(message);
    }

    public List<Message> getChatDataList() {
        return chatDataList;
    }
}
