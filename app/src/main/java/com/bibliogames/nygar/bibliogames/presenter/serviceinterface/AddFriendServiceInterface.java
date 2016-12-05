package com.bibliogames.nygar.bibliogames.presenter.serviceinterface;


import com.bibliogames.nygar.bibliogames.model.ApiResponse;

public interface AddFriendServiceInterface extends BaseServiceInterface {
    void addFriendOk(ApiResponse response);
    void addFriendFail();
}
