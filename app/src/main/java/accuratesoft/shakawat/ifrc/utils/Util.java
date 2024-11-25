package accuratesoft.shakawat.ifrc.utils;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

public class Util {
    public static final String secret_key = "ifrc-dengue";  //Shared-preferrence
    public static final String url = "http://119.40.84.187/ifrc/";  //Shared-preferrence

    public static void makeToast(Context ctx,String s){
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }
}
