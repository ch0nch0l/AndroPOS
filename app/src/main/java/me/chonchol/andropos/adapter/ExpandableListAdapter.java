package me.chonchol.andropos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import me.chonchol.andropos.R;
import me.chonchol.andropos.model.Category;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listMother;
    private HashMap<String, List<String>> listChild;

    public ExpandableListAdapter(Context context, List<String> listMother, HashMap<String, List<String>> listChild) {
        this.context = context;
        this.listMother = listMother;
        this.listChild = listChild;
    }

    @Override
    public int getGroupCount() {
        return listMother.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listChild.get(listMother.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listMother.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listChild.get(listMother.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String motherTitle = (String) getGroup(groupPosition);
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_mother, null);
        }

        TextView txtCatName = convertView.findViewById(R.id.txtCatName);
        txtCatName.setText(motherTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String childTitle = (String) getChild(groupPosition, childPosition);

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_child, null);
        }
        TextView txtSubCatName = convertView.findViewById(R.id.txtSubCatName);
        txtSubCatName.setText(childTitle);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
