package com.example.myapplication.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.utils.SyncData;
import com.example.myapplication.utils.LocalDatabaseHelper;

public class MainFragment extends Fragment implements View.OnClickListener {

    private EditText editTextKey, editTextValue;
    private Button buttonSave, buttonSync, buttonCheck, buttonClear;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        editTextKey = view.findViewById(R.id.editTextKey);
        editTextValue = view.findViewById(R.id.editTextValue);
        buttonSave = view.findViewById(R.id.buttonSave);
        buttonSync = view.findViewById(R.id.buttonSync);
        buttonCheck = view.findViewById(R.id.buttonCheck);
        buttonClear = view.findViewById(R.id.buttonClear);


        buttonSave.setOnClickListener(this);
        buttonSync.setOnClickListener(this);
        buttonCheck.setOnClickListener(this);
        buttonClear.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        LocalDatabaseHelper myDB = new LocalDatabaseHelper(this.getContext());
        switch (v.getId()) {
            case R.id.buttonSave:
                myDB.addData(editTextKey.getText().toString().trim(),
                        editTextValue.getText().toString().trim());

                editTextKey.setText("");
                editTextValue.setText("");
                break;
            case R.id.buttonSync:
                SyncData syncData = new SyncData(this.getContext());
                syncData.execute();
                break;

            case R.id.buttonCheck:
                NavHostFragment.findNavController(MainFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//                List<User> userList = myDB.getData();
//                for (User user : userList) {
//                    Log.i("myData", user.getKey() + " " + user.getValue());
//                }
                break;

            case R.id.buttonClear:
                myDB.clearData();
                break;
        }
    }
}