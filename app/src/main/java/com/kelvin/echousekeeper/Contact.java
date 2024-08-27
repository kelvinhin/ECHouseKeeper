package com.kelvin.echousekeeper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kelvin on 2017/2/11.
 */

public class Contact {
    private String name;
    private boolean isSelected;

    public Contact() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Contact> generateSampleList(){
        List<Contact> list = new ArrayList<>();
        for(int i=0; i < 30; i++){
            Contact contact = new Contact();
            contact.setName("Name - " + i);
            list.add(contact);
        }
        return list;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
