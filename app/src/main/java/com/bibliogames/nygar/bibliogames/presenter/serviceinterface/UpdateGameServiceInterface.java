package com.bibliogames.nygar.bibliogames.presenter.serviceinterface;


import com.bibliogames.nygar.bibliogames.model.ApiResponse;

public interface UpdateGameServiceInterface extends BaseServiceInterface {
    void updateGameOk(ApiResponse response);
    void updateGameFail();
}
