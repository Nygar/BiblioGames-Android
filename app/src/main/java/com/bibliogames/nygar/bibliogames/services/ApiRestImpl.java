package com.bibliogames.nygar.bibliogames.services;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.bibliogames.nygar.bibliogames.model.ApiResponse;
import com.bibliogames.nygar.bibliogames.model.Games;
import com.bibliogames.nygar.bibliogames.model.GraphicEntry;
import com.bibliogames.nygar.bibliogames.model.User;
import com.bibliogames.nygar.bibliogames.services.serviceinterface.AddFriendServiceInterface;
import com.bibliogames.nygar.bibliogames.services.serviceinterface.AddGameServiceInterface;
import com.bibliogames.nygar.bibliogames.services.serviceinterface.BaseServiceInterface;
import com.bibliogames.nygar.bibliogames.services.serviceinterface.DeleteFriendServiceInterface;
import com.bibliogames.nygar.bibliogames.services.serviceinterface.DeleteGameServiceInterface;
import com.bibliogames.nygar.bibliogames.services.serviceinterface.GameDetailsServiceInterface;
import com.bibliogames.nygar.bibliogames.services.serviceinterface.GetFriendsServiceInterface;
import com.bibliogames.nygar.bibliogames.services.serviceinterface.GetNoFriendsServiceInterface;
import com.bibliogames.nygar.bibliogames.services.serviceinterface.GraphicServiceInterface;
import com.bibliogames.nygar.bibliogames.services.serviceinterface.LoginServiceInterface;
import com.bibliogames.nygar.bibliogames.services.serviceinterface.RegisterServiceInterface;
import com.bibliogames.nygar.bibliogames.services.serviceinterface.UpdateGameServiceInterface;
import com.bibliogames.nygar.bibliogames.services.serviceinterface.UpdateUserServiceInterface;
import com.bibliogames.nygar.bibliogames.services.serviceinterface.UserGamesServiceInterface;
import com.github.aurae.retrofit2.LoganSquareConverterFactory;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.bibliogames.nygar.bibliogames.services.ApiRest.API_BASE_URL;

/**
 * Aqui se encuentra la implementacion de {@link ApiRest} a los servicios
 */

public class ApiRestImpl {

    private final Context context;
    private final BaseServiceInterface listener;
    private ApiRest retrofitAPI;

    /**
     * Constructor of the class
     */
    public ApiRestImpl(BaseServiceInterface listener,Context context) {
        if (context == null) {
            throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
        }
        this.context = context;
        this.listener=listener;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(LoganSquareConverterFactory.create())
                .build();
        retrofitAPI = retrofit.create(ApiRest.class);
    }

    /**
     * Checks if the device has any active internet connection.
     *
     * @return true device with internet connection, otherwise false.
     */
    private boolean isThereInternetConnection() {
        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }

