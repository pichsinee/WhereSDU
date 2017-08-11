package sdu.alice.wheresdu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import sdu.alice.wheresdu.GetAllUser;
import sdu.alice.wheresdu.MyAlert;
import sdu.alice.wheresdu.R;
import sdu.alice.wheresdu.Service;

/**
 * Created by aom on 6/7/2560.
 */

public class MainFragment extends Fragment {

    //Explicit
    private EditText userEditText, passwordEditText;

    public static MainFragment mainInstance() {
        MainFragment mainFragment = new MainFragment();
        Bundle bundle = new Bundle();
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    @Nullable
    @Override
    //onCreateView เป็น Method สำหรับสร้าง View MainFragment
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_frangment_layout,
                container, false);

        return view;
    }   //onCreateView

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Register Controller การทำให้ตัวอักศร New Register คลิกได้
        RegisterController();

        //Login Controller
        loginController();

    }   //onActivityCreated

    private void loginController() {

        //Initial View
        userEditText = (EditText) getView().findViewById(R.id.edtUser);
        passwordEditText = (EditText) getView().findViewById(R.id.edtPassword);
        Button button = (Button) getView().findViewById(R.id.btnLogin);

        //Check Space
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strUser = userEditText.getText().toString().trim();  //แปลงค่า EditText เป็น String
                String strPassword = passwordEditText.getText().toString().trim();

                if (strUser.equals("") || strPassword.equals("")) {

                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.myDialog(getResources().getString(R.string.title_have), //getResources().getString(R...) เป็นการเรียกใช้ resource จาก xml ไฟล์
                            getResources().getString(R.string.message_have));

                } else {

                    checkUserAndPass(strUser, strPassword);

                }
            }
        });

    }   //Method loginController()

    private void checkUserAndPass(String strUser, String strPassword) {

        String tag = "7JulyV1";

        try {

            GetAllUser getAllUser = new GetAllUser(getActivity());
            getAllUser.execute();

            String strJSON = getAllUser.get();  //ดึงข้อมูลเจอสันออกมาเป็น String ยาวๆ เรียงต่อกัน
            Log.d(tag, "JSON ==> " + strJSON);
            boolean status = true; // true ==> user false ไม่มี user ใน DB
            String strID = null;
            String strName = null;
            String strPasswordTrue = null;

            JSONArray jsonArray = new JSONArray(strJSON);

            for (int i=0; i<jsonArray.length(); i+=1) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (strUser.equals(jsonObject.getString("User"))) {
                    status = false; // user ==> True  มี user อยู่จริง
                    strID = jsonObject.getString("id"); //ตัวแปรสีเขียว ชื่อต้องตรงกับ field ใน DB
                    strName = jsonObject.getString("Name");
                    strPasswordTrue = jsonObject.getString("Password");
                } //if

            } //For

            //Check ๊User&Password ที่ป้อนเข้ามา
            if (status) {   //ถ้า status เป็น True
                //User False
                MyAlert myAlert = new MyAlert(getActivity());
                myAlert.myDialog("User False", "No this user in my database");
            } else if (strPassword.equals(strPasswordTrue)) {
                //Password True ใส่ Password ถูกต้อง
                Toast.makeText(getActivity(), "Welcome " + strName, Toast.LENGTH_SHORT).show(); //ถ้าใส่ user&pass ถูกต้องให้แสดงข้อความ Toast

                //Intent to Service
                Intent intent = new Intent(getActivity(), Service.class);
                intent.putExtra("ID", strID);
                getActivity().startActivity(intent);

            } else {
                //Password False ใส่ Password ไม่ถูกต้อง
                MyAlert myAlert = new MyAlert(getActivity());
                myAlert.myDialog("Password False", "Please try again, Password False!!");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }   //Method checkUserAndPass


    private void RegisterController() {
        TextView textView = (TextView) getView().findViewById(R.id.txtNewRegister);
        textView.setOnClickListener(new View.OnClickListener() {    //กด n เลือก new กด On เลือก OnClickListener()
            @Override
            public void onClick(View view) {

                //ทำการ Intent เมื่อคลิก New Register ให้เปลี่ยน Fragment ไปหน้า RegisterFragment.java โดยแทนที่ fragment เดิม
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frangmentContent, RegisterFragment.registerInstance()).commit();
            }
        });
    }


}   //Main Class
