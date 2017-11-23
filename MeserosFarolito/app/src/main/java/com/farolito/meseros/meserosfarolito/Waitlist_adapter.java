package com.farolito.meseros.meserosfarolito;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hector on 16/11/2017.
 */

public class Waitlist_adapter extends Adapter<Waitlist_adapter.waitlistViewHolder> {

    public static class Client {
        String name;
        int count;

        public Client(String name_, int count_){
            name = name_;
            count = count_;
        }
    }

    private Context mContext;
    private WaitlistDBHelper mDbHelper;

    private List<Client> clients;

    private int lastPosition;

    public Waitlist_adapter(Context context){
        mContext = context;
        mDbHelper = new WaitlistDBHelper(context);
        clients = new ArrayList<>();
        lastPosition = -1;
        updateData();
    }

    public class waitlistViewHolder extends RecyclerView.ViewHolder {
        public TextView clientName, clientCount;
        public RelativeLayout viewBackground, viewForeground;
        public View container;

        public waitlistViewHolder(View view) {
            super(view);
            container = view;
            clientName = (TextView) view.findViewById(R.id.waitlist_clientName);
            clientCount = (TextView) view.findViewById(R.id.waitlist_clientCount);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
        }
    }

    public void updateData(){
        new readDatabaseTask().execute();
    }

    public class readDatabaseTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            SQLiteDatabase db = mDbHelper.getReadableDatabase();

            Cursor cursor = db.query(
                    WaitlistContract.WaitlistEntry.WAITLIST_TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            clients.clear();

            while(cursor.moveToNext()){
                clients.add(new Client(
                        cursor.getString(cursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_CLIENT_NAME)),
                        cursor.getInt(cursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_CLIENT_COUNT))
                ));
            }

            cursor.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            notifyDataSetChanged();
            super.onPostExecute(aVoid);
        }
    }

    @Override
    public Waitlist_adapter.waitlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.client_list_item, parent, false);
        return new waitlistViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(waitlistViewHolder holder, int position) {
        Client client = clients.get(position);
        holder.clientCount.setText(""+client.count);
        holder.clientName.setText(client.name);

        setAnimation(holder.container, position);
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    public List<Client> getClients(){
        return this.clients;
    }

    public void removeClient(int position){
        lastPosition--;
        Client client = clients.remove(position);

        WaitlistDBHelper mDbHelper = new WaitlistDBHelper(mContext);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String selection = WaitlistContract.WaitlistEntry.COLUMN_CLIENT_NAME + " = ?";
        String[] selectionArgs = { client.name };
        db.delete(WaitlistContract.WaitlistEntry.WAITLIST_TABLE_NAME, selection, selectionArgs);

        notifyItemRemoved(position);
    }

    public void restoreClient(Client client, int position){
        clients.add(position, client);
        int count = client.count;
        String name = client.name;

        Log.d("MyLog", name);

        WaitlistDBHelper mDbHelper = new WaitlistDBHelper(mContext);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(WaitlistContract.WaitlistEntry.COLUMN_CLIENT_NAME, name);
        values.put(WaitlistContract.WaitlistEntry.COLUMN_CLIENT_COUNT, count);

        db.insert(WaitlistContract.WaitlistEntry.WAITLIST_TABLE_NAME, null, values);
        notifyItemInserted(position);
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public void insertClient(Client client, int position, RecyclerView.LayoutManager mLayoutManager){
        clients.add(position, client);
        mLayoutManager.scrollToPosition(clients.size() -1);
        int count = client.count;
        String name = client.name;

        Log.d("MyLog", name);

        WaitlistDBHelper mDbHelper = new WaitlistDBHelper(mContext);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(WaitlistContract.WaitlistEntry.COLUMN_CLIENT_NAME, name);
        values.put(WaitlistContract.WaitlistEntry.COLUMN_CLIENT_COUNT, count);

        db.insert(WaitlistContract.WaitlistEntry.WAITLIST_TABLE_NAME, null, values);
        notifyItemInserted(position);
    }


}
