package accuratesoft.shakawat.ifrc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import accuratesoft.shakawat.ifrc.EventFollowup;
import accuratesoft.shakawat.ifrc.R;
import accuratesoft.shakawat.ifrc.dialogs.CalenderDialog;
import accuratesoft.shakawat.ifrc.interfaces.CalenderInterface;
import accuratesoft.shakawat.ifrc.models.MmemList;

public class MemListRecAdapter extends RecyclerView.Adapter<MemListRecAdapter.MyViewHolder>
        implements CalenderInterface {
    Context ctx;
    ArrayList<MmemList> mmemLists;
    RecyclerView recyclerView;
    EventFollowup eventFollowup;
    public MemListRecAdapter(Context ctx, ArrayList<MmemList> mmemLists,
                             RecyclerView recyclerView, EventFollowup eventFollowup){
        this.ctx = ctx;
        this.mmemLists = mmemLists;
        this.recyclerView = recyclerView;
        this.eventFollowup = eventFollowup;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_mem_list,
                viewGroup,false);
//        v.setOnClickListener(clickListener);
        return new MemListRecAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        MmemList mmemList = mmemLists.get(i);
        myViewHolder.name.setText(mmemList.name);
        myViewHolder.dof.setText(mmemList.dof);
        myViewHolder.dof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CalenderDialog(ctx, MemListRecAdapter.this,
                        myViewHolder.dof.getText().toString(), "", myViewHolder.dof, i).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mmemLists.size();
    }

    @Override
    public void getDate(String date, TextView editText) {
//        editText.setText(date);
//        int pos = recyclerView.getChildLayoutPosition(editText);
//        mmemLists.get(pos).dof = date;
    }

    @Override
    public void getDate(String date, TextView editText, int pos) {
        editText.setText(date);
        mmemLists.get(pos).dof = date;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,dof;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            dof = (TextView) itemView.findViewById(R.id.dof);
        }
    }
}
