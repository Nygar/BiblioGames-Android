package com.bibliogames.nygar.bibliogames.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bibliogames.nygar.bibliogames.R;
import com.bibliogames.nygar.bibliogames.model.ApiResponse;
import com.bibliogames.nygar.bibliogames.model.User;
import com.bibliogames.nygar.bibliogames.services.ApiRestImpl;
import com.bibliogames.nygar.bibliogames.services.serviceinterface.DeleteFriendServiceInterface;
import com.bibliogames.nygar.bibliogames.view.adapter.FriendsAdapter;
import com.bibliogames.nygar.bibliogames.view.interfaces.MainActivityInterface;
import com.bibliogames.nygar.bibliogames.view.utils.CustomSharedPreferences;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Clase {@link Fragment} fragment usado para la pantalla de mostrar los amigos
 * Esta clase se usa en {@link com.bibliogames.nygar.bibliogames.view.activity.MainActivity}
 */
public class FriendFragment extends Fragment implements DeleteFriendServiceInterface{

    @BindView(R.id.recycleview_friends)
    RecyclerView recyclerView;

    private List<User> users;
    private FriendsAdapter adapter;
    private MainActivityInterface mainActivityInterface;

    public FriendFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(users==null) {
            users = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_friend, container, false);
        ButterKnife.bind(this,view);

        inicializeFragment();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivityInterface = (MainActivityInterface)context;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void inicializeFragment(){

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new FriendsAdapter(users);
        adapter.setListener(onItemClickListener);
        recyclerView.setAdapter(adapter);
    }

    FriendsAdapter.OnItemClickListener onItemClickListener = new FriendsAdapter.OnItemClickListener() {
        @Override
        public void onDelete(User sUser) {
            deleteFriend(sUser);
        }

        @Override
        public void onItemClick(User sUser) {
            mainActivityInterface.showFriendGames(sUser);
        }
    };

    public void updateFragment(List<User> nListFriends){
        users=nListFriends;
        if(adapter!=null) {
            adapter.updateAdapter(users);
        }
    }

    private void deleteFriend(User friend){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string.delete_friend_dialog_message)
                .setTitle(R.string.confirm);

        builder.setPositiveButton(R.string.ok, (dialog, id) -> {
            CustomSharedPreferences preferences = new CustomSharedPreferences(getContext());
            User user = preferences.getCurrentUser();
            mainActivityInterface.loadOn();
            new ApiRestImpl(this, getContext()).postDeleteFriend(user.getId(),friend.getId());

            dialog.dismiss();
        });
        builder.setNegativeButton(R.string.cancel, (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * onClickListener
     */
    @OnClick(R.id.button_friends_add)
    public void addOnClickListener(){
        mainActivityInterface.addNewFriend();
    }

    /**
     * Respuestas de la api
     * {@link DeleteFriendServiceInterface}
     */
    @Override
    public void deleteFriendOk(ApiResponse response) {
        if (response.getStatus() == 200) {
            mainActivityInterface.reloadFriends();
        }
    }
    @Override
    public void deleteFriendFail() {
        mainActivityInterface.loadOff();
    }

    /*private List<User> datatestList(){
        List<User> testFriends = new ArrayList<>();

        testFriends.add(new User(1,"Prueba User1"));
        testFriends.add(new User(2,"Prueba User2"));
        testFriends.add(new User(3,"Prueba User3"));
        testFriends.add(new User(4,"Prueba User4"));
        testFriends.add(new User(5,"Prueba User5"));
        testFriends.add(new User(6,"Prueba User6"));
        testFriends.add(new User(7,"Prueba User7"));
        testFriends.add(new User(18,"Prueba User8"));

        return testFriends;
    }*/

}
