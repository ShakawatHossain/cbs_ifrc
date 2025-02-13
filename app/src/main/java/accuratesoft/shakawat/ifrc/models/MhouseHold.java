package accuratesoft.shakawat.ifrc.models;

import java.text.BreakIterator;
import java.util.ArrayList;

public class MhouseHold {
    public static int id=0; //Local DB id to check up/ins
    public static int cc=0;
    public static int ward=0;
    public static int hh=0; //Interview number
    public static String hh_num="";
    public static String house_address="";
    public static String area="";
    public static String lat="";
    public static String lung="";
    public static String info_name="";
    public static int info_sex=0;
    public static int info_age=0;
    public static String mob="";
    public static int mem=0;
    public static int symptom_7=0;
    public static int symptom_7_mem=0;
    public static int symptom_6m=0;
    public static int symptom_6m_mem=0;
    public static int reffer=0;
    public static int is_death=0;
    public static String dname="";
    public static int dage=0;
//    new fields
    public static String unknown_disease_time = "";
    public static String unknown_sick = "";
    public static String unknown_dead = "";
    public static String unknown_around_time = "";
    public static String death_animals_number = "";
    public static String death_animals_time = "";
    public static String death_animals_type = "";
    public static int unknown_disease = 0;
    public static int unkown_around = 0;
    public static int has_animals = 0;
    public static int death_animals = 0;
    public static ArrayList<Mparticipant> participants = new ArrayList<>();
    public static String unknown_disease_type="";
    public static String unknown_disease_type_oth="";

    public static void clear(){
        id=0;
        cc=0;
        ward=0;
        hh=0;
        hh_num="";
        house_address = "";
        area="";
        lat="";
        lung="";
        info_name="";
        info_sex=0;
        info_age=0;
        mob="";
        mem=0;
        symptom_7=0;
        symptom_7_mem=0;
        symptom_6m=0;
        symptom_6m_mem=0;
        is_death=0;
        dname="";
        dage=0;
        reffer=0;
        unknown_disease_time = "";
        unknown_sick = "";
        unknown_dead = "";
        unknown_around_time = "";
        death_animals_number = "";
        death_animals_time = "";
        death_animals_type = "";
        unknown_disease = 0;
        unkown_around = 0;
        has_animals = 0;
        death_animals = 0;
        unknown_disease_type="";
        unknown_disease_type_oth="";
        participants.clear();
    }
}
