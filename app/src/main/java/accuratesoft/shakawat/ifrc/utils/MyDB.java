package accuratesoft.shakawat.ifrc.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ifrc";
    private static final int DATABASE_VERSION = 12;

    private static String household = "household";
    private static String participant = "participant";
    private static String followup = "followup";

    public static final String create_household ="CREATE TABLE " + household
            + "( id INTEGER PRIMARY KEY AUTOINCREMENT,"
            +" cc INTEGER NOT NULL DEFAULT 0,"
            +" ward INTEGER NOT NULL DEFAULT 0,"
            +" hh INTEGER NOT NULL DEFAULT 0,"
            +" hh_num TEXT NOT NULL DEFAULT '',"
            +" house_address TEXT NOT NULL DEFAULT '',"
            +" area TEXT NOT NULL DEFAULT '',"
            +" lat TEXT NOT NULL DEFAULT '',"
            +" lung TEXT NOT NULL DEFAULT '',"
            +" info_name TEXT NOT NULL DEFAULT '',"
            +" info_sex INTEGER NOT NULL DEFAULT 0,"
            +" info_age INTEGER NOT NULL DEFAULT 0,"
            +" mob TEXT NOT NULL DEFAULT '',"
            +" mem INTEGER NOT NULL DEFAULT 0,"
            +" symptom_7 INTEGER NOT NULL DEFAULT 0,"
            +" symptom_7_mem INTEGER NOT NULL DEFAULT 0,"
            +" symptom_6m INTEGER NOT NULL DEFAULT 0,"
            +" symptom_6m_mem INTEGER NOT NULL DEFAULT 0,"
            +" reffer INTEGER NOT NULL DEFAULT 0,"
            +" is_death INTEGER NOT NULL DEFAULT 0,"
            +" dname TEXT NOT NULL DEFAULT '',"
            +" dage INTEGER NOT NULL DEFAULT 0,"
            +" unknown_disease_time TEXT NOT NULL DEFAULT '',"
            +" unknown_sick TEXT NOT NULL DEFAULT '',"
            +" unknown_dead TEXT NOT NULL DEFAULT '',"
            +" unknown_around_time TEXT NOT NULL DEFAULT '',"
            +" death_animals_number TEXT NOT NULL DEFAULT '',"
            +" death_animals_time TEXT NOT NULL DEFAULT '',"
            +" death_animals_type TEXT NOT NULL DEFAULT '',"
            +" unknown_disease INTEGER NOT NULL DEFAULT 0,"
            +" unkown_around INTEGER NOT NULL DEFAULT 0,"
            +" has_animals INTEGER NOT NULL DEFAULT 0,"
            +" death_animals INTEGER NOT NULL DEFAULT 0,"
            +" unknown_disease_type TEXT NOT NULL DEFAULT '',"
            +" unknown_disease_type_oth TEXT NOT NULL DEFAULT '',"
            +" user INTEGER NOT NULL DEFAULT 0,"
            +" created_at DATETIME DEFAULT CURRENT_TIMESTAMP )";
    public static final String create_participant ="CREATE TABLE " + participant
            + "( id INTEGER PRIMARY KEY AUTOINCREMENT,"
            +" num INTEGER NOT NULL DEFAULT 0,"
            +" hh_number TEXT NOT NULL DEFAULT '',"
            +" id_number TEXT NOT NULL DEFAULT '',"
            +" name  TEXT NOT NULL DEFAULT '',"
            +" age INTEGER NOT NULL DEFAULT 0,"
            +" sex INTEGER NOT NULL DEFAULT 0,"
            +" relation INTEGER NOT NULL DEFAULT 0,"
            +" comorbidity TEXT NOT NULL DEFAULT '',"
            +" rel_oth TEXT NOT NULL DEFAULT '',"
            +" sick7 INTEGER NOT NULL DEFAULT 0,"
            +" fever INTEGER NOT NULL DEFAULT 0,"
            +" headache INTEGER NOT NULL DEFAULT 0,"
            +" aches INTEGER NOT NULL DEFAULT 0,"
            +" mus_pain INTEGER NOT NULL DEFAULT 0,"
            +" arth_pain INTEGER NOT NULL DEFAULT 0,"
            +" rash INTEGER NOT NULL DEFAULT 0,"
            +" vomit INTEGER NOT NULL DEFAULT 0,"
            +" vomit_tendency INTEGER NOT NULL DEFAULT 0,"
            +" dia INTEGER NOT NULL DEFAULT 0,"
            +" is_test INTEGER NOT NULL DEFAULT 0,"
            +" ns1 INTEGER NOT NULL DEFAULT 0,"
            +" igg INTEGER NOT NULL DEFAULT 0,"
            +" igm INTEGER NOT NULL DEFAULT 0,"
            +" unk_test INTEGER NOT NULL DEFAULT 0,"
            +" ns1_p INTEGER NOT NULL DEFAULT 0,"
            +" igg_p INTEGER NOT NULL DEFAULT 0,"
            +" igm_p INTEGER NOT NULL DEFAULT 0,"
            +" ns1_vis INTEGER NOT NULL DEFAULT 0,"
            +" igg_vis INTEGER NOT NULL DEFAULT 0,"
            +" igm_vis INTEGER NOT NULL DEFAULT 0,"
            +" is_reffer INTEGER NOT NULL DEFAULT 0,"
            +" sick6m INTEGER NOT NULL DEFAULT 0,"
            +" doc_list INTEGER NOT NULL DEFAULT 0,"
            +" prev_ns1 INTEGER NOT NULL DEFAULT 0,"
            +" prev_igg INTEGER NOT NULL DEFAULT 0,"
            +" prev_igm INTEGER NOT NULL DEFAULT 0,"
            +" prev_unk_test INTEGER NOT NULL DEFAULT 0,"
            +" prev_ns1_p INTEGER NOT NULL DEFAULT 0,"
            +" prev_igg_p INTEGER NOT NULL DEFAULT 0,"
            +" prev_igm_p INTEGER NOT NULL DEFAULT 0,"
            +" prev_ns1_vis INTEGER NOT NULL DEFAULT 0,"
            +" prev_igg_vis INTEGER NOT NULL DEFAULT 0,"
            +" prev_igm_vis INTEGER NOT NULL DEFAULT 0,"
            +" outcome INTEGER NOT NULL DEFAULT 0,"
            +" comorbidity_oth_txt TEXT NOT NULL DEFAULT '',"
            +" temp_measured INTEGER NOT NULL DEFAULT 0,"
            +" fever_duration TEXT NOT NULL DEFAULT '',"
            +" awd_daygap TEXT NOT NULL DEFAULT '',"
            +" user INTEGER NOT NULL DEFAULT 0,"
            +" created_at DATETIME DEFAULT CURRENT_TIMESTAMP )";

    public static final String create_followup ="CREATE TABLE " + followup
            + "( id INTEGER PRIMARY KEY AUTOINCREMENT,"
            +" num INTEGER NOT NULL DEFAULT 0,"
            +" hh_number TEXT NOT NULL DEFAULT '',"
            +" id_number TEXT NOT NULL DEFAULT '',"
            +" mob TEXT NOT NULL DEFAULT '',"
            +" name TEXT NOT NULL DEFAULT '',"
            +" age INTEGER NOT NULL DEFAULT 0,"
            +" sex INTEGER NOT NULL DEFAULT 0,"
            +" relation INTEGER NOT NULL DEFAULT 0,"
            +" sick7 INTEGER NOT NULL DEFAULT 0,"
            +" fever INTEGER NOT NULL DEFAULT 0,"
            +" headache INTEGER NOT NULL DEFAULT 0,"
            +" aches INTEGER NOT NULL DEFAULT 0,"
            +" mus_pain INTEGER NOT NULL DEFAULT 0,"
            +" arth_pain INTEGER NOT NULL DEFAULT 0,"
            +" rash INTEGER NOT NULL DEFAULT 0,"
            +" vomit INTEGER NOT NULL DEFAULT 0,"
            +" vomit_tendency INTEGER NOT NULL DEFAULT 0,"
            +" dia INTEGER NOT NULL DEFAULT 0,"
            +" is_test INTEGER NOT NULL DEFAULT 0,"
            +" ns1 INTEGER NOT NULL DEFAULT 0,"
            +" igg INTEGER NOT NULL DEFAULT 0,"
            +" igm INTEGER NOT NULL DEFAULT 0,"
            +" unk_test INTEGER NOT NULL DEFAULT 0,"
            +" ns1_p INTEGER NOT NULL DEFAULT 0,"
            +" igg_p INTEGER NOT NULL DEFAULT 0,"
            +" igm_p INTEGER NOT NULL DEFAULT 0,"
            +" ns1_vis INTEGER NOT NULL DEFAULT 0,"
            +" igg_vis INTEGER NOT NULL DEFAULT 0,"
            +" igm_vis INTEGER NOT NULL DEFAULT 0,"
            +" is_reffer INTEGER NOT NULL DEFAULT 0,"
            +" is_doc_visit INTEGER NOT NULL DEFAULT 0,"
            +" doc_list INTEGER NOT NULL DEFAULT 0,"
            +" user INTEGER NOT NULL DEFAULT 0,"
            +" created_at DATETIME DEFAULT CURRENT_TIMESTAMP )";
    public MyDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_household);
        db.execSQL(create_participant);
        db.execSQL(create_followup);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +household);
        db.execSQL("DROP TABLE IF EXISTS " +participant);
        db.execSQL("DROP TABLE IF EXISTS " +followup);
        onCreate(db);
    }
    public void insert(ContentValues cv_household, ArrayList<ContentValues> cv_participants){
        checkndelRec(cv_household.getAsString("hh_num"));
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cv_household.put("created_at",dateFormat.format(currentTime));
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(household, null, cv_household);
        for (ContentValues cv_Participant:cv_participants){
            cv_Participant.put("created_at",dateFormat.format(currentTime));
            db.insert(participant, null, cv_Participant);
        }
    }
    public void insert_followup(ArrayList<ContentValues> cvs){
//        checkndelRec_followup(cv_followup.getAsString("id_number"));
//        Date currentTime = Calendar.getInstance().getTime();
//        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        cv_followup.put("created_at",dateFormat.format(currentTime));
        SQLiteDatabase db = this.getWritableDatabase();
//        db.insert(followup, null, cv_followup);
        for (ContentValues cv:cvs) {
            db.insert(followup,null,cv);
        }
    }
    public void update_followup(ContentValues cv_followup,String id){
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cv_followup.put("created_at",dateFormat.format(currentTime));
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(followup,cv_followup,"id_number=?",new String[]{id});
    }
    public Cursor get_followup(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + followup ;
        Cursor c = db.rawQuery(selectQuery, null);
        return c;
    }
    public Cursor get_followup_done(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + followup +" WHERE is_doc_visit >0 ";
        Cursor c = db.rawQuery(selectQuery, null);
        return c;
    }
