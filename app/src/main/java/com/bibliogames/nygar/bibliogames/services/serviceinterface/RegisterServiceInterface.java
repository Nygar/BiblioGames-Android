package com.bibliogames.nygar.bibliogames.services.serviceinterface;


import com.bibliogames.nygar.bibliogames.model.ApiResponse;

public interface RegisterServiceInterface extends BaseServiceInterface {
    void registerOk(ApiResponse response);
    void registerFail();
}
