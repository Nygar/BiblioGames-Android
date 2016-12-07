package com.bibliogames.nygar.bibliogames.presenter.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bibliogames.nygar.bibliogames.R;
import com.bibliogames.nygar.bibliogames.model.Games;
import com.bibliogames.nygar.bibliogames.services.ApiRestImpl;
import com.bibliogames.nygar.bibliogames.services.serviceinterface.UserGamesServiceInterface;
import com.bibliogames.nygar.bibliogames.presenter.adapter.DetailsFriendsAdapter;
import com.bibliogames.nygar.bibliogames.presenter.interfaces.MainActivityInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFriendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFriendFragment extends Fragment implements UserGamesServiceInterface{

    @BindView(R.id.recycleview_details_friends)
    RecyclerView recyclerView;

    private static final String ID_FRIEND = "IDFRIEND";

    private int backupIdFriend;

    private DetailsFriendsAdapter adapter;

    private MainActivityInterface mainActivityInterface;

    public DetailsFriendFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param idFriend idFriend.
     * @return A new instance of fragment DetailsFriendFragment.
     */
    public static DetailsFriendFragment newInstance(int idFriend) {
        DetailsFriendFragment fragment = new DetailsFriendFragment();
        Bundle args = new Bundle();
        args.putInt(ID_FRIEND, idFriend);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            backupIdFriend = getArguments().getInt(ID_FRIEND,0);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivityInterface = (MainActivityInterface)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_details_friend, container, false);
        ButterKnife.bind(this,view);
        inicializeRecycle();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mainActivityInterface.loadOn();
        new ApiRestImpl(this,getContext()).getUserGames(backupIdFriend);
    }

    private void inicializeRecycle(){
        //games = datatestList();
        List<Games> games=new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new DetailsFriendsAdapter(games);
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.button_toolbar_back)
    public void onBack(){
        mainActivityInterface.onBack();
    }

    @Override
    public void userGamesOk(List<Games> userListGames) {
        mainActivityInterface.loadOff();
        adapter.updateAdapter(userListGames);
    }

    @Override
    public void userGamesFail() {
        mainActivityInterface.loadOff();
    }

     /*private List<Games> datatestList(){
        List<Games> testGames = new ArrayList<>();

        testGames.add(new Games(01,"prueba1",60,new Console(01,"Consola de Pruebas1")));
        testGames.add(new Games(02,"prueba2",60,new Console(01,"Consola de Pruebas2")));
        testGames.add(new Games(03,"prueba3",60,new Console(02,"Consola de Pruebas3")));
        testGames.add(new Games(04,"prueba4",60,new Console(02,"Consola de Pruebas4")));

        return testGames;
    }*/
}
