package accuratesoft.shakawat.ifrc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import accuratesoft.shakawat.ifrc.adapters.FollowupSave;
import accuratesoft.shakawat.ifrc.adapters.ListRecAdapter;
import accuratesoft.shakawat.ifrc.models.MhouseHold;
import accuratesoft.shakawat.ifrc.utils.GpsTracker;
import accuratesoft.shakawat.ifrc.utils.Loading;
import accuratesoft.shakawat.ifrc.utils.MyDB;
import accuratesoft.shakawat.ifrc.utils.Util;

public class ListActivity extends AppCompatActivity {
    Button logout,sync,followup,revisit;
    FloatingActionButton fab;
    SharedPreferences sharedPreferences;
    RecyclerView rec;
    LinearLayoutManager linearLayoutManager;
    ListRecAdapter listRecAdapter;
    MyDB myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        init();
    }
    private void init(){

        logout = (Button) findViewById(R.id.logut);
        sync = (Button) findViewById(R.id.sync);
        revisit=(Button) findViewById(R.id.revisit);
        logout.setOnClickListener(clickListener);
        sync.setOnClickListener(clickListener);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(clickListener);
        sharedPreferences = getSharedPreferences(Util.secret_key, Context.MODE_PRIVATE);
        ((TextView) findViewById(R.id.d_name)).setText("Login as: "+sharedPreferences.getString("name","Md. Anwar"));
        myDB = new MyDB(ListActivity.this);
        rec = (RecyclerView) findViewById(R.id.rec);
        linearLayoutManager = new LinearLayoutManager(ListActivity.this);
        rec.setLayoutManager(linearLayoutManager);
        followup = (Button) findViewById(R.id.followup);
        followup.setOnClickListener(clickListener);
        revisit.setOnClickListener(clickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listRecAdapter = new ListRecAdapter(ListActivity.this,rec,ListActivity.this,myDB.get_household());
        rec.setAdapter(listRecAdapter);
        GpsTracker gpsTracker = new GpsTracker(ListActivity.this);
        if(gpsTracker.canGetLocation()){
        }else{
            gpsTracker.showSettingsAlert();
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == logout.getId()){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(ListActivity.this,MainActivity.class));
                finish();
            } else if (view.getId() == sync.getId()) {
                saveData();
            }else if(view.getId()==fab.getId()){
                MhouseHold.clear();
                startActivity(new Intent(ListActivity.this,HouseHold.class));
            } else if (view.getId()==followup.getId()) {
                new Loading(followup,(ProgressBar) findViewById(R.id.fprs)).alterVisibility();
                saveFollowup();
            } else if (view.getId()==revisit.getId()) {
                startActivity(new Intent(ListActivity.this, RevisitHHActivity.class));
            }
        }
    };
    private void saveData(){
        btn_toggle();
//        Cursor cHouse = myDB.get_household();
//        Cursor cParticipant = myDB.get_participant();
        RequestQueue queue = Volley.newRequestQueue(ListActivity.this);
        String link = Util.url+"insert.php";
        if (myDB.get_household().moveToFirst()){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, link,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            btn_toggle();
                            Log.d("Upload Record",response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int success = jsonObject.getInt("success");
                                String msg = jsonObject.getString("message");
                                if(success == 1) {
                                    Toast.makeText(ListActivity.this,msg,Toast.LENGTH_SHORT).show();
                                    myDB.del_all();
                                    startActivity(new Intent(ListActivity.this,ListActivity.class));
//                                            finish();
                                }else{
                                    Toast.makeText(ListActivity.this,msg,Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    btn_toggle();
                    Toast.makeText(ListActivity.this,"Upload error!",Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> params = new HashMap<String,String>();
                    Cursor c_house = myDB.get_household();
                    Cursor c_participant = myDB.get_participant();
                    JSONObject jobj = new JSONObject();
                    JSONArray jsonArray_house = new JSONArray();
                    JSONArray jsonArray_participant = new JSONArray();
                    try {
                        if(c_house.moveToFirst()){
                            do{
                                JSONObject jsonObject = new JSONObject();
                                for (int i=1;i<c_house.getColumnCount();i++)
                                    jsonObject.put(c_house.getColumnName(i),c_house.getString(i));
                                jsonArray_house.put(jsonObject);
                            }while (c_house.moveToNext());
                            jobj.put("household",jsonArray_house);
                        }
                        if(c_participant.moveToFirst()){
                            do{
                                JSONObject jsonObject = new JSONObject();
                                for (int i=1;i<c_participant.getColumnCount();i++)
                                    jsonObject.put(c_participant.getColumnName(i),c_participant.getString(i));
                                jsonArray_participant.put(jsonObject);
                            }while (c_participant.moveToNext());
                            jobj.put("participant",jsonArray_participant);
                        }
                    }catch (JSONException ex){
                        ex.printStackTrace();
                    }
                    params.put("request",jobj.toString());
                    return params;
                }

            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    (int) TimeUnit.SECONDS.toMillis(20), //After the set time elapses the request will timeout
                    0,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(stringRequest);
        }else {
            btn_toggle();
            Util.makeToast(ListActivity.this,"No data to sync");
        }

    }
    private void btn_toggle(){
        if (((ProgressBar) findViewById(R.id.prs)).getVisibility()!=View.VISIBLE) {
            ((ProgressBar) findViewById(R.id.prs)).setVisibility(View.VISIBLE);
            sync.setVisibility(View.GONE);
        }
        else {
            ((ProgressBar) findViewById(R.id.prs)).setVisibility(View.GONE);
            sync.setVisibility(View.VISIBLE);
        }
    }
    private void saveFollowup(){
        Cursor c = myDB.get_followup();
        if (c.moveToFirst()){
            new Loading(followup,(ProgressBar) findViewById(R.id.fprs)).alterVisibility();
            Log.d("LISTACTIVITY","ALTER VISIBILITY");
            startActivity(new Intent(ListActivity.this,FollowupList.class));
            return;
        }
        RequestQueue queue = Volley.newRequestQueue(ListActivity.this);
        String link = Util.url+"get_followup.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        new Loading(followup,(ProgressBar) findViewById(R.id.fprs)).alterVisibility();
                        Log.d("Upload Record",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            String msg = jsonObject.getString("message");
                            if(success == 1) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                FollowupSave followupSave = new FollowupSave(jsonArray,ListActivity.this);
                                followupSave.run();
                                startActivity(new Intent(ListActivity.this,FollowupList.class));
//                                            finish();
                            }else{
                                Toast.makeText(ListActivity.this,msg,Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new Loading(followup,(ProgressBar) findViewById(R.id.fprs)).alterVisibility();
                Toast.makeText(ListActivity.this,"Upload error!",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<String,String>();
                params.put("user_id",String.valueOf(sharedPreferences.getInt("id",0)));
                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20), //After the set time elapses the request will timeout
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }
}