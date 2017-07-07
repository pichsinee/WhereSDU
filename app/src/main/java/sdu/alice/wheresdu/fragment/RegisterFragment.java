package sdu.alice.wheresdu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import sdu.alice.wheresdu.R;

/**
 * Created by aom on 6/7/2560.
 */

public class RegisterFragment extends Fragment {


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
