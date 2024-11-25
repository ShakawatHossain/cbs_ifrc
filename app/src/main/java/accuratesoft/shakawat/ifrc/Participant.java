package accuratesoft.shakawat.ifrc;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import androidx.annotation.ChecksSdkIntAtLeast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.BuildCompat;

import accuratesoft.shakawat.ifrc.models.MhouseHold;
import accuratesoft.shakawat.ifrc.models.Mparticipant;

public class Participant extends AppCompatActivity {
    EditText id_number,name,age,rel_oth_txt;
    Spinner gen,relation,comorbidity,sick7,is_test,ns1_p,igg_p,igm_p,ns1_vis,igg_vis,igm_vis,sick6m,prev_ns1_p,prev_igg_p,
            prev_igm_p,prev_ns1_vis,prev_igg_vis,prev_igm_vis,outcome,doc_list;
    CheckBox fever,headache,aches,mus_pain,rash,vomit,dia,ns1,igg,igm,unk_test,prev_ns1,prev_igg,prev_igm,prev_unk_test,
                arth_pain,vomit_tendency;
    TableRow sick7_row,symptom_row,is_test_row,test_row,ns1_p_row,igg_p_row,igm_p_row,ns1_vis_row,
            igg_vis_row,igm_vis_row,sick6m_row,prev_test_row,prev_ns1_p_row,prev_igg_p_row,
            prev_igm_p_row,prev_ns1_vis_row,prev_igg_vis_row,prev_igm_vis_row,outcome_row,doc_list_row,
            rel_oth_row;
    Button submit,unfinished;
    Mparticipant mparticipant;
    int index=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant);
        init();
        getData();
        if (android.os.Build.VERSION.SDK_INT>=33){
            setBack();
        }
    }

    private void setBack(){
        if (BuildCompat.isAtLeastT()) {
            getOnBackInvokedDispatcher()
                    .registerOnBackInvokedCallback(
                            OnBackInvokedDispatcher.PRIORITY_DEFAULT,
                            ()
                                    -> {
                                if (index>0){
                                    Intent intent = new Intent(Participant.this,Participant.class);
                                    intent.putExtra("serial",index-1);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    finish();
                                }
                            });
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
        gen = (Spinner) findViewById(R.id.gen);
        relation = (Spinner) findViewById(R.id.relation);
        comorbidity = (Spinner) findViewById(R.id.comorbidity);
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
        age.setText(String.valueOf(mparticipant.age));
        gen.setSelection(mparticipant.sex);
        relation.setSelection(mparticipant.relation);
        comorbidity.setSelection(mparticipant.comorbidity);
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

        relation.setOnItemSelectedListener(itemSelectedListener);
        sick7.setOnItemSelectedListener(itemSelectedListener);
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
        decideVisibility();
        submit.setOnClickListener(clickListener);
        unfinished.setOnClickListener(clickListener);
    }
    private void decideVisibility(){
        if (MhouseHold.symptom_7==2)    sick7_row.setVisibility(View.VISIBLE);
        if (mparticipant.relation==10) rel_oth_row.setVisibility(View.VISIBLE);
        if (mparticipant.sick7==2){
            symptom_row.setVisibility(View.VISIBLE);
            is_test_row.setVisibility(View.VISIBLE);
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
        if(MhouseHold.symptom_6m==2) sick6m_row.setVisibility(View.VISIBLE);
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
    }
    private AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (view.getParent()==relation){
                if (i==10){
                    rel_oth_row.setVisibility(View.VISIBLE);
                }else {
                    rel_oth_row.setVisibility(View.GONE);
                }
            } else if (view.getParent()==sick7){
                if(i==2){
                    symptom_row.setVisibility(View.VISIBLE);
                    is_test_row.setVisibility(View.VISIBLE);
                }else{
                    symptom_row.setVisibility(View.GONE);
                    is_test_row.setVisibility(View.GONE);
                }
            } else if (view.getParent()==is_test) {
                if (i==2){
                    test_row.setVisibility(View.VISIBLE);
                }else {
                    test_row.setVisibility(View.GONE);
                }
            } else if (view.getParent()==sick6m) {
                if (i==2){
                    prev_test_row.setVisibility(View.VISIBLE);
                    outcome_row.setVisibility(View.VISIBLE);
                    doc_list_row.setVisibility(View.VISIBLE);
                }else{
                    prev_test_row.setVisibility(View.GONE);
                    outcome_row.setVisibility(View.GONE);
                    doc_list_row.setVisibility(View.GONE);
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
                if (b){
                    igm_p_row.setVisibility(View.VISIBLE);
                    igm_vis_row.setVisibility(View.VISIBLE);
                }else{
                    igm_p_row.setVisibility(View.GONE);
                    igm_vis_row.setVisibility(View.GONE);
                }
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
            }if (view.getId()==unfinished.getId()){
                saveData();
                startActivity(new Intent(Participant.this,Revision.class));
                finish();
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
        if (gen.getSelectedItemPosition()==0){
            Toast.makeText(Participant.this,"Select gender",Toast.LENGTH_SHORT).show();
            is_ok=false;
        }
        if (relation.getSelectedItemPosition()==0){
            Toast.makeText(Participant.this,"Select relationship",Toast.LENGTH_SHORT).show();
            is_ok=false;
        }
        if (comorbidity.getSelectedItemPosition()==0){
            Toast.makeText(Participant.this,"Select comorbidity",Toast.LENGTH_SHORT).show();
            is_ok=false;
        }
        if(MhouseHold.symptom_7==2){
            if(sick7.getSelectedItemPosition()==0){
                Toast.makeText(Participant.this,"Select dengue symptom within 7 days",Toast.LENGTH_SHORT).show();
                is_ok=false;
            }
            if(sick7.getSelectedItemPosition()==2) {
                if (is_test.getSelectedItemPosition() == 0) {
                    Toast.makeText(Participant.this, "Select test status within 7 days", Toast.LENGTH_SHORT).show();
                    is_ok = false;
                }
                if(!fever.isChecked() && !headache.isChecked() && !aches.isChecked() && !mus_pain.isChecked()
                        && !rash.isChecked() && !vomit.isChecked() && !dia.isChecked() && !arth_pain.isChecked()
                        && !vomit_tendency.isChecked()){
                    Toast.makeText(Participant.this,"Select dengue symptom within 7 days",Toast.LENGTH_SHORT).show();
                    is_ok=false;
                }
            }
            if (unk_test.isChecked()){
                if (ns1.isChecked() || igg.isChecked() || igm.isChecked()){
                    Toast.makeText(Participant.this,"Select test performed with in 7days",Toast.LENGTH_SHORT).show();
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
                if (igm_p.getSelectedItemPosition()==0 || igm_vis.getSelectedItemPosition()==0){
                    Toast.makeText(Participant.this,"Select igm result and report",Toast.LENGTH_SHORT).show();
                    is_ok=false;
                }
            }
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


        return is_ok;
    }
    private void saveData(){
        mparticipant.name = name.getText().toString();
        mparticipant.age = Integer.parseInt(age.getText().toString());
        mparticipant.sex = gen.getSelectedItemPosition();
        mparticipant.relation = relation.getSelectedItemPosition();
        mparticipant.comorbidity = comorbidity.getSelectedItemPosition();
        mparticipant.rel_oth = rel_oth_txt.getText().toString();
        mparticipant.sick7 = sick7.getSelectedItemPosition();
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
    }
    @NonNull
    @Override
    public OnBackInvokedDispatcher getOnBackInvokedDispatcher() {
        Log.d("Participant","Backpressed");
//        if (index>0){
//            Intent intent = new Intent(Participant.this,Participant.class);
//            intent.putExtra("serial",index-1);
//            startActivity(intent);
//            finish();
//        }else {
//            finish();
//        }
        return super.getOnBackInvokedDispatcher();
    }

    @Deprecated
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (index>0){
            Intent intent = new Intent(Participant.this,Participant.class);
            intent.putExtra("serial",index-1);
            startActivity(intent);
            finish();
        }else {
            finish();
        }
    }
    //    @Override
//    protected void onStop() {
//        super.onStop();
//        if (index>0){
//            Intent intent = new Intent(Participant.this,Participant.class);
//            intent.putExtra("serial",index-1);
//            startActivity(intent);
//            finish();
//        }else {
//            finish();
//        }
//    }
}