package ru.mirea.anichkov.mireaproject.ui.history;

import android.content.Context;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JSONHelper {
    private static final String FILE_NAME = "data.json";

    static boolean exportToJSON(Context context, ArrayList<String> dataList) {
        Gson gson = new Gson();
        DataItems dataItems = new DataItems();
        dataItems.setUsers(dataList);
        String jsonString = gson.toJson(dataItems);
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fileOutputStream.write(jsonString.getBytes());
            return  true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    static ArrayList<String> importFromJSON(Context context) {

        try(FileInputStream fileInputStream = context.openFileInput(FILE_NAME);
            InputStreamReader streamReader = new InputStreamReader(fileInputStream)){

            Gson gson = new Gson();
            DataItems dataItems = gson.fromJson(streamReader, DataItems.class);
            return  dataItems.getUsers();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return null;
    }
    private static class DataItems {
        private ArrayList<String> users;

        ArrayList<String> getUsers() {
            return users;
        }
        void setUsers(ArrayList<String> users) {
            this.users = users;
        }
    }
}
