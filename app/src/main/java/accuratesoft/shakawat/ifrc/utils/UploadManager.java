package accuratesoft.shakawat.ifrc.utils;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import accuratesoft.shakawat.ifrc.interfaces.UploadCallback;

public class UploadManager {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void uploadImage(Context context, File imageFile, String serverUrl, UploadCallback callback) {
        try {
            byte[] imageData = Files.readAllBytes(imageFile.toPath());

            VolleyMultipartRequest request = new VolleyMultipartRequest(
                    serverUrl,
                    response -> callback.onSuccess(new String(response.data)),
                    error -> callback.onError(error.getMessage())
            );

            request.addFile("image", imageData, imageFile.getName());

            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(request);

        } catch (IOException e) {
            callback.onError("File read error: " + e.getMessage());
        }
    }
}
