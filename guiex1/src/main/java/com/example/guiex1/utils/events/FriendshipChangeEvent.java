package com.example.guiex1.utils.events;

import com.example.guiex1.domain.Utilizator;

public class FriendshipChangeEvent implements Event {
    private ChangeEventType type;


    public FriendshipChangeEvent(ChangeEventType type) {
        this.type = type;

    }


    public ChangeEventType getType() {
        return type;
    }

}
