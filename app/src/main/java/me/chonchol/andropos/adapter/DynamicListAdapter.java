package me.chonchol.andropos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import me.chonchol.andropos.R;
import me.chonchol.andropos.interfaces.OnRecyclerViewItemClickListener;
import me.chonchol.andropos.model.Category;
import me.chonchol.andropos.model.Subcategory;

public class DynamicListAdapter extends RecyclerView.Adapter<DynamicListAdapter.ObjectItemViewHolder> {

    private Context context;
    private List<Object> objects;
    private OnRecyclerViewItemClickListener itemClickListener;

    public DynamicListAdapter(Context context, List<Object> objects, OnRecyclerViewItemClickListener itemClickListener) {
        this.context = context;
        this.objects = objects;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ObjectItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false);
        return new ObjectItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ObjectItemViewHolder holder, int position) {
        holder.bind(objects.get(position), itemClickListener);
        final Object object = objects.get(position);

        if (object instanceof Category){
            Category category = (Category) object;
            holder.catName.setText(category.getCatName());
        } else if (object instanceof Subcategory){
            Subcategory subcategory = (Subcategory) object;
            holder.catName.setText(subcategory.getSubcatName());
        }
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public void removeItem(int position) {
        objects.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Object item, int position) {
        objects.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    public class ObjectItemViewHolder extends RecyclerView.ViewHolder{

        public TextView catName;
        public ImageView thumbnail;
        public RelativeLayout viewForeground, viewBackground;

        public ObjectItemViewHolder(View itemView) {
            super(itemView);
            catName = itemView.findViewById(R.id.txtCatName);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }

        public void bind(final Object object, final OnRecyclerViewItemClickListener itemClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onObjectItemClick(object);
                }
            });
        }
    }
}
