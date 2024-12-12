package ru.shift;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatClient {
    private String username;
    private ChatChannel chatChannel;

    public ChatClient(ChatChannel chatChannel) {
        this.chatChannel = chatChannel;
    }
}
