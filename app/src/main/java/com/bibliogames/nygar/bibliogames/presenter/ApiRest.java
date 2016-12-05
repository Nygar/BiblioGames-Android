package com.bibliogames.nygar.bibliogames.presenter;


import com.bibliogames.nygar.bibliogames.model.ApiResponse;
import com.bibliogames.nygar.bibliogames.model.Games;
import com.bibliogames.nygar.bibliogames.model.GraphicEntry;
import com.bibliogames.nygar.bibliogames.model.User;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiRest {
    //LOGIN
    @FormUrlEncoded
    @POST("Login.php")
    Observable<User> postLogin(@Field("nick") String nick, @Field("password") String password);

    //REGISTRO
    @FormUrlEncoded
    @POST("Register.php")
    Observable<ApiResponse> postRegister(@Field("nick") String nick, @Field("password") String password, @Field("name") String name);

    //UPDATEUSER
    @FormUrlEncoded
    @POST("UpdateUser.php")
    Observable<ApiResponse> postUpdateUser(@Field("user_id") int id,@Field("nick") String nick, @Field("password") String password, @Field("name") String name, @Field("avatar") String avatar);

    //USERGAMES
    @GET("GetGames.php")
    Observable<List<Games>> getUserGames(@Query("id_user") int id);

    //ADDGAME
    @FormUrlEncoded
    @POST("InsertGame.php")
    Observable<ApiResponse> postAddGame(@Field("iduser") int id,@Field("namegame") String title, @Field("buyprice") float price, @Field("datebuy") String dateBuy,@Field("consoleid") int consoleId,@Field("cover") String cover);

    //UPDATEGAME
    @FormUrlEncoded
    @POST("UpdateGame.php")
    Observable<ApiResponse> postUpdateGame(@Field("idgame") int gameId,@Field("namegame") String title, @Field("buyprice") float price, @Field("datebuy") String dateBuy,@Field("consoleid") int consoleId,@Field("cover") String cover);

    //DELETEGAME
    @FormUrlEncoded
    @POST("DeleteGame.php")
    Observable<ApiResponse> postDeleteGame(@Field("idgame") int id);

    //GAMEDETAILS
    @GET("GetGamesDetails.php")
    Observable<Games> getGameDetails(@Query("game_id") int id);

    //GETFRIENDS
    @GET("GetFriends.php")
    Observable<List<User>> getUserFriends(@Query("user_id") int id);

    //ADDFRIEND
    @FormUrlEncoded
    @POST("AddFriend.php")
    Observable<ApiResponse> postAddFriend(@Field("user_id") int idUser,@Field("friend_id") int idFriend);

    //DELETEFRIEND
    @FormUrlEncoded
    @POST("DeleteFriend.php")
    Observable<ApiResponse> postDeleteFriend(@Field("user_id") int idUser,@Field("friend_id") int idFriend);

    //GETUSERSNOFRIENDS
    @GET("GetNoFriends.php")
    Observable<List<User>> getUserNoFriends(@Query("user_id") int id);

    //GETUSERSGRAPHICS
    @GET("GetGraphics.php")
    Observable<List<GraphicEntry>> getUserGraphics(@Query("user_id") int id);

}
