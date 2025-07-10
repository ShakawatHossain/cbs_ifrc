package accuratesoft.shakawat.ifrc.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import accuratesoft.shakawat.ifrc.HouseHold;
import accuratesoft.shakawat.ifrc.R;
import accuratesoft.shakawat.ifrc.RevisitHHActivity;
import accuratesoft.shakawat.ifrc.RevisitPPActivity;
import accuratesoft.shakawat.ifrc.models.MRevesitPP;
import accuratesoft.shakawat.ifrc.models.MRevisitHH;
import accuratesoft.shakawat.ifrc.models.MhouseHold;
import accuratesoft.shakawat.ifrc.models.Mparticipant;
import accuratesoft.shakawat.ifrc.utils.Util;

public class RevisitListRecAdapter extends RecyclerView.Adapter<RevisitListRecAdapter.MyViewHolder>{
    Context ctx;
    RevisitHHActivity revisitHHActivity;
    RecyclerView recyclerView;
    ArrayList<MRevisitHH> arrayList;
    public RevisitListRecAdapter(Context ctx,RevisitHHActivity revisitHHActivity,RecyclerView recyclerView,ArrayList<MRevisitHH> arrayList){
        this.ctx = ctx;
        this.revisitHHActivity = revisitHHActivity;
        this.recyclerView = recyclerView;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list,parent,
                false);
        v.setOnClickListener(clickListener);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MRevisitHH revisitHH = arrayList.get(position);
        holder.uni_id.setText(revisitHH.id_number);
        holder.name.setText("Interviewee name: "+revisitHH.in_name+" ("+revisitHH.in_age+") ");
        holder.crt_at.setText("Collection date: "+revisitHH.created_at);
        holder.mob.setText("Mob: "+revisitHH.mob);
        holder.age.setText("Ward: "+revisitHH.ward);
        holder.sex.setText("Area: "+revisitHH.area);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView uni_id,name,mob,age,sex,crt_at;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            uni_id = (TextView) itemView.findViewById(R.id.uni_id);
            name = (TextView) itemView.findViewById(R.id.name);
            mob = (TextView) itemView.findViewById(R.id.mob);
            sex= (TextView) itemView.findViewById(R.id.sex);
            age = (TextView) itemView.findViewById(R.id.age);
            crt_at = (TextView) itemView.findViewById(R.id.crt_at);
        }
    }
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int pos = recyclerView.getChildAdapterPosition(view);
            int id = arrayList.get(pos).id;
            get_hh_pp(id);
