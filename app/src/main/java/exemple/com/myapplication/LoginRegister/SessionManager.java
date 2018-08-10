package exemple.com.myapplication.LoginRegister;

/**
 * Created by chratah on 13/11/2017.
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import java.util.HashMap;

public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "AndroidHiveLogin";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }



    public void UserDetail(String email){


        editor.putString("email",email);

        // commit changes
        editor.commit();
    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<String, String>();

        // user email id
        user.put("email", pref.getString("email",null));

        // return user
        return user;
    }
    public void setId(String id){


        editor.putString("id",id);

        // commit changes
        editor.commit();
    }

    public HashMap<String, String> getId(){
        HashMap<String, String> user = new HashMap<String, String>();

        // user email id
        user.put("id", pref.getString("id",null));

        // return user
        return user;
    }


    public void setStatus(String status){


        editor.putString("status",status);

        editor.commit();
    }

    public HashMap<String, String> getStatus(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put("status", pref.getString("status",null));
        return user;
    }



    public void setGroup(String group){


        editor.putString("group",group);

        // commit changes
        editor.commit();
    }

    public HashMap<String, String> getGroup(){
        HashMap<String, String> user = new HashMap<String, String>();

        // user email id
        user.put("group", pref.getString("group",null));

        // return user
        return user;
    }


    public void setUser(String id, String nom, String email, String status, String num_tel ,String type ,String sexe ){


        editor.putString("id",id);
        editor.putString("nom",nom);
        editor.putString("email",email);
        editor.putString("status",status);
        editor.putString("num_tel", num_tel);
        editor.putString("type", type);
        editor.putString("sexe", sexe);


        // commit changes
        editor.commit();
    }

    public HashMap<String, String> getUser(){
        HashMap<String, String> matriel = new HashMap<String, String>();

        // user email id
        matriel.put("id", pref.getString("id",null));
        matriel.put("nom", pref.getString("nom",null));
        matriel.put("email", pref.getString("email",null));
        matriel.put("status", pref.getString("status",null));
        matriel.put("num_tel", pref.getString("num_tel",null));
        matriel.put("type", pref.getString("type",null));
        matriel.put("sexe", pref.getString("sexe",null));

        // return user
        return matriel;
    }
























    public void setName(String name){


        editor.putString("name",name);


        // commit changes
        editor.commit();
    }

    public HashMap<String, String> getName(){
        HashMap<String, String> user = new HashMap<String, String>();

        // user email id
        user.put("name", pref.getString("name",null));

        // return user
        return user;
    }


    ////////////*************///////////////////


    public void setIdroom(String idroom){


        editor.putString("idroom",idroom);


        editor.commit();
    }

    public HashMap<String, String> getIdroom(){
        HashMap<String, String> user = new HashMap<String, String>();

        user.put("idroom", pref.getString("idroom",null));

        return user;
    }
    ////*******iduser2
    public void setIduser2(String iduser2){


        editor.putString("iduser2",iduser2);


        editor.commit();
    }

    public HashMap<String, String> getIduser2(){
        HashMap<String, String> user = new HashMap<String, String>();

        user.put("iduser2", pref.getString("iduser2",null));

        return user;
    }
    /////********iduser3***////////////

    public void setIduser3(String iduser3){


        editor.putString("iduser3",iduser3);


        editor.commit();
    }

    public HashMap<String, String> getIduser3(){
        HashMap<String, String> user = new HashMap<String, String>();

        user.put("iduser3", pref.getString("iduser3",null));

        return user;
    }

















    ////////***************/////////////








    public void UserPassword(String Password){


        editor.putString("Password",Password);

        // commit changes
        editor.commit();
    }

    public HashMap<String, String> getUserPassword(){
        HashMap<String, String> user = new HashMap<String, String>();

        // user email id
        user.put("Password", pref.getString("Password",null));

        // return user
        return user;
    }






    public void setIdr(String idr){


        editor.putString("idr",idr);

        // commit changes
        editor.commit();
    }

    public HashMap<String, String> getGIdr(){
        HashMap<String, String> user = new HashMap<String, String>();

        // user email id
        user.put("idr", pref.getString("idr",null));

        // return user
        return user;
    }












    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }
}
