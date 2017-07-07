package sdu.alice.wheresdu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import sdu.alice.wheresdu.MyAlert;
import sdu.alice.wheresdu.MyConstant;
import sdu.alice.wheresdu.PostNewUser;
import sdu.alice.wheresdu.R;

/**
 * Created by aom on 6/7/2560.
 */

public class RegisterFragment extends Fragment {

    //Explicite
    private String nameString, userString, passwordString;

    public static RegisterFragment registerInstance() {
        RegisterFragment registerFragment = new RegisterFragment();
        Bundle bundle = new Bundle();
        registerFragment.setArguments(bundle);
        return registerFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register_fragment_layout, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Back Controller
        backController();

        //New Register Controller
        newRegisterController();

    }   //onActivityCreated

    private void newRegisterController() {

        //Initial View ทำการผูกตัวแปรบน xml (id ของ view) ให้ java รู้จัก
        final EditText nameEditText = (EditText) getView().findViewById(R.id.edtName);
        final EditText userEditText = (EditText) getView().findViewById(R.id.edtUser);
        final EditText passwordEditText = (EditText) getView().findViewById(R.id.edtPassword);
        Button newRegisterButton = (Button) getView().findViewById(R.id.btnNewRegister);

        //Get Event From Click New Register
        newRegisterButton.setOnClickListener(new View.OnClickListener() {  //ในวงเล็บกด n เลือก new กด O เลือก OnClickListener()
            @Override
            public void onClick(View view) {

                //Get Value From EditText
                nameString = nameEditText.getText().toString().trim();
                userString = userEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();

                //Check Space
                if (nameString.length() == 0
                        || userString.length() == 0
                        || passwordString.length() ==0) { //ถ้าช่อง Name,User,Password เป็นค่าว่าง
                    //Have Space ถ้ามีค่าว่างเกิดขึ้น ให้แสดง Alert
                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.myDialog(getString(R.string.title_have), getString(R.string.message_have));

                } else {
                    //No Space ถ้าไม่ว่าง ให้ตรวจสอบว่า ใส่ค่า user&pass ถูกต้องหรือไม่
                    uploadValueToServer();

                }

            }   //OnClickListener
        });

    }   //newRegisterController

    private void uploadValueToServer() {

        String tag = "7JulyV1";
        MyConstant myConstant = new MyConstant();
        String urlPHP = myConstant.getUrlAddress();

        try {

            PostNewUser postNewUser = new PostNewUser(getActivity());
            postNewUser.execute(nameString, userString, passwordString, urlPHP);    //ตัวแปรที่ส่งขึ้นไป add ใน server
            String result = postNewUser.get();  //แสดงผลลัพธืการทำงาน
            Log.d(tag, "result ==> " + result);

            if (Boolean.parseBoolean(result)) { //เมื่อผลการทำงานเป็น true แปลงค่า String คำว่า true เป็น Boolean และทำการกลับไปยังหน้า Main Fragment

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frangmentContent, MainFragment.mainInstance())
                        .commit();

            } else {
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void backController() {
        ImageView imageView = (ImageView) getView().findViewById(R.id.imvBack);
        imageView.setOnClickListener(new View.OnClickListener() {   //ในวงเล็บพิมพ์ n เลือก new พิมพ์ O เลือก OnClickListener()
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frangmentContent, MainFragment.mainInstance()).commit();
            }
        });
    }


}   //Main Class
