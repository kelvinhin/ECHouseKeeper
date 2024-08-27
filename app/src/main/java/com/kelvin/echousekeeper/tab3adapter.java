package com.kelvin.echousekeeper;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.List;

/**
 * Created by Kelvin on 2017/2/11.
 */

public class tab3adapter extends RecyclerView.Adapter<tab3adapter.ViewHolder> {

    /*private List<Student> stList;

    public tab3adapter(List<Student> students) {
        this.stList = students;

    }*/

    private List<Contact> mContacts;

    public tab3adapter(List<Contact> contacts){
        mContacts = contacts;
    }

    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.tab3shoppinglist_item_layout, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        final int pos = position;

        viewHolder.chkSelected.setText(mContacts.get(position).getName());
        viewHolder.chkSelected.setChecked(mContacts.get(position).isSelected());
        viewHolder.chkSelected.setTag(mContacts.get(position));

        viewHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Contact contact = (Contact) cb.getTag();

                contact.setSelected(cb.isChecked());
                mContacts.get(pos).setSelected(cb.isChecked());
                Snackbar.make(v, "Clicked on Checkbox: " + cb.getText() + " is "
                        + cb.isChecked(), Snackbar.LENGTH_LONG).show();
            }
        });

    }

    // Return the size arraylist
    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CheckBox chkSelected;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            chkSelected = (CheckBox) itemLayoutView
                    .findViewById(R.id.chkSelected);

        }

    }

    // method to access in activity after updating selection
    public List<Contact> getStudentist() {
        return mContacts;
    }
}
