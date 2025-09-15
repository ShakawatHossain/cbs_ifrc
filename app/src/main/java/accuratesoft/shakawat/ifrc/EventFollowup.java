package accuratesoft.shakawat.ifrc;

import static accuratesoft.shakawat.ifrc.utils.Util.readBytesFromUri;
import static accuratesoft.shakawat.ifrc.utils.Util.writeBytesToFile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import accuratesoft.shakawat.ifrc.interfaces.UploadCallback;
import accuratesoft.shakawat.ifrc.utils.Loading;
import accuratesoft.shakawat.ifrc.utils.UploadManager;
import accuratesoft.shakawat.ifrc.utils.Util;

public class EventFollowup extends AppCompatActivity {
    TextView title;
    EditText des,consult_oth,mem_num;
    Spinner mode,health_status,is_consult,consult_type,oth_family,is_referred,refer_place;
    TableRow consult_type_row,consult_oth_row,mem_num_row,referred_row;
    Button img_btn,submit;
    ProgressBar pbar;
    ImageView img;
    int followup_id=0;
    String participant_id="";
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    byte[] imageBytes;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_followup);
        sharedPreferences = getSharedPreferences(Util.secret_key, Context.MODE_PRIVATE);
        init();
    }
    private void init(){
        title = (TextView) findViewById(R.id.title);
        des = (EditText) findViewById(R.id.des);
        mode = (Spinner) findViewById(R.id.mode);
        health_status = (Spinner) findViewById(R.id.health_status);
        is_consult = (Spinner) findViewById(R.id.is_consult);
        consult_type = (Spinner) findViewById(R.id.consult_type);
        consult_oth = (EditText) findViewById(R.id.consult_oth);
        oth_family = (Spinner) findViewById(R.id.oth_family);
        mem_num = (EditText) findViewById(R.id.mem_num);
        consult_type_row = (TableRow) findViewById(R.id.consult_type_row);
        consult_oth_row = (TableRow) findViewById(R.id.consult_oth_row);
        mem_num_row = (TableRow) findViewById(R.id.mem_num_row);
        img_btn = (Button) findViewById(R.id.img_btn);
        submit = (Button) findViewById(R.id.submit);
        pbar = (ProgressBar) findViewById(R.id.pBar);
        img = (ImageView) findViewById(R.id.img);
        is_referred = (Spinner) findViewById(R.id.is_referred);
        refer_place = (Spinner) findViewById(R.id.refer_place);
        referred_row = (TableRow) findViewById(R.id.referred_row);

        Intent intent = getIntent();
        title.setText(intent.getStringExtra("followup_cause")+" event followup: "+intent.getStringExtra("name"));
        followup_id = intent.getIntExtra("id",0);
        participant_id = intent.getStringExtra("participant_id");

        submit.setOnClickListener(clickListener);
        img_btn.setOnClickListener(clickListener);
        img.setOnClickListener(clickListener);
        is_consult.setOnItemSelectedListener(onItemSelectedListener);
        oth_family.setOnItemSelectedListener(onItemSelectedListener);
        is_referred.setOnItemSelectedListener(onItemSelectedListener);

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        Uri imageUri = data.getData();
                        try {
                            imageBytes = readBytesFromUri(EventFollowup.this, imageUri);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        img.setImageURI(imageUri);
                        // Use imageUri to get the image path or display it
                    }
                }
        );
    }
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId()==submit.getId()){
                if(check()){
                    sendResponse();
                }
            } else if (view.getId() ==img_btn.getId()) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                imagePickerLauncher.launch(intent);
            } else if (view.getId()==img.getId()) {
                new AlertDialog.Builder(EventFollowup.this)
                        .setTitle("Image remove")
                        .setMessage("Are you sure you want to remove this Image?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Perform delete operation
                                img.setImageDrawable(null);
                                imageBytes = null;
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        }
    };
    private AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (view.getParent()==is_consult){
                if(i==2) {
                    consult_type_row.setVisibility(View.VISIBLE);
                    consult_oth_row.setVisibility(View.VISIBLE);
                }else {
                    consult_type_row.setVisibility(View.GONE);
                    consult_oth_row.setVisibility(View.GONE);
                }
            } else if (view.getParent() ==oth_family) {
                if (i==2)
                    mem_num_row.setVisibility(View.VISIBLE);
                else
                    mem_num_row.setVisibility(View.GONE);
            } else if (view.getParent() ==is_referred) {
                if (i==2)
                    referred_row.setVisibility(View.VISIBLE);
                else
                    referred_row.setVisibility(View.GONE);
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };
    private boolean check(){
        boolean result = true;
            if (mode.getSelectedItemPosition()==0){
                result = false;
                Util.makeToast(EventFollowup.this,"Please select mode of contact");
            } else if (health_status.getSelectedItemPosition()==0) {
                result = false;
                Util.makeToast(EventFollowup.this,"Please select health status");
            } else if (is_consult.getSelectedItemPosition()==0) {
                result = false;
                Util.makeToast(EventFollowup.this,"Please select consultation status");
            }

            if(is_consult.getSelectedItemPosition()==2){
                if(consult_type.getSelectedItemPosition()==0){
                    result = false;
                    Util.makeToast(EventFollowup.this,"Please select consultation type");
                }
            }
        return result;
    }
    private void sendResponse(){
        new Loading(submit,pbar).alterVisibility();
        RequestQueue queue = Volley.newRequestQueue(EventFollowup.this);
        String link = Util.url+"event_followup_insert.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        new Loading(submit,pbar).alterVisibility();
                        Log.d("Upload Record",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            String msg = jsonObject.getString("message");
                            if(success == 1) {
                                Toast.makeText(EventFollowup.this,msg,Toast.LENGTH_SHORT).show();
                                if(imageBytes!=null)
                                    sendImage(jsonObject.getString("filename"));
                                else
                                    finish();
                            }else{
                                Toast.makeText(EventFollowup.this,msg,Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new Loading(submit,pbar).alterVisibility();
                Toast.makeText(EventFollowup.this,"Upload error!",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<String,String>();
                params.put("participant_id",participant_id);
                params.put("followup_id",String.valueOf(followup_id));
                params.put("des",des.getText().toString());
                params.put("mode",String.valueOf(mode.getSelectedItemPosition()));
                params.put("health_status",String.valueOf(health_status.getSelectedItemPosition()));
                params.put("is_consult",String.valueOf(is_consult.getSelectedItemPosition()));
                params.put("consult_type",String.valueOf(consult_type.getSelectedItemPosition()));
                params.put("consult_oth",consult_oth.getText().toString());
                params.put("oth_family",String.valueOf(oth_family.getSelectedItemPosition()));
                params.put("mem_num",String.valueOf(mem_num.getText().toString()));
                params.put("is_referred",String.valueOf(is_referred.getSelectedItemPosition()));
                params.put("refer_place",String.valueOf(refer_place.getSelectedItemPosition()));
                if (imageBytes!=null) params.put("has_image","1");
                else params.put("has_image","0");
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
    private void sendImage(String filename){
        File imageFile = null;
        try {
            imageFile = writeBytesToFile(EventFollowup.this, imageBytes, filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String serverUrl = Util.url+"img_upload/upload.php";
        if (imageFile == null) {
            Toast.makeText(getApplicationContext(), "File not found", Toast.LENGTH_LONG).show();
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            UploadManager.uploadImage(EventFollowup.this, imageFile, serverUrl, new UploadCallback() {
                @Override
                public void onSuccess(String response) {
                    Toast.makeText(getApplicationContext(), "Upload Success: " + response, Toast.LENGTH_LONG).show();
                    finish();
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(getApplicationContext(), "Upload Failed: " + error, Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}