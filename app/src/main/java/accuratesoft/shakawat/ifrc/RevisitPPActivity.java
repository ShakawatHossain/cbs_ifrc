package accuratesoft.shakawat.ifrc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import accuratesoft.shakawat.ifrc.adapters.RevisitPPListRecAdapter;
import accuratesoft.shakawat.ifrc.models.MRevesitPP;
import accuratesoft.shakawat.ifrc.models.MRevisitHH;
import accuratesoft.shakawat.ifrc.utils.Util;

public class RevisitPPActivity extends AppCompatActivity {
    RecyclerView rec;
    ProgressBar prs;
    int hh_id,user;
    String hh_number,created_at;
    ArrayList<MRevesitPP> participants;
    RevisitPPListRecAdapter revisitPPListRecAdapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revisit_ppactivity);
        init();
    }
    private void init(){
        rec = (RecyclerView) findViewById(R.id.rec);
        prs = (ProgressBar) findViewById(R.id.prs);
        participants = new ArrayList<>();
        Intent intent = getIntent();
        hh_id = intent.getIntExtra("hh_id",0);
        user = intent.getIntExtra("user",0);
        hh_number = intent.getStringExtra("hh_number");
        created_at = intent.getStringExtra("created_at");
        linearLayoutManager = new LinearLayoutManager(RevisitPPActivity.this);
        rec.setLayoutManager(linearLayoutManager);
        revisitPPListRecAdapter = new RevisitPPListRecAdapter(RevisitPPActivity.this,RevisitPPActivity.this,rec,participants);
        rec.setAdapter(revisitPPListRecAdapter);
        getParticipant();
    }
    private void getParticipant(){
        prs.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(RevisitPPActivity.this);
        String link = Util.url+"get_participant.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        prs.setVisibility(View.GONE);
                        Log.d("search participant",response);
                        participants.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            String msg = jsonObject.getString("message");
                            if(success == 1) {
                                Util.makeToast(RevisitPPActivity.this,msg);
                                JSONArray jsonArray = jsonObject.getJSONArray("participants");
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jobj = jsonArray.getJSONObject(i);
                                    MRevesitPP participant = new MRevesitPP();
                                    participant.hh_id = hh_id;
                                    participant.pp_id = jobj.getInt("id");
                                    participant.id_number = jobj.getString("id_number");
                                    participant.name = jobj.getString("name");
                                    participant.age = jobj.getInt("age");
                                    participant.sex = jobj.getInt("sex");
                                    participant.relation = jobj.getInt("relation");
                                    participant.user = jobj.getInt("user");
                                    participant.created_at = jobj.getString("created_at");
                                    participants.add(participant);
                                }
                                revisitPPListRecAdapter.notifyDataSetChanged();
                            }else{
                                Util.makeToast(RevisitPPActivity.this,msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                prs.setVisibility(View.GONE);
                Util.makeToast(RevisitPPActivity.this,"Upload error!");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<String,String>();
                params.put("hh_number",hh_number);
                params.put("user",String.valueOf(user));
                params.put("created_at",created_at);
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