package com.example.user.RSSIscanner;

import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanSettings;

import android.content.SharedPreferences;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;


import java.io.File;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;


import static android.bluetooth.le.ScanSettings.SCAN_MODE_LOW_LATENCY;

public class MainActivity extends AppCompatActivity {

    //BLE 相關定義
    BluetoothManager mBluetoothManager;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothLeScanner mBluetoothLeScanner;

    //介面元件
    private EditText edit_state;
    private Button btn_scan,btn_stopscan,btn_sketch;
    TextView RSSIdata1 ;
    TextView RSSIdata2;
    TextView RSSIdata3;

    TextView RSSI_average;
    //TextView RSSI_variance ;
    //TextView Max_Value;
    TextView MaxValueBT1;
    TextView MaxValueBT2;
    TextView MaxValueBT3;



    TextView BT1;
    TextView BT2;
    TextView BT3;


    //Debug 相關
    String TAG_Scan = "(Scan)";
    String TAG_Rec = "(Rec)";
    String TAG_Distinguish = "(Distinguish)";
    String TAG_Time1 = "(Time1)";
    String TAG_Time2 = "(Time2)";
    String TAG_Time3 = "(Time3)";
    String TAG_Save = "(Save)";
    //String TAG_Stamp1 = "(stamp1)";

    //Timer相關
    Timer timer = new Timer(true);

    //Data相關
    long time_first ;
    int max_number=500;
    String BT1_average="";
    String BT2_average="";
    String BT3_average="";
    String BT1_average_power="";
    String BT2_average_power="";
    String BT3_average_power="";
    String BT1_modi_average="";
    String BT2_modi_average="";
    String BT3_modi_average="";
    final String uuid1= "[0000aaaa-0000-1000-8000-00805f9b34fb]";
    final String uuid2= "[0000bbbb-0000-1000-8000-00805f9b34fb]";
    final String uuid3= "[0000cccc-0000-1000-8000-00805f9b34fb]";



    //String uuid2 = "74278bda-b644-2222-8f0c-720eaf059935";
        /*String uuid1= "0000aaaa-0000-1000-8000-00805f9b34fb";
        String uuid2= "0000bbbb-0000-1000-8000-00805f9b34fb";
        String uuid3= "0000cccc-0000-1000-8000-00805f9b34fb";*/
        /*ParcelUuid UUID1 = new ParcelUuid(UUID.fromString("0000aaaa-0000-1000-8000-00805f9b34fb"));
        ParcelUuid Uuid1Mask = new ParcelUuid(UUID.fromString("0000ffff-0000-0000-0000-000000000000"));
        ParcelUuid UUID2 = new ParcelUuid(UUID.fromString("0000bbbb-0000-1000-8000-00805f9b34fb"));
        ParcelUuid Uuid2Mask = new ParcelUuid(UUID.fromString("0000ffff-0000-0000-0000-000000000000"));
        ParcelUuid UUID3 = new ParcelUuid(UUID.fromString("0000cccc-0000-1000-8000-00805f9b34fb"));
        ParcelUuid Uuid3Mask = new ParcelUuid(UUID.fromString("0000ffff-0000-0000-0000-000000000000"));*/

    //byte[] uuid2Data = new byte[]{0x7,0x4, 0x2,0x7, 0x8,0xb, 0xd,0xa,0xb,0x6,0x4,0x4,0x2,0x2,0x2,0x2,0x8,0xf,0x0,0xc,0x7,0x2,0x0,0xe,0xa,0xf,0x0,0x5,0x9,0x9,0x3,0x5};

    //ParcelUuid parcelUuidMask = new ParcelUuid(UUID.fromString("ffffffff-ffff-0000-0000-000000000000"));
    //UUID[] uuid2 = new UUID[] { UUID.fromString("74278bda-b644-2222-8f0c-720eaf059935")};
    //final ParcelUuid[] uuid3 =new ParcelUuid[]{ParcelUuid.fromString("74278bda-b644-2222-8f0c-720eaf059935")};
    //ParcelUuid uuid1 =[0x74, 0x27, 0x8b, 0xda,0xb6,0x44,0x11,0x11,0x8f,0x0c,0x72,0x0e,0xaf,0x05,0x99,0x35];


