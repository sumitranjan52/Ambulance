package com.ambulance;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class SharedPreferencesHandler {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferencesHandler(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.permission_file_name),Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    /**
     * @param s name of the permission which is to be updated
     */
    public void updateSharedPreferences(String s){
        switch (s){
            case "location_permission":
                editor.putBoolean(context.getString(R.string.location_permission),true);
                editor.commit();
                break;
            default:
                Toast.makeText(context,"Something went wrong while updating records.",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * @param s name of the permission, which is to be checked
     * @return boolean
     */
    public boolean checkSharedPreferences(String s){
        boolean isShown = false;
        switch (s){
            case "location_permission":
                isShown = sharedPreferences.getBoolean(context.getString(R.string.location_permission),false);
                break;
            default:
                Toast.makeText(context,"Something went wrong while checking records.",Toast.LENGTH_SHORT).show();
                break;
        }
        return isShown;
    }
}