//            Intent intent = new Intent(ctx, RevisitPPActivity.class);
//            intent.putExtra("hh_id",arrayList.get(pos).id);
//            intent.putExtra("hh_number",arrayList.get(pos).id_number);
//            intent.putExtra("user",arrayList.get(pos).user);
//            intent.putExtra("created_at",arrayList.get(pos).created_at);
//            ctx.startActivity(intent);
        }
    };
    private void get_hh_pp(int id){
        revisitHHActivity.loading.alterVisibility();
        RequestQueue queue = Volley.newRequestQueue(ctx);
        String link = Util.url+"get_hh_pp.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        revisitHHActivity.loading.alterVisibility();
                        Log.d("get_hh_pp",response);
                        MhouseHold.clear();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            String msg = jsonObject.getString("message");
                            if(success == 1) {
                                JSONArray jsonArray = jsonObject.getJSONArray("hh");
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jobj = jsonArray.getJSONObject(i);
                                    if(jobj.has("cc")) MhouseHold.cc=jobj.getInt("cc");
                                    if(jobj.has("ward")) MhouseHold.ward=jobj.getInt("ward");
                                    if(jobj.has("hh")) MhouseHold.hh=jobj.getInt("hh");
                                    if(jobj.has("id_number")) MhouseHold.hh_num=jobj.getString("id_number");
                                    if(jobj.has("house_address")) MhouseHold.house_address=jobj.getString("house_address");
                                    if(jobj.has("area")) MhouseHold.area=jobj.getString("area");
                                    if(jobj.has("lat")) MhouseHold.lat=jobj.getString("lat");
                                    if(jobj.has("lung")) MhouseHold.lung=jobj.getString("lung");
                                    if(jobj.has("in_name")) MhouseHold.info_name=jobj.getString("in_name");
                                    if(jobj.has("in_sex")) MhouseHold.info_sex=jobj.getInt("in_sex");
                                    if(jobj.has("in_age")) MhouseHold.info_age=jobj.getInt("in_age");
                                    if(jobj.has("mob")) MhouseHold.mob=jobj.getString("mob");
                                    if(jobj.has("mem_num")) MhouseHold.mem=jobj.getInt("mem_num");
                                    if(jobj.has("symptom_7")) MhouseHold.symptom_7=jobj.getInt("symptom_7");
                                    if(jobj.has("symptom_7_mem")) MhouseHold.symptom_7_mem=jobj.getInt("symptom_7_mem");
                                    if(jobj.has("symptom_6m")) MhouseHold.symptom_6m=jobj.getInt("symptom_6m");
                                    if(jobj.has("symptom_6m_mem")) MhouseHold.symptom_6m_mem=jobj.getInt("symptom_6m_mem");
                                    if(jobj.has("reffer")) MhouseHold.reffer=jobj.getInt("reffer");
                                    if(jobj.has("is_death")) MhouseHold.is_death=jobj.getInt("is_death");
                                    if(jobj.has("dname")) MhouseHold.dname=jobj.getString("dname");
                                    if(jobj.has("dage")) MhouseHold.dage=jobj.getInt("dage");
                                    if(jobj.has("unknown_disease_time")) MhouseHold.unknown_disease_time = jobj.getString("unknown_disease_time");
                                    if(jobj.has("unknown_sick")) MhouseHold.unknown_sick = jobj.getString("unknown_sick");
                                    if(jobj.has("unknown_dead")) MhouseHold.unknown_dead = jobj.getString("unknown_dead");
                                    if(jobj.has("unknown_around_time")) MhouseHold.unknown_around_time = jobj.getString("unknown_around_time");
                                    if(jobj.has("death_animals_number")) MhouseHold.death_animals_number = jobj.getString("death_animals_number");
                                    if(jobj.has("death_animals_time")) MhouseHold.death_animals_time = jobj.getString("death_animals_time");
                                    if(jobj.has("death_animals_type")) MhouseHold.death_animals_type = jobj.getString("death_animals_type");
                                    if(jobj.has("unknown_disease")) MhouseHold.unknown_disease = jobj.getInt("unknown_disease");
                                    if(jobj.has("unkown_around")) MhouseHold.unkown_around = jobj.getInt("unkown_around");
                                    if(jobj.has("has_animals")) MhouseHold.has_animals = jobj.getInt("has_animals");
                                    if(jobj.has("death_animals")) MhouseHold.death_animals = jobj.getInt("death_animals");
                                    if(jobj.has("unknown_disease_type")) MhouseHold.unknown_disease_type=jobj.getString("unknown_disease_type");
                                    if(jobj.has("unknown_disease_type_oth")) MhouseHold.unknown_disease_type_oth=jobj.getString("unknown_disease_type_oth");
                                }
                                JSONArray jsonArray1 = jsonObject.getJSONArray("pp");
                                for (int i=0;i<jsonArray1.length();i++){
                                    JSONObject jobj = jsonArray1.getJSONObject(i);
                                    Mparticipant p = new Mparticipant();
                                    if(jobj.has("num")) p.num = jobj.getInt("num");
                                    if(jobj.has("hh_number")) p.hh_number = jobj.getString("hh_number");
                                    if(jobj.has("id_number")) p.id_number = jobj.getString("id_number");
                                    if(jobj.has("name")) p.name = jobj.getString("name");
                                    if(jobj.has("age")) p.age = jobj.getInt("age");
                                    if(jobj.has("sex")) p.sex = jobj.getInt("sex");
                                    if(jobj.has("relation")) p.relation = jobj.getInt("relation");
                                    if(jobj.has("comorbidity")) p.comorbidity = jobj.getString("comorbidity");
                                    if(jobj.has("rel_oth")) p.rel_oth = jobj.getString("rel_oth");
                                    if(jobj.has("sick7")) p.sick7 = jobj.getInt("sick7");
                                    if(jobj.has("fever")) p.fever = jobj.getInt("fever");
                                    if(jobj.has("headache")) p.headache = jobj.getInt("headache");
                                    if(jobj.has("aches")) p.aches = jobj.getInt("aches");
                                    if(jobj.has("mus_pain")) p.mus_pain = jobj.getInt("mus_pain");
                                    if(jobj.has("arth_pain")) p.arth_pain = jobj.getInt("arth_pain");
                                    if(jobj.has("rash")) p.rash = jobj.getInt("rash");
                                    if(jobj.has("vomit")) p.vomit = jobj.getInt("vomit");
                                    if(jobj.has("vomit_tendency")) p.vomit_tendency = jobj.getInt("vomit_tendency");
                                    if(jobj.has("dia")) p.dia = jobj.getInt("dia");
                                    if(jobj.has("is_test")) p.is_test = jobj.getInt("is_test");
                                    if(jobj.has("ns1")) p.ns1 = jobj.getInt("ns1");
                                    if(jobj.has("igg")) p.igg = jobj.getInt("igg");
                                    if(jobj.has("igm")) p.igm = jobj.getInt("igm");
                                    if(jobj.has("unk_test")) p.unk_test = jobj.getInt("unk_test");
                                    if(jobj.has("ns1_p")) p.ns1_p = jobj.getInt("ns1_p");
                                    if(jobj.has("igg_p")) p.igg_p = jobj.getInt("igg_p");
                                    if(jobj.has("igm_p")) p.igm_p = jobj.getInt("igm_p");
                                    if(jobj.has("ns1_vis")) p.ns1_vis = jobj.getInt("ns1_vis");
                                    if(jobj.has("igg_vis")) p.igg_vis = jobj.getInt("igg_vis");
                                    if(jobj.has("igm_vis")) p.igm_vis = jobj.getInt("igm_vis");
                                    if(jobj.has("is_reffer")) p.is_reffer = jobj.getInt("is_reffer");
                                    if(jobj.has("sick6m")) p.sick6m = jobj.getInt("sick6m");
                                    if(jobj.has("doc_list")) p.doc_list = jobj.getInt("doc_list");
                                    if(jobj.has("prev_ns1")) p.prev_ns1 = jobj.getInt("prev_ns1");
                                    if(jobj.has("prev_igg")) p.prev_igg = jobj.getInt("prev_igg");
                                    if(jobj.has("prev_igm")) p.prev_igm = jobj.getInt("prev_igm");
                                    if(jobj.has("prev_unk_test")) p.prev_unk_test = jobj.getInt("prev_unk_test");
                                    if(jobj.has("prev_ns1_p")) p.prev_ns1_p = jobj.getInt("prev_ns1_p");
                                    if(jobj.has("prev_igg_p")) p.prev_igg_p = jobj.getInt("prev_igg_p");
                                    if(jobj.has("prev_igm_p")) p.prev_igm_p = jobj.getInt("prev_igm_p");
                                    if(jobj.has("prev_ns1_vis")) p.prev_ns1_vis = jobj.getInt("prev_ns1_vis");
                                    if(jobj.has("prev_igg_vis")) p.prev_igg_vis = jobj.getInt("prev_igg_vis");
                                    if(jobj.has("prev_igm_vis")) p.prev_igm_vis = jobj.getInt("prev_igm_vis");
                                    if(jobj.has("outcome")) p.outcome = jobj.getInt("outcome");
                                    if(jobj.has("comorbidity_oth_txt")) p.comorbidity_oth_txt = jobj.getString("comorbidity_oth_txt");
                                    if(jobj.has("temp_measured")) p.temp_measured = jobj.getInt("temp_measured");
                                    if(jobj.has("fever_duration")) p.fever_duration = jobj.getString("fever_duration");
                                    if(jobj.has("awd_daygap")) p.awd_daygap = jobj.getString("awd_daygap");
                                    MhouseHold.participants.add(p);
                                }
                                ctx.startActivity(new Intent(ctx, HouseHold.class));
                            }else{
                                Util.makeToast(ctx,msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                revisitHHActivity.loading.alterVisibility();
                Util.makeToast(ctx,"Upload error!");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<String,String>();
                params.put("id",String.valueOf(id));
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
