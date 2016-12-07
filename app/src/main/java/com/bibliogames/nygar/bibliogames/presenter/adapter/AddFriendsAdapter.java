package com.bibliogames.nygar.bibliogames.presenter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bibliogames.nygar.bibliogames.R;
import com.bibliogames.nygar.bibliogames.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter for class {@link com.bibliogames.nygar.bibliogames.presenter.fragment.LibraryFragment}
 */

public class AddFriendsAdapter extends RecyclerView.Adapter<AddFriendsAdapter.ViewHolder>{

    private List<User> userList;

    /**
     * Interface
     */
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(User sUser);
    }

    public AddFriendsAdapter(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_add_friends, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final User selectedUser = userList.get(position);

        holder.tvName.setText(selectedUser.getName());

        Picasso.with(holder.avatar.getContext()).load(holder.urlImages+"avatar/"+selectedUser.getId()+".JPG")
                .placeholder(R.drawable.ic_defaultuser).fit().into(holder.avatar);

        holder.it.setOnClickListener(v -> onItemClickListener.onItemClick(selectedUser));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void updateAdapter(List<User> updateList){
        //Collections.sort(userList);
        this.userList =updateList;
        this.notifyDataSetChanged();
    }

    /**
     * Set On Click Listener
     * @param onItemClickListener
     */
    public void setListener (OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //VIEWHOLDER
    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_friends_linear_layout)
        LinearLayout it;
        @BindView(R.id.iv_friends_avatar)
        ImageView avatar;
        @BindView(R.id.textView_friends_name)
        TextView tvName;
        @BindString(R.string.base_url_images)
        String urlImages;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
