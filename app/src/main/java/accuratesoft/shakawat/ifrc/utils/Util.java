package accuratesoft.shakawat.ifrc.utils;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Util {
    public static final String secret_key = "ifrc-dengue";  //Shared-preferrence
    public static final String url_old = "http://119.40.84.187/ifrc/";
    public static final String url = "http://119.40.84.187/ifrc2/";

    public static void makeToast(Context ctx,String s){
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }
    public static byte[] readBytesFromUri(Context context, Uri uri) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }

        inputStream.close();
        return byteBuffer.toByteArray();
    }
    public static File writeBytesToFile(Context context, byte[] data, String filename) throws IOException {
        File file = new File(context.getCacheDir(), filename);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data);
        }
        return file;
    }
}
