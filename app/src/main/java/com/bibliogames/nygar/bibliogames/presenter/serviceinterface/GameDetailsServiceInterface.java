package com.bibliogames.nygar.bibliogames.presenter.serviceinterface;


import com.bibliogames.nygar.bibliogames.model.Games;

public interface GameDetailsServiceInterface extends BaseServiceInterface {
    void gameDetailsOk(Games game);
    void gameDetailsFail();
}
