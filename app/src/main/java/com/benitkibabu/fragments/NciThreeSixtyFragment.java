package com.benitkibabu.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.benitkibabu.ncigomobile.R;

public class NciThreeSixtyFragment extends Fragment {

    private static final String ITEM_NAME = "itemName";

    public static NciThreeSixtyFragment newInstance(String name){
        NciThreeSixtyFragment fragment = new NciThreeSixtyFragment();
        Bundle args = new Bundle();
        args.putString(ITEM_NAME, name);
        fragment.setArguments(args);

        return fragment;
    }

    public NciThreeSixtyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_360_home, container, false);

//        Button goTo = (Button) view.findViewById(R.id.serviceBtn);
//        goTo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getActivity(), ThreeSixtyPage.class);
//                getActivity().startActivity(i);
//            }
//        });
        return view;
    }


}
