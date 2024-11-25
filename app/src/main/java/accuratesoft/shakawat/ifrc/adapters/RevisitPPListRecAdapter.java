package accuratesoft.shakawat.ifrc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import accuratesoft.shakawat.ifrc.R;
import accuratesoft.shakawat.ifrc.RevisitPPActivity;
import accuratesoft.shakawat.ifrc.models.MRevesitPP;
import accuratesoft.shakawat.ifrc.models.MRevisitHH;

public class RevisitPPListRecAdapter extends RecyclerView.Adapter<RevisitListRecAdapter.MyViewHolder>{
    Context ctx;
    RevisitPPActivity revisitPPActivity;
    RecyclerView recyclerView;
    ArrayList<MRevesitPP> arrayList;
    public RevisitPPListRecAdapter(Context ctx,RevisitPPActivity revisitPPActivity,RecyclerView recyclerView,ArrayList<MRevesitPP> arrayList){
        this.ctx = ctx;
        this.revisitPPActivity = revisitPPActivity;
        this.recyclerView = recyclerView;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RevisitListRecAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list,parent,
                false);
        v.setOnClickListener(clickListener);
        return new RevisitListRecAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RevisitListRecAdapter.MyViewHolder holder, int position) {
        MRevesitPP mRevesitPP = arrayList.get(position);

        holder.uni_id.setText(mRevesitPP.id_number);
        holder.name.setText("Name: "+mRevesitPP.name);
        holder.crt_at.setText("Collection date: "+mRevesitPP.created_at);
        holder.age.setText("Age: "+String.valueOf(mRevesitPP.age));
        String sx="";
        if (mRevesitPP.sex==1) sx="M";
        else if (mRevesitPP.sex==2) sx="F";
        else if (mRevesitPP.sex==3) sx="T";
        holder.sex.setText("Sex: "+sx);
        String rl="";
        if (mRevesitPP.relation==1) rl="Self";
        if (mRevesitPP.relation==2) rl="Child";
        if (mRevesitPP.relation==3) rl="Spouse";
        if (mRevesitPP.relation==4) rl="Parents";
        if (mRevesitPP.relation==5) rl="Grandchild";
        if (mRevesitPP.relation==6) rl="Siblings";
        if (mRevesitPP.relation==7) rl="niece";
        if (mRevesitPP.relation==8) rl="nephew";
        if (mRevesitPP.relation==9) rl="Maid";
        if (mRevesitPP.relation==10) rl="Others";
        holder.mob.setText("Relation: "+rl);
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

        }
    };
}