//    private void checkndelRec_followup(String id_num){
//        SQLiteDatabase db1 = this.getWritableDatabase();
//        String deletetQuery = "DELETE FROM " + followup +" WHERE id_number = '"+id_num +"'";
//        db1.execSQL(deletetQuery);
//    }
    public Cursor get_household(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + household ;
        Cursor c = db.rawQuery(selectQuery, null);
        return c;
    }
    public Cursor get_participant(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + participant ;
        Cursor c = db.rawQuery(selectQuery, null);
        return c;
    }
    public Cursor get_household(String hh_num){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + household + " WHERE hh_num='"+hh_num+"'" ;
        Cursor c = db.rawQuery(selectQuery, null);
        return c;
    }
    public Cursor get_participant(String hh_num){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + participant + " WHERE hh_number='"+hh_num+"'" ;
        Cursor c = db.rawQuery(selectQuery, null);
        return c;
    }
    private void checkndelRec(String hh_num){
        Log.d("prev.","Record Check");
        SQLiteDatabase db = this.getReadableDatabase();
        SQLiteDatabase db1 = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + household +" WHERE hh_num = '"+hh_num+"'";
        Log.d("checkQ",selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()){
            do {
                Log.d("prev.","case found");
                String deletetQuery = "DELETE FROM " + household +" WHERE hh_num = '"+hh_num +"'";
                db1.execSQL(deletetQuery);
                deletetQuery = "DELETE FROM " + participant +" WHERE hh_number = '"+hh_num +"'";
                db1.execSQL(deletetQuery);
            }while (c.moveToNext());
        }
    }
    public void del_all(){
        SQLiteDatabase db1 = this.getWritableDatabase();
        String deletetQuery = "DELETE FROM " + household ;
        db1.execSQL(deletetQuery);
        deletetQuery = "DELETE FROM " + participant;
        db1.execSQL(deletetQuery);
    }
    public void del_followup(){
        SQLiteDatabase db1 = this.getWritableDatabase();
        String deletetQuery = "DELETE FROM " + followup +" WHERE is_doc_visit >0 ";
        db1.execSQL(deletetQuery);
    }
    public Cursor get_max_hh(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT MAX(cast(hh as integer)) as 'hh' FROM " + household;
        Cursor c = db.rawQuery(selectQuery, null);
        return c;
    }
}
