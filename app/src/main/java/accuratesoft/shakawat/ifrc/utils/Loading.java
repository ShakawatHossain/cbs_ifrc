package accuratesoft.shakawat.ifrc.utils;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class Loading {
    Button btn;
    ProgressBar progressBar;

    public Loading(Button btn,ProgressBar progressBar){
        this.btn = btn;
        this.progressBar = progressBar;
    }

    public void alterVisibility(){
        if (this.btn.getVisibility()== View.VISIBLE){
            this.btn.setVisibility(View.GONE);
            this.progressBar.setVisibility(View.VISIBLE);
        }else {
            this.btn.setVisibility(View.VISIBLE);
            this.progressBar.setVisibility(View.GONE);
        }
    }
}
