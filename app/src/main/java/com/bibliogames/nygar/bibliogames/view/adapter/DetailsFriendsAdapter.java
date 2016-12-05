package com.bibliogames.nygar.bibliogames.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bibliogames.nygar.bibliogames.R;
import com.bibliogames.nygar.bibliogames.model.Games;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.vipul.hp_hp.timelineview.TimelineView;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter for class {@link com.bibliogames.nygar.bibliogames.view.fragment.LibraryFragment}
 */

public class DetailsFriendsAdapter extends RecyclerView.Adapter<DetailsFriendsAdapter.ViewHolder>{

    private List<Games> gamesList;

    public DetailsFriendsAdapter(List<Games> gamesList) {
        this.gamesList = gamesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_details_friends, parent, false);
        return new ViewHolder(view,viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Games selectedGame = gamesList.get(position);

        holder.tvTittle.setText(selectedGame.getTitle());
        holder.tvPrice.setText(selectedGame.getPrice() + " €");
        holder.tvConsole.setText(selectedGame.getConsole().getName());
        Picasso.with(holder.avatar.getContext()).load(holder.urlImages+"coverGames/"+selectedGame.getId()+".JPG")
                .placeholder(R.drawable.default_game).fit().into(holder.avatar);

    }

    @Override
    public int getItemCount() {
        return gamesList.size();
    }

    public void updateAdapter(List<Games> gamesList){
        //Collections.sort(gamesList);
        this.gamesList=gamesList;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }

    //VIEWHOLDER
    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_library_avatar)
        ImageView avatar;
        @BindView(R.id.textView_library_gameTitle)
        TextView tvTittle;
        @BindView(R.id.textView_library_gameConsole)
        TextView tvConsole;
        @BindView(R.id.textView_library_gameDateBuy)
        TextView tvDateBuy;
        @BindView(R.id.textView_library_gamePrice)
        TextView tvPrice;
        @BindView(R.id.time_marker)
        TimelineView mTimelineView;
        @BindString(R.string.base_url_images)
        String urlImages;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mTimelineView.initLine(viewType);
        }
    }
}
