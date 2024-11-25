package accuratesoft.shakawat.ifrc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import accuratesoft.shakawat.ifrc.adapters.RevisitListRecAdapter;
import accuratesoft.shakawat.ifrc.models.MRevisitHH;
import accuratesoft.shakawat.ifrc.utils.Loading;
import accuratesoft.shakawat.ifrc.utils.Util;

public class RevisitHHActivity extends AppCompatActivity {
    EditText search;
    Button sync;
    ProgressBar prs;
    RecyclerView rec;
    LinearLayoutManager linearLayoutManager;
    RevisitListRecAdapter revisitListRecAdapter;
    Loading loading;
    ArrayList<MRevisitHH> revisitHHS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revisit_hhactivity);
        init();
    }
    private void init(){
        search = (EditText) findViewById(R.id.search);
        sync = (Button) findViewById(R.id.sync);
        prs = (ProgressBar) findViewById(R.id.prs);
        rec = (RecyclerView) findViewById(R.id.rec);
        loading = new Loading(sync,prs);
        revisitHHS = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(RevisitHHActivity.this);
        rec.setLayoutManager(linearLayoutManager);
        revisitListRecAdapter = new RevisitListRecAdapter(RevisitHHActivity.this, RevisitHHActivity.this, rec, revisitHHS);
        rec.setAdapter(revisitListRecAdapter);
        sync.setOnClickListener(clickListener);
    }
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId()==sync.getId()){
                if (!search.getText().toString().isEmpty()){
                    searchHH(search.getText().toString());
                }else{
                    Util.makeToast(RevisitHHActivity.this,"Search value is empty");
                }
            }
        }
    };
    private void searchHH(String value){
        loading.alterVisibility();
        RequestQueue queue = Volley.newRequestQueue(RevisitHHActivity.this);
        String link = Util.url+"get_household.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link,
                new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    loading.alterVisibility();
                    Log.d("search hh",response);
                    revisitHHS.clear();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int success = jsonObject.getInt("success");
                        String msg = jsonObject.getString("message");
                        if(success == 1) {
                            Util.makeToast(RevisitHHActivity.this,msg);
                            JSONArray jsonArray = jsonObject.getJSONArray("houses");
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jobj = jsonArray.getJSONObject(i);
                                MRevisitHH hh = new MRevisitHH();
                                hh.id = jobj.getInt("id");
                                hh.cc = jobj.getInt("cc");
                                hh.ward = jobj.getString("ward");
                                hh.id_number = jobj.getString("id_number");
                                hh.area = jobj.getString("area");
                                hh.in_name = jobj.getString("in_name");
                                hh.in_sex = jobj.getInt("in_sex");
                                hh.in_age = jobj.getInt("in_age");
                                hh.mob = jobj.getString("mob");
                                hh.mem_num = jobj.getInt("mem_num");
                                hh.user = jobj.getInt("user");
                                hh.created_at = jobj.getString("created_at");
                                revisitHHS.add(hh);
                            }
                            revisitListRecAdapter.notifyDataSetChanged();
                        }else{
                            Util.makeToast(RevisitHHActivity.this,msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loading.alterVisibility();
                    Util.makeToast(RevisitHHActivity.this,"Upload error!");
                }
            }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<String,String>();
                params.put("value",value);
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