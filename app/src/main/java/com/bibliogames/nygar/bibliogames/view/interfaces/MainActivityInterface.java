package com.bibliogames.nygar.bibliogames.view.interfaces;


import com.bibliogames.nygar.bibliogames.model.Games;
import com.bibliogames.nygar.bibliogames.model.User;

import java.util.List;

public interface MainActivityInterface {
    void addNewGame();
    void updateGame(Games updateGame);
    void addNewFriend();
    void showFriendGames(User sUser);
    void onBack();
    void closeSession();
    void reloadGames();
    void reloadFriends();
    void loadOn();
    void loadOff();
}
