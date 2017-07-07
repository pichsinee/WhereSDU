package sdu.alice.wheresdu;

import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * Created by aom on 7/7/2560.
 */

public class PostNewUser extends AsyncTask<String, Void,String>{

    private Context context;    //การสร้าง Context เป็นการทำ Content Provider ต่อท่องส่งข้อมูล ตัวแปร แบบ Text 3 ตัว ไปยัง Server

    public PostNewUser(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {    //เป็นการทำงานเบื้องหลังเมื่อมีเน็ต โดยไม่ต้องสั่ง Start

        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("isAdd", "true")
                    .add("Name", strings[0])
                    .add("User", strings[1])
                    .add("Password", strings[2])
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(strings[3]).post(requestBody).build();   //เป็นการระบุจ่าหน้าซองที่ต้องการส่ง โดย strings[3] คือ ตำแหน่งง url ที่ต้องการส่ง
            Response response = okHttpClient.newCall(request).execute();    //เรื่อมการทำงานแบบซ้ำๆ กรณีทำไม่สำเร็จ
            return response.body().string();

        } catch (Exception e) {
            e.printStackTrace();
            return null;    //ถ้า Trace ทำงานสำเร็จจะโยนผลการทำงานกลับ แต่ถ้าทำไม่สำเร็จจะ Alert บอก
        }


    }
}   //Main Class
