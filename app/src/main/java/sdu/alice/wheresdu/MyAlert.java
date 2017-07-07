package sdu.alice.wheresdu;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import java.security.PrivateKey;

/**
 * Created by aom on 7/7/2560.
 */

public class MyAlert {

    private Context context;

    public MyAlert(Context context) {
        this.context = context;
    }

    public void myDialog(String strTitle, String strMessage) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);   //ทำให้ปุ่ม cancle ไม่ทำงาน
        builder.setIcon(R.mipmap.ic_name);  //กำหนกรูป icon
        builder.setTitle(strTitle);     //กำหนดข้อความ Title
        builder.setMessage(strMessage);  //กำหนดข้อความ Message
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { //OK คือข้อความบนปุ่มที่ยืนยันการทำงาน โดยหลัง , กด n เลือก new กด O เลือก OnClickListener
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();  //ทำให้ popup Dialog หายไป
            }
        });
        builder.show(); //สั่งให้ Method myDialog ทำงาน

    }
}
