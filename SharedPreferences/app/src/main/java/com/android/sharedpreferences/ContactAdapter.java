package com.android.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private Context context;
    private List<Map> allContact = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private int contactNumber;

    public ContactAdapter(Context context,SharedPreferences sharedPreferences) {
        this.context = context;
        this.sharedPreferences=sharedPreferences;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView contact_Name;
        TextView contact_phoneNumber;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contact_Name = (TextView)itemView.findViewById(R.id.contact_Name);
            contact_phoneNumber = (TextView)itemView.findViewById(R.id.contact_phoneNumber);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item,parent,false);
        ViewHolder holder =  new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Map<String,String> contact = allContact.get(position);
        holder.contact_Name.setText(contact.get("name"));
        holder.contact_phoneNumber.setText(contact.get("phoneNumber"));
    }

    @Override
    public int getItemCount() {
        return allContact.size();
    }

    public void refreshContact(int contactNumber){
        allContact.clear();
        for(int i=1;i<=contactNumber;i++){
            Map<String,String> contact = new HashMap<>();
            contact.put("name",sharedPreferences.getString("contact_Name"+i,"error"));
            contact.put("phoneNumber",sharedPreferences.getString("contact_phoneNumber"+i,"error"));
            allContact.add(contact);
        }
        notifyDataSetChanged();
    }

}
