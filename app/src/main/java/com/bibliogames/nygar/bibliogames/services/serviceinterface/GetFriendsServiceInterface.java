package com.bibliogames.nygar.bibliogames.services.serviceinterface;


import com.bibliogames.nygar.bibliogames.model.User;

import java.util.List;

public interface GetFriendsServiceInterface extends BaseServiceInterface {
    void getFriendsOk(List<User> frieUserList);
    void getFriendsFail();
}
