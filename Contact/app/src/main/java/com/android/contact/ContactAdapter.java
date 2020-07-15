package com.android.contact;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private Context context;
    private List<Map> allContact = new ArrayList<>();
    private ContactSqliteHelper contactSqliteHelper;
    private SQLiteDatabase contactDb;

    public ContactAdapter(Context context) {
        this.context = context;
        refreshContact();
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactDetail.actionStart(context,contact.get("contact_id"),contact.get("name"),contact.get("phoneNumber"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return allContact.size();
    }

    public void refreshContact(){
        allContact.clear();
        contactSqliteHelper = new ContactSqliteHelper(context,"contact.db",null,1);
        contactDb = contactSqliteHelper.getReadableDatabase();
        Cursor contactCursor = contactDb.query("contact",null,null,null,null,null,null);
        while (contactCursor.moveToNext()){
            Map<String,String> contact = new HashMap<>();
            contact.put("contact_id",contactCursor.getString(0));
            contact.put("name",contactCursor.getString(1));
            contact.put("phoneNumber",contactCursor.getString(2));
            allContact.add(contact);
        }
        contactDb.close();
        notifyDataSetChanged();
    }

    public void setFilter(String filterString){
        filterString = "%"+filterString+"%";
        allContact.clear();
        contactSqliteHelper = new ContactSqliteHelper(context,"contact.db",null,1);
        contactDb = contactSqliteHelper.getReadableDatabase();
        Cursor contactCursor = contactDb.rawQuery("select * from contact where name like ? or phoneNumber like ?",new String[]{filterString,filterString});
        while (contactCursor.moveToNext()){
            Map<String,String> contact = new HashMap<>();
            contact.put("contact_id",contactCursor.getString(0));
            contact.put("name",contactCursor.getString(1));
            contact.put("phoneNumber",contactCursor.getString(2));
            allContact.add(contact);
        }
        contactDb.close();
        notifyDataSetChanged();
    }

}
