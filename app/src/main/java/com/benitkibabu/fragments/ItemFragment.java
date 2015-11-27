package com.benitkibabu.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.benitkibabu.adapters.TimetableAdapter;
import com.benitkibabu.helper.DbHelper;
import com.benitkibabu.models.Timetable;
import com.benitkibabu.ncigomobile.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ItemFragment extends Fragment implements AbsListView.OnItemClickListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private int secNumber;

    private OnFragmentInteractionListener mListener;

    private AbsListView mListView;
    private TimetableAdapter adapter;
    public static DbHelper db;
    private List<Timetable> timetables = new ArrayList<>();
    private String[] dayList;
    List<Timetable> tempList;

    public static ItemFragment newInstance(int sectionNumber) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ItemFragment() {
    }

    public void LoadData(){
        if(db != null){
            timetables.clear();
            for(Timetable tt : db.getTimetable()){
                if(tt != null){
                    timetables.add(tt);
                }
            }
            if(timetables.isEmpty()){
                Timetable t = new Timetable("Empty N/A","N/A","N/A","N/A","N/A","N/A");
                timetables.add(t);
            }
        }else{
            Timetable t = new Timetable("Null N/A","N/A","N/A","N/A","N/A","N/A");
            timetables.add(t);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DbHelper(getActivity().getBaseContext());
        LoadData();

        if (getArguments() != null) {
            secNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        }

        dayList = getResources().getStringArray(R.array.day_of_week_array);

        tempList = new ArrayList<>();
        for(Timetable t : timetables){
            if(t.getDay().equalsIgnoreCase(dayList[secNumber])){
                tempList.add(t);
            }
        }
        if(!tempList.isEmpty()){
            Collections.sort(tempList, new Comparator<Timetable>() {
                @Override
                public int compare(Timetable t1, Timetable t2) {
                    String start1 = t1.getStart();
                    String start2 = t2.getStart();
                    return start1.compareTo(start2);
                }
            });
        }

        adapter = new TimetableAdapter(getActivity(), R.layout.timetable_item_layout, tempList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(adapter);

        mListView.setOnItemClickListener(this);


        setEmptyText("Timetable Not Set");


        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(timetables.get(position).getId());
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(int id);
    }

}
