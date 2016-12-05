package com.bibliogames.nygar.bibliogames.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bibliogames.nygar.bibliogames.R;
import com.bibliogames.nygar.bibliogames.model.User;
import com.daimajia.swipe.SwipeLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter for class {@link com.bibliogames.nygar.bibliogames.view.fragment.LibraryFragment}
 */

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder>{

    private List<User> userList;

    /**
     * Interface
     */
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onDelete(User sUser);
        void onItemClick(User sUser);
    }

    public FriendsAdapter(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_friends, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final User selectedUser = userList.get(position);

        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, holder.buttonWrapperLeft);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right,holder.buttonWrapperRight);

        holder.tvName.setText(selectedUser.getName());

        Picasso.with(holder.avatar.getContext()).load(holder.urlImages+"avatar/"+selectedUser.getId()+".JPG")
                .placeholder(R.drawable.ic_defaultuser).fit().into(holder.avatar);

        //OnClickListeners
        holder.deleteButton.setOnClickListener(v -> onItemClickListener.onDelete(selectedUser));

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

        @BindView(R.id.swipe_layout_friends)
        SwipeLayout swipeLayout;
        @BindView(R.id.bottom_wrapper_left)
        FrameLayout buttonWrapperLeft;
        @BindView(R.id.bottom_wrapper_right)
        FrameLayout buttonWrapperRight;
        @BindView(R.id.item_friends_linear_layout)
        LinearLayout it;
        @BindView(R.id.iv_friends_avatar)
        ImageView avatar;
        @BindView(R.id.iv_friends_delete)
        ImageButton deleteButton;
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
