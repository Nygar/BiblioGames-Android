package com.bibliogames.nygar.bibliogames.presenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.bibliogames.nygar.bibliogames.R;
import com.bibliogames.nygar.bibliogames.model.Games;
import com.bibliogames.nygar.bibliogames.model.GraphicEntry;
import com.bibliogames.nygar.bibliogames.model.User;
import com.bibliogames.nygar.bibliogames.services.ApiRestImpl;
import com.bibliogames.nygar.bibliogames.services.serviceinterface.GetFriendsServiceInterface;
import com.bibliogames.nygar.bibliogames.services.serviceinterface.GraphicServiceInterface;
import com.bibliogames.nygar.bibliogames.services.serviceinterface.UserGamesServiceInterface;
import com.bibliogames.nygar.bibliogames.presenter.fragment.AddFriendFragment;
import com.bibliogames.nygar.bibliogames.presenter.fragment.DetailsFriendFragment;
import com.bibliogames.nygar.bibliogames.presenter.fragment.DetailsGamesFragment;
import com.bibliogames.nygar.bibliogames.presenter.fragment.FriendFragment;
import com.bibliogames.nygar.bibliogames.presenter.fragment.GraphicFragment;
import com.bibliogames.nygar.bibliogames.presenter.fragment.LibraryFragment;
import com.bibliogames.nygar.bibliogames.presenter.fragment.ProfileFragment;
import com.bibliogames.nygar.bibliogames.presenter.interfaces.MainActivityInterface;
import com.bibliogames.nygar.bibliogames.presenter.utils.CustomSharedPreferences;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Esta clase contendra el resto de {@link Fragment}, la navegacion entre estos
 * y como deberan actualizarse los datos
 */
public class MainActivity extends BaseActivity implements MainActivityInterface, UserGamesServiceInterface, GetFriendsServiceInterface,GraphicServiceInterface{

    @BindView(R.id.bottomNavigation)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.view_loading)
    LinearLayout loadingview;
    @BindColor(R.color.firstItem)
    int firstColor;
    @BindColor(R.color.secondItem)
    int secondColor;
    @BindColor(R.color.thirdItem)
    int thirdColor;
    @BindColor(R.color.fourtyItem)
    int fourtyColor;

    private CustomSharedPreferences preferences;
    private User user;

    private boolean getGamesStatus;
    private boolean getFriendsStatus;

    private LibraryFragment currentLibrary;
    private FriendFragment currentFriend;
    private GraphicFragment currentGraphic;
    private ProfileFragment currentProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        inicializeActivity();
        preferences = new CustomSharedPreferences(this);
        user= preferences.getCurrentUser();
        if(user==null) {
            user = new User();
        }
    }

    @Override
    protected void onResume() {
        loadingview.setVisibility(View.VISIBLE);
        reloadFriends();
        reloadGames();
        super.onStart();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    private void inicializeActivity(){

        inicializeBottonBar();
        inicializeFragmentContainer();
    }

    private void inicializeFragmentContainer(){
        currentLibrary =new LibraryFragment();
        currentFriend = new FriendFragment();
        currentGraphic = new GraphicFragment();
        currentProfile = new ProfileFragment();
        addFragment(R.id.mainFragmentContainer,currentLibrary);
    }

    @Override
    public void loadOn(){
        loadingview.setVisibility(View.VISIBLE);
    }
    @Override
    public void loadOff(){
        loadingview.setVisibility(View.GONE);
    }

    private void inicializeBottonBar(){
        bottomNavigationView.addTab(new BottomNavigationItem
                ("Lybrary", firstColor, R.drawable.ic_local_library_white_24dp));
        bottomNavigationView.addTab(new BottomNavigationItem
                ("Friends", secondColor, R.drawable.ic_group_white_24dp));
        bottomNavigationView.addTab(new BottomNavigationItem
                ("Graphics", thirdColor, R.drawable.ic_assessment_white_24dp));
        bottomNavigationView.addTab(new BottomNavigationItem
                ("Profile", fourtyColor, R.drawable.ic_account_circle_white_24dp));
        bottomNavigationView.disableShadow();
        bottomNavigationView.selectTab(0);

        bottomNavigationView.setOnBottomNavigationItemClickListener(index -> {
            Log.d("ItemSelected"," "+index);
            switch(index){
                case 0:
                    replaceFragment(R.id.mainFragmentContainer, currentLibrary);
                    break;
                case 1:
                    replaceFragment(R.id.mainFragmentContainer, currentFriend);
                    break;
                case 2:
                    replaceFragment(R.id.mainFragmentContainer, currentGraphic);
                    break;
                case 3:
                    replaceFragment(R.id.mainFragmentContainer, currentProfile);
                    break;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Fragment backEntry=null;
        loadOff();

        if(getSupportFragmentManager().getBackStackEntryCount()>=0) {
            backEntry = getSupportFragmentManager().findFragmentById(R.id.mainFragmentContainer);
        }
        //Esto es para que la aplicacion salga cuando sea uno de los fragments principales
        if(backEntry instanceof LibraryFragment||backEntry instanceof FriendFragment||backEntry instanceof GraphicFragment||backEntry instanceof ProfileFragment) {
            this.finish();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public void addNewGame() {
        replaceFragmentBackStack(R.id.mainFragmentContainer, DetailsGamesFragment.newInstance(null));
    }

    @Override
    public void updateGame(Games updateGame) {
        replaceFragmentBackStack(R.id.mainFragmentContainer, DetailsGamesFragment.newInstance(updateGame));
    }

    @Override
    public void addNewFriend() {
        replaceFragmentBackStack(R.id.mainFragmentContainer, new AddFriendFragment());
    }

    @Override
    public void showFriendGames(User sUser) {
        replaceFragmentBackStack(R.id.mainFragmentContainer, DetailsFriendFragment.newInstance(sUser.getId()));
    }

    @Override
    public void onBack() {
        super.onBackPressed();
    }

    @Override
    public void closeSession() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        preferences.clearUser();
        finish();
    }

    @Override
    public void reloadGames() {
        getGamesStatus=false;
        new ApiRestImpl(this,this).getUserGames(user.getId());
    }

    @Override
    public void reloadFriends() {
        getFriendsStatus=false;
        new ApiRestImpl(this,this).getUserFriends(user.getId());
    }

    @Override
    public void getFriendsOk(List<User> frieUserList) {
        getFriendsStatus=true;
        currentFriend.updateFragment(frieUserList);
        endGetApiData();
    }

    @Override
    public void getFriendsFail() {
        loadingview.setVisibility(View.GONE);
    }

    @Override
    public void userGamesOk(List<Games> userListGames) {
        getGamesStatus=true;
        currentLibrary.updateFragment(userListGames);
        new ApiRestImpl(this, this).getGraphics(user.getId());
        endGetApiData();
    }

    @Override
    public void userGamesFail() {
        loadingview.setVisibility(View.GONE);

    }

    private void endGetApiData(){
        if(getFriendsStatus&&getGamesStatus){
            loadingview.setVisibility(View.GONE);
        }
    }

    @Override
    public void graphicOk(List<GraphicEntry> graphicEntries) {
        currentGraphic.updateGraphic(graphicEntries);
    }

    @Override
    public void graphicFail() {

    }
}
