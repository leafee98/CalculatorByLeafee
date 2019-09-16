package com.example.calculatorbyleafee.ui.sharedTools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.calculatorbyleafee.R;

import java.util.List;

/*
 * this class is used to adapt String into spinner_layout
 */
public class CustomAdapter extends BaseAdapter {

    private List<String> list;
    private Context context;

    public CustomAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.spinner_layout, viewGroup, false);
        TextView tv = view.findViewById(R.id.textViewOfSpinner);
        tv.setText(list.get(i));
        return tv;
    }
}
