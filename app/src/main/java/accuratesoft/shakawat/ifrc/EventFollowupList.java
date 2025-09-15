package accuratesoft.shakawat.ifrc;

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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import accuratesoft.shakawat.ifrc.adapters.EventFollowupListRecAdapter;
import accuratesoft.shakawat.ifrc.utils.Loading;
import accuratesoft.shakawat.ifrc.utils.Util;

public class EventFollowupList extends AppCompatActivity {
    TextView d_name;
    Button sync;
    ProgressBar prs;
    RecyclerView rec;
    SharedPreferences sharedPreferences;
    LinearLayoutManager linearLayoutManager;
    EventFollowupListRecAdapter recAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event_followup_list);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        init();
    }
    private void init(){
        sharedPreferences = getSharedPreferences(Util.secret_key, Context.MODE_PRIVATE);
        d_name = (TextView) findViewById(R.id.d_name);
        d_name.setText("Login As: "+sharedPreferences.getString("name","John"));
        sync = (Button) findViewById(R.id.sync);
        prs = (ProgressBar) findViewById(R.id.prs);
        rec = (RecyclerView) findViewById(R.id.rec);
        linearLayoutManager = new LinearLayoutManager(EventFollowupList.this);
        rec.setLayoutManager(linearLayoutManager);
        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_followup();
            }
        });
//        rec.setHasFixedSize(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        get_followup();
    }
    private void get_followup() {
        new Loading(sync, prs).alterVisibility();
        RequestQueue queue = Volley.newRequestQueue(EventFollowupList.this);
        String link = Util.url + "get_event_followup.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        new Loading(sync, prs).alterVisibility();
                        Log.d("Event List",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            String msg = jsonObject.getString("message");
                            if(success == 1) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                recAdapter = new EventFollowupListRecAdapter(EventFollowupList.this, EventFollowupList.this, rec,  jsonArray);
                                rec.setAdapter(recAdapter);
                            }else{
                                Toast.makeText(EventFollowupList.this,msg,Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new Loading(sync, prs).alterVisibility();
                Toast.makeText(EventFollowupList.this,"Request error!",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<String,String>();
                params.put("user",String.valueOf(sharedPreferences.getInt("id",0)));
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