package sdu.alice.wheresdu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import sdu.alice.wheresdu.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Show Fragment
        if (savedInstanceState == null) {   //ถ้าเปิดใช้งานครั้งแรก คือ savedInstanceState จะว่างเปล่า
            MainFragment mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frangmentContent, mainFragment).commit();
        }

    }      //Main Method



}   //Main Class
