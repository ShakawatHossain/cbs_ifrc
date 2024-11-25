package accuratesoft.shakawat.ifrc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.Toast;

import accuratesoft.shakawat.ifrc.models.Mfollowup;
import accuratesoft.shakawat.ifrc.models.MhouseHold;
import accuratesoft.shakawat.ifrc.utils.MyDB;
import accuratesoft.shakawat.ifrc.utils.Util;

public class Followup extends AppCompatActivity {

    EditText id_number,name,age;
    Spinner gen,relation,sick7,is_test,ns1_p,igg_p,igm_p,ns1_vis,igg_vis,igm_vis,is_doc_visit,doc_list;
    CheckBox fever,headache,aches,mus_pain,rash,vomit,dia,ns1,igg,igm,unk_test,arth_pain,vomit_tendency;
    TableRow sick7_row,symptom_row,is_test_row,test_row,ns1_p_row,igg_p_row,igm_p_row,ns1_vis_row,
            igg_vis_row,igm_vis_row,is_doc_visit_row,doc_list_row;
    Button submit;
    MyDB myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followup);
        init();
        getData();
    }
    private void init(){
        id_number = (EditText) findViewById(R.id.id_number);
        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        gen = (Spinner) findViewById(R.id.gen);
        relation = (Spinner) findViewById(R.id.relation);
        sick7 = (Spinner) findViewById(R.id.sick7);
        is_test = (Spinner) findViewById(R.id.is_test);
        ns1_p = (Spinner) findViewById(R.id.ns1_p);
        igg_p = (Spinner) findViewById(R.id.igg_p);
        igm_p = (Spinner) findViewById(R.id.igm_p);
        ns1_vis = (Spinner) findViewById(R.id.ns1_vis);
        igg_vis = (Spinner) findViewById(R.id.igg_vis);
        igm_vis = (Spinner) findViewById(R.id.igm_vis);
        is_doc_visit = (Spinner) findViewById(R.id.is_doc_visit);
        doc_list = (Spinner) findViewById(R.id.doc_list);
        fever = (CheckBox) findViewById(R.id.fever);
        headache = (CheckBox) findViewById(R.id.headache);
        aches = (CheckBox) findViewById(R.id.aches);
        mus_pain = (CheckBox) findViewById(R.id.mus_pain);
        arth_pain = (CheckBox) findViewById(R.id.arth_pain);
        rash = (CheckBox) findViewById(R.id.rash);
        vomit = (CheckBox) findViewById(R.id.vomit);
        vomit_tendency = (CheckBox) findViewById(R.id.vomit_tendency);
        dia = (CheckBox) findViewById(R.id.dia);
        ns1 = (CheckBox) findViewById(R.id.ns1);
        igg = (CheckBox) findViewById(R.id.igg);
        igm = (CheckBox) findViewById(R.id.igm);
        unk_test = (CheckBox) findViewById(R.id.unk_test);
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
        is_doc_visit_row = (TableRow) findViewById(R.id.is_doc_visit_row);
        doc_list_row = (TableRow) findViewById(R.id.doc_list_row);
        submit = (Button) findViewById(R.id.submit);
        myDB = new MyDB(Followup.this);
    }
    private void getData(){
        id_number.setText(Mfollowup.id_number);
        name.setText(Mfollowup.name);
        age.setText(String.valueOf(Mfollowup.age));

        gen.setSelection(Mfollowup.sex);
        relation.setSelection(Mfollowup.relation);
        sick7.setSelection(Mfollowup.sick7);
        is_test.setSelection(Mfollowup.is_test);
        ns1_p.setSelection(Mfollowup.ns1_p);
        igg_p.setSelection(Mfollowup.igg_p);
        igm_p.setSelection(Mfollowup.igm_p);
        ns1_vis.setSelection(Mfollowup.ns1_vis);
        igg_vis.setSelection(Mfollowup.igg_vis);
        igm_vis.setSelection(Mfollowup.igm_vis);
        is_doc_visit.setSelection(Mfollowup.is_doc_visit);
        doc_list.setSelection(Mfollowup.doc_list);
        
        if (Mfollowup.fever==1)  fever.setChecked(true);
        if (Mfollowup.headache==1) headache.setChecked(true);
        if (Mfollowup.aches==1) aches.setChecked(true);
        if (Mfollowup.mus_pain==1) mus_pain.setChecked(true);
        if (Mfollowup.arth_pain==1) arth_pain.setChecked(true);
        if (Mfollowup.rash==1) rash.setChecked(true);
        if (Mfollowup.vomit==1) vomit.setChecked(true);
        if (Mfollowup.vomit_tendency==1) vomit_tendency.setChecked(true);
        if (Mfollowup.dia==1) dia.setChecked(true);
        if (Mfollowup.ns1==1) ns1.setChecked(true);
        if (Mfollowup.igg==1) igg.setChecked(true);
        if (Mfollowup.igm==1) igm.setChecked(true);
        if (Mfollowup.unk_test==1) unk_test.setChecked(true);

        sick7.setOnItemSelectedListener(itemSelectedListener);
        is_test.setOnItemSelectedListener(itemSelectedListener);
        is_doc_visit.setOnItemSelectedListener(itemSelectedListener);
        ns1.setOnCheckedChangeListener(checkedChangeListener);
        igg.setOnCheckedChangeListener(checkedChangeListener);
        igm.setOnCheckedChangeListener(checkedChangeListener);
        unk_test.setOnCheckedChangeListener(checkedChangeListener);

        
        decideVisibility();
        submit.setOnClickListener(clickListener);
    }
    private void decideVisibility(){
        if (Mfollowup.sick7==2){
            symptom_row.setVisibility(View.VISIBLE);
            is_test_row.setVisibility(View.VISIBLE);
        }
        if (Mfollowup.is_test==2) test_row.setVisibility(View.VISIBLE);
        if (Mfollowup.ns1==1){
            ns1_p_row.setVisibility(View.VISIBLE);
            ns1_vis_row.setVisibility(View.VISIBLE);
        }
        if (Mfollowup.igg==1){
            igg_p_row.setVisibility(View.VISIBLE);
            igg_vis_row.setVisibility(View.VISIBLE);
        }
        if (Mfollowup.igm==1){
            igm_p_row.setVisibility(View.VISIBLE);
            igm_vis_row.setVisibility(View.VISIBLE);
        }
        if (Mfollowup.is_doc_visit==2){
            doc_list_row.setVisibility(View.VISIBLE);
        }
    }
    private AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (view.getParent()==sick7){
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
            }else if (view.getParent()==is_doc_visit) {
                if (i==2){
                    doc_list_row.setVisibility(View.VISIBLE);
                }else {
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
            if (compoundButton.getId() == ns1.getId()) {
                if (b) {
                    ns1_p_row.setVisibility(View.VISIBLE);
                    ns1_vis_row.setVisibility(View.VISIBLE);
                } else {
                    ns1_p_row.setVisibility(View.GONE);
                    ns1_vis_row.setVisibility(View.GONE);
                }
            } else if (compoundButton.getId() == igg.getId()) {
                if (b) {
                    igg_p_row.setVisibility(View.VISIBLE);
                    igg_vis_row.setVisibility(View.VISIBLE);
                } else {
                    igg_p_row.setVisibility(View.GONE);
                    igg_vis_row.setVisibility(View.GONE);
                }
            } else if (compoundButton.getId() == igm.getId()) {
                if (b) {
                    igm_p_row.setVisibility(View.VISIBLE);
                    igm_vis_row.setVisibility(View.VISIBLE);
                } else {
                    igm_p_row.setVisibility(View.GONE);
                    igm_vis_row.setVisibility(View.GONE);
                }
            }
        }
    };
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId()==submit.getId()){
                if(check()){
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("name ",name.getText().toString());
                    contentValues.put("age",Integer.parseInt(age.getText().toString()));
                    contentValues.put("sex",gen.getSelectedItemPosition());
                    contentValues.put("relation",relation.getSelectedItemPosition());
                    contentValues.put("sick7",sick7.getSelectedItemPosition());
                    contentValues.put("fever",fever.isChecked()?1:0);
                    contentValues.put("headache",headache.isChecked()?1:0);
                    contentValues.put("aches",aches.isChecked()?1:0);
                    contentValues.put("mus_pain",mus_pain.isChecked()?1:0);
                    contentValues.put("arth_pain",mus_pain.isChecked()?1:0);
                    contentValues.put("rash",rash.isChecked()?1:0);
                    contentValues.put("vomit",vomit.isChecked()?1:0);
                    contentValues.put("vomit_tendency",vomit_tendency.isChecked()?1:0);
                    contentValues.put("dia",dia.isChecked()?1:0);
                    contentValues.put("is_test",is_test.getSelectedItemPosition());
                    contentValues.put("ns1",ns1.isChecked()?1:0);
                    contentValues.put("igg",igg.isChecked()?1:0);
                    contentValues.put("igm",igm.isChecked()?1:0);
                    contentValues.put("unk_test",unk_test.isChecked()?1:0);
                    contentValues.put("ns1_p",ns1_p.getSelectedItemPosition());
                    contentValues.put("igg_p",igg_p.getSelectedItemPosition());
                    contentValues.put("igm_p",igm_p.getSelectedItemPosition());
                    contentValues.put("ns1_vis",ns1_vis.getSelectedItemPosition());
                    contentValues.put("igg_vis",igg_vis.getSelectedItemPosition());
                    contentValues.put("igm_vis",igg_vis.getSelectedItemPosition());
                    contentValues.put("is_doc_visit",is_doc_visit.getSelectedItemPosition());
                    contentValues.put("doc_list",doc_list.getSelectedItemPosition());
                    contentValues.put("user",getSharedPreferences(Util.secret_key, Context.MODE_PRIVATE).getInt("id",0));
                    myDB.update_followup(contentValues,id_number.getText().toString());
                    finish();
                }
            }
        }
    };
    private boolean check(){
        boolean is_ok=true;
        if (name.getText().toString()==null || name.getText().toString().isEmpty()){
            Toast.makeText(Followup.this,"Write name",Toast.LENGTH_SHORT).show();
            is_ok=false;
        }
        if(age.getText().toString()==null || age.getText().toString().isEmpty()){
            Toast.makeText(Followup.this,"Write age",Toast.LENGTH_SHORT).show();
            is_ok=false;
        }
        if (gen.getSelectedItemPosition()==0){
            Toast.makeText(Followup.this,"Select gender",Toast.LENGTH_SHORT).show();
            is_ok=false;
        }
        if (relation.getSelectedItemPosition()==0){
            Toast.makeText(Followup.this,"Select relationship",Toast.LENGTH_SHORT).show();
            is_ok=false;
        }
        if(sick7.getSelectedItemPosition()==0){
            Toast.makeText(Followup.this,"Select dengue symptom within 7 days",Toast.LENGTH_SHORT).show();
            is_ok=false;
        }
        if(sick7.getSelectedItemPosition()==2) {
            if (is_test.getSelectedItemPosition() == 0) {
                Toast.makeText(Followup.this, "Select test status within 7 days", Toast.LENGTH_SHORT).show();
                is_ok = false;
            }
        }
        if (unk_test.isChecked()){
            if (ns1.isChecked() || igg.isChecked() || igm.isChecked()){
                Toast.makeText(Followup.this,"Select test performed with in 7days",Toast.LENGTH_SHORT).show();
                is_ok=false;
            }
        }
        if (ns1.isChecked()){
            if (ns1_p.getSelectedItemPosition()==0 || ns1_vis.getSelectedItemPosition()==0){
                Toast.makeText(Followup.this,"Select ns1 result and report",Toast.LENGTH_SHORT).show();
                is_ok=false;
            }
        }
        if (igg.isChecked()){
            if (igg_p.getSelectedItemPosition()==0 || igg_vis.getSelectedItemPosition()==0){
                Toast.makeText(Followup.this,"Select igg result and report",Toast.LENGTH_SHORT).show();
                is_ok=false;
            }
        }
        if (igm.isChecked()){
            if (igm_p.getSelectedItemPosition()==0 || igm_vis.getSelectedItemPosition()==0){
                Toast.makeText(Followup.this,"Select igm result and report",Toast.LENGTH_SHORT).show();
                is_ok=false;
            }
        }
        if (is_doc_visit.getSelectedItemPosition()==0){
            Util.makeToast(Followup.this,"Check status of doctor visit");
            is_ok=false;
        }
        if (is_doc_visit.getSelectedItemPosition()==2){
            if (doc_list.getSelectedItemPosition()==0){
                Util.makeToast(Followup.this,"Check doctor");
                is_ok=false;
            }
        }
        return is_ok;
    }
}