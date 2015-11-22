package com.benitkibabu.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.benitkibabu.abstracts.RecyclerViewScrollListener;
import com.benitkibabu.adapters.CustomNewsAdapter;
import com.benitkibabu.app.AppConfig;
import com.benitkibabu.app.AppController;
import com.benitkibabu.helper.DbHelper;
import com.benitkibabu.models.UpdateItem;
import com.benitkibabu.ncigomobile.UpdatesActivity;
import com.benitkibabu.ncigomobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 28/09/2015.
 */
public class UpdatesFragment extends Fragment {

    public static final String ITEM_NAME = "itemName";

    RecyclerView recyclerView;
    LinearLayout layout;
    SwipeRefreshLayout refreshLayout;

    List<UpdateItem> updateItems = new ArrayList<>();
    List<UpdateItem> tempList = new ArrayList<>();

    String[] typeList;

    ProgressDialog pd;
    CustomNewsAdapter adapter;
    DbHelper db;

    ArrayAdapter<String> spinAdapter;

    public static UpdatesFragment newInstance(String name) {
        UpdatesFragment fragment = new UpdatesFragment();
        Bundle args = new Bundle();
        args.putString(ITEM_NAME, name);
        fragment.setArguments(args);

        return fragment;
    }

    public UpdatesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_updates, container, false);

        pd = new ProgressDialog(getContext());
        pd.setCancelable(false);
        db = new DbHelper(getContext());

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.newsRecyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));

        typeList = getResources().getStringArray(R.array.type_array);
        spinAdapter = new ArrayAdapter<>(
                getActivity().getBaseContext(),
                android.R.layout.simple_spinner_item,
                typeList);

        adapter = new CustomNewsAdapter(getActivity(), R.layout.update_item_layout);

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CustomNewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                UpdateItem item = tempList.get(position);
                Intent i = new Intent(getActivity().getBaseContext(), UpdatesActivity.class);
                i.putExtra("id", item.getId());
                startActivity(i);
            }
        });

        recyclerView.setOnScrollListener(new RecyclerViewScrollListener(getActivity().getBaseContext()) {
            @Override
            public void onMoved(int distance) {
//                layout.setTranslationY(+distance);
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                newsList();
            }
        });
        refreshLayout.setColorSchemeResources(android.R.color.holo_red_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark
        );

//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                ((TextView) parent.getChildAt(0)).setTextColor(
//                        getResources().getColor(R.color.white));
//                filterList();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        return view;
    }

    void filterList() {
        if (updateItems != null) {
            adapter.clear();
            tempList.clear();
            for (UpdateItem i : updateItems) {
                tempList.add(i);
            }
            if (tempList.isEmpty()) {
                UpdateItem item = new UpdateItem("0", "No Updates", "No Updates", "Unknown", "------");
                tempList.add(item);
            }
            adapter.addAll(tempList);
        }
    }

    private void newsList() {
        String req = "get_news";
        refreshLayout.setRefreshing(true);
        Uri url = Uri.parse(AppConfig.API_UPD_URL)
                .buildUpon()
                .appendQueryParameter("tag", "getUpdates")
                .build();
        StringRequest request = new StringRequest(Request.Method.GET, url.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("News response", response);
                        db.removeUpdates();

                        if (tempList != null)
                            tempList.clear();
                        if (updateItems != null)
                            updateItems.clear();

                        refreshLayout.setRefreshing(false);
                        try {
                            JSONObject obj = new JSONObject(response);
                            boolean error = obj.getBoolean("error");
                            if (!error) {
                                JSONArray newsList = obj.getJSONArray("result");
                                for (int i = 0; i < newsList.length(); i++) {
                                    JSONObject news = newsList.getJSONObject(i);
                                    String id = news.getString("id");
                                    String title = news.getString("title");
                                    String body = news.getString("body");
                                    String type = news.getString("type");
                                    String date = news.getString("date");

                                    UpdateItem item = new UpdateItem(id, title, body, type, date);
                                    updateItems.add(item);
                                }

                            } else {
                                UpdateItem item = new UpdateItem("0", "No Updates", "---", "Unknown", "------");
                                updateItems.add(item);
                            }

                            for (UpdateItem it : updateItems) {
                                db.addNews(it);
                            }

                            filterList();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                refreshLayout.setRefreshing(false);
                Log.d("Updates Error", "Error " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(request, req);
    }

    @Override
    public void onResume() {
        super.onResume();
        newsList();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