    /**
     * METHODS
     */
    //LOGIN
    public void postLogin(String nick, String pass){
        final LoginServiceInterface serviceInterface = (LoginServiceInterface) listener;
        if (isThereInternetConnection()) {
            try {
                Observable<User> call = retrofitAPI.postLogin(nick,pass);

                call.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<User>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                serviceInterface.loginFail();
                            }

                            @Override
                            public void onNext(User user) {
                                serviceInterface.loginOk(user);
                                onCompleted();
                            }
                        });

            } catch (Exception e) {
                Log.d("Response", "error" + e.getMessage());
                serviceInterface.loginFail();
            }
        } else {
            serviceInterface.loginFail();
        }
    }

    //REGISTER
    public void postRegister(String nick, String pass,String name){
        final RegisterServiceInterface serviceInterface = (RegisterServiceInterface) listener;
        if (isThereInternetConnection()) {
            try {
                Observable<ApiResponse> call = retrofitAPI.postRegister(nick,pass,name);

                call.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ApiResponse>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                serviceInterface.registerFail();
                            }

                            @Override
                            public void onNext(ApiResponse s) {
                                serviceInterface.registerOk(s);
                                onCompleted();
                            }
                        });
            } catch (Exception e) {
                Log.d("Response", "error" + e.getMessage());
                serviceInterface.registerFail();
            }
        } else {
            serviceInterface.registerFail();
        }
    }

    //UPDATEUSER
    public void postUpdateUser(int id,String nick, String pass,String name,String avatar){
        final UpdateUserServiceInterface serviceInterface = (UpdateUserServiceInterface) listener;
        if (isThereInternetConnection()) {
            try {
                Observable<ApiResponse> call = retrofitAPI.postUpdateUser(id,nick,pass,name,avatar);
                call.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ApiResponse>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                serviceInterface.updateUserFail();
                            }

                            @Override
                            public void onNext(ApiResponse s) {
                                serviceInterface.updateUserOk(s);
                                onCompleted();
                            }
                        });
            } catch (Exception e) {
                Log.d("Response", "error" + e.getMessage());
                serviceInterface.updateUserFail();
            }
        } else {
            serviceInterface.updateUserFail();
        }
    }

    //USERGAMES
    public void getUserGames(int userId){
        final UserGamesServiceInterface serviceInterface = (UserGamesServiceInterface) listener;
        if (isThereInternetConnection()) {
            try {
                Observable<List<Games>> call = retrofitAPI.getUserGames(userId);
                call.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<Games>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                serviceInterface.userGamesFail();
                            }

                            @Override
                            public void onNext(List<Games> games) {
                                serviceInterface.userGamesOk(games);
                                onCompleted();
                            }
                        });
            } catch (Exception e) {
                Log.d("Response", "error" + e.getMessage());
                serviceInterface.userGamesFail();
            }
        } else {
            serviceInterface.userGamesFail();
        }
    }

    //ADDGAME
    public void postAddGame( int id,String title, float price,  String dateBuy,int consoleId,String cover){
        final AddGameServiceInterface serviceInterface = (AddGameServiceInterface) listener;
        if (isThereInternetConnection()) {
            try {
                Observable<ApiResponse> call = retrofitAPI.postAddGame(id, title, price, dateBuy, consoleId, cover);
                call.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ApiResponse>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                serviceInterface.addGameFail();
                            }

                            @Override
                            public void onNext(ApiResponse apiResponse) {
                                serviceInterface.addGameOk(apiResponse);
                                onCompleted();
                            }
                        });
            } catch (Exception e) {
                Log.d("Response", "error" + e.getMessage());
                serviceInterface.addGameFail();
            }
        } else {
            serviceInterface.addGameFail();
        }
    }

    //UPDATEGAME
    public void postUpdateGame( int gameId,String title, float price,  String dateBuy,int consoleId,String cover){
        final UpdateGameServiceInterface serviceInterface = (UpdateGameServiceInterface) listener;
        if (isThereInternetConnection()) {
            try {
                Observable<ApiResponse> call = retrofitAPI.postUpdateGame( gameId,title, price, dateBuy, consoleId,cover);
                call.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ApiResponse>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                serviceInterface.updateGameFail();
                            }

                            @Override
                            public void onNext(ApiResponse apiResponse) {
                                serviceInterface.updateGameOk(apiResponse);
                                onCompleted();
                            }
                        });
            } catch (Exception e) {
                Log.d("Response", "error" + e.getMessage());
                serviceInterface.updateGameFail();
            }
        } else {
            serviceInterface.updateGameFail();
        }
    }

    //DELETEGAME
    public void postDeleteGame( int gameid){
        final DeleteGameServiceInterface serviceInterface = (DeleteGameServiceInterface) listener;
        if (isThereInternetConnection()) {
            try {
                Observable<ApiResponse> call = retrofitAPI.postDeleteGame(gameid);
                call.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ApiResponse>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                serviceInterface.deleteGameFail();
                            }

                            @Override
                            public void onNext(ApiResponse apiResponse) {
                                serviceInterface.deleteGameOk();
                                onCompleted();
                            }
                        });
            } catch (Exception e) {
                Log.d("Response", "error" + e.getMessage());
                serviceInterface.deleteGameFail();
            }
        } else {
            serviceInterface.deleteGameFail();
        }
    }

    //GAMEDETAILS
    public void getGameDetails(int gameId){
        final GameDetailsServiceInterface serviceInterface = (GameDetailsServiceInterface) listener;
        if (isThereInternetConnection()) {
            try {
                Observable<Games> call = retrofitAPI.getGameDetails(gameId);
                call.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Games>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                serviceInterface.gameDetailsFail();
                            }

                            @Override
                            public void onNext(Games games) {
                                serviceInterface.gameDetailsOk(games);
                                onCompleted();
                            }
                        });
            } catch (Exception e) {
                Log.d("Response", "error" + e.getMessage());
                serviceInterface.gameDetailsFail();
            }
        } else {
            serviceInterface.gameDetailsFail();
        }
    }

    //USERFRIENDS
    public void getUserFriends(int userId){
        final GetFriendsServiceInterface serviceInterface = (GetFriendsServiceInterface) listener;
        if (isThereInternetConnection()) {
            try {
                Observable<List<User>> call = retrofitAPI.getUserFriends(userId);
                call.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<User>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                serviceInterface.getFriendsFail();
                            }

                            @Override
                            public void onNext(List<User> users) {
                                serviceInterface.getFriendsOk(users);
                                onCompleted();
                            }
                        });
            } catch (Exception e) {
                Log.d("Response", "error" + e.getMessage());
                serviceInterface.getFriendsFail();
            }
        } else {
            serviceInterface.getFriendsFail();
        }
    }

    //ADDFRIEND
    public void postAddFriend( int idUser,int idFriend){
        final AddFriendServiceInterface serviceInterface = (AddFriendServiceInterface) listener;
        if (isThereInternetConnection()) {
            try {
                Observable<ApiResponse> call = retrofitAPI.postAddFriend(idUser, idFriend);
                call.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ApiResponse>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                serviceInterface.addFriendFail();
                            }

                            @Override
                            public void onNext(ApiResponse apiResponse) {
                                serviceInterface.addFriendOk(apiResponse);
                                onCompleted();
                            }
                        });
            } catch (Exception e) {
                Log.d("Response", "error" + e.getMessage());
                serviceInterface.addFriendFail();
            }
        } else {
            serviceInterface.addFriendFail();
        }
    }

    //DELETEFRIEND
    public void postDeleteFriend( int idUser,int idFriend){
        final DeleteFriendServiceInterface serviceInterface = (DeleteFriendServiceInterface) listener;
        if (isThereInternetConnection()) {
            try {
                Observable<ApiResponse> call = retrofitAPI.postDeleteFriend(idUser, idFriend);
                call.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ApiResponse>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                serviceInterface.deleteFriendFail();
                            }

                            @Override
                            public void onNext(ApiResponse apiResponse) {
                                serviceInterface.deleteFriendOk(apiResponse);
                                onCompleted();
                            }
                        });
            } catch (Exception e) {
                Log.d("Response", "error" + e.getMessage());
                serviceInterface.deleteFriendFail();
            }
        } else {
            serviceInterface.deleteFriendFail();
        }
    }

    //USERNOFRIENDS
    public void getUserNoFriends(int userId){
        final GetNoFriendsServiceInterface serviceInterface = (GetNoFriendsServiceInterface) listener;
        if (isThereInternetConnection()) {
            try {
                Observable<List<User>> call = retrofitAPI.getUserNoFriends(userId);
                call.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<User>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                serviceInterface.getNoFriendsFail();
                            }

                            @Override
                            public void onNext(List<User> users) {
                                serviceInterface.getNoFriendsOk(users);
                                onCompleted();
                            }
                        });
            } catch (Exception e) {
                Log.d("Response", "error" + e.getMessage());
                serviceInterface.getNoFriendsFail();
            }
        } else {
            serviceInterface.getNoFriendsFail();
        }
    }

    //GRAPHICS
    public void getGraphics(int userId){
        final GraphicServiceInterface serviceInterface = (GraphicServiceInterface) listener;
        if (isThereInternetConnection()) {
            try {
                Observable<List<GraphicEntry>> call = retrofitAPI.getUserGraphics(userId);
                call.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<GraphicEntry>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                serviceInterface.graphicFail();
                            }

                            @Override
                            public void onNext(List<GraphicEntry> graphics) {
                                serviceInterface.graphicOk(graphics);
                                onCompleted();
                            }
                        });
            } catch (Exception e) {
                Log.d("Response", "error" + e.getMessage());
                serviceInterface.graphicFail();
            }
        } else {
            serviceInterface.graphicFail();
        }
    }

}//EndClass
