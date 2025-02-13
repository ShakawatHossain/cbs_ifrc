package accuratesoft.shakawat.ifrc;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.ChecksSdkIntAtLeast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.BuildCompat;

import java.util.HashMap;

import accuratesoft.shakawat.ifrc.fragments.MultiSelect;
import accuratesoft.shakawat.ifrc.interfaces.MultiInterface;
import accuratesoft.shakawat.ifrc.models.MhouseHold;
import accuratesoft.shakawat.ifrc.models.Mparticipant;
import accuratesoft.shakawat.ifrc.utils.Util;

public class Participant extends AppCompatActivity implements MultiInterface {
    TextView comorbidity,relation_title,comorbidity_title,sick7_title,is_test_title,igm_p_title,outcome_title;
    EditText id_number,name,age,rel_oth_txt,comorbidity_oth_txt,fever_duration,awd_daygap;
    Spinner gen,relation,sick7,is_test,ns1_p,igg_p,igm_p,ns1_vis,igg_vis,igm_vis,sick6m,prev_ns1_p,prev_igg_p,
            prev_igm_p,prev_ns1_vis,prev_igg_vis,prev_igm_vis,outcome,doc_list,temp_measured;
    CheckBox fever,headache,aches,mus_pain,rash,vomit,dia,ns1,igg,igm,unk_test,prev_ns1,prev_igg,prev_igm,prev_unk_test,
                arth_pain,vomit_tendency;
    TableRow sick7_row,symptom_row,is_test_row,test_row,ns1_p_row,igg_p_row,igm_p_row,ns1_vis_row,
            igg_vis_row,igm_vis_row,sick6m_row,prev_test_row,prev_ns1_p_row,prev_igg_p_row,
            prev_igm_p_row,prev_ns1_vis_row,prev_igg_vis_row,prev_igm_vis_row,outcome_row,doc_list_row,
            rel_oth_row,comorbidity_oth_row,fever_duration_row,temp_measured_row;
    Button submit,unfinished;
    Mparticipant mparticipant;
    int index=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant);
        init();
        getData();
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                setBack();
            }
        });

    }
    private void setBack(){
        if (index>0){
            Intent intent = new Intent(Participant.this,Participant.class);
            intent.putExtra("serial",index-1);
            startActivity(intent);
            finish();
        }else {
            finish();
        }
    }
    private void init(){
        Intent intent = getIntent();
        index = intent.getIntExtra("serial",1);
        mparticipant = MhouseHold.participants.get(index);

        id_number = (EditText) findViewById(R.id.id_number);
        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        rel_oth_txt = (EditText) findViewById(R.id.rel_oth_txt);
        comorbidity_oth_txt = (EditText) findViewById(R.id.comorbidity_oth_txt);
        fever_duration = (EditText) findViewById(R.id.fever_duration);
        awd_daygap = (EditText) findViewById(R.id.awd_daygap);

        relation_title = (TextView) findViewById(R.id.relation_title);
        comorbidity_title = (TextView) findViewById(R.id.comorbidity_title);
        sick7_title = (TextView) findViewById(R.id.sick7_title);
        is_test_title = (TextView) findViewById(R.id.is_test_title);
        igm_p_title = (TextView) findViewById(R.id.igm_p_title);
        outcome_title = (TextView) findViewById(R.id.outcome_title);

        gen = (Spinner) findViewById(R.id.gen);
        relation = (Spinner) findViewById(R.id.relation);
        comorbidity = (TextView) findViewById(R.id.comorbidity);
        sick7 = (Spinner) findViewById(R.id.sick7);
        is_test = (Spinner) findViewById(R.id.is_test);
        ns1_p = (Spinner) findViewById(R.id.ns1_p);
        igg_p = (Spinner) findViewById(R.id.igg_p);
        igm_p = (Spinner) findViewById(R.id.igm_p);
        ns1_vis = (Spinner) findViewById(R.id.ns1_vis);
        igg_vis = (Spinner) findViewById(R.id.igg_vis);
        igm_vis = (Spinner) findViewById(R.id.igm_vis);
        sick6m = (Spinner) findViewById(R.id.sick6m);
        prev_ns1_p = (Spinner) findViewById(R.id.prev_ns1_p);
        prev_igg_p = (Spinner) findViewById(R.id.prev_igg_p);
        prev_igm_p = (Spinner) findViewById(R.id.prev_igm_p);
        prev_ns1_vis = (Spinner) findViewById(R.id.prev_ns1_vis);
        prev_igg_vis = (Spinner) findViewById(R.id.prev_igg_vis);
        prev_igm_vis = (Spinner) findViewById(R.id.prev_igm_vis);
        outcome = (Spinner) findViewById(R.id.outcome);
        doc_list = (Spinner) findViewById(R.id.doc_list);
        temp_measured = (Spinner) findViewById(R.id.temp_measured);

        fever = (CheckBox) findViewById(R.id.fever);
        headache = (CheckBox) findViewById(R.id.headache);
        aches = (CheckBox) findViewById(R.id.aches);
        mus_pain = (CheckBox) findViewById(R.id.mus_pain);
        rash = (CheckBox) findViewById(R.id.rash);
        vomit = (CheckBox) findViewById(R.id.vomit);
        dia = (CheckBox) findViewById(R.id.dia);
        ns1 = (CheckBox) findViewById(R.id.ns1);
        igg = (CheckBox) findViewById(R.id.igg);
        igm = (CheckBox) findViewById(R.id.igm);
        unk_test = (CheckBox) findViewById(R.id.unk_test);
        prev_ns1 = (CheckBox) findViewById(R.id.prev_ns1);
        prev_igg = (CheckBox) findViewById(R.id.prev_igg);
        prev_igm = (CheckBox) findViewById(R.id.prev_igm);
        prev_unk_test = (CheckBox) findViewById(R.id.prev_unk_test);
        arth_pain = (CheckBox) findViewById(R.id.arth_pain);
        vomit_tendency = (CheckBox) findViewById(R.id.vomit_tendency);
        sick7_row = (TableRow) findViewById(R.id.sick7_row);
        symptom_row = (TableRow) findViewById(R.id.symptom_row);
        is_test_row = (TableRow) findViewById(R.id.is_test_row);
        test_row = (TableRow) findViewById(R.id.test_row);
        ns1_p_row = (TableRow) findViewById(R.id.ns1_p_row);
        igg_p_row = (TableRow) findViewById(R.id.igg_p_row);
        igm_p_row = (TableRow) findViewById(R.id.igm_p_row);
        ns1_vis_row = (TableRow) findViewById(R.id.ns1_vis_row);
        igg_vis_row = (TableRow) findViewById(R.id.igg_vis_row);
        igm_vis_row = (TableRow) findViewById(R.id.igm_vis_row);
        sick6m_row = (TableRow) findViewById(R.id.sick6m_row);
        prev_test_row = (TableRow) findViewById(R.id.prev_test_row);
        prev_ns1_p_row = (TableRow) findViewById(R.id.prev_ns1_p_row);
        prev_igg_p_row = (TableRow) findViewById(R.id.prev_igg_p_row);
        prev_igm_p_row = (TableRow) findViewById(R.id.prev_igm_p_row);
        prev_ns1_vis_row = (TableRow) findViewById(R.id.prev_ns1_vis_row);
        prev_igg_vis_row = (TableRow) findViewById(R.id.prev_igg_vis_row);
        prev_igm_vis_row = (TableRow) findViewById(R.id.prev_igm_vis_row);
        outcome_row = (TableRow) findViewById(R.id.outcome_row);
        doc_list_row = (TableRow) findViewById(R.id.doc_list_row);
        rel_oth_row = (TableRow) findViewById(R.id.rel_oth_row);
        comorbidity_oth_row = (TableRow) findViewById(R.id.comorbidity_oth_row);
        fever_duration_row = (TableRow) findViewById(R.id.fever_duration_row);
        temp_measured_row = (TableRow) findViewById(R.id.temp_measured_row);
        submit = (Button) findViewById(R.id.submit);
        unfinished = (Button) findViewById(R.id.unfinished);

        ((ScrollView) findViewById(R.id.scroll)).setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                if (id_number.hasFocus()) id_number.clearFocus();
                if(name.hasFocus()) name.clearFocus();
                if(age.hasFocus()) age.clearFocus();
            }
        });
    }
    private void getData(){
        id_number.setText(mparticipant.id_number);
        name.setText(mparticipant.name);
        setTitle();
        if(mparticipant.age>0)
            age.setText(String.valueOf(mparticipant.age));
        gen.setSelection(mparticipant.sex);
        relation.setSelection(mparticipant.relation);
//        comorbidity.setText(mparticipant.comorbidity);
        rel_oth_txt.setText(mparticipant.rel_oth);
        sick7.setSelection(mparticipant.sick7);
        is_test.setSelection(mparticipant.is_test);
        ns1_p.setSelection(mparticipant.ns1_p);
        igg_p.setSelection(mparticipant.igg_p);
        igm_p.setSelection(mparticipant.igm_p);
        ns1_vis.setSelection(mparticipant.ns1_vis);
        igg_vis.setSelection(mparticipant.igg_vis);
        igm_vis.setSelection(mparticipant.igm_vis);
        sick6m.setSelection(mparticipant.sick6m);
        doc_list.setSelection(mparticipant.doc_list);
        prev_ns1_p.setSelection(mparticipant.prev_ns1_p);
        prev_igg_p.setSelection(mparticipant.prev_igg_p);
        prev_igm_p.setSelection(mparticipant.prev_igm_p);
        prev_ns1_vis.setSelection(mparticipant.prev_ns1_vis);
        prev_igg_vis.setSelection(mparticipant.prev_igg_vis);
        prev_igm_vis.setSelection(mparticipant.prev_igm_vis);
        outcome.setSelection(mparticipant.outcome);
        if (mparticipant.fever==1)  fever.setChecked(true);
        if (mparticipant.headache==1) headache.setChecked(true);
        if (mparticipant.aches==1) aches.setChecked(true);
        if (mparticipant.mus_pain==1) mus_pain.setChecked(true);
        if (mparticipant.arth_pain==1) arth_pain.setChecked(true);
        if (mparticipant.rash==1) rash.setChecked(true);
        if (mparticipant.vomit==1) vomit.setChecked(true);
        if (mparticipant.vomit_tendency==1) vomit_tendency.setChecked(true);
        if (mparticipant.dia==1) dia.setChecked(true);
        if (mparticipant.ns1==1) ns1.setChecked(true);
        if (mparticipant.igg==1) igg.setChecked(true);
        if (mparticipant.igm==1) igm.setChecked(true);
        if (mparticipant.unk_test==1) unk_test.setChecked(true);
        if (mparticipant.prev_ns1==1) prev_ns1.setChecked(true);
        if (mparticipant.prev_igg==1) prev_igg.setChecked(true);
        if (mparticipant.prev_igm==1) prev_igm.setChecked(true);
        if (mparticipant.prev_unk_test==1) prev_unk_test.setChecked(true);

        comorbidity_oth_txt.setText(mparticipant.comorbidity_oth_txt);
        fever_duration.setText(mparticipant.fever_duration);
        awd_daygap.setText(mparticipant.awd_daygap);
        temp_measured.setSelection(mparticipant.temp_measured);
        setText(mparticipant.comorbidity,comorbidity);
        relation.setOnItemSelectedListener(itemSelectedListener);
        sick7.setOnItemSelectedListener(itemSelectedListener);
        igm_p.setOnItemSelectedListener(itemSelectedListener);
        is_test.setOnItemSelectedListener(itemSelectedListener);
        sick6m.setOnItemSelectedListener(itemSelectedListener);
        ns1.setOnCheckedChangeListener(checkedChangeListener);
        igg.setOnCheckedChangeListener(checkedChangeListener);
        igm.setOnCheckedChangeListener(checkedChangeListener);
        unk_test.setOnCheckedChangeListener(checkedChangeListener);
        prev_ns1.setOnCheckedChangeListener(checkedChangeListener);
        prev_igg.setOnCheckedChangeListener(checkedChangeListener);
        prev_igm.setOnCheckedChangeListener(checkedChangeListener);
        prev_unk_test.setOnCheckedChangeListener(checkedChangeListener);
        comorbidity.setOnClickListener(clickListener);
        submit.setOnClickListener(clickListener);
        unfinished.setOnClickListener(clickListener);

        decideVisibility();
    }
    private void decideVisibility(){
//        if (MhouseHold.symptom_7==2)
            sick7_row.setVisibility(View.VISIBLE);
        if (mparticipant.relation==11) rel_oth_row.setVisibility(View.VISIBLE);
        if (mparticipant.sick7==2){
            symptom_row.setVisibility(View.VISIBLE);
//            is_test_row.setVisibility(View.VISIBLE);
            fever_duration_row.setVisibility(View.VISIBLE);
            temp_measured_row.setVisibility(View.VISIBLE);
        }
        if (mparticipant.is_test==2) test_row.setVisibility(View.VISIBLE);
        if (mparticipant.ns1==1){
            ns1_p_row.setVisibility(View.VISIBLE);
            ns1_vis_row.setVisibility(View.VISIBLE);
        }
        if (mparticipant.igg==1){
            igg_p_row.setVisibility(View.VISIBLE);
            igg_vis_row.setVisibility(View.VISIBLE);
        }
        if (mparticipant.igm==1){
            igm_p_row.setVisibility(View.VISIBLE);
            igm_vis_row.setVisibility(View.VISIBLE);
        }
        if(MhouseHold.symptom_6m==2)
            sick6m_row.setVisibility(View.VISIBLE);
        if(mparticipant.sick6m==2) {
            doc_list_row.setVisibility(View.VISIBLE);
            prev_test_row.setVisibility(View.VISIBLE);
            outcome_row.setVisibility(View.VISIBLE);
        }
        if (mparticipant.prev_ns1==1){
            prev_ns1_p_row.setVisibility(View.VISIBLE);
            prev_ns1_vis_row.setVisibility(View.VISIBLE);
        }
        if (mparticipant.prev_igg==1){
            prev_igg_p_row.setVisibility(View.VISIBLE);
            prev_igg_vis_row.setVisibility(View.VISIBLE);
        }
        if (mparticipant.prev_igm==1){
            prev_igm_p_row.setVisibility(View.VISIBLE);
            prev_igm_vis_row.setVisibility(View.VISIBLE);
        }
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                setTitle();
            }
        });
    }
    private void setTitle(){
        relation_title.setText("পরিবারের প্রধানের সাথে "+name.getText().toString()+" এর সম্পর্ক");
        comorbidity_title.setText(" "+name.getText().toString()+" এর কি কোনো কোমরবিডিটি (দীর্ঘমেয়াদী কোনো শারীরিক অসুবিধা) রয়েছে");
        sick7_title.setText(" "+name.getText().toString()+" এর কি গত ১০ দিনের মধ্যে (আজকে সহ) জ্বর ছিল?");
        is_test_title.setText("গত ১ মাসের মধ্যে "+name.getText().toString()+" এর কি পাতলা পায়খানা হয়েছে?");
        igm_p_title.setText("গত ১৪ দিনে "+name.getText().toString()+" এর কি ত্বক বা চোখের সাদা অংশ হলুদ বর্ণ বা প্রস্রাব রং গাঢ় বর্ণ ধারন করেছে?");
        outcome_title.setText("শেষের ১০ দিন বাদ দিয়ে গত ১ মাসের মধ্যে "+name.getText().toString()+" এর কি উপরের এমন কোনো লক্ষণ দেখা গিয়েছে?");
    }
    private AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (view!=null) {
                if (view.getParent() == relation) {
                    if (i == 11) {
                        rel_oth_row.setVisibility(View.VISIBLE);
                    } else {
                        rel_oth_row.setVisibility(View.GONE);
                    }
                } else if (view.getParent() == sick7) {
                    if (i == 2) {
                        symptom_row.setVisibility(View.VISIBLE);
//                    is_test_row.setVisibility(View.VISIBLE);
                        fever_duration_row.setVisibility(View.VISIBLE);
                        temp_measured_row.setVisibility(View.VISIBLE);
                    } else {
                        symptom_row.setVisibility(View.GONE);
//                    is_test_row.setVisibility(View.GONE);
                        fever_duration_row.setVisibility(View.GONE);
                        temp_measured_row.setVisibility(View.GONE);
                    }
                } else if (view.getParent() == is_test) {
                    if (i == 2) {
                        test_row.setVisibility(View.VISIBLE);
                    } else {
                        test_row.setVisibility(View.GONE);
                    }
                } else if (view.getParent() == sick6m) {
                    if (i == 2) {
                        prev_test_row.setVisibility(View.VISIBLE);
                        outcome_row.setVisibility(View.VISIBLE);
                        doc_list_row.setVisibility(View.VISIBLE);
                    } else {
                        prev_test_row.setVisibility(View.GONE);
//                    outcome_row.setVisibility(View.GONE);
                        doc_list_row.setVisibility(View.GONE);
                    }
                } else if (view.getParent() == igm_p) {
                    if (i == 2) {
                        igm_vis_row.setVisibility(View.VISIBLE);
                    } else {
                        igm_vis_row.setVisibility(View.GONE);
                    }
                }
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    private CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (compoundButton.getId()==ns1.getId()){
                if (b){
                    ns1_p_row.setVisibility(View.VISIBLE);
                    ns1_vis_row.setVisibility(View.VISIBLE);
                }else{
                    ns1_p_row.setVisibility(View.GONE);
                    ns1_vis_row.setVisibility(View.GONE);
                }
            }else if (compoundButton.getId()==igg.getId()){
                if (b){
                    igg_p_row.setVisibility(View.VISIBLE);
                    igg_vis_row.setVisibility(View.VISIBLE);
                }else{
                    igg_p_row.setVisibility(View.GONE);
                    igg_vis_row.setVisibility(View.GONE);
                }
            }else if (compoundButton.getId()==igm.getId()){
//                due to change componenet
//                if (b){
//                    igm_p_row.setVisibility(View.VISIBLE);
//                    igm_vis_row.setVisibility(View.VISIBLE);
//                }else{
//                    igm_p_row.setVisibility(View.GONE);
//                    igm_vis_row.setVisibility(View.GONE);
//                }
            }
            if (compoundButton.getId()==prev_ns1.getId()){
                if (b){
                    prev_ns1_p_row.setVisibility(View.VISIBLE);
                    prev_ns1_vis_row.setVisibility(View.VISIBLE);
                }else{
                    prev_ns1_p_row.setVisibility(View.GONE);
                    prev_ns1_vis_row.setVisibility(View.GONE);
                }
            }else if (compoundButton.getId()==prev_igg.getId()){
                if (b){
                    prev_igg_p_row.setVisibility(View.VISIBLE);
                    prev_igg_vis_row.setVisibility(View.VISIBLE);
                }else{
                    prev_igg_p_row.setVisibility(View.GONE);
                    prev_igg_vis_row.setVisibility(View.GONE);
                }
            }else if (compoundButton.getId()==prev_igm.getId()){
                if (b){
                    prev_igm_p_row.setVisibility(View.VISIBLE);
                    prev_igm_vis_row.setVisibility(View.VISIBLE);
                }else{
                    prev_igm_p_row.setVisibility(View.GONE);
                    prev_igm_vis_row.setVisibility(View.GONE);
                }
            }
        }
    };
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId()==submit.getId()){
                if(check()){
                    saveData();
                    Util.makeToast(Participant.this,"Please wait Loading UI...");
                    if (index<MhouseHold.participants.size()-1){
                        Intent intent = new Intent(Participant.this,Participant.class);
                        intent.putExtra("serial",index+1);
                        startActivity(intent);
                        finish();
                    }else {
                        startActivity(new Intent(Participant.this,Revision.class));
                        finish();
                    }
                }
            }else if (view.getId()==unfinished.getId()){
                saveData();
                startActivity(new Intent(Participant.this,Revision.class));
                finish();
            }else if(view.getId()==comorbidity.getId()){
                HashMap<String,String> params = new HashMap<>();
                params.put("0","দীর্ঘমেয়াদি কোনো রোগ নাই");
                params.put("1","হাঁপানি ");
                params.put("2","ডায়াবেটিস");
                params.put("3","উচ্চ রক্তচাপ");
                params.put("4","হৃদরোগ");
                params.put("5","স্ট্রোক");
                params.put("6","কিডনি রোগ");
                params.put("7","এলার্জি");
                params.put("8","যকৃতের রোগ");
                params.put("9","ক্যান্সার");
                params.put("10","কিডনি রোগ");
                params.put("11","যেকোনো শারীরিক অক্ষমতা");
                params.put("12","মানসিক অক্ষমতা");
                params.put("13","ফুসফুসের যেকোনো রোগ");
                params.put("14","বাতব্যথা");
                params.put("15","অন্যান্য (অনুগ্রহ করে উল্লেখ করুন)");
                new MultiSelect(Participant.this,Participant.this,
                        comorbidity.getText().toString(),params,comorbidity,"comorbidity").show();
            }
        }
    };
    private boolean check(){
        boolean is_ok=true;
        if (name.getText().toString()==null || name.getText().toString().isEmpty()){
            Toast.makeText(Participant.this,"Write name",Toast.LENGTH_SHORT).show();
            is_ok=false;
        }
        if(age.getText().toString()==null || age.getText().toString().isEmpty()){
            Toast.makeText(Participant.this,"Write age",Toast.LENGTH_SHORT).show();
            is_ok=false;
        }
        if(comorbidity.getText().toString()==null || comorbidity.getText().toString().isEmpty()){
            Toast.makeText(Participant.this,"Select comorbidity",Toast.LENGTH_SHORT).show();
            is_ok=false;
        }
        if (gen.getSelectedItemPosition()==0){
            Toast.makeText(Participant.this,"Select gender",Toast.LENGTH_SHORT).show();
            is_ok=false;
        }
        if (relation.getSelectedItemPosition()==0){
            Toast.makeText(Participant.this,"Select relationship",Toast.LENGTH_SHORT).show();
            is_ok=false;
        }
        if (comorbidity.getText().toString().length()==0){
            Toast.makeText(Participant.this,"Select comorbidity",Toast.LENGTH_SHORT).show();
            is_ok=false;
        }
//        if(MhouseHold.symptom_7==2){
        if(sick7.getSelectedItemPosition()==0){
            Toast.makeText(Participant.this,"Select fever within 10 days",Toast.LENGTH_SHORT).show();
            is_ok=false;
        }
        if(sick7.getSelectedItemPosition()==2) {
            if (fever_duration.getText().toString().length() == 0) {
                Toast.makeText(Participant.this, "Write fever duration", Toast.LENGTH_SHORT).show();
                is_ok = false;
            }
            if (temp_measured.getSelectedItemPosition()==0){
                Toast.makeText(Participant.this,"Select temperature",Toast.LENGTH_SHORT).show();
                is_ok=false;
            }
//            if(!fever.isChecked() && !headache.isChecked() && !aches.isChecked() && !mus_pain.isChecked()
//                    && !rash.isChecked() && !vomit.isChecked() && !dia.isChecked() && !arth_pain.isChecked()
//                    && !vomit_tendency.isChecked()){
//                Toast.makeText(Participant.this,"Select symptom with fever",Toast.LENGTH_SHORT).show();
//                is_ok=false;
//            }
        }
        if (is_test.getSelectedItemPosition() == 0) {
            Toast.makeText(Participant.this, "Select diarrhoea status within last month", Toast.LENGTH_SHORT).show();
            is_ok = false;
        }
        if (is_test.getSelectedItemPosition()==2 && awd_daygap.getText().toString().isEmpty()){
            Toast.makeText(Participant.this, "Write how long ago the diarrhoea occurs?", Toast.LENGTH_SHORT).show();
            is_ok = false;
        }
        if (is_test.getSelectedItemPosition()==2){
            if(!igm.isChecked() && !unk_test.isChecked()){
                Toast.makeText(Participant.this, "Select frequency of passing liquid stool?", Toast.LENGTH_SHORT).show();
                is_ok = false;
            }
        }
        if(is_test.getSelectedItemPosition()==2){
            if (unk_test.isChecked()){
                if (ns1.isChecked() || igg.isChecked() || igm.isChecked()){
                    Toast.makeText(Participant.this,"Select diarrhoea in 24 hours",Toast.LENGTH_SHORT).show();
                    is_ok=false;
                }
            }
            if (ns1.isChecked()){
                if (ns1_p.getSelectedItemPosition()==0 || ns1_vis.getSelectedItemPosition()==0){
                    Toast.makeText(Participant.this,"Select ns1 result and report",Toast.LENGTH_SHORT).show();
                    is_ok=false;
                }
            }
            if (igg.isChecked()){
                if (igg_p.getSelectedItemPosition()==0 || igg_vis.getSelectedItemPosition()==0){
                    Toast.makeText(Participant.this,"Select igg result and report",Toast.LENGTH_SHORT).show();
                    is_ok=false;
                }
            }
            if (igm.isChecked()){
//            if (igm_p.getSelectedItemPosition()==0 || igm_vis.getSelectedItemPosition()==0){
//                Toast.makeText(Participant.this,"Select igm result and report",Toast.LENGTH_SHORT).show();
//                is_ok=false;
//            }
            }
        }
//        }
        if(igm_p.getSelectedItemPosition()==0){
            Toast.makeText(Participant.this,"Select jaundice status",Toast.LENGTH_SHORT).show();
            is_ok=false;
        } else if (igm_p.getSelectedItemPosition() == 2 && igm_vis.getSelectedItemPosition()==0) {
            Util.makeToast(Participant.this,"select jaundice duration");
            is_ok=false;
        }
        if(MhouseHold.symptom_6m==2){
            if(sick6m.getSelectedItemPosition()==0){
                Toast.makeText(Participant.this,"Select dengue patients within 6 months",Toast.LENGTH_SHORT).show();
                is_ok=false;
            }
            if (prev_unk_test.isChecked()){
                if (prev_ns1.isChecked() || prev_igg.isChecked() || prev_igm.isChecked()){
                    Toast.makeText(Participant.this,"Select test performed with in 6 months",Toast.LENGTH_SHORT).show();
                    is_ok=false;
                }
            }
            if (prev_ns1.isChecked()){
                if (prev_ns1_p.getSelectedItemPosition()==0 || prev_ns1_vis.getSelectedItemPosition()==0){
                    Toast.makeText(Participant.this,"Select ns1 result and report within 6 months",Toast.LENGTH_SHORT).show();
                    is_ok=false;
                }
            }
            if (prev_igg.isChecked()){
                if (prev_igg_p.getSelectedItemPosition()==0 || prev_igg_vis.getSelectedItemPosition()==0){
                    Toast.makeText(Participant.this,"Select igg result and report with in 6 months",Toast.LENGTH_SHORT).show();
                    is_ok=false;
                }
            }
            if (prev_igm.isChecked()){
                if (prev_igm_p.getSelectedItemPosition()==0 || prev_igm_vis.getSelectedItemPosition()==0){
                    Toast.makeText(Participant.this,"Select igm result and report with in 6 months",Toast.LENGTH_SHORT).show();
                    is_ok=false;
                }
            }
            if (sick6m.getSelectedItemPosition()==2){
                if (outcome.getSelectedItemPosition()==0){
                    Toast.makeText(Participant.this,"Select outcome with in 6 months",Toast.LENGTH_SHORT).show();
                    is_ok=false;
                }
                if (doc_list.getSelectedItemPosition()==0){
                    Toast.makeText(Participant.this,"Select type of doctor identified dengue",Toast.LENGTH_SHORT).show();
                    is_ok=false;
                }
            }
        }
        if (outcome.getSelectedItemPosition()==0){
            Toast.makeText(Participant.this,"Select status of last month except last 10 days",Toast.LENGTH_SHORT).show();
            is_ok=false;
        }
        return is_ok;
    }
    private void saveData(){
        mparticipant.name = name.getText().toString();
        if(age.getText().toString()==null || age.getText().toString().isEmpty()) mparticipant.age =0;
        else mparticipant.age = Integer.parseInt(age.getText().toString());
        mparticipant.sex = gen.getSelectedItemPosition();
        mparticipant.relation = relation.getSelectedItemPosition();
        mparticipant.comorbidity = comorbidity.getText().toString();
        mparticipant.rel_oth = rel_oth_txt.getText().toString();
        mparticipant.sick7 = sick7.getSelectedItemPosition();
        mparticipant.comorbidity_oth_txt = comorbidity_oth_txt.getText().toString();
        mparticipant.fever = fever.isChecked()?1:0;
        mparticipant.headache = headache.isChecked()?1:0;
        mparticipant.aches = aches.isChecked()?1:0;
        mparticipant.mus_pain = mus_pain.isChecked()?1:0;
        mparticipant.arth_pain = arth_pain.isChecked()?1:0;
        mparticipant.rash = rash.isChecked()?1:0;
        mparticipant.vomit = vomit.isChecked()?1:0;
        mparticipant.vomit_tendency = vomit_tendency.isChecked()?1:0;
        mparticipant.dia = dia.isChecked()?1:0;
        mparticipant.is_test = is_test.getSelectedItemPosition();
        mparticipant.ns1 = ns1.isChecked()?1:0;
        mparticipant.igg = igg.isChecked()?1:0;
        mparticipant.igm = igm.isChecked()?1:0;
        mparticipant.unk_test = unk_test.isChecked()?1:0;
        mparticipant.sick6m = sick6m.getSelectedItemPosition();
        mparticipant.doc_list = doc_list.getSelectedItemPosition();
        mparticipant.prev_ns1 = prev_ns1.isChecked()?1:0;
        mparticipant.prev_igg = prev_igg.isChecked()?1:0;
        mparticipant.prev_igm = prev_igm.isChecked()?1:0;
        mparticipant.prev_unk_test = prev_unk_test.isChecked()?1:0;
        mparticipant.ns1_p = ns1_p.getSelectedItemPosition();
        mparticipant.igg_p = igg_p.getSelectedItemPosition();
        mparticipant.igm_p = igm_p.getSelectedItemPosition();
        mparticipant.ns1_vis = ns1_vis.getSelectedItemPosition();
        mparticipant.igg_vis = igg_vis.getSelectedItemPosition();
        mparticipant.igm_vis = igm_vis.getSelectedItemPosition();
        mparticipant.prev_ns1_p = prev_ns1_p.getSelectedItemPosition();
        mparticipant.prev_igg_p = prev_igg_p.getSelectedItemPosition();
        mparticipant.prev_igm_p = prev_igm_p.getSelectedItemPosition();
        mparticipant.prev_ns1_vis = prev_ns1_vis.getSelectedItemPosition();
        mparticipant.prev_igg_vis = prev_igg_vis.getSelectedItemPosition();
        mparticipant.prev_igm_vis = prev_igm_vis.getSelectedItemPosition();
        mparticipant.outcome = outcome.getSelectedItemPosition();
        mparticipant.fever_duration = fever_duration.getText().toString();
        mparticipant.temp_measured = temp_measured.getSelectedItemPosition();
        mparticipant.awd_daygap = awd_daygap.getText().toString();
    }
    @Override
    public void setText(String selected, TextView tv) {
        tv.setText(selected);
        if (selected.length()>0){
            String[] parts = selected. split(",");
            comorbidity_oth_row.setVisibility(View.GONE);
            for (String part : parts){
                if(part.compareTo("15")==0){
                    comorbidity_oth_row.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}