package com.kelvin.echousekeeper;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innodroid.expandablerecycler.ExpandableRecyclerAdapter;

/**
 * Created by Kelvin on 2017/2/5.
 */

public class tab5explore extends Fragment {
    View rootView;
    PeopleAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tab5explore, container, false);

        RecyclerView rvList = (RecyclerView) rootView.findViewById(R.id.explore_recyclerView);
        /*tab5Adapter adapter = new tab5Adapter(Contact.generateSampleList());
        rvList.setAdapter(adapter);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));*/
        adapter = new PeopleAdapter(this.getContext());
        adapter.setMode(ExpandableRecyclerAdapter.MODE_ACCORDION);
        rvList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvList.setAdapter(adapter);

        return rootView;
    }


}
