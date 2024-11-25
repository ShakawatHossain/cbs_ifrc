package accuratesoft.shakawat.ifrc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.OnSelectionChangedListener;

import java.util.ArrayList;

import accuratesoft.shakawat.ifrc.models.MhouseHold;
import accuratesoft.shakawat.ifrc.models.Mparticipant;
import accuratesoft.shakawat.ifrc.utils.MyDB;
import accuratesoft.shakawat.ifrc.utils.Util;

public class Revision extends AppCompatActivity {
    TextView tv;
    EditText referrs,name,age;
    Spinner is_death;
    SharedPreferences sharedPreferences;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revision);
        init();
        sharedPreferences = getSharedPreferences(Util.secret_key, Context.MODE_PRIVATE);
    }
    private void init(){
        tv = (TextView) findViewById(R.id.tv);
        referrs = (EditText) findViewById(R.id.referrs);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(clickListener);
        is_death = (Spinner) findViewById(R.id.is_death);
        name = (EditText) findViewById(R.id.dname);
        age = (EditText) findViewById(R.id.dage);
        is_death.setOnItemSelectedListener(itemSelectedListener);
        getData();
    }
    private void getData(){
        String s="";
        s+="cc: "+MhouseHold.cc+"\n";
        s+="ward: "+MhouseHold.ward+"\n";
        s+="hh: "+MhouseHold.hh+"\n";
        s+="hh_number: "+MhouseHold.hh_num+"\n";
        s+="area: "+MhouseHold.area+"\n";
        s+="lat: "+MhouseHold.lat+"\n";
        s+="long: "+MhouseHold.lung+"\n";
        s+="Int. name: "+MhouseHold.info_name+"\n";
        s+="Int. sex: "+MhouseHold.info_sex+"\n";
        s+="Int. age: "+MhouseHold.info_age+"\n";
        s+="Mobile: "+MhouseHold.mob+"\n";
        s+="Members: "+MhouseHold.mem+"\n";
        s+="Symptom within 7 days: "+MhouseHold.symptom_7+"\n";
        s+="Members: "+MhouseHold.symptom_7_mem+"\n";
        s+="Patient with in 6 months: "+MhouseHold.symptom_6m+"\n";
        s+="Patients: "+MhouseHold.symptom_7_mem+"\n";
        for (int i=0;i<MhouseHold.participants.size();i++){
            Mparticipant pat = MhouseHold.participants.get(i);
            s+="participant number: "+pat.num+"\n";
            s+="House: "+pat.hh_number+"\n";
            s+="id_number: "+pat.id_number+"\n";
            s+="name : "+pat.name +"\n";
            s+="age: "+pat.age+"\n";
            s+="sex: "+pat.sex+"\n";
            s+="relation: "+pat.relation+"\n";
            s+="comorbidity: "+pat.comorbidity+"\n";
            s+="relation others: "+pat.rel_oth+"\n";
            s+="sick7: "+pat.sick7+"\n";
            s+="fever: "+pat.fever+"\n";
            s+="headache: "+pat.headache+"\n";
            s+="aches: "+pat.aches+"\n";
            s+="mus_pain: "+pat.mus_pain+"\n";
            s+="rash: "+pat.rash+"\n";
            s+="vomit: "+pat.vomit+"\n";
            s+="dia: "+pat.dia+"\n";
            s+="is_test: "+pat.is_test+"\n";
            s+="ns1: "+pat.ns1+"\n";
            s+="igg: "+pat.igg+"\n";
            s+="igm: "+pat.igm+"\n";
            s+="unk_test: "+pat.unk_test+"\n";
            s+="ns1_p: "+pat.ns1_p+"\n";
            s+="igg_p: "+pat.igg_p+"\n";
            s+="igm_p: "+pat.igm_p+"\n";
            s+="ns1_vis: "+pat.ns1_vis+"\n";
            s+="igg_vis: "+pat.igg_vis+"\n";
            s+="igm_vis: "+pat.igm_vis+"\n";
            s+="is_reffer: "+pat.is_reffer+"\n";
            s+="sick6m: "+pat.sick6m+"\n";
            s+="prev_ns1: "+pat.prev_ns1+"\n";
            s+="prev_igg: "+pat.prev_igg+"\n";
            s+="prev_igm: "+pat.prev_igm+"\n";
            s+="prev_unk_test: "+pat.prev_unk_test+"\n";
            s+="prev_ns1_p: "+pat.prev_ns1_p+"\n";
            s+="prev_igg_p: "+pat.prev_igg_p+"\n";
            s+="prev_igm_p: "+pat.prev_igm_p+"\n";
            s+="prev_ns1_vis: "+pat.prev_ns1_vis+"\n";
            s+="prev_igg_vis: "+pat.prev_igg_vis+"\n";
            s+="prev_igm_vis: "+pat.prev_igm_vis+"\n";
            s+="outcome: "+pat.outcome+"\n";
        }
        tv.setText(s);

        referrs.setText(String.valueOf(MhouseHold.reffer));
        is_death.setSelection(MhouseHold.is_death);
        name.setText(MhouseHold.dname);
        age.setText(String.valueOf(MhouseHold.dage));
        if (MhouseHold.is_death==2){
            ((LinearLayout) findViewById(R.id.dname_row)).setVisibility(View.VISIBLE);
            ((LinearLayout) findViewById(R.id.dage_row)).setVisibility(View.VISIBLE);
        }
    }
    private AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (view.getParent()==is_death){
                if (i==2){
                    ((LinearLayout) findViewById(R.id.dname_row)).setVisibility(View.VISIBLE);
                    ((LinearLayout) findViewById(R.id.dage_row)).setVisibility(View.VISIBLE);
                }else{
                    ((LinearLayout) findViewById(R.id.dname_row)).setVisibility(View.GONE);
                    ((LinearLayout) findViewById(R.id.dage_row)).setVisibility(View.GONE);
                }
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId()==submit.getId()){
                if (referrs.getText().toString()==null || referrs.getText().toString().isEmpty()){
                    Toast.makeText(Revision.this,"Write number of patient reffers",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    if (Integer.parseInt(referrs.getText().toString())>MhouseHold.mem){
                        Toast.makeText(Revision.this,"Check number of patient reffers",Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        MhouseHold.reffer = Integer.parseInt(referrs.getText().toString());
                    }
                }
                if(is_death.getSelectedItemPosition()==0){
                    Toast.makeText(Revision.this,"Check for death",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    MhouseHold.is_death = is_death.getSelectedItemPosition();
                    if (is_death.getSelectedItemPosition() == 2){
                        if (name.getText().toString()==null || name.getText().toString().isEmpty()){
                            Toast.makeText(Revision.this,"Write name of death person",Toast.LENGTH_SHORT).show();
                            return;
                        }else{
                            MhouseHold.dname = name.getText().toString();
                        }
                        if (age.getText().toString()==null || age.getText().toString().isEmpty()){
                            Toast.makeText(Revision.this,"Write age of death person",Toast.LENGTH_SHORT).show();
                            return;
                        }else{
                            MhouseHold.dage = Integer.parseInt(age.getText().toString());
                        }

                    }
                }
                ContentValues cv_h = new ContentValues();
                cv_h.put("cc",MhouseHold.cc);
                cv_h.put("ward",MhouseHold.ward);
                cv_h.put("hh",MhouseHold.hh);
                cv_h.put("hh_num",MhouseHold.hh_num);
                cv_h.put("area",MhouseHold.area);
                cv_h.put("lat",MhouseHold.lat);
                cv_h.put("lung",MhouseHold.lung);
                cv_h.put("info_name",MhouseHold.info_name);
                cv_h.put("info_sex",MhouseHold.info_sex);
                cv_h.put("info_age",MhouseHold.info_age);
                cv_h.put("mob",MhouseHold.mob);
                cv_h.put("mem",MhouseHold.mem);
                cv_h.put("symptom_7",MhouseHold.symptom_7);
                cv_h.put("symptom_7_mem",MhouseHold.symptom_7_mem);
                cv_h.put("symptom_6m",MhouseHold.symptom_6m);
                cv_h.put("symptom_6m_mem",MhouseHold.symptom_6m_mem);
                cv_h.put("reffer",MhouseHold.reffer);
                cv_h.put("is_death",MhouseHold.is_death);
                cv_h.put("dname",MhouseHold.dname);
                cv_h.put("dage",MhouseHold.dage);
                cv_h.put("user",sharedPreferences.getInt("id",1));
                ArrayList<ContentValues> contentValues = new ArrayList<>();
                for (int i =0;i<MhouseHold.participants.size();i++){
                    ContentValues cv_p = new ContentValues();
                    Mparticipant mparticipant = MhouseHold.participants.get(i);
                    cv_p.put("num",mparticipant.num);
                    cv_p.put("hh_number",mparticipant.hh_number);
                    cv_p.put("id_number",mparticipant.id_number);
                    cv_p.put("name ",mparticipant.name );
                    cv_p.put("age",mparticipant.age);
                    cv_p.put("sex",mparticipant.sex);
                    cv_p.put("relation",mparticipant.relation);
                    cv_p.put("comorbidity",mparticipant.comorbidity);
                    cv_p.put("rel_oth",mparticipant.rel_oth);
                    cv_p.put("sick7",mparticipant.sick7);
                    cv_p.put("fever",mparticipant.fever);
                    cv_p.put("headache",mparticipant.headache);
                    cv_p.put("aches",mparticipant.aches);
                    cv_p.put("mus_pain",mparticipant.mus_pain);
                    cv_p.put("arth_pain",mparticipant.arth_pain);
                    cv_p.put("rash",mparticipant.rash);
                    cv_p.put("vomit",mparticipant.vomit);
                    cv_p.put("vomit_tendency",mparticipant.vomit_tendency);
                    cv_p.put("dia",mparticipant.dia);
                    cv_p.put("is_test",mparticipant.is_test);
                    cv_p.put("ns1",mparticipant.ns1);
                    cv_p.put("igg",mparticipant.igg);
                    cv_p.put("igm",mparticipant.igm);
                    cv_p.put("unk_test",mparticipant.unk_test);
                    cv_p.put("ns1_p",mparticipant.ns1_p);
                    cv_p.put("igg_p",mparticipant.igg_p);
                    cv_p.put("igm_p",mparticipant.igm_p);
                    cv_p.put("ns1_vis",mparticipant.ns1_vis);
                    cv_p.put("igg_vis",mparticipant.igg_vis);
                    cv_p.put("igm_vis",mparticipant.igm_vis);
                    cv_p.put("is_reffer",mparticipant.is_reffer);
                    cv_p.put("sick6m",mparticipant.sick6m);
                    cv_p.put("doc_list",mparticipant.doc_list);
                    cv_p.put("prev_ns1",mparticipant.prev_ns1);
                    cv_p.put("prev_igg",mparticipant.prev_igg);
                    cv_p.put("prev_igm",mparticipant.prev_igm);
                    cv_p.put("prev_unk_test",mparticipant.prev_unk_test);
                    cv_p.put("prev_ns1_p",mparticipant.prev_ns1_p);
                    cv_p.put("prev_igg_p",mparticipant.prev_igg_p);
                    cv_p.put("prev_igm_p",mparticipant.prev_igm_p);
                    cv_p.put("prev_ns1_vis",mparticipant.prev_ns1_vis);
                    cv_p.put("prev_igg_vis",mparticipant.prev_igg_vis);
                    cv_p.put("prev_igm_vis",mparticipant.prev_igm_vis);
                    cv_p.put("outcome",mparticipant.outcome);
                    cv_p.put("user",sharedPreferences.getInt("id",1));
                    contentValues.add(cv_p);
                }
                MyDB myDB = new MyDB(Revision.this);
                myDB.insert(cv_h,contentValues);
                if(MhouseHold.id>0){
                    MhouseHold.clear();
                }else {
                    int i = sharedPreferences.getInt("hh_num",1);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    i = i+1;
                    Log.d("Revision","Increase index: "+i);
                    editor.putInt("hh_num",i);
                    editor.apply();
                    MhouseHold.clear();
                }
                HouseHold.houseHold.finish();
                finish();
            }
        }
    };
}