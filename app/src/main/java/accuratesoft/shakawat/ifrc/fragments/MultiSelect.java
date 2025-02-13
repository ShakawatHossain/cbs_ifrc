package accuratesoft.shakawat.ifrc.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import accuratesoft.shakawat.ifrc.R;
import accuratesoft.shakawat.ifrc.interfaces.MultiInterface;
import accuratesoft.shakawat.ifrc.utils.Util;

public class MultiSelect extends Dialog {
    Context ctx;
    String slectedItems,headline;
    ArrayList<String> opList,hashKeySet;
    HashMap<String,String> ophash;
    ArrayList<CheckBox> checkBoxes;
    LinearLayout lin;
    Button submit;
    MultiInterface multiInterface;
    TextView head,tv;

    public MultiSelect(Context ctx, MultiInterface multiInterface, String slectedItems, ArrayList<String> opList, TextView tv,String headline){
        super(ctx);
        this.ctx = ctx;
        this.slectedItems = slectedItems;
        this.opList = opList;
        checkBoxes = new ArrayList<>();
        this.multiInterface = multiInterface;
        this.tv = tv;
        this.headline = headline;
    }
    public MultiSelect(Context ctx, MultiInterface multiInterface, String slectedItems, HashMap<String,String> ophash, TextView tv,String headline){
        super(ctx);
        this.ctx = ctx;
        this.slectedItems = slectedItems;
        this.ophash = ophash;
        checkBoxes = new ArrayList<>();
        this.multiInterface = multiInterface;
        this.tv = tv;
        this.headline = headline;
        Iterator keys = ophash.keySet().iterator();
        hashKeySet = new ArrayList<>();
        while (keys.hasNext()){
            hashKeySet.add(((String) keys.next()));
        }
        for (String s : hashKeySet)
            Log.d("value: ",s);
        hashKeySet = sortStrings(hashKeySet);
        Log.d("Multiselect","hash values serial!!");
        for (String s : hashKeySet)
            Log.d("value: ",s);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiselect_view);
        head = (TextView) findViewById(R.id.headline);
        head.setText(headline);
        lin = (LinearLayout) findViewById(R.id.lin);
        submit = (Button) findViewById(R.id.submit);
        String[] parts = slectedItems. split(",");
        if (opList!=null) {
            for (int i = 0; i < opList.size(); i++) {
                CheckBox checkBox = new CheckBox(ctx);
                checkBox.setText(opList.get(i));
                boolean is_checked = false;
                for (int j = 0; j < parts.length; j++) {
                    if (!parts[j].isEmpty()) {
                        int si = Integer.parseInt(parts[j]);
                        if (si == i) {
                            is_checked = true;
                            break;
                        }
                    }
                }
                if (is_checked)
                    checkBox.setChecked(true);
                lin.addView(checkBox);
                checkBoxes.add(checkBox);
            }
        }
        if (ophash!=null){
//            for (int i=0;i<ophash.size();i++){
            for (int i=0;i<hashKeySet.size();i++){
                CheckBox checkBox = new CheckBox(ctx);
                checkBox.setText(ophash.get(hashKeySet.get(i)));
                boolean is_checked=false;
                for (int j=0;j<parts.length;j++){
                    if(!parts[j].isEmpty()){
                        if (parts[j].compareTo(hashKeySet.get(i))==0){
                            is_checked = true;
                            break;
                        }
                    }
                }
                if (is_checked)
                    checkBox.setChecked(true);
                lin.addView(checkBox);
                checkBoxes.add(checkBox);
            }
        }
        submit.setOnClickListener(clickListener);
    }
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId()==submit.getId()){
                String s = "";
                if (opList!=null){
                    for (int i=0; i<checkBoxes.size();i++){
                        if (checkBoxes.get(i).isChecked()){
                            if (s.isEmpty()) s+=i;
                            else s+=","+i;
                        }
                    }
                } else if (ophash!=null) {
                    boolean none_checked=false;
                    for (int i=0; i<checkBoxes.size();i++){

                        if (checkBoxes.get(i).isChecked()){
                            if (s.isEmpty()) {
                                if (Integer.parseInt(hashKeySet.get(i))==0){
                                    none_checked=true;
                                }
                                s += hashKeySet.get(i);
                            }
                            else {
                                if (none_checked){
                                    Util.makeToast(ctx,"Please unselect none");
                                    return;
                                }
                                s += "," + hashKeySet.get(i);
                            }
                        }
                    }
                }
                multiInterface.setText(s,tv);
                dismiss();
            }
        }
    };
    private ArrayList<String> sortStrings(ArrayList<String> strings){
        ArrayList<String> strs = new ArrayList<>();
        if (strs.size()==0)
            strs.add(strings.get(0));
        for (int i=1;i<strings.size();i++){
            boolean is_inserted=false;
            String k = strings.get(i);
            for(int j=0;j<i;j++){
                if(Integer.parseInt(k)<(Integer.parseInt(strs.get(j)))){
                    strs.add(j,k);
                    is_inserted=true;
                    break;
                }
            }
            if (!is_inserted)
                strs.add(strings.get(i));
        }
        return strs;
    }
}