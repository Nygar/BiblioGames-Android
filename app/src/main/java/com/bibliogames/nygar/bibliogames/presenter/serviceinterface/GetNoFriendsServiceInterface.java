package com.bibliogames.nygar.bibliogames.presenter.serviceinterface;


import com.bibliogames.nygar.bibliogames.model.User;

import java.util.List;

public interface GetNoFriendsServiceInterface extends BaseServiceInterface {
    void getNoFriendsOk(List<User> frieUserList);
    void getNoFriendsFail();
}
