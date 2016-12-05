package com.bibliogames.nygar.bibliogames.presenter.serviceinterface;


import com.bibliogames.nygar.bibliogames.model.User;

public interface LoginServiceInterface extends BaseServiceInterface {
    void loginOk(User userLogged);
    void loginFail();
}
