package accuratesoft.shakawat.ifrc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import accuratesoft.shakawat.ifrc.utils.GpsTracker;
import accuratesoft.shakawat.ifrc.utils.Util;

public class MainActivity extends AppCompatActivity {
    EditText user_id,pass;
    ProgressBar pBar;
    Button submit;
    GpsTracker gpsTracker;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        user_id = (EditText) findViewById(R.id.user_id);
        pass = (EditText) findViewById(R.id.pass);
        gpsTracker = new GpsTracker(MainActivity.this);
        pBar = (ProgressBar) findViewById(R.id.pBar);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(clickListener);
        sharedPreferences = getSharedPreferences(Util.secret_key, Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt("id",0);
        if (id>0){
            startActivity(new Intent(MainActivity.this,ListActivity.class));
            finish();
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId()==submit.getId()){
                if(check()){
                    sendData();
                }
            }
        }
    };
    private boolean check(){
        boolean is_ok=true;
        if(user_id.getText().toString()==null || user_id.getText().toString().isEmpty()){
            is_ok=false;
            Toast.makeText(MainActivity.this,"Write user id",Toast.LENGTH_SHORT).show();
        } else if (pass.getText().toString()==null || pass.getText().toString().isEmpty()) {
            is_ok=false;
            Toast.makeText(MainActivity.this,"Write password",Toast.LENGTH_SHORT).show();
        }
        return is_ok;
    }
    private void sendData(){
        alterbtn();
        String url=Util.url+"user.php";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        alterbtn();
                        Log.d("MainActivity_login",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
//                            todo
                            int success = jsonObject.getInt("success");
                            if (success==1){
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("id",jsonObject.getInt("id"));
                                editor.putString("name",jsonObject.getString("name"));
                                editor.putInt("hh_num",jsonObject.getInt("index"));
                                editor.apply();
                                startActivity(new Intent(MainActivity.this,ListActivity.class));
//                                startActivity(new Intent(MainActivity.this,LeadActivity.class));
                            }else{
                                String msg = jsonObject.getString("msg");
                                Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("MainActivity",e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                alterbtn();
//                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                Log.e("volley error Main",error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("user_id",user_id.getText().toString());
                params.put("pass",pass.getText().toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20), //After the set time elapses the request will timeout
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    private void alterbtn(){
        if (submit.getVisibility()==View.VISIBLE){
            submit.setVisibility(View.GONE);
            pBar.setVisibility(View.VISIBLE);
        }else{
            submit.setVisibility(View.VISIBLE);
            pBar.setVisibility(View.GONE);
        }
    }
}