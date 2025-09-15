package accuratesoft.shakawat.ifrc.utils;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class VolleyMultipartRequest extends Request<NetworkResponse> {
    private final Response.Listener<NetworkResponse> mListener;
    private final Map<String, String> mHeaders;
    private final Map<String, DataPart> mByteData;

    public VolleyMultipartRequest(String url,
                                  Response.Listener<NetworkResponse> listener,
                                  Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        this.mListener = listener;
        this.mHeaders = new HashMap<>();
        this.mByteData = new HashMap<>();
    }

    public void addFile(String key, byte[] data, String filename) {
        mByteData.put(key, new DataPart(filename, data));
    }

    @Override
    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    @Override
    public String getBodyContentType() {
        return "multipart/form-data;boundary=" + boundary;
    }

    @Override
    public byte[] getBody() {
        // Build multipart body using ByteArrayOutputStream
        return new MultipartUtils().buildMultipartBody(mByteData, boundary);
    }

    @Override
    protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
        return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(NetworkResponse response) {
        mListener.onResponse(response);
    }

    public static class DataPart {
        public final String filename;
        public final byte[] content;

        public DataPart(String filename, byte[] content) {
            this.filename = filename;
            this.content = content;
        }
    }

    private static final String boundary = "apiclient-" + System.currentTimeMillis();

    public class MultipartUtils {
        public byte[] buildMultipartBody(Map<String, VolleyMultipartRequest.DataPart> byteData, String boundary) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);

            try {
                for (Map.Entry<String, VolleyMultipartRequest.DataPart> entry : byteData.entrySet()) {
                    String key = entry.getKey();
                    VolleyMultipartRequest.DataPart dataPart = entry.getValue();

                    dos.writeBytes("--" + boundary + "\r\n");
                    dos.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"; filename=\"" + dataPart.filename + "\"\r\n");
                    dos.writeBytes("Content-Type: image/jpeg\r\n\r\n");
                    dos.write(dataPart.content);
                    dos.writeBytes("\r\n");
                }

                dos.writeBytes("--" + boundary + "--\r\n");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bos.toByteArray();
        }
    }

}
