package com.bibliogames.nygar.bibliogames.presenter.serviceinterface;


import com.bibliogames.nygar.bibliogames.model.ApiResponse;

public interface DeleteFriendServiceInterface extends BaseServiceInterface {
    void deleteFriendOk(ApiResponse response);
    void deleteFriendFail();
}
