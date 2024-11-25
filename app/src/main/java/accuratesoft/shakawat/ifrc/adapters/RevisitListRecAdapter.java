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

import java.util.ArrayList;

import accuratesoft.shakawat.ifrc.R;
import accuratesoft.shakawat.ifrc.RevisitHHActivity;
import accuratesoft.shakawat.ifrc.RevisitPPActivity;
import accuratesoft.shakawat.ifrc.models.MRevisitHH;

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
            Intent intent = new Intent(ctx, RevisitPPActivity.class);
            intent.putExtra("hh_id",arrayList.get(pos).id);
            intent.putExtra("hh_number",arrayList.get(pos).id_number);
            intent.putExtra("user",arrayList.get(pos).user);
            intent.putExtra("created_at",arrayList.get(pos).created_at);
            ctx.startActivity(intent);
        }
    };
}
