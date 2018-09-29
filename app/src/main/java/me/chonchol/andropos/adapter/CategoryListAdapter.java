package me.chonchol.andropos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import me.chonchol.andropos.R;
import me.chonchol.andropos.model.Category;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CatItemViewHolder> {

    private Context context;
    private List<Category> categories;

    public CategoryListAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public CatItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false);
        return new CatItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CatItemViewHolder holder, int position) {
        final Category category = categories.get(position);
        holder.catName.setText(category.getCatName());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void removeItem(int position) {
        categories.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Category item, int position) {
        categories.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    public class CatItemViewHolder extends RecyclerView.ViewHolder{

        public TextView catName;
        public ImageView thumbnail;
        public RelativeLayout viewForeground, viewBackground;

        public CatItemViewHolder(View itemView) {
            super(itemView);
            catName = itemView.findViewById(R.id.txtCatName);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }
    }
}
