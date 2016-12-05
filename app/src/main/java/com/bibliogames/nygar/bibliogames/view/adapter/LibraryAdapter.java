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
import com.bibliogames.nygar.bibliogames.model.Games;
import com.daimajia.swipe.SwipeLayout;
import com.squareup.picasso.Picasso;
import com.vipul.hp_hp.timelineview.TimelineView;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter for class {@link com.bibliogames.nygar.bibliogames.view.fragment.LibraryFragment}
 */

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder>{

    private List<Games> gamesList;

    /**
     * Interface
     */
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onDelete(Games sGame);
        void onEdit(Games sGame);
    }

    public LibraryAdapter( List<Games> gamesList) {
        this.gamesList = gamesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_library, parent, false);
        return new ViewHolder(view,viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Games selectedGame = gamesList.get(position);

        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, holder.buttonWrapperLeft);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.buttonWrapperRight);

        holder.tvTittle.setText(selectedGame.getTitle());
        holder.tvPrice.setText(selectedGame.getPrice() + " €");
        holder.tvDateBuy.setText(selectedGame.getDateBuy());
        if(selectedGame.getConsole()!=null) {
            holder.tvConsole.setText(selectedGame.getConsole().getName());
        }else{
            holder.tvConsole.setText(holder.noConsole);
        }
        Picasso.with(holder.avatar.getContext()).load(holder.urlImages+"coverGames/"+selectedGame.getId()+".JPG")
                .placeholder(R.drawable.default_game).fit().centerInside().into(holder.avatar);

        //OnClickListeners
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onDelete(selectedGame);
            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onEdit(selectedGame);
            }
        });
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

    /**
     * Set On Click Listener
     * @param onItemClickListener
     */
    public void setListener (OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }

    //VIEWHOLDER
    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.swipe_layout_library)
        SwipeLayout swipeLayout;
        @BindView(R.id.bottom_wrapper_left)
        FrameLayout buttonWrapperLeft;
        @BindView(R.id.bottom_wrapper_right)
        FrameLayout buttonWrapperRight;
        @BindView(R.id.item_library_linear_layout)
        LinearLayout it;
        @BindView(R.id.iv_library_avatar)
        ImageView avatar;
        @BindView(R.id.iv_library_delete)
        ImageButton deleteButton;
        @BindView(R.id.iv_library_edit)
        ImageButton editButton;
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
        @BindString(R.string.no_console)
        String noConsole;
        @BindString(R.string.base_url_images)
        String urlImages;


        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mTimelineView.initLine(viewType);
        }
    }
}
