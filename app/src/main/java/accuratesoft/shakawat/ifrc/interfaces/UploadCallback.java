package accuratesoft.shakawat.ifrc.interfaces;

public interface UploadCallback {
    void onSuccess(String response);
    void onError(String error);
}
