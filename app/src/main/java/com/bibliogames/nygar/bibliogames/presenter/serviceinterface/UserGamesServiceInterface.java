package com.bibliogames.nygar.bibliogames.presenter.serviceinterface;


import com.bibliogames.nygar.bibliogames.model.Games;

import java.util.List;

public interface UserGamesServiceInterface extends BaseServiceInterface {
    void userGamesOk(List<Games> userListGames);
    void userGamesFail();
}
