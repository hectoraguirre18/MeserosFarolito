package com.farolito.meseros.meserosfarolito;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hector on 16/11/2017.
 */

public class Waitlist_adapter extends Adapter<Waitlist_adapter.waitlistViewHolder> {

    public class Client {
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

    public Waitlist_adapter(Context context){
        mContext = context;
        mDbHelper = new WaitlistDBHelper(context);
        clients = new ArrayList<>();
        updateData();
    }

    public class waitlistViewHolder extends RecyclerView.ViewHolder {
        public TextView clientName, clientCount;

        public waitlistViewHolder(View view) {
            super(view);
            clientName = (TextView) view.findViewById(R.id.waitlist_clientName);
            clientCount = (TextView) view.findViewById(R.id.waitlist_clientCount);
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
        holder.clientCount.setText(""+clients.get(position).count);
        holder.clientName.setText(clients.get(position).name);
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }


}
