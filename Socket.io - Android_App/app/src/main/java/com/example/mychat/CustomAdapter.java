/*
    This adapter takes the friend name from the database and convert to a view
*/


package com.example.mychat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> friendList;
    private LayoutInflater inflater;

    CustomAdapter(Context context, ArrayList<String> friendList){
        this.context = context;
        this.friendList = friendList;
    }

    @Override
    public int getCount() {
        return friendList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activefriend,viewGroup,false);

        }
        TextView signView = view.findViewById(R.id.activeSign);
        TextView textView = view.findViewById(R.id.usernameid);

//        signView.setText();
        textView.setText(friendList.get(i));

        return view;
    }
}
