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

import org.json.JSONArray;
import org.json.JSONObject;

import accuratesoft.shakawat.ifrc.EventFollowup;
import accuratesoft.shakawat.ifrc.EventFollowupList;
import accuratesoft.shakawat.ifrc.FollowupList;
import accuratesoft.shakawat.ifrc.R;

public class EventFollowupListRecAdapter extends RecyclerView.Adapter<EventFollowupListRecAdapter.MyViewHolder>{
    Context ctx;
    EventFollowupList eventFollowupList;
    RecyclerView recyclerView;
    JSONArray jsonArray;
    public EventFollowupListRecAdapter(Context ctx, EventFollowupList eventFollowupList,
                                       RecyclerView recyclerView, JSONArray jsonArray){
        this.ctx = ctx;
        this.eventFollowupList = eventFollowupList;
        this.recyclerView = recyclerView;
        this.jsonArray = jsonArray;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_list,viewGroup,false);
        v.setOnClickListener(clickListener);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        JSONObject jsonObject = jsonArray.optJSONObject(i);
        myViewHolder.uni_id.setText("ID: "+jsonObject.optString("participant_id"));
        myViewHolder.name.setText("Name: "+jsonObject.optString("name"));
        myViewHolder.age.setText("Age: "+jsonObject.optString("age"));
        if(jsonObject.optInt("sex")==1) myViewHolder.sex.setText("Sex: M");
        else if(jsonObject.optInt("sex")==2) myViewHolder.sex.setText("Sex: F");
        else if(jsonObject.optInt("sex")==3) myViewHolder.sex.setText("Sex: T");

        myViewHolder.mob.setText("Mob: "+jsonObject.optString("hh"));
        myViewHolder.crt_at.setText("Disease: "+jsonObject.optString("followup_cause"));
    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
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
        public void onClick(View v) {
            int position = recyclerView.getChildLayoutPosition(v);
            JSONObject jsonObject = jsonArray.optJSONObject(position);
            Intent intent = new Intent(ctx, EventFollowup.class);
            intent.putExtra("id",jsonObject.optInt("id"));
            intent.putExtra("participant_id",jsonObject.optString("participant_id"));
            intent.putExtra("name",jsonObject.optString("name"));
            intent.putExtra("followup_cause",jsonObject.optString("followup_cause"));
            ctx.startActivity(intent);
        }
    };
}
