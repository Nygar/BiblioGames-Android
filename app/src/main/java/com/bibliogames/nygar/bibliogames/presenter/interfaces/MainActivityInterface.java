package com.bibliogames.nygar.bibliogames.presenter.interfaces;


import com.bibliogames.nygar.bibliogames.model.Games;
import com.bibliogames.nygar.bibliogames.model.User;

/**
 * Interfaz para comunicar los {@link android.support.v4.app.Fragment}
 * con {@link com.bibliogames.nygar.bibliogames.presenter.activity.MainActivity}
 */

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
