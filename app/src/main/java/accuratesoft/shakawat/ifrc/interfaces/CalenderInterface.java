package accuratesoft.shakawat.ifrc.interfaces;

import android.widget.TextView;

public interface CalenderInterface {
    void getDate(String date, TextView editText);
//    Sepecially disengned for RecyclerView
    void getDate(String date, TextView editText, int pos);
}
