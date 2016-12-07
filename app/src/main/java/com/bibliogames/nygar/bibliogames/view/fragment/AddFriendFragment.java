package com.bibliogames.nygar.bibliogames.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bibliogames.nygar.bibliogames.R;
import com.bibliogames.nygar.bibliogames.model.ApiResponse;
import com.bibliogames.nygar.bibliogames.model.User;
import com.bibliogames.nygar.bibliogames.services.ApiRestImpl;
import com.bibliogames.nygar.bibliogames.services.serviceinterface.AddFriendServiceInterface;
import com.bibliogames.nygar.bibliogames.services.serviceinterface.GetNoFriendsServiceInterface;
import com.bibliogames.nygar.bibliogames.view.adapter.AddFriendsAdapter;
import com.bibliogames.nygar.bibliogames.view.interfaces.MainActivityInterface;
import com.bibliogames.nygar.bibliogames.view.utils.CustomSharedPreferences;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Clase {@link Fragment} fragment usado para la pantalla de add Friend
 * Esta clase se usa en {@link com.bibliogames.nygar.bibliogames.view.activity.MainActivity}
 */
public class AddFriendFragment extends Fragment implements GetNoFriendsServiceInterface, AddFriendServiceInterface {

    @BindView(R.id.recycleview_friends)
    RecyclerView recyclerView;
    @BindView(R.id.etSearchFriend)
    EditText searchFriend;

    private List<User> users;
    private AddFriendsAdapter adapter;

    private User currentUser;

    private MainActivityInterface mainActivityInterface;

    public AddFriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_add_friend, container, false);
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
        loadnoFriends();
    }

    private void inicializeFragment(){
        users= new ArrayList<>();
        searchFriend.addTextChangedListener(textListener);

        CustomSharedPreferences customSharedPreferences = new CustomSharedPreferences(getContext());
        currentUser = customSharedPreferences.getCurrentUser();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new AddFriendsAdapter(users);
        adapter.setListener(onItemClickListener);
        recyclerView.setAdapter(adapter);
    }

    AddFriendsAdapter.OnItemClickListener  onItemClickListener= sUser -> {
        final User sUseraux=sUser;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string.add_friends_dialog_message)
                .setTitle(R.string.confirm);

        builder.setPositiveButton(R.string.ok, (dialog, id) -> {
            addFriend(sUseraux.getId());
            dialog.dismiss();
        });
        builder.setNegativeButton(R.string.cancel, (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    };

    /**
     * Search interface
     */
    private TextWatcher textListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            s = s.toString().toLowerCase();

            final List<User> filteredList = new ArrayList<>();

            for (int i = 0; i < users.size(); i++) {

                final String text = users.get(i).getName().toLowerCase();
                if (text.contains(s)) {

                    filteredList.add(users.get(i));
                }
            }

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            /**
             * New adapter with the filter list
             * without this new adapter the search never reset
             */
            adapter = new AddFriendsAdapter(filteredList);
            adapter.setListener(onItemClickListener);

            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void loadnoFriends(){
        mainActivityInterface.loadOn();
        new ApiRestImpl(this,getContext()).getUserNoFriends(currentUser.getId());
    }

    private void addFriend(int idFriend){
        mainActivityInterface.loadOn();
        new ApiRestImpl(this,getContext()).postAddFriend(currentUser.getId(),idFriend);
    }

    /**
     * onClickListener
     */
    @OnClick(R.id.button_toolbar_back)
    public void backOnClickListener(){
        mainActivityInterface.onBack();
    }

    /**
     * Respuestas de la api
     * {@link GetNoFriendsServiceInterface}
     */
    @Override
    public void getNoFriendsOk(List<User> frieUserList) {
        mainActivityInterface.loadOff();
        users=frieUserList;
        adapter.updateAdapter(users);
    }

    @Override
    public void getNoFriendsFail() {
        mainActivityInterface.loadOff();
    }

    /**
     * Respuestas de la api
     * {@link AddFriendServiceInterface}
     */
    @Override
    public void addFriendOk(ApiResponse response) {
        mainActivityInterface.reloadFriends();
        mainActivityInterface.onBack();
    }

    @Override
    public void addFriendFail() {
        mainActivityInterface.loadOff();
    }


    /*private List<User> datatestList(){
        List<User> testFriends = new ArrayList<>();

        testFriends.add(new User(1,"Prueba User1591"));
        testFriends.add(new User(2,"Prueba User2612"));
        testFriends.add(new User(3,"Prueba User3723"));
        testFriends.add(new User(4,"Prueba User4834"));
        testFriends.add(new User(5,"Prueba User1595"));
        testFriends.add(new User(6,"Prueba User2616"));
        testFriends.add(new User(7,"Prueba User3727"));
        testFriends.add(new User(8,"Prueba User4838"));
        testFriends.add(new User(9,"Prueba User1599"));
        testFriends.add(new User(10,"Prueba User26110"));
        testFriends.add(new User(11,"Prueba User37211"));
        testFriends.add(new User(12,"Prueba User48312"));

        return testFriends;
    }*/
}
