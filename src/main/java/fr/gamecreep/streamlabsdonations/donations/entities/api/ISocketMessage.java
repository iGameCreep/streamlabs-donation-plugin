package fr.gamecreep.streamlabsdonations.donations.entities.api;

import java.util.List;

public interface ISocketMessage<T> {
    String getType();
    String getForAccount();
    List<T> getMessages();
}
