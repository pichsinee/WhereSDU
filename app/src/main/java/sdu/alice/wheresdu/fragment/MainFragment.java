package sdu.alice.wheresdu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sdu.alice.wheresdu.R;

/**
 * Created by aom on 6/7/2560.
 */

public class MainFragment extends Fragment {

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
        TextView textView = (TextView) getView().findViewById(R.id.txtNewRegister);
        textView.setOnClickListener(new View.OnClickListener() {    //กด n เลือก new กด On เลือก OnClickListener()
            @Override
            public void onClick(View view) {

                //ทำการ Intent เมื่อคลิก New Register ให้เปลี่ยน Fragment ไปหน้า RegisterFragment.java โดยแทนที่ fragment เดิม
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frangmentContent, RegisterFragment.registerInstance()).commit();
            }
        });

    }   //onActivityCreated


}   //Main Class
