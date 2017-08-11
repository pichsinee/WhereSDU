package sdu.alice.wheresdu;

import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * Created by aom on 11/8/2560.
 */

public class EditLatLng extends AsyncTask<String, Void, String> {

    private Context context;

    public EditLatLng(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {

        try {

            OkHttpClient okHttpClient = new OkHttpClient(); //OkHttpClient เป็น Library ที่เชื่อมต่อ http
            RequestBody requestBody = new FormEncodingBuilder() //พิมพ์ถึง Fo แล้วกด Ctrl+Space เลือก FormEncodingBuilder()
                    .add("isAdd", "true")
                    .add("id", strings[0])
                    .add("Lat", strings[1])
                    .add("Lng", strings[2])
                    .build();

            Request.Builder builder = new Request.Builder(); // Request เลือกของ com.squreup.okhttp เท่านั้น
            Request request = builder.url(strings[3]).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();


        } catch (Exception e) { //กรณีทำไม่สำเร็จ จะส่งค่าว่าง เพื่อทำงานจนกว่าจะสำเร็จ
            e.printStackTrace();
            return null;
        }


    }
}   //Main Class
