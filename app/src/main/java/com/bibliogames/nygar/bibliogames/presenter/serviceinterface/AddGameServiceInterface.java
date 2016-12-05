package com.bibliogames.nygar.bibliogames.presenter.serviceinterface;


import com.bibliogames.nygar.bibliogames.model.ApiResponse;

public interface AddGameServiceInterface extends BaseServiceInterface{
    void addGameOk(ApiResponse response);
    void addGameFail();
}
