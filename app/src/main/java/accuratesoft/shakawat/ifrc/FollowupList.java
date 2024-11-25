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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import accuratesoft.shakawat.ifrc.adapters.FollowupListRecAdapter;
import accuratesoft.shakawat.ifrc.utils.Loading;
import accuratesoft.shakawat.ifrc.utils.MyDB;
import accuratesoft.shakawat.ifrc.utils.Util;

public class FollowupList extends AppCompatActivity {
    TextView d_name;
    Button sync;
    ProgressBar prs;
    RecyclerView rec;
    LinearLayoutManager linearLayoutManager;
    FollowupListRecAdapter followupListRecAdapter;
    SharedPreferences sharedPreferences;
    MyDB myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followup_list);
        init();
    }
    private void init(){
        sharedPreferences = getSharedPreferences(Util.secret_key, Context.MODE_PRIVATE);
        d_name = (TextView) findViewById(R.id.d_name);
        d_name.setText("Login As: "+sharedPreferences.getString("name","John"));
        sync = (Button) findViewById(R.id.sync);
        prs = (ProgressBar) findViewById(R.id.prs);
        rec = (RecyclerView) findViewById(R.id.rec);
        linearLayoutManager = new LinearLayoutManager(FollowupList.this);
        rec.setLayoutManager(linearLayoutManager);
        myDB = new MyDB(FollowupList.this);
        sync.setOnClickListener(clickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        followupListRecAdapter = new FollowupListRecAdapter(FollowupList.this,rec,FollowupList.this, myDB.get_followup());
        rec.setAdapter(followupListRecAdapter);
    }
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new Loading(sync,prs).alterVisibility();
            RequestQueue queue = Volley.newRequestQueue(FollowupList.this);
            String link = Util.url+"insert_followup.php";
            if (myDB.get_followup_done().moveToFirst()){
                StringRequest stringRequest = new StringRequest(Request.Method.POST, link,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                new Loading(sync,prs).alterVisibility();
                                Log.d("Upload Record",response);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    int success = jsonObject.getInt("success");
                                    String msg = jsonObject.getString("message");
                                    if(success == 1) {
                                        Util.makeToast(FollowupList.this,msg);
                                        myDB.del_followup();
                                        startActivity(new Intent(FollowupList.this,FollowupList.class));
                                        finish();
                                    }else{
                                        Util.makeToast(FollowupList.this,msg);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new Loading(sync,prs).alterVisibility();
                        Toast.makeText(FollowupList.this,"Upload error!",Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> params = new HashMap<String,String>();
                        Cursor c_followup = myDB.get_followup_done();
                        JSONObject jobj = new JSONObject();
                        JSONArray jsonArray_followup = new JSONArray();
                        try {
                            if(c_followup.moveToFirst()){
                                do{
                                    JSONObject jsonObject = new JSONObject();
                                    for (int i=1;i<c_followup.getColumnCount();i++)
                                        jsonObject.put(c_followup.getColumnName(i),c_followup.getString(i));
                                    jsonArray_followup.put(jsonObject);
                                }while (c_followup.moveToNext());
                                jobj.put("followup",jsonArray_followup);
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
                new Loading(sync,prs).alterVisibility();
                Util.makeToast(FollowupList.this,"No data to sync");
            }
        }
    };
}