package mochamad.ulin.nuha.ta;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;


public class JSONParser {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    // Kontraktor
    public JSONParser() {

    }

    // Fungsi pengambil JSON dari URL
    // Untuk membuat Method POST dan GET
    public JSONObject makeHttpRequest(String url, String method,
                                      List<NameValuePair> params) {

        // Membuat Request HTTP
        try {

            // Cek Request Metode POST
            if (method == "POST") {
                // request method is POST
                // defaultHttpClient
                DefaultHttpClient httpClient = new
                        DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new
                        UrlEncodedFormEntity(params));

                HttpResponse httpResponse =
                        httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

                // Cek Request Metode GET
            } else if (method == "GET") {
                // request method is GET
                DefaultHttpClient httpClient = new
                        DefaultHttpClient();
                String paramString =
                        URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);

                HttpResponse httpResponse =
                        httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " +
                    e.toString());
        }

        // mencoba parse String ke JSON Object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " +
                    e.toString());
        }

        // Mengembalikan Nilai JSON String
        return jObj;

    }


}
