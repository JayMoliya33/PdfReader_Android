package com.example.pdfreader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv_pdf;
    public static ArrayList<File> filelist = new ArrayList<>();
    public static int REQUEST_PERMISSION = 1;
    PDFAdapter mAdapter;
    boolean boolean_permission;
    File dir;

    // filelist = All pdf file

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_pdf = (ListView)findViewById(R.id.list_item);
        dir = new File(Environment.getExternalStorageDirectory().toString());

        permission_fn();

        lv_pdf.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position,long id){
               Intent intent = new Intent(getApplicationContext(),ViewPDFFiles.class);
               intent.putExtra("Position",position);
               startActivity(intent);
           }
        });
    }

    public void permission_fn()
    {
        if((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED))
        {
            if((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,android.Manifest.permission.READ_EXTERNAL_STORAGE)))
            { }
            else
            {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            }
        }
        else
        {
            boolean_permission = true;
            getfile(dir);
            mAdapter = new PDFAdapter(getApplicationContext(),filelist);
            lv_pdf.setAdapter(mAdapter);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode== REQUEST_PERMISSION)
        {
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                boolean_permission = true;
                getfile(dir);
                mAdapter = new PDFAdapter(getApplicationContext(),filelist);
                lv_pdf.setAdapter(mAdapter);

            }
            else
            {
                Toast.makeText(this,"Please Allow the Permission",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public ArrayList<File> getfile(File dir)
    {
        File listFile[] = dir.listFiles();
        // listFile = All files of Your Mobile

        if(listFile!=null && listFile.length>0)
        {
            for(int i=0 ; i<listFile.length; i++)
            {
                if(listFile[i].isDirectory()){
                    getfile(listFile[i]);
                }
                else
                {
                    boolean pdf = false;
                    if(listFile[i].getName().endsWith(".pdf"))
                    {
                        for(int j=0; j<filelist.size();  j++)
                        {
                            if(filelist.get(j).getName().equals(listFile[i].getName())){
                                pdf=true;
                            }
                            else
                            {

                            }
                        }
                        if(pdf)
                        {
                            pdf=false;
                        }
                        else
                        {
                            filelist.add(listFile[i]);

                        }
                    }
                }
            }
        }
        return filelist;
    }
}
