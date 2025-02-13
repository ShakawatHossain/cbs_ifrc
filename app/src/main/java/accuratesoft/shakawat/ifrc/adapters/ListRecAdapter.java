package accuratesoft.shakawat.ifrc.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import accuratesoft.shakawat.ifrc.HouseHold;
import accuratesoft.shakawat.ifrc.ListActivity;
import accuratesoft.shakawat.ifrc.R;
import accuratesoft.shakawat.ifrc.models.MhouseHold;
import accuratesoft.shakawat.ifrc.models.Mparticipant;
import accuratesoft.shakawat.ifrc.utils.MyDB;
import accuratesoft.shakawat.ifrc.utils.Util;

public class ListRecAdapter extends RecyclerView.Adapter<ListRecAdapter.MyViewHolder>{
    Context ctx;
    ListActivity listActivity;
    RecyclerView recyclerView;
    Cursor modelLists;
    public ListRecAdapter(Context context,RecyclerView recyclerView,ListActivity listActivity,Cursor listDataStores){
        this.ctx = context;
        this.listActivity = listActivity;
        this.modelLists = listDataStores;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list,parent,
                false);
        v.setOnClickListener(clickListener);
        return new MyViewHolder(v);
    }
    @SuppressWarnings("Range")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(modelLists.moveToPosition(position)){
            holder.uni_id.setText("ID: "+modelLists.getString(modelLists.getColumnIndex("hh_num")));
            holder.name.setText("Name: "+modelLists.getString(modelLists.getColumnIndex("info_name")));
            holder.crt_at.setText("Date: "+modelLists.getString(modelLists.getColumnIndex("created_at")));
            holder.mob.setText("Mobile: "+modelLists.getString(modelLists.getColumnIndex("mob")));
            holder.age.setText("Area: "+modelLists.getString(modelLists.getColumnIndex("area")));
            holder.sex.setText("Member: "+modelLists.getString(modelLists.getColumnIndex("mem")));
        }
    }

    @Override
    public int getItemCount() {
        return modelLists.getCount();
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
    @SuppressWarnings("Range")
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Util.makeToast(ctx,"Please wait Loading UI...");
            MyDB myDB = new MyDB(ctx);
            MhouseHold.clear();
            int pos = recyclerView.getChildAdapterPosition(view);
            if (modelLists.moveToPosition(pos)){
                MhouseHold.id = modelLists.getInt(modelLists.getColumnIndex("id"));
                MhouseHold.cc= modelLists.getInt(modelLists.getColumnIndex("cc"));;
                MhouseHold.ward= modelLists.getInt(modelLists.getColumnIndex("ward"));
                MhouseHold.hh= modelLists.getInt(modelLists.getColumnIndex("hh")); //Interview number
                MhouseHold.hh_num= modelLists.getString(modelLists.getColumnIndex("hh_num"));
                MhouseHold.house_address= modelLists.getString(modelLists.getColumnIndex("house_address"));
                MhouseHold.area= modelLists.getString(modelLists.getColumnIndex("area"));
                MhouseHold.lat= modelLists.getString(modelLists.getColumnIndex("lat"));
                MhouseHold.lung= modelLists.getString(modelLists.getColumnIndex("lung"));
                MhouseHold.info_name= modelLists.getString(modelLists.getColumnIndex("info_name"));
                MhouseHold.info_sex = modelLists.getInt(modelLists.getColumnIndex("info_sex"));
                MhouseHold.info_age = modelLists.getInt(modelLists.getColumnIndex("info_age"));
                MhouseHold.mob = modelLists.getString(modelLists.getColumnIndex("mob"));
                MhouseHold.mem = modelLists.getInt(modelLists.getColumnIndex("mem"));
                MhouseHold.symptom_7 = modelLists.getInt(modelLists.getColumnIndex("symptom_7"));
                MhouseHold.symptom_7_mem = modelLists.getInt(modelLists.getColumnIndex("symptom_7_mem"));
                MhouseHold.symptom_6m = modelLists.getInt(modelLists.getColumnIndex("symptom_6m"));
                MhouseHold.symptom_6m_mem = modelLists.getInt(modelLists.getColumnIndex("symptom_6m_mem"));
                MhouseHold.reffer = modelLists.getInt(modelLists.getColumnIndex("reffer"));
                MhouseHold.is_death = modelLists.getInt(modelLists.getColumnIndex("is_death"));
                MhouseHold.dname = modelLists.getString(modelLists.getColumnIndex("dname"));
                MhouseHold.dage = modelLists.getInt(modelLists.getColumnIndex("dage"));
                MhouseHold.unknown_disease_time = modelLists.getString(modelLists.getColumnIndex("unknown_disease_time"));
                MhouseHold.unknown_sick = modelLists.getString(modelLists.getColumnIndex("unknown_sick"));
                MhouseHold.unknown_dead = modelLists.getString(modelLists.getColumnIndex("unknown_dead"));
                MhouseHold.unknown_around_time = modelLists.getString(modelLists.getColumnIndex("unknown_around_time"));
                MhouseHold.death_animals_number = modelLists.getString(modelLists.getColumnIndex("death_animals_number"));
                MhouseHold.death_animals_time = modelLists.getString(modelLists.getColumnIndex("death_animals_time"));
                MhouseHold.death_animals_type = modelLists.getString(modelLists.getColumnIndex("death_animals_type"));
                MhouseHold.unknown_disease = modelLists.getInt(modelLists.getColumnIndex("unknown_disease"));
                MhouseHold.unkown_around = modelLists.getInt(modelLists.getColumnIndex("unkown_around"));
                MhouseHold.has_animals = modelLists.getInt(modelLists.getColumnIndex("has_animals"));
                MhouseHold.death_animals = modelLists.getInt(modelLists.getColumnIndex("death_animals"));
                Cursor cp = myDB.get_participant(MhouseHold.hh_num);
                if (cp.moveToFirst()){
                    do {
                        Mparticipant mparticipant = new Mparticipant();
                        mparticipant.num=cp.getInt(cp.getColumnIndex("num"));
                        mparticipant.hh_number=cp.getString(cp.getColumnIndex("hh_number"));
                        mparticipant.id_number=cp.getString(cp.getColumnIndex("id_number"));
                        mparticipant.name =cp.getString(cp.getColumnIndex("name"));
                        mparticipant.age=cp.getInt(cp.getColumnIndex("age"));
                        mparticipant.sex=cp.getInt(cp.getColumnIndex("sex"));
                        mparticipant.relation=cp.getInt(cp.getColumnIndex("relation"));
                        mparticipant.comorbidity=cp.getString(cp.getColumnIndex("comorbidity"));
                        mparticipant.rel_oth = cp.getString(cp.getColumnIndex("rel_oth"));
                        mparticipant.sick7=cp.getInt(cp.getColumnIndex("sick7"));
                        mparticipant.fever=cp.getInt(cp.getColumnIndex("fever"));
                        mparticipant.headache=cp.getInt(cp.getColumnIndex("headache"));
                        mparticipant.aches=cp.getInt(cp.getColumnIndex("aches"));
                        mparticipant.mus_pain=cp.getInt(cp.getColumnIndex("mus_pain"));
                        mparticipant.arth_pain=cp.getInt(cp.getColumnIndex("arth_pain"));
                        mparticipant.rash=cp.getInt(cp.getColumnIndex("rash"));
                        mparticipant.vomit=cp.getInt(cp.getColumnIndex("vomit"));
                        mparticipant.vomit_tendency=cp.getInt(cp.getColumnIndex("vomit_tendency"));
                        mparticipant.dia=cp.getInt(cp.getColumnIndex("dia"));
                        mparticipant.is_test=cp.getInt(cp.getColumnIndex("is_test"));
                        mparticipant.ns1=cp.getInt(cp.getColumnIndex("ns1"));
                        mparticipant.igg=cp.getInt(cp.getColumnIndex("igg"));
                        mparticipant.igm=cp.getInt(cp.getColumnIndex("igm"));
                        mparticipant.unk_test=cp.getInt(cp.getColumnIndex("unk_test"));
                        mparticipant.ns1_p=cp.getInt(cp.getColumnIndex("ns1_p"));
                        mparticipant.igg_p=cp.getInt(cp.getColumnIndex("igg_p"));
                        mparticipant.igm_p=cp.getInt(cp.getColumnIndex("igm_p"));
                        mparticipant.ns1_vis=cp.getInt(cp.getColumnIndex("ns1_vis"));
                        mparticipant.igg_vis=cp.getInt(cp.getColumnIndex("igg_vis"));
                        mparticipant.igm_vis=cp.getInt(cp.getColumnIndex("igm_vis"));
                        mparticipant.is_reffer=cp.getInt(cp.getColumnIndex("is_reffer"));
                        mparticipant.sick6m=cp.getInt(cp.getColumnIndex("sick6m"));
                        mparticipant.doc_list=cp.getInt(cp.getColumnIndex("doc_list"));
                        mparticipant.prev_ns1=cp.getInt(cp.getColumnIndex("prev_ns1"));
                        mparticipant.prev_igg=cp.getInt(cp.getColumnIndex("prev_igg"));
                        mparticipant.prev_igm=cp.getInt(cp.getColumnIndex("prev_igm"));
                        mparticipant.prev_unk_test=cp.getInt(cp.getColumnIndex("prev_unk_test"));
                        mparticipant.prev_ns1_p=cp.getInt(cp.getColumnIndex("prev_ns1_p"));
                        mparticipant.prev_igg_p=cp.getInt(cp.getColumnIndex("prev_igg_p"));
                        mparticipant.prev_igm_p=cp.getInt(cp.getColumnIndex("prev_igm_p"));
                        mparticipant.prev_ns1_vis=cp.getInt(cp.getColumnIndex("prev_ns1_vis"));
                        mparticipant.prev_igg_vis=cp.getInt(cp.getColumnIndex("prev_igg_vis"));
                        mparticipant.prev_igm_vis=cp.getInt(cp.getColumnIndex("prev_igm_vis"));
                        mparticipant.outcome=cp.getInt(cp.getColumnIndex("outcome"));
                        mparticipant.comorbidity_oth_txt=cp.getString(cp.getColumnIndex("comorbidity_oth_txt"));
                        mparticipant.temp_measured=cp.getInt(cp.getColumnIndex("temp_measured"));
                        mparticipant.fever_duration=cp.getString(cp.getColumnIndex("fever_duration"));
                        mparticipant.awd_daygap=cp.getString(cp.getColumnIndex("awd_daygap"));
                        MhouseHold.participants.add(mparticipant);
                    }while (cp.moveToNext());
                }
                ctx.startActivity(new Intent(ctx, HouseHold.class));
            }

        }
    };
}
