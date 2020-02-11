package com.example.registrationuserdatabase.data.local;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import androidx.room.Room;

import java.util.List;

public class DataBaseInstance {
    private static DataBaseInstance instance;
    private final AppDatabase db;

    private DataBaseInstance(Context context) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, "database-name.db")
                .allowMainThreadQueries()
                .build();
    }

    public static DataBaseInstance getInstance(Context context){
        if (instance == null){
            instance = new DataBaseInstance(context);
        }
        return instance;
    }

    public void insertSingleAsync(final User user){
//        db.UserDao().insertSingle(user);
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                db.UserDao().insertSingle(user);
                Log.d("TAG","USER inserted");
                return null;
            }
        }.execute();
    }

    public void deleteAll(final List<User> all){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db.UserDao().deleteAll(all);
                return null;
            }
        }.execute();

    }

    public void getAll(final DataBaseListener<List<User>> callback){
        new AsyncTask<Void,Void,List<User>>() {
            @Override
            protected List<User> doInBackground(Void... voids) {
                List<User> users = db.UserDao().getAll();
                return users;
            }

            @Override
            protected void onPostExecute(List<User> listOfUsers) {
                super.onPostExecute(listOfUsers);
                callback.onDataReceived(listOfUsers);
            }
        }.execute();
    }

    public interface DataBaseListener<T>{
        void onDataReceived(T data);
    }


}
