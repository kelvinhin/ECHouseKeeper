package com.kelvin.echousekeeper;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by s1159217 on 4/25/2017.
 */

public class Getnfcdata extends MainActivity{
    NfcAdapter mNfcAdapter;
    ListView listView;
    PendingIntent mNfcPendingIntent;
    IntentFilter[] mWriteTagFilters;
    IntentFilter[] mNdefExchangeFilters;
    JSONArray jsonArray = null;
    TextView nfcdata;
    ArrayList<String> grocery = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_data);
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        //listView = (ListView) findViewById(R.id.nfc_listview);
        nfcdata = (TextView) findViewById(R.id.nfcdata);
        mNfcPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        // Intent filters for reading a note from a tag or exchanging over p2p.
        IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndefDetected.addDataType("text/plain");
        } catch (IntentFilter.MalformedMimeTypeException e) { }
        mNdefExchangeFilters = new IntentFilter[] { ndefDetected };

        // Intent filters for writing to a tag
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        mWriteTagFilters = new IntentFilter[] { tagDetected };
    }
    @Override
    protected void onResume(){
        System.out.print("onResume");
        super.onResume();
        if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())){
            NdefMessage[] messages = getNdefMessages(getIntent());
            //byte[] payload = messages[0].getRecords()[0].getPayload();
        }
        enableNdefExchangeMode();
    }
    @Override
    protected void onNewIntent(Intent intent) {
        // NDEF exchange mode
        System.out.print("onNewIntent");
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            NdefMessage[] msgs = getNdefMessages(intent);
            promptForContent(msgs[0]);
        }
    }
    private void promptForContent(final NdefMessage msg) {
        System.out.print("promptForContent");
        new AlertDialog.Builder(this).setTitle("Replace current content?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        String body = new String(msg.getRecords()[0].getPayload());
                        System.out.println("body"+body);
                        setNoteBody(body);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                }).show();
    }
    private void setNoteBody(String body) {
        System.out.print(body);
        nfcdata.setText(body.toString());
        /*jsonArray.put(body);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.activity_nfc_data, jsonArray);
        listView.setAdapter(adapter);*/
        
    }

    NdefMessage[] getNdefMessages(Intent intent){
        System.out.print("getNdefMessages");
        NdefMessage[] msgs = null;
        String action = intent.getAction();
        if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                ||NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)){
            Parcelable[]rawMsags = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if(rawMsags != null){
                msgs = new NdefMessage[rawMsags.length];
                for(int i = 0; i < rawMsags.length; i++){
                    msgs[i] = (NdefMessage) rawMsags[i];
                }
            } else {
                byte[] empty = new byte[]{};
                NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, empty, empty);
                NdefMessage msg = new NdefMessage(new NdefRecord[]{
                        record
                });
                msgs = new NdefMessage[]{
                        msg
                };
            }
        }
        return msgs;
    }
    private void enableNdefExchangeMode() {
        System.out.print("enableNdefExchangeMode");
        //mNfcAdapter.enableForegroundNdefPush(MainActivity.this, getNoteAsNdef());
        mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent, mNdefExchangeFilters, null);
    }
}
