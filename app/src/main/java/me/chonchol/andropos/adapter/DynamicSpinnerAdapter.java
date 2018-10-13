package me.chonchol.andropos.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.chonchol.andropos.R;
import me.chonchol.andropos.model.Category;
import me.chonchol.andropos.model.Subcategory;

public class DynamicSpinnerAdapter extends ArrayAdapter<Object> {

    private LayoutInflater layoutInflater;
    private Context context;
    private List<Object> objects;
    private Category category = new Category();
    private Subcategory subcategory = new Subcategory();

    public DynamicSpinnerAdapter(@NonNull Context context, int resource, List<Object> objects) {
        super(context, resource);
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        view = getCustomView(position, convertView, parent);
        return view;

    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.spinner_list_view, parent, false);
        TextView txtSpinnerValue = view.findViewById(R.id.txtCatName);
        txtSpinnerValue.setText(R.string.please_select);

        for (Object object : objects) {
            if (object instanceof Category) {
                category = (Category) objects.get(position);
                txtSpinnerValue.setText(category.getCatName());
            } else if (object instanceof Subcategory) {
                subcategory = (Subcategory) objects.get(position);
                txtSpinnerValue.setText(subcategory.getSubcatName());
            }
        }
        return view;
    }
}