    //List<Integer> eachRSSI = new ArrayList<>();
    List<Integer> eachRSSI_HM1 = new ArrayList<>();
    List<Integer> eachRSSI_HM2 = new ArrayList<>();
    List<Integer> eachRSSI_HM3 = new ArrayList<>();


    List<Long>  time1 = new ArrayList<>();
    List<Long>  time2 = new ArrayList<>();
    List<Long>  time3 = new ArrayList<>();
    //private String filename = "testdata";


    int maxRSSI_BT1;
    int maxRSSI_BT2;
    int maxRSSI_BT3;

    double average_cal;
    double average_HM1;
    double average_HM2;
    double average_HM3;

    double modify_average_BT1;
    double modify_average_BT2;
    double modify_average_BT3;

    double average_power_BT1;
    double average_power_BT2;
    double average_power_BT3;

    int distinguish_1;
    int distinguish_2;
    int distinguish_3;
    int distinguish_4;

    int count1 = 0;
    int count2 = 0;
    int count3 = 0;


    //long timestampNanos;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //BLE初始設定
        mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();


        btn_scan = findViewById(R.id.btn_scan);
        BT1 = findViewById(R.id.BT1);
        BT2 = findViewById(R.id.BT2);
        BT3 = findViewById(R.id.BT3);
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScanning();
                time_first = System.currentTimeMillis();
                Log.e(TAG_Rec, "First Time : " + time_first);
                timer = new Timer(true);
                timer.scheduleAtFixedRate(new MyTimerTask() , 0 , 2000);
            }
        });
        btn_stopscan = findViewById(R.id.btn_stop);
        btn_stopscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //stopScanning();
                timer.cancel();
                timer.purge();
            }
        });
        RSSIdata1 = findViewById(R.id.RSSIvalue1);
        RSSIdata2 = findViewById(R.id.RSSIvalue2);
        RSSIdata3 = findViewById(R.id.RSSIvalue3);

        RSSI_average = findViewById(R.id.RSSI_average);
        //Max_Value = findViewById(R.id.maxValue);
        MaxValueBT1 = findViewById(R.id.maxRSSI1);
        MaxValueBT2 = findViewById(R.id.maxRSSI2);
        MaxValueBT3 = findViewById(R.id.maxRSSI3);


        //RSSI_variance = findViewById(R.id.maxValue);
        //BT1.setText(String.valueOf(average_HM1));
        //BT2.setText(String.valueOf(average_HM2));
        //BT3.setText(String.valueOf(average_HM3));

        /*btn_sketch = findViewById(R.id.btn_draw);
        btn_sketch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }


    public void startScanning() {
        Log.e(TAG_Scan, "Have entered  startScanning");
        btn_scan.setEnabled(false);
        btn_stopscan.setEnabled(true);

        eachRSSI_HM1 = new ArrayList<>();
        eachRSSI_HM2 = new ArrayList<>();
        eachRSSI_HM3 = new ArrayList<>();
        // device name
        String name1 = "BT1";
        String name2 = "BT2";
        String name3 = "BT3";



        //用device name 過濾

        List<ScanFilter> filterList = new ArrayList<>();
        //ScanFilter filter = new ScanFilter.Builder().setServiceUuid(parcelUuid, parcelUuidMask).build();

        /*use device name as filter
        ScanFilter filter = new ScanFilter.Builder().setDeviceName(name1).build();
        filterList.add(filter);
        filter = new ScanFilter.Builder().setDeviceName(name2).build();
        filterList.add(filter);
        filter = new ScanFilter.Builder().setManufacturerData(76,uuid2Data).build();
        filterList.add(filter);
        filter = new ScanFilter.Builder().setDeviceName(name3).build();
        filterList.add(filter);
        */



        //use serviceUUID as filter
        ScanFilter filter = new ScanFilter.Builder().setServiceUuid(ParcelUuid.fromString("0000aaaa-0000-1000-8000-00805f9b34fb")).build();
        filterList.add(filter);
        filter = new ScanFilter.Builder().setServiceUuid(ParcelUuid.fromString("0000bbbb-0000-1000-8000-00805f9b34fb")).build();
        filterList.add(filter);
        filter = new ScanFilter.Builder().setServiceUuid(ParcelUuid.fromString("0000cccc-0000-1000-8000-00805f9b34fb")).build();
        filterList.add(filter);

        //用預設的
        ScanSettings settings = new ScanSettings.Builder().setScanMode(SCAN_MODE_LOW_LATENCY).build();

        //
        time_first = System.currentTimeMillis();
        mBluetoothLeScanner.startScan( filterList , settings,  callback);
        //mBluetoothLeScanner.startScan(null,settings,callback);

    }

    public void stopScanning() {
        Log.e(TAG_Scan, "Have entered  stopScanning");
        btn_scan.setEnabled(true);
        btn_stopscan.setEnabled(false);
        mBluetoothLeScanner.stopScan(callback);

        if(eachRSSI_HM1.size()!=0) {
            //display1();
            // Log.e(TAG_Scan, "start calculate average");
            //average(eachRSSI_HM1);
            //Log.e(TAG_Scan, "finish calculating average");
            //average_cal = average(eachRSSI_HM1);

            average_HM1 = average(eachRSSI_HM1);
            average_power_BT1 = rssitopower(eachRSSI_HM1);
            //BT1_average = String.valueOf(average_HM1);
            //BT1.setText(BT1_average);

            //Log.e(TAG_Scan, "Average RSSI of HM1:" +String.valueOf(average_cal));
            Log.e(TAG_Scan, "Average RSSI of BT1:" +String.valueOf(average_HM1));
            Log.e(TAG_Scan,"Number of HM1: " + String.valueOf(eachRSSI_HM1.size()));
            Log.e(TAG_Scan, "Average Power of BT1:" +String.valueOf(average_power_BT1));
        }else {
            Log.e(TAG_Scan, "Nothing in HM1.");
        }

        //BT1_average = String.valueOf(average_HM1);
        BT1_average = Double.toString(average_HM1);
        BT1_average_power = Double.toString((average_power_BT1));
        //Log.e(TAG_Scan,"BT1_average: "+ BT1_average);
        //BT1.setText("Average RSSI of BT1 : "+"\n" +BT1_average+" dBm");
        BT1.setText("Average RSSI of BT1 : "+"\n" +String.format("%.2f",average_HM1)+" dBm"+"\n");


        if(eachRSSI_HM2.size()!=0) {

            //display2();
            //average_cal = average(eachRSSI_HM2);
            average_HM2 = average(eachRSSI_HM2);
            average_power_BT2 = rssitopower(eachRSSI_HM2);
            //Log.e(TAG_Scan, "Average RSSI of HM2:" +String.valueOf(average_cal));
            Log.e(TAG_Scan, "Average RSSI of HM2:" +String.valueOf(average_HM2));
            Log.e(TAG_Scan,"Number of HM2: " + String.valueOf(eachRSSI_HM2.size()));
        }else {
            Log.e(TAG_Scan, "Nothing in HM2.");
        }
        BT2_average = Double.toString(average_HM2);
        BT2_average_power = Double.toString((average_power_BT2));
        //Log.e(TAG_Scan,"BT1_average: "+ BT1_average);
        //BT2.setText("Average RSSI of BT2 : "+"\n" +BT2_average+" dBm");
        BT2.setText("Average RSSI of BT2 : "+"\n" +String.format("%.2f",average_HM2)+" dBm");

        if(eachRSSI_HM3.size()!=0) {

            //display3();
            //average_cal = average(eachRSSI_HM3);
            average_HM3 = average(eachRSSI_HM3);
            average_power_BT3 = rssitopower(eachRSSI_HM3);
            //Log.e(TAG_Scan, "Average RSSI of HM3:" +String.valueOf(average_cal));
            Log.e(TAG_Scan, "Average RSSI of HM3:" +String.valueOf(average_HM3));
            Log.e(TAG_Scan,"Number of HM3: " + String.valueOf(eachRSSI_HM3.size()));
        } else{
            Log.e(TAG_Scan, "Nothing in HM3.");
        }
        BT3_average = Double.toString(average_HM3);
        BT3_average_power = Double.toString((average_power_BT3));
        //Log.e(TAG_Scan,"BT1_average: "+ BT1_average);
        //BT3.setText("Average RSSI of BT3 : "+"\n" +BT3_average+" dBm");
        BT3.setText("Average RSSI of BT3 : "+"\n" +String.format("%.2f",average_HM3)+" dBm");

        //Max_Value.setText("Maximum RSSI Value");
        maxRSSI_BT1 = Collections.max(eachRSSI_HM1);
        Log.e(TAG_Scan, "Maximum BT1:"+maxRSSI_BT1);
        MaxValueBT1.setText("Maximum of BT1 : "+maxRSSI_BT1+" dBm"+"\n"+"count "+count1);


        maxRSSI_BT2 = Collections.max(eachRSSI_HM2);
        Log.e(TAG_Scan, "Maximum BT2:"+maxRSSI_BT2);
        MaxValueBT2.setText("Maximum of BT2 : "+maxRSSI_BT2+" dBm"+"\n"+"count "+count2);

        maxRSSI_BT3 = Collections.max(eachRSSI_HM3);
        Log.e(TAG_Scan, "Maximum BT3:"+maxRSSI_BT3);
        MaxValueBT3.setText("Maximum of BT3 : "+maxRSSI_BT3+" dBm"+"\n"+"count "+count3);

        Log.e(TAG_Scan,"size of BT1:"+eachRSSI_HM1.size()+" size of BT2:"+eachRSSI_HM2.size()+" size of BT3:"+eachRSSI_HM3.size());

        RSSI_average.setText("Average RSSI Value \n");

        //File file = new File("testsave.txt");

    Log.e(TAG_Scan,"BT1's count : "+count1+ " BT2's count : "+count2+" BT3's count :"+count3);
        Log.e(TAG_Rec,"time before store"+System.currentTimeMillis());
        try {       //save each RSSI and average RSSI 
            //FileOutputStream fout = openFileOutput("0902result.txt", MODE_APPEND);
            FileOutputStream fout = openFileOutput("1213.txt", MODE_APPEND);
            //FileOutputStream fout = new FileOutputStream("result.txt",true);
            OutputStreamWriter osw = new OutputStreamWriter(fout);
            osw.write(eachRSSI_HM1.toString());
            osw.write("\n");
            //osw.write(time1.toString());
            osw.write(eachRSSI_HM2.toString());
            osw.write("\n");
            //osw.write(time2.toString());
             osw.write(eachRSSI_HM3.toString());
            osw.write("\n");
            //osw.write(time3.toString());

            osw.write("["+BT1_average.toString()+",");
            osw.write(BT2_average.toString()+",");
            osw.write(BT3_average.toString()+"]");
            osw.write("\n");
            osw.write("["+BT1_average_power.toString()+",");
            osw.write(BT2_average_power.toString()+",");
            osw.write(BT3_average_power.toString()+"]");
            osw.write("\n");
            osw.flush();
            fout.flush();
            osw.close();
            fout.close();
            Toast.makeText(this, "File saved successfully",Toast.LENGTH_SHORT).show();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        Log.e(TAG_Rec,"time after store"+System.currentTimeMillis());
        eachRSSI_HM1.clear();
        eachRSSI_HM2.clear();
        eachRSSI_HM3.clear();

        count1=0;
        count2=0;
        count3=0;
            //Log.e(TAG_Scan, "after clearing, size of array is；" + eachRSSI.size());


        time_first = System.currentTimeMillis();

    }


    public void display1(){
        String finalstring="";

        StringBuilder stringBuilder = new StringBuilder();

        for(int i=0; i<eachRSSI_HM1.size(); i++){
            stringBuilder.append(eachRSSI_HM1.get(i).toString());
        }

         for(int i=0; i<eachRSSI_HM1.size(); i++){
                Log.e(TAG_Scan, "each data  is；"+ eachRSSI_HM1.get(i) );
            }


        finalstring = stringBuilder.toString();
        RSSIdata1.setText("RSSI Value of BT1 : \n" + finalstring);

        }
    public void display2(){
        String finalstring="";

        StringBuilder stringBuilder = new StringBuilder();

        for(int i=0; i<eachRSSI_HM2.size(); i++){
            stringBuilder.append(eachRSSI_HM2.get(i).toString());
        }

        for(int i=0; i<eachRSSI_HM2.size(); i++){
            Log.e(TAG_Scan, "each data  is；"+ eachRSSI_HM2.get(i) );
        }


        finalstring = stringBuilder.toString();
        RSSIdata2.setText("RSSI Value of BT2 : \n" + finalstring);

    }
    public void display3(){
        String finalstring="";

        StringBuilder stringBuilder = new StringBuilder();

        for(int i=0; i<eachRSSI_HM3.size(); i++){
            stringBuilder.append(eachRSSI_HM3.get(i).toString());
        }

        for(int i=0; i<eachRSSI_HM3.size(); i++){
            Log.e(TAG_Scan, "each data  is；"+ eachRSSI_HM3.get(i) );
        }


        finalstring = stringBuilder.toString();
        RSSIdata3.setText("RSSI Value of BT3 : \n" + finalstring);

    }

    public double average( List<Integer> RSSI_cal){
        int sum =0;
        double average_of_all;
        String string_average="";
        for(int i=0; i<RSSI_cal.size(); i++){
            sum += RSSI_cal.get(i);
        }
        average_of_all = sum/ (double)RSSI_cal.size();
        string_average = String.valueOf(average_of_all);
        //RSSI_average.setText(string_average);
        return average_of_all;
    }
    public double modify_average( List<Integer> Modify_RSSI_cal){
        int sum =0;
        double modi_average_of_all;
        String string_average="";
        if(Modify_RSSI_cal.size()>5) {
            for (int i = 0; i < 5; i++) {
                sum += Modify_RSSI_cal.get(i);
            }
            modi_average_of_all = sum / (double) 5;
        }else
        {
            for (int i = 0; i < Modify_RSSI_cal.size(); i++)
            {
                sum += Modify_RSSI_cal.get(i);
            }
            modi_average_of_all = sum / (double) Modify_RSSI_cal.size();
        }

        string_average = String.valueOf(modi_average_of_all);
        //RSSI_average.setText(string_average);
        return modi_average_of_all;
    }

    public double rssitopower(List<Integer> RSSI_power){
        //int sum = 0;
        double power_of_each;
        double sum_of_power = 0;
        double average_of_power;
        for(int i=0; i<RSSI_power.size(); i++){
            power_of_each = Math.pow(10,RSSI_power.get(i)/10);
            sum_of_power += power_of_each;
        }
        average_of_power = sum_of_power/ (double)RSSI_power.size();

        return average_of_power;
    }
    public int calculated_number() {
        int Abs_max;
        int count_BT1=0;
        int count_BT2=0;
        int count_BT3=0;
        int distinguish4=0;

        List<Integer> Abs_array = new ArrayList<>();
        List<Integer> Count = new ArrayList<>();
        Abs_array.add(eachRSSI_HM1.get(0));
        Abs_array.add(eachRSSI_HM2.get(0));
        Abs_array.add(eachRSSI_HM3.get(0));
        Abs_max = Collections.max(Abs_array);
        Log.e(TAG_Scan,"max bt1:"+eachRSSI_HM1.get(0));
        Log.e(TAG_Scan,"max bt2:"+eachRSSI_HM2.get(0));
        Log.e(TAG_Scan,"max bt3:"+eachRSSI_HM3.get(0));
        Log.e(TAG_Rec,"abs max : "+Abs_max);

        for(int i=0; i<eachRSSI_HM1.size();i++)
        {
            if(eachRSSI_HM1.get(i)<=Abs_max-5)
                count_BT1++;
        }
        for(int j=0; j<eachRSSI_HM2.size();j++)
        {
            if(eachRSSI_HM2.get(j)<=Abs_max-5)
                count_BT2++;
        }
        for(int k=0; k<eachRSSI_HM3.size();k++)
        {
            if(eachRSSI_HM3.get(k)<=Abs_max-5)
                count_BT3++;
        }
        Log.e(TAG_Rec,"after abs countBT1:"+ count_BT1);
        Log.e(TAG_Rec,"after abs countBT2:"+ count_BT2);
        Log.e(TAG_Rec,"after abs countBT3:"+ count_BT3);

        if(count_BT1 < count_BT2 && count_BT1<count_BT3){
            distinguish4=411;
            Log.e(TAG_Distinguish,"method 4 BT1 max");
        }
        else if(count_BT2 < count_BT1 && count_BT2<count_BT3) {
            distinguish4=422;
            Log.e(TAG_Distinguish,"method 4 BT2 max");
        }else if(count_BT3 < count_BT1 && count_BT3<count_BT2){
            distinguish4=433;
            Log.e(TAG_Distinguish,"method 4 BT3 max");
        }

        return distinguish4;
    }
    public int distinguish1(){
        int distinguish1=0;

        if(average_HM1 > average_HM2 && average_HM1>average_HM3){
            distinguish1=111;
            Log.e(TAG_Distinguish,"method 1 Average BT1 max");
        }
        else if(average_HM2 > average_HM1 && average_HM2>average_HM3) {
            distinguish1=122;
            Log.e(TAG_Distinguish, "method 1 Average BT2 max");
        }else if(average_HM3 > average_HM1 && average_HM3>average_HM1){
            distinguish1=133;
            Log.e(TAG_Distinguish,"method 1 Average BT3 max");
        }

        return distinguish1;

    }
    public int distinguish2(){
        int distinguish2=0;
        if(average_power_BT1 > average_power_BT2 && average_power_BT1>average_power_BT3){
            distinguish2=2111;
            Log.e(TAG_Distinguish,"method 2 Average Power BT1 max");
        }
        else if(average_power_BT2 > average_power_BT1 && average_power_BT2>average_power_BT3) {
            distinguish2=2222;
            Log.e(TAG_Distinguish, "method 2 Average Power BT2 max");
        }else if(average_power_BT3 > average_power_BT1 && average_power_BT3>average_power_BT1){
            distinguish2=2333;
            Log.e(TAG_Distinguish,"method 2 Average Power BT3 max");
        }
        return distinguish2;

    }
    public int distinguish3(){
        int distinguish3=0;

        if(modify_average_BT1 > modify_average_BT2 && modify_average_BT1>modify_average_BT3){
            distinguish3=31111;
            Log.e(TAG_Distinguish,"method 3 BT1 max");
        }
        else if(modify_average_BT2 > modify_average_BT1 && modify_average_BT2>modify_average_BT3) {
            distinguish3=32222;
            Log.e(TAG_Distinguish,"method 3 BT2 max");
        }else if(modify_average_BT3 > modify_average_BT1 && modify_average_BT3>modify_average_BT1){
            distinguish3=33333;
            Log.e(TAG_Distinguish,"method 3 BT3 max");
        }

        return distinguish3;

    }


    private ScanCallback callback = new ScanCallback() {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);

            //ScanRecord scanRecord = result.getScanRecord();
            Log.e(TAG_Scan,"ENTER scancallback");
            Log.e(TAG_Scan,"time "+System.currentTimeMillis());
            //timestampNanos = result.getTimestampNanos();
            //Log.e(TAG_Stamp1,String.valueOf(timestampNanos));

            if( (System.currentTimeMillis() - time_first) < 6*1000){
                //eachRSSI.add(result.getRssi());
            //SparseArray<byte[]> manufacturerData = result.getScanRecord().getManufacturerSpecificData();


                String selected = result.getScanRecord().getServiceUuids().toString();
                Log.e(TAG_Scan,"selected"+selected);
                switch (selected)
                //switch(result.getScanRecord().getServiceData().toString())
                {
                    case uuid1:
                        eachRSSI_HM1.add(result.getRssi());
                        time1.add(System.currentTimeMillis())  ;
                        Log.e(TAG_Scan,"BT1 whole result's information :"+result);
                        Log.e(TAG_Scan,"BT1 RSSI:"+result.getRssi());
                        Log.e(TAG_Scan,"BT1's Service UUID"+result.getScanRecord().getServiceUuids());
                        Log.e(TAG_Scan,"BT1's service data:"+result.getScanRecord().getServiceData());
                        //Log.e(TAG_Scan,"BT1's manufacturer data:"+result.getScanRecord().getManufacturerSpecificData());
                        //Log.e(TAG_Scan,"BT1's powerLevel:"+ result.getScanRecord().getTxPowerLevel());
                        //Log.e(TAG_Scan,"BT1's Tx Power:"+ result.getTxPower());
                        count1=count1+1;
                        break;
                    case uuid2:
                        eachRSSI_HM2.add(result.getRssi());
                        time2.add(System.currentTimeMillis())  ;
                        Log.e(TAG_Scan,"BT2 whole result's information :"+result);
                        Log.e(TAG_Scan,"BT2 RSSI:"+result.getRssi());
                        Log.e(TAG_Scan,"BT2's Service UUID"+result.getScanRecord().getServiceUuids());
                        Log.e(TAG_Scan,"BT2's service data:"+result.getScanRecord().getServiceData());
                        //Log.e(TAG_Scan,"BT2's manufacturer data:"+result.getScanRecord().getManufacturerSpecificData());
                        //Log.e(TAG_Scan,"BT2's powerLevel:"+ result.getScanRecord().getTxPowerLevel());
                        //Log.e(TAG_Scan,"BT2's Tx Power:"+ result.getTxPower());
                        count2=count2+1;
                    break;
                    case uuid3:
                        eachRSSI_HM3.add(result.getRssi());
                        time3.add(System.currentTimeMillis())  ;
                        Log.e(TAG_Scan,"BT3 whole result's information :"+result);
                        Log.e(TAG_Scan,"BT3 RSSI:"+result.getRssi());
                        Log.e(TAG_Scan,"BT3's Service UUID"+result.getScanRecord().getServiceUuids());
                        Log.e(TAG_Scan,"BT3's service data:"+result.getScanRecord().getServiceData());
                        //Log.e(TAG_Scan,"BT3's manufacturer data:"+result.getScanRecord().getManufacturerSpecificData());
                        //Log.e(TAG_Scan,"BT3's powerLevel:"+ result.getScanRecord().getTxPowerLevel());
                        //Log.e(TAG_Scan,"BT3's Tx Power:"+ result.getTxPower());
                        count3=count3+1;
                        break;

                }

            }

            else {
                //stopScanning();
                mBluetoothLeScanner.stopScan(callback);
                timer.cancel();
                timer.purge();
            }

        }

    };
    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            Log.e(TAG_Rec, "Time timer：" + System.currentTimeMillis());
            Log.e(TAG_Scan,"111111111111");
            Log.e(TAG_Scan,"BT1's count : "+count1+ " BT2's count : "+count2+" BT3's count :"+count3);
            //BT1 part


            Log.e(TAG_Rec,"BT1's value:"+String.valueOf(eachRSSI_HM1));
            average_HM1 = average(eachRSSI_HM1);
            Log.e(TAG_Rec, "Average RSSI of BT1:" +String.valueOf(average_HM1));
            average_power_BT1 = rssitopower(eachRSSI_HM1);
            Log.e(TAG_Rec, "Average Power of BT1:" +String.valueOf(average_power_BT1));
            Log.e(TAG_Rec,"Number of HM1: " + String.valueOf(eachRSSI_HM1.size()));
            Collections.sort(eachRSSI_HM1);
            Collections.reverse(eachRSSI_HM1);
            Log.e(TAG_Rec,"BT1's sort value:"+String.valueOf(eachRSSI_HM1));
            modify_average_BT1 = modify_average(eachRSSI_HM1);
            Log.e(TAG_Rec, "Modify Average RSSI of BT1:" +String.valueOf(modify_average_BT1));

            //BT2 part
            Log.e(TAG_Rec,"BT2's value:"+String.valueOf(eachRSSI_HM2));
            average_HM2 = average(eachRSSI_HM2);
            Log.e(TAG_Rec, "Average RSSI of BT2:" +String.valueOf(average_HM2));
            average_power_BT2 = rssitopower(eachRSSI_HM2);
            Log.e(TAG_Rec, "Average Power of BT2:" +String.valueOf(average_power_BT2));
            Log.e(TAG_Rec,"Number of HM2: " + String.valueOf(eachRSSI_HM2.size()));
            Collections.sort(eachRSSI_HM2);
            Collections.reverse(eachRSSI_HM2);
            Log.e(TAG_Rec,"BT2's sort value:"+String.valueOf(eachRSSI_HM2));
            modify_average_BT2 = modify_average(eachRSSI_HM2);
            Log.e(TAG_Rec, "Modify Average RSSI of BT2:" +String.valueOf(modify_average_BT2));

            //BT3 part
            Log.e(TAG_Rec,"BT3's value:"+String.valueOf(eachRSSI_HM3));
            average_HM3 = average(eachRSSI_HM3);
            Log.e(TAG_Rec, "Average RSSI of BT3:" +String.valueOf(average_HM3));
            average_power_BT3 = rssitopower(eachRSSI_HM3);
            Log.e(TAG_Rec, "Average Power of BT3:" +String.valueOf(average_power_BT3));
            Log.e(TAG_Rec,"Number of HM3: " + String.valueOf(eachRSSI_HM3.size()));
            Collections.sort(eachRSSI_HM3);
            Collections.reverse(eachRSSI_HM3);
            Log.e(TAG_Rec,"BT3's sort value:"+String.valueOf(eachRSSI_HM3));
            modify_average_BT3 = modify_average(eachRSSI_HM3);
            Log.e(TAG_Rec, "Modify Average RSSI of BT3:" +String.valueOf(modify_average_BT3));


            if(eachRSSI_HM1.size()!=0&&eachRSSI_HM2.size()!=0&&eachRSSI_HM3.size()!=0) {
                distinguish_4 = calculated_number();
                Log.e(TAG_Distinguish,"method 4:"+distinguish_4);
            }

            distinguish_1=distinguish1();
            Log.e(TAG_Distinguish,"method 1:"+distinguish_1);
            distinguish_2=distinguish2();
            Log.e(TAG_Distinguish,"method 2: "+distinguish_2);
            distinguish_3=distinguish3();
            Log.e(TAG_Distinguish,"method 3: "+distinguish_3);



            BT1_average = Double.toString(average_HM1);
            BT1_average_power = Double.toString((average_power_BT1));
            BT1_modi_average = Double.toString(modify_average_BT1);

            BT2_average = Double.toString(average_HM2);
            BT2_average_power = Double.toString((average_power_BT2));
            BT2_modi_average = Double.toString(modify_average_BT2);

            BT3_average = Double.toString(average_HM3);
            BT3_average_power = Double.toString((average_power_BT3));
            BT3_modi_average = Double.toString(modify_average_BT3);

            //save all the distinguish result
            try {
                 FileOutputStream fout = openFileOutput("1217.txt", MODE_APPEND);
                //FileOutputStream fout = new FileOutputStream("result.txt",true);
                OutputStreamWriter osw = new OutputStreamWriter(fout);
                osw.write(eachRSSI_HM1.toString());
                osw.write("\n");
                //osw.write(time1.toString());
                osw.write(eachRSSI_HM2.toString());
                osw.write("\n");
                //osw.write(time2.toString());
                osw.write(eachRSSI_HM3.toString());
                osw.write("\n");
                //osw.write(time3.toString());

                osw.write("["+BT1_average.toString()+",");
                osw.write(BT2_average.toString()+",");
                osw.write(BT3_average.toString()+"]");
                osw.write("\n");
                osw.write("method 1:"+distinguish_1+"\n");
                osw.write("["+BT1_average_power.toString()+",");
                osw.write(BT2_average_power.toString()+",");
                osw.write(BT3_average_power.toString()+"]");
                osw.write("\n");
                osw.write("method 2:"+distinguish_2+"\n");
                osw.write("["+BT1_modi_average.toString()+",");
                osw.write(BT2_modi_average.toString()+",");
                osw.write(BT3_modi_average.toString()+"]");
                osw.write("\n");
                osw.write("method 3:"+distinguish_3+"\n");
                osw.write("method 4:"+distinguish_4+"\n");
                osw.flush();
                fout.flush();
                osw.close();
                fout.close();
                //Toast.makeText(this, "File saved successfully",Toast.LENGTH_SHORT).show();
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }

            Log.e(TAG_Rec, "Timetask over time：" + System.currentTimeMillis());
            eachRSSI_HM1.clear();
            eachRSSI_HM2.clear();
            eachRSSI_HM3.clear();
            count1 = 0;
            count2 = 0;
            count3 = 0;

        }
    }



}

//mBluetoothLeScanner.stopScan(leScanCallback);
