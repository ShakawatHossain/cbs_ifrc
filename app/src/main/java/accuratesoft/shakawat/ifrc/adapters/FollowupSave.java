package accuratesoft.shakawat.ifrc.adapters;

import android.content.ContentValues;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import accuratesoft.shakawat.ifrc.utils.MyDB;

public class FollowupSave extends Thread{
    JSONArray jsonArray;
    Context context;
    MyDB myDB;
    public FollowupSave(JSONArray jsonArray,Context context){
        this.jsonArray = jsonArray;
        this.context = context;
        myDB = new MyDB(context);
    }

    @Override
    public void run() {
        super.run();
        ArrayList<ContentValues> cvs = new ArrayList<>();
        for (int i=0;i<jsonArray.length();i++){
            try {
                ContentValues contentValues = new ContentValues();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                contentValues.put("num",jsonObject.getInt("num"));
                contentValues.put("hh_number",jsonObject.getString("hh_number"));
                contentValues.put("id_number",jsonObject.getString("id_number"));
                contentValues.put("mob",jsonObject.getString("mob"));
                contentValues.put("name",jsonObject.getString("name"));
                contentValues.put("age",jsonObject.getInt("age"));
                contentValues.put("sex",jsonObject.getInt("sex"));
                contentValues.put("relation",jsonObject.getInt("relation"));
                contentValues.put("sick7",jsonObject.getInt("sick7"));
                contentValues.put("fever",jsonObject.getInt("fever"));
                contentValues.put("headache",jsonObject.getInt("headache"));
                contentValues.put("aches",jsonObject.getInt("aches"));
                contentValues.put("mus_pain",jsonObject.getInt("mus_pain"));
                contentValues.put("arth_pain",jsonObject.getInt("arth_pain"));
                contentValues.put("rash",jsonObject.getInt("rash"));
                contentValues.put("vomit",jsonObject.getInt("vomit"));
                contentValues.put("vomit_tendency",jsonObject.getInt("vomit_tendency"));
                contentValues.put("dia",jsonObject.getInt("dia"));
                contentValues.put("is_test",jsonObject.getInt("is_test"));
                contentValues.put("ns1",jsonObject.getInt("ns1"));
                contentValues.put("igg",jsonObject.getInt("igg"));
                contentValues.put("igm",jsonObject.getInt("igm"));
                contentValues.put("unk_test",jsonObject.getInt("unk_test"));
                contentValues.put("ns1_p",jsonObject.getInt("ns1_p"));
                contentValues.put("igg_p",jsonObject.getInt("igg_p"));
                contentValues.put("igm_p",jsonObject.getInt("igm_p"));
                contentValues.put("ns1_vis",jsonObject.getInt("ns1_vis"));
                contentValues.put("igg_vis",jsonObject.getInt("igg_vis"));
                contentValues.put("igm_vis",jsonObject.getInt("igm_vis"));
                cvs.add(contentValues);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        myDB.insert_followup(cvs);
    }
}
