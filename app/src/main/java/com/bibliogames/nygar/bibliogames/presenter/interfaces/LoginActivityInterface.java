package com.bibliogames.nygar.bibliogames.presenter.interfaces;


import com.bibliogames.nygar.bibliogames.model.User;

/**
 * Interfaz para comunicar los {@link android.support.v4.app.Fragment}
 * con {@link com.bibliogames.nygar.bibliogames.presenter.activity.LoginActivity}
 */

public interface LoginActivityInterface {
    void loginFragmentOk(User user);
    void loginBad();
    void goToRegisterUser();
    void backToLogin();
    void loadOn();
    void loadOff();

}
