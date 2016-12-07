package com.bibliogames.nygar.bibliogames.services.serviceinterface;


import com.bibliogames.nygar.bibliogames.model.ApiResponse;

public interface UpdateUserServiceInterface extends BaseServiceInterface {
    void updateUserOk(ApiResponse response);
    void updateUserFail();
}
