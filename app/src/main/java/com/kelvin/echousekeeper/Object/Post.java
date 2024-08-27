package com.kelvin.echousekeeper.Object;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by Loi Cheung on 2/12/2017.
 */

public class Post {
    private String productID, productname;
    private Bitmap photo;
    public Post(JSONObject jsonObject){
        try{
            this.productID = jsonObject.getString("_id");
            this.productname = jsonObject.getString("name");
            this.photo = base64ToBitmap(jsonObject.getString("data"));

        }catch (JSONException j){
            j.printStackTrace();
        }
    }
    public Post(String name, Bitmap photo){
        this.productname = name;
        this.photo = photo;
    }
    public String getName(){ return productname; }
    public Bitmap getPhoto(){ return photo;}
    public String getProductID(){ return productID; }

    public String passToJsonObjectStr() throws JSONException {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("_id",getProductID());
        jsonObj.put("name",getName());
        jsonObj.put("data",bitmapToBase64(photo));
        return jsonObj.toString();
    }
    public String toString(){
        return "product detail and information: "+getProductID()+","+getName();
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();

        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
    private Bitmap base64ToBitmap(String iconStr){
        InputStream stream = new ByteArrayInputStream(Base64.decode(iconStr.getBytes(), Base64.DEFAULT));
        Bitmap bitmap = BitmapFactory.decodeStream(stream);

        return bitmap;
    }


}

