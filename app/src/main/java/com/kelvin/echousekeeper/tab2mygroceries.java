package com.kelvin.echousekeeper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kelvin on 2017/2/5.
 */

public class tab2mygroceries extends Fragment {
    RecyclerView mList;
    GridLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2mygroceries, container, false);

        ArrayList<String> myDataset = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            myDataset.add(i + "");
        }
        MyAdapter myAdapter = new MyAdapter(myDataset);
        mList = (RecyclerView) rootView.findViewById(R.id.groceries_recycler_view);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        mList.setLayoutManager(layoutManager);
        mList.setAdapter(myAdapter);

        return rootView;
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<String> mData;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;
            public ViewHolder(View v) {
                super(v);
                mTextView = (TextView) v.findViewById(R.id.card_title);
            }
        }

        public MyAdapter(List<String> data) {
            mData = data;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tab2mygroceries_card_layout, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mTextView.setText(mData.get(position));

        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }
}