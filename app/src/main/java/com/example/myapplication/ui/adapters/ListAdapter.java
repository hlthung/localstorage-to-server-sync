package com.example.myapplication.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.data.User;

import java.util.List;

public class ListAdapter extends BaseAdapter {

    private List<User> mData;
    private LayoutInflater mInflater;

    public ListAdapter(Context context, List<User> data) {
        mData = data;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.fragment_list, parent, false);
        }

        TextView textViewA = convertView.findViewById(R.id.text_key);
        TextView textViewB = convertView.findViewById(R.id.text_value);

        User data = mData.get(position);
        textViewA.setText(data.getSampleKey());
        textViewB.setText(data.getSampleValue());

        return convertView;
    }

}