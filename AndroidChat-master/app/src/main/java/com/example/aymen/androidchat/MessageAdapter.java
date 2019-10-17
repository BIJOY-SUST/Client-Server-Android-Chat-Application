/*
    This adapter takes the friend name and his message from the database and convert to a view
*/


package com.example.aymen.androidchat;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<MessageFormat> {
    public MessageAdapter(Context context, int resource, List<MessageFormat> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MessageFormat message = getItem(position);

        if(TextUtils.isEmpty(message.getMessage())){
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.user_connected, parent, false);
            TextView messageText = convertView.findViewById(R.id.message_body);
            String userConnected = message.getUsername();
            messageText.setText(userConnected);
        }
        else if(message.getMessage().equals("*$*")){
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.user_connected, parent, false);
            TextView messageText = convertView.findViewById(R.id.message_body);
            String userConnected = message.getUsername()+" Connected";
            messageText.setText(userConnected);
        }
        else if(message.getMessage().equals("$*$")){
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.user_connected, parent, false);
            TextView messageText = convertView.findViewById(R.id.message_body);
            String userConnected = message.getUsername()+" Disconnected";
            messageText.setText(userConnected);
        }
        else if(message.getUsername().equals(ChatBoxActivity.Nickname)){
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.my_message, parent, false);
            TextView messageText = convertView.findViewById(R.id.message_body);
            messageText.setText(message.getMessage());

        }else {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.their_message, parent, false);
            TextView messageText = convertView.findViewById(R.id.message_body);
            TextView usernameText = (TextView) convertView.findViewById(R.id.name);
            messageText.setVisibility(View.VISIBLE);
            usernameText.setVisibility(View.VISIBLE);
            messageText.setText(message.getMessage());
            usernameText.setText(message.getUsername());
        }

        return convertView;
    }
}
