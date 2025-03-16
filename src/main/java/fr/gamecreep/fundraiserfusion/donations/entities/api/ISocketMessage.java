package fr.gamecreep.fundraiserfusion.donations.entities.api;

import java.util.List;

public interface ISocketMessage<T> {
    String getType();
    String getForAccount();
    List<T> getMessages();
}
