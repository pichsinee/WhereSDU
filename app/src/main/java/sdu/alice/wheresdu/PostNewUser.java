package sdu.alice.wheresdu;

import android.content.Context;
import android.os.AsyncTask;

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



        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}   //Main Class
