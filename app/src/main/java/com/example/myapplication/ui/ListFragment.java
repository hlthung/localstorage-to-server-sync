package com.example.myapplication.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.ui.adapters.ListAdapter;
import com.example.myapplication.utils.LocalDatabaseHelper;
import com.example.myapplication.R;
import com.example.myapplication.data.User;

import java.util.List;

public class ListFragment extends Fragment {

    private ListView mListView;
    private ListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LocalDatabaseHelper myDB = new LocalDatabaseHelper(this.getContext());
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        mListView = view.findViewById(R.id.list_view);

        List<User> userList = myDB.getData();
        // Create the adapter and set it to the list view
        mAdapter = new ListAdapter(getContext(), userList);
        mListView.setAdapter(mAdapter);

        return view;
    }
}