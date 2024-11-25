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

import accuratesoft.shakawat.ifrc.Followup;
import accuratesoft.shakawat.ifrc.FollowupList;
import accuratesoft.shakawat.ifrc.HouseHold;
import accuratesoft.shakawat.ifrc.ListActivity;
import accuratesoft.shakawat.ifrc.R;
import accuratesoft.shakawat.ifrc.models.Mfollowup;
import accuratesoft.shakawat.ifrc.models.MhouseHold;
import accuratesoft.shakawat.ifrc.models.Mparticipant;
import accuratesoft.shakawat.ifrc.utils.MyDB;

public class FollowupListRecAdapter extends RecyclerView.Adapter<FollowupListRecAdapter.MyViewHolder>{
    Context ctx;
    FollowupList listActivity;
    RecyclerView recyclerView;
    Cursor modelLists;
    public FollowupListRecAdapter(Context context, RecyclerView recyclerView, FollowupList listActivity, Cursor listDataStores){
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
            holder.uni_id.setText("ID: "+modelLists.getString(modelLists.getColumnIndex("id_number")));
            holder.name.setText("Name: "+modelLists.getString(modelLists.getColumnIndex("name")));
            holder.age.setText("Age: "+String.valueOf(modelLists.getInt(modelLists.getColumnIndex("age"))));
//            if (modelLists.getInt(modelLists.getColumnIndex("sex"))==1)
//                holder.sex.setText("Sex: M");
//            else if (modelLists.getInt(modelLists.getColumnIndex("sex"))==2)
//                holder.sex.setText("Sex: F");
//            else if (modelLists.getInt(modelLists.getColumnIndex("sex"))==3)
//                holder.sex.setText("Sex: T");
            holder.sex.setText("Mob: "+modelLists.getString(modelLists.getColumnIndex("mob")));
            if (modelLists.getInt(modelLists.getColumnIndex("relation"))==1)
                holder.mob.setText("Relation: Self");
            else if (modelLists.getInt(modelLists.getColumnIndex("relation"))==2)
                holder.mob.setText("Relation: Son/Daughter");
            else if (modelLists.getInt(modelLists.getColumnIndex("relation"))==3)
                holder.mob.setText("Relation: Spouse");
            else if (modelLists.getInt(modelLists.getColumnIndex("relation"))==4)
                holder.mob.setText("Relation: Parents");
            else if (modelLists.getInt(modelLists.getColumnIndex("relation"))==5)
                holder.mob.setText("Relation: Grand Children");
            else if (modelLists.getInt(modelLists.getColumnIndex("relation"))==6)
                holder.mob.setText("Relation: Nephew (sister)");
            else if (modelLists.getInt(modelLists.getColumnIndex("relation"))==7)
                holder.mob.setText("Relation: Nephew (brother)");
            else if (modelLists.getInt(modelLists.getColumnIndex("relation"))==8)
                holder.mob.setText("Relation: House maid");
            else if (modelLists.getInt(modelLists.getColumnIndex("relation"))==9)
                holder.mob.setText("Relation: Others");
            if(modelLists.getInt(modelLists.getColumnIndex("is_doc_visit"))==0)
                holder.crt_at.setText("FOLLOW UP");
            else
                holder.crt_at.setText("DONE");
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
            int pos = recyclerView.getChildAdapterPosition(view);
            if (modelLists.moveToPosition(pos)){
                Mfollowup.num=modelLists.getInt(modelLists.getColumnIndex("num"));
                Mfollowup.hh_number=modelLists.getString(modelLists.getColumnIndex("hh_number"));
                Mfollowup.id_number=modelLists.getString(modelLists.getColumnIndex("id_number"));
                Mfollowup.mob =modelLists.getString(modelLists.getColumnIndex("mob"));
                Mfollowup.name =modelLists.getString(modelLists.getColumnIndex("name"));
                Mfollowup.age=modelLists.getInt(modelLists.getColumnIndex("age"));
                Mfollowup.sex=modelLists.getInt(modelLists.getColumnIndex("sex"));
                Mfollowup.relation=modelLists.getInt(modelLists.getColumnIndex("relation"));
                Mfollowup.sick7=modelLists.getInt(modelLists.getColumnIndex("sick7"));
                Mfollowup.fever=modelLists.getInt(modelLists.getColumnIndex("fever"));
                Mfollowup.headache=modelLists.getInt(modelLists.getColumnIndex("headache"));
                Mfollowup.aches=modelLists.getInt(modelLists.getColumnIndex("aches"));
                Mfollowup.mus_pain=modelLists.getInt(modelLists.getColumnIndex("mus_pain"));
                Mfollowup.rash=modelLists.getInt(modelLists.getColumnIndex("rash"));
                Mfollowup.vomit=modelLists.getInt(modelLists.getColumnIndex("vomit"));
                Mfollowup.dia=modelLists.getInt(modelLists.getColumnIndex("dia"));
                Mfollowup.is_test=modelLists.getInt(modelLists.getColumnIndex("is_test"));
                Mfollowup.ns1=modelLists.getInt(modelLists.getColumnIndex("ns1"));
                Mfollowup.igg=modelLists.getInt(modelLists.getColumnIndex("igg"));
                Mfollowup.igm=modelLists.getInt(modelLists.getColumnIndex("igm"));
                Mfollowup.unk_test=modelLists.getInt(modelLists.getColumnIndex("unk_test"));
                Mfollowup.ns1_p=modelLists.getInt(modelLists.getColumnIndex("ns1_p"));
                Mfollowup.igg_p=modelLists.getInt(modelLists.getColumnIndex("igg_p"));
                Mfollowup.igm_p=modelLists.getInt(modelLists.getColumnIndex("igm_p"));
                Mfollowup.ns1_vis=modelLists.getInt(modelLists.getColumnIndex("ns1_vis"));
                Mfollowup.igg_vis=modelLists.getInt(modelLists.getColumnIndex("igg_vis"));
                Mfollowup.igm_vis=modelLists.getInt(modelLists.getColumnIndex("igm_vis"));
                Mfollowup.is_reffer=modelLists.getInt(modelLists.getColumnIndex("is_reffer"));
                Mfollowup.is_doc_visit=modelLists.getInt(modelLists.getColumnIndex("is_doc_visit"));
                Mfollowup.doc_list=modelLists.getInt(modelLists.getColumnIndex("doc_list"));
                ctx.startActivity(new Intent(ctx, Followup.class));
            }
        }
    };
}
