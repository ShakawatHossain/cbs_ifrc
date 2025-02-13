package accuratesoft.shakawat.ifrc;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import java.util.ArrayList;
import java.util.HashMap;

import accuratesoft.shakawat.ifrc.fragments.MultiSelect;
import accuratesoft.shakawat.ifrc.interfaces.MultiInterface;
import accuratesoft.shakawat.ifrc.models.MhouseHold;
import accuratesoft.shakawat.ifrc.models.Mparticipant;
import accuratesoft.shakawat.ifrc.utils.GpsTracker;
import accuratesoft.shakawat.ifrc.utils.Util;

public class HouseHold extends AppCompatActivity implements MultiInterface {
    public static HouseHold houseHold;
    EditText ward,area,lat,lung,hh_num,house_address,info_name,info_age,mob,mem_qty,symptom_7_mem,
            symptom_6m_mem,hh,unknown_disease_time,unknown_sick,unknown_dead,unknown_around_time,
            death_animals_number,death_animals_time;
    Spinner cc,info_gen,symptom_7,symptom_6m,word_num,unknown_disease,unkown_around,has_animals,
            death_animals;
    Button gen_number,ref_gps,submit,unfinished;
    TableRow symptom_7_mem_row,symptom_6m_mem_row,unknown_disease_time_row,unknown_sick_row,
            unknown_dead_row,unkown_around_row,unknown_around_time_row,has_animals_row,
            death_animals_row,death_animals_type_row,death_animals_number_row,death_animals_time_row;
    CheckBox unknown_around_time_unk;
    TextView death_animals_type;
    GpsTracker gpsTracker;
    SharedPreferences sharedPreferences;
    ArrayList<String> word_num_list;
    ArrayAdapter<String> word_num_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_hold);
        HouseHold.houseHold = this;
        sharedPreferences = getSharedPreferences(Util.secret_key, Context.MODE_PRIVATE);
        init();
        getData();
        // Handle back button press using OnBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Your custom back press logic
                // For example, show a confirmation dialog
                new AlertDialog.Builder(HouseHold.this)
                        .setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", (dialog, id) -> {
                            // Finish the activity or do other actions
                            finish();
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }
    private void init(){
        ward = (EditText) findViewById(R.id.warde);
        area = (EditText) findViewById(R.id.area);
        lat = (EditText) findViewById(R.id.lat);
        lung = (EditText) findViewById(R.id.lung);
        hh_num = (EditText) findViewById(R.id.hh_num);
        house_address = (EditText) findViewById(R.id.house_address);
        info_name = (EditText) findViewById(R.id.info_name);
        info_age = (EditText) findViewById(R.id.info_age);
        mob = (EditText) findViewById(R.id.mob);
        mem_qty = (EditText) findViewById(R.id.mem_qty);
        symptom_7_mem = (EditText) findViewById(R.id.symptom_7_mem);
        symptom_6m_mem = (EditText) findViewById(R.id.symptom_6m_mem);
        hh = (EditText) findViewById(R.id.hh);
        unknown_disease_time = (EditText) findViewById(R.id.unknown_disease_time);
        unknown_sick = (EditText) findViewById(R.id.unknown_sick);
        unknown_dead = (EditText) findViewById(R.id.unknown_dead);
        unknown_around_time = (EditText) findViewById(R.id.unknown_around_time);
        death_animals_number = (EditText) findViewById(R.id.death_animals_number);
        death_animals_time = (EditText) findViewById(R.id.death_animals_time);
        death_animals_type = (TextView) findViewById(R.id.death_animals_type);

        cc = (Spinner) findViewById(R.id.cc);
        info_gen = (Spinner) findViewById(R.id.info_gen);
        symptom_7 = (Spinner) findViewById(R.id.symptom_7);
        symptom_6m = (Spinner) findViewById(R.id.symptom_6m);
        word_num = (Spinner) findViewById(R.id.word_num);
        unknown_disease = (Spinner) findViewById(R.id.unknown_disease);
        unkown_around = (Spinner) findViewById(R.id.unkown_around);
        has_animals = (Spinner) findViewById(R.id.has_animals);
        death_animals = (Spinner) findViewById(R.id.death_animals);

        unknown_disease.setOnItemSelectedListener(selectedListener);
        unkown_around.setOnItemSelectedListener(selectedListener);
        has_animals.setOnItemSelectedListener(selectedListener);
        death_animals.setOnItemSelectedListener(selectedListener);
        symptom_7.setOnItemSelectedListener(selectedListener);
        symptom_6m.setOnItemSelectedListener(selectedListener);
        cc.setOnItemSelectedListener(selectedListener);

        gen_number = (Button) findViewById(R.id.gen_number);
        ref_gps = (Button) findViewById(R.id.ref_gps);
        submit = (Button) findViewById(R.id.submit);
        unfinished = (Button) findViewById(R.id.unfinished);

        gen_number.setOnClickListener(clickListener);
        ref_gps.setOnClickListener(clickListener);
        submit.setOnClickListener(clickListener);
        unfinished.setOnClickListener(clickListener);
        unknown_around_time_unk = (CheckBox) findViewById(R.id.unknown_around_time_unk);
        unknown_around_time_unk.setOnCheckedChangeListener(checkedChangeListener);

        symptom_7_mem_row = (TableRow) findViewById(R.id.symptom_7_mem_row);
        symptom_6m_mem_row = (TableRow) findViewById(R.id.symptom_6m_mem_row);
        unknown_disease_time_row = (TableRow) findViewById(R.id.unknown_disease_time_row);
        unknown_sick_row = (TableRow) findViewById(R.id.unknown_sick_row);
        unknown_dead_row = (TableRow) findViewById(R.id.unknown_dead_row);
        unkown_around_row = (TableRow) findViewById(R.id.unkown_around_row);
        unknown_around_time_row = (TableRow) findViewById(R.id.unknown_around_time_row);
        has_animals_row = (TableRow) findViewById(R.id.has_animals_row);
        death_animals_row = (TableRow) findViewById(R.id.death_animals_row);
        death_animals_type_row = (TableRow) findViewById(R.id.death_animals_type_row);
        death_animals_number_row = (TableRow) findViewById(R.id.death_animals_number_row);
        death_animals_time_row = (TableRow) findViewById(R.id.death_animals_time_row);
        gpsTracker = new GpsTracker(HouseHold.this);
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        word_num_list = new ArrayList<String>();
        word_num_list.add("পছন্দ করুন");
        word_num_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,word_num_list);
        word_num.setAdapter(word_num_adapter);
    }
    private void getData(){
        if (MhouseHold.hh==0){
            hh.setText(String.valueOf(sharedPreferences.getInt("hh_num",0)+1));
        }else {
            hh.setText(String.valueOf(MhouseHold.hh));
        }
        cc.setSelection(MhouseHold.cc);
//        ward.setText(String.valueOf(MhouseHold.ward));
        set_word(MhouseHold.cc);
        for(int i=0;i<word_num_list.size();i++){
            if (word_num_list.get(i).compareTo(String.valueOf(MhouseHold.ward))==0){
                word_num.setSelection(i);
            }
        }
        area.setText(MhouseHold.area);
        lat.setText(MhouseHold.lat);
        lung.setText(MhouseHold.lung);
        hh_num.setText(MhouseHold.hh_num);
        house_address.setText(MhouseHold.house_address);
        info_name.setText(MhouseHold.info_name);
        if(MhouseHold.info_age>0) info_age.setText(String.valueOf(MhouseHold.info_age));
        info_gen.setSelection(MhouseHold.info_sex);
        mob.setText(MhouseHold.mob);
        if(MhouseHold.mem>0) mem_qty.setText(String.valueOf(MhouseHold.mem));
        unknown_disease_time.setText(MhouseHold.unknown_disease_time);
        unknown_sick.setText(MhouseHold.unknown_sick);
        unknown_dead.setText(MhouseHold.unknown_dead);
        unknown_around_time.setText(MhouseHold.unknown_around_time);
        death_animals_number.setText(MhouseHold.death_animals_number);
        death_animals_time.setText(MhouseHold.death_animals_time);
        death_animals_type.setText(MhouseHold.death_animals_type);
        symptom_7.setSelection(MhouseHold.symptom_7);
        symptom_6m.setSelection(MhouseHold.symptom_6m);
        if (MhouseHold.symptom_7==2) symptom_7_mem_row.setVisibility(View.VISIBLE);
        if (MhouseHold.symptom_6m==2) symptom_6m_mem_row.setVisibility(View.VISIBLE);
        symptom_7_mem.setText(String.valueOf(MhouseHold.symptom_7_mem));
        symptom_6m_mem.setText(String.valueOf(MhouseHold.symptom_6m_mem));

        if(MhouseHold.unknown_around_time.compareTo("99")==0) unknown_around_time_unk.setChecked(true);
        unknown_disease_time.setText(MhouseHold.unknown_disease_time);
        unknown_sick.setText(MhouseHold.unknown_sick);
        unknown_dead.setText(MhouseHold.unknown_dead);
        unknown_around_time.setText(MhouseHold.unknown_around_time);
        death_animals_number.setText(MhouseHold.death_animals_number);
        death_animals_time.setText(MhouseHold.death_animals_time);
        death_animals_type.setText(MhouseHold.death_animals_type);
        unknown_disease.setSelection(MhouseHold.unknown_disease);
        unkown_around.setSelection(MhouseHold.unkown_around);
        has_animals.setSelection(MhouseHold.has_animals);
        death_animals.setSelection(MhouseHold.death_animals);
        death_animals_type.setOnClickListener(clickListener);
    }
    private void setData(){
        MhouseHold.cc = cc.getSelectedItemPosition();
//        if (ward.getText().toString()!=null && !ward.getText().toString().isEmpty())
//            MhouseHold.ward = Integer.parseInt(ward.getText().toString());
        if (word_num.getSelectedItemPosition()>0)
            MhouseHold.ward = Integer.parseInt(word_num.getSelectedItem().toString());
        MhouseHold.area = area.getText().toString();
        MhouseHold.lat = lat.getText().toString();
        MhouseHold.lung = lung.getText().toString();
        MhouseHold.hh = Integer.parseInt(hh.getText().toString());
        MhouseHold.hh_num = hh_num.getText().toString();
        MhouseHold.house_address = house_address.getText().toString();
        MhouseHold.info_name = info_name.getText().toString();
        if(info_age.getText().toString()!=null && !info_age.getText().toString().isEmpty())
            MhouseHold.info_age = Integer.parseInt(info_age.getText().toString());
        MhouseHold.info_sex = info_gen.getSelectedItemPosition();
        MhouseHold.mob = mob.getText().toString();
        if(mem_qty.getText().toString()!=null && !mem_qty.getText().toString().isEmpty())
            MhouseHold.mem = Integer.parseInt(mem_qty.getText().toString());
        MhouseHold.symptom_7 = symptom_7.getSelectedItemPosition();
        MhouseHold.symptom_6m = symptom_6m.getSelectedItemPosition();
        if(symptom_7_mem.getText().toString()!=null && !symptom_7_mem.getText().toString().isEmpty())
            MhouseHold.symptom_7_mem = Integer.parseInt(symptom_7_mem.getText().toString());
        if(symptom_6m_mem.getText().toString()!=null && !symptom_6m_mem.getText().toString().isEmpty())
            MhouseHold.symptom_6m_mem = Integer.parseInt(symptom_6m_mem.getText().toString());
        MhouseHold.unknown_disease_time = unknown_disease_time.getText().toString();
        MhouseHold.unknown_sick = unknown_sick.getText().toString();
        MhouseHold.unknown_dead = unknown_dead.getText().toString();
        MhouseHold.unknown_around_time = unknown_around_time.getText().toString();
        MhouseHold.death_animals_number = death_animals_number.getText().toString();
        MhouseHold.death_animals_time = death_animals_time.getText().toString();
        MhouseHold.death_animals_type = death_animals_type.getText().toString();
        MhouseHold.unknown_disease = unknown_disease.getSelectedItemPosition();
        MhouseHold.unkown_around = unkown_around.getSelectedItemPosition();
        MhouseHold.has_animals = has_animals.getSelectedItemPosition();
        MhouseHold.death_animals = death_animals.getSelectedItemPosition();
    }
    private boolean check(){
        boolean is_ok = true;
        if (cc.getSelectedItemPosition()==0){
            is_ok=false;
            Toast.makeText(HouseHold.this,"Select city corporation",Toast.LENGTH_SHORT).show();
        }
        if (info_gen.getSelectedItemPosition()==0){
            is_ok=false;
            Toast.makeText(HouseHold.this,"Select information giver gender",Toast.LENGTH_SHORT).show();
        }
//        if (symptom_7.getSelectedItemPosition()==0){
//            is_ok=false;
//            Toast.makeText(HouseHold.this,"Select appeard symptom within 7 days",Toast.LENGTH_SHORT).show();
//        }
//        if (symptom_6m.getSelectedItemPosition()==0){
//            is_ok=false;
//            Toast.makeText(HouseHold.this,"Select dengue patient within 6 months",Toast.LENGTH_SHORT).show();
//        }
        if (word_num.getSelectedItemPosition()==0){
            is_ok=false;
            Toast.makeText(HouseHold.this,"Select ward number",Toast.LENGTH_SHORT).show();
        }
//        house_address
        if(house_address.getText().toString()==null || house_address.getText().toString().isEmpty()){
            is_ok=false;
            Toast.makeText(HouseHold.this,"Write house_address",Toast.LENGTH_SHORT).show();
        }
        if(area.getText().toString()==null || area.getText().toString().isEmpty()){
            is_ok=false;
            Toast.makeText(HouseHold.this,"Write area name",Toast.LENGTH_SHORT).show();
        }
        if(hh_num.getText().toString()==null || hh_num.getText().toString().isEmpty()){
            is_ok=false;
            Toast.makeText(HouseHold.this,"Generate house hold number",Toast.LENGTH_SHORT).show();
        }
        if(info_name.getText().toString()==null || info_name.getText().toString().isEmpty()){
            is_ok=false;
            Toast.makeText(HouseHold.this,"Write information giver name",Toast.LENGTH_SHORT).show();
        }
        if(info_age.getText().toString()==null || info_age.getText().toString().isEmpty()){
            is_ok=false;
            Toast.makeText(HouseHold.this,"Write information giver age",Toast.LENGTH_SHORT).show();
        }
        if(mob.getText().toString()==null || mob.getText().toString().isEmpty() || mob.getText().toString().length()!=11){
            is_ok=false;
            Toast.makeText(HouseHold.this,"Write mobile number",Toast.LENGTH_SHORT).show();
        }
        if(mem_qty.getText().toString()==null || mem_qty.getText().toString().isEmpty()){
            is_ok=false;
            Toast.makeText(HouseHold.this,"Write number of members",Toast.LENGTH_SHORT).show();
        }
//        if(symptom_7.getSelectedItemPosition()==2){
//            if(symptom_7_mem.getText().toString()==null || symptom_7_mem.getText().toString().isEmpty()){
//                is_ok=false;
//                Toast.makeText(HouseHold.this,"Write number of members having symptom",Toast.LENGTH_SHORT).show();
//            } else if (Integer.parseInt(symptom_7_mem.getText().toString())> Integer.parseInt(mem_qty.getText().toString())) {
//                is_ok=false;
//                Toast.makeText(HouseHold.this,"Check members quantity",Toast.LENGTH_SHORT).show();
//            }
//        }
//        if(symptom_6m.getSelectedItemPosition()==2){
//            if(symptom_6m_mem.getText().toString()==null || symptom_6m_mem.getText().toString().isEmpty()){
//                is_ok=false;
//                Toast.makeText(HouseHold.this,"Write number of members had dengue",Toast.LENGTH_SHORT).show();
//            }else  if (Integer.parseInt(symptom_6m_mem.getText().toString())> Integer.parseInt(mem_qty.getText().toString())){
//                is_ok=false;
//                Toast.makeText(HouseHold.this,"Check members quantity",Toast.LENGTH_SHORT).show();
//            }
//        }

        return is_ok;
    }
    private CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (compoundButton.getId()==unknown_around_time_unk.getId()){
                if (b) unknown_around_time.setText("99");
            }
        }
    };
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId()==ref_gps.getId()){
                if(gpsTracker.canGetLocation()){
                    lat.setText(String.valueOf(gpsTracker.getLatitude()));
                    lung.setText(String.valueOf(gpsTracker.getLongitude()));
                }else{
                    gpsTracker.showSettingsAlert();
                }
            } else if (view.getId()==death_animals_type.getId()) {
                HashMap<String,String> params = new HashMap<>();
                params.put("1","মাছ");
                params.put("2","গরু/ ছাগল/ মহিষ/ ভেরা");
                params.put("3","কুকুর");
                params.put("4","বিরাল");
                params.put("5","মুরগি/ হাঁস/ কবুতর");
                params.put("6","অন্য কোন ধরনের পাখি");
                new MultiSelect(HouseHold.this,HouseHold.this,death_animals_type.getText().toString(),params,death_animals_type,"প্রাণী").show();
            } else if (view.getId()==submit.getId()) {
                if (check()){
                    setData();
                    Util.makeToast(HouseHold.this,"Please wait Loading UI...");
                    if (MhouseHold.participants.size()==0){
                        int i = Integer.parseInt(mem_qty.getText().toString());
                        for (int j=1;j<=i;j++){
                            Mparticipant participant = new Mparticipant();
                            participant.num=j;
                            participant.hh_number=hh_num.getText().toString();
                            participant.id_number=hh_num.getText().toString()+(j<10?"0"+j:""+j);
                            MhouseHold.participants.add(participant);
                        }
                    }else if(MhouseHold.participants.size()>Integer.parseInt(mem_qty.getText().toString())){
//                        for (int j = Integer.parseInt(mem_qty.getText().toString());j>MhouseHold.participants.size();j--){
                        for (int j = MhouseHold.participants.size();j>Integer.parseInt(mem_qty.getText().toString());j--){
                            MhouseHold.participants.remove(j-1);
                        }
                    }else if(MhouseHold.participants.size()<Integer.parseInt(mem_qty.getText().toString())){
                        for (int j = MhouseHold.participants.size();j<Integer.parseInt(mem_qty.getText().toString());j++){
                            Mparticipant participant = new Mparticipant();
                            participant.num=j+1;
                            participant.hh_number=hh_num.getText().toString();
                            participant.id_number=hh_num.getText().toString()+(j+1<10?"0"+(j+1):""+(j+1));
                            MhouseHold.participants.add(participant);
                        }
                    }
                    Intent intent = new Intent(HouseHold.this,Participant.class);
                    intent.putExtra("serial",0);
                    startActivity(intent);
                }
            } else if (view.getId()==unfinished.getId()) {
                setData();
                startActivity(new Intent(HouseHold.this,Revision.class));
            } else if (view.getId()==gen_number.getId()) {
                if (cc.getSelectedItemPosition()==0){
                    Toast.makeText(HouseHold.this,"Select city corp.",Toast.LENGTH_SHORT).show();
                    return;
                } else if (word_num.getSelectedItemPosition()==0) {
                    Toast.makeText(HouseHold.this,"Select word number.",Toast.LENGTH_SHORT).show();
                    return;
                }
//                else if (ward.getText().toString()==null || ward.getText().toString().isEmpty()) {
//                    Toast.makeText(HouseHold.this,"Write ward number",Toast.LENGTH_SHORT).show();
//                    return;
//                }
                String s="";
                if (cc.getSelectedItemPosition()==1)    s="D";
                else if (cc.getSelectedItemPosition()==2)    s="R";
                else if (cc.getSelectedItemPosition()==3)    s="S";
                s+= ward.getText().toString();
                s+=word_num.getSelectedItem().toString();
                int id = sharedPreferences.getInt("id",0);
                if (id<10){
                    s+="00"+id;
                }else if (id<100){
                    s+="0"+id;
                }else{
                    s+=""+id;
                }
                int h = Integer.parseInt(hh.getText().toString());
                if (h<10){
                    s=s+"000"+h;
                }else if (h<100){
                    s=s+"00"+h;
                }else if (h<1000){
                    s=s+"00"+h;
                }else if (h<10000){
                    s=s+"0"+h;
                }else{
                    s=s+h;
                }
                hh_num.setText(s);
            }
        }
    };

    private AdapterView.OnItemSelectedListener selectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if(view!=null){
                if (view.getParent() == symptom_7) {
                    if (i == 2)
                        symptom_7_mem_row.setVisibility(View.VISIBLE);
                    else {
                        symptom_7_mem_row.setVisibility(View.GONE);
                        symptom_7_mem.setText("");
                    }
                } else if (view.getParent() == symptom_6m) {
                    if (i == 2)
                        symptom_6m_mem_row.setVisibility(View.VISIBLE);
                    else {
                        symptom_6m_mem_row.setVisibility(View.GONE);
                        symptom_6m_mem.setText("");
                    }
                } else if (view.getParent() == cc) {
                    set_word(i);
                } else if (view.getParent() == unknown_disease) {
                    if (i == 2) {
                        unknown_disease_time_row.setVisibility(View.VISIBLE);
                        unknown_sick_row.setVisibility(View.VISIBLE);
                        unknown_dead_row.setVisibility(View.VISIBLE);
                    } else {
                        unknown_disease_time_row.setVisibility(View.GONE);
                        unknown_sick_row.setVisibility(View.GONE);
                        unknown_dead_row.setVisibility(View.GONE);
                    }
                } else if (view.getParent() == unkown_around) {
                    if (i == 2) {
                        unknown_around_time_row.setVisibility(View.VISIBLE);
                    } else {
                        unknown_around_time_row.setVisibility(View.GONE);
                    }
                } else if (view.getParent() == has_animals) {
                    if (i == 2) {
                        death_animals_row.setVisibility(View.VISIBLE);
                    } else {
                        death_animals_row.setVisibility(View.GONE);
                        death_animals_type_row.setVisibility(View.GONE);
                        death_animals_number_row.setVisibility(View.GONE);
                        death_animals_time_row.setVisibility(View.GONE);
                    }
                } else if (view.getParent() == death_animals) {
                    if (i == 2) {
                        death_animals_type_row.setVisibility(View.VISIBLE);
                        death_animals_number_row.setVisibility(View.VISIBLE);
                        death_animals_time_row.setVisibility(View.VISIBLE);
                    } else {
                        death_animals_type_row.setVisibility(View.GONE);
                        death_animals_number_row.setVisibility(View.GONE);
                        death_animals_time_row.setVisibility(View.GONE);
                    }
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    private void set_word(int val){
        word_num_list.clear();
        word_num_list.add("পছন্দ করুন");
        if (val==1){
            word_num_list.add("47");
            word_num_list.add("51");
        }else if (val==2){
            word_num_list.add("04");
            word_num_list.add("16");
            word_num_list.add("19");
            word_num_list.add("24");
            word_num_list.add("28");
        }else if (val==3){
            word_num_list.add("10");
            word_num_list.add("23");
            word_num_list.add("26");
            word_num_list.add("37");
            word_num_list.add("39");
        }
        word_num_adapter.notifyDataSetChanged();
    }

    @Override
    public void setText(String selected, TextView tv) {
        tv.setText(selected);
    }
}