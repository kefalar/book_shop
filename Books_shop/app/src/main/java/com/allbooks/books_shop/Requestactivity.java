package com.allbooks.books_shop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class Requestactivity extends AppCompatActivity {
    private EditText name,author,publisher,info;
    private DatabaseReference dbr;
    String getextra;
    Worker currentworker;
    String[] getextramass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestactivity);
        name=findViewById(R.id.etrequestname);
        author=findViewById(R.id.etrequestauthor);
        publisher=findViewById(R.id.etrequestpublisher);
        info=findViewById(R.id.etrequestinfo);
        Bundle arguments = getIntent().getExtras();
        getextra=arguments.get("user").toString();
        getextramass=getextra.split(";:");
        currentworker=new Worker("user",getextramass[2],getextramass[3],getextramass[0],getextramass[1],Integer.parseInt(getextramass[8]),Integer.parseInt(getextramass[5]),
                Integer.parseInt(getextramass[6]),Integer.parseInt(getextramass[7]),getextramass[4]);
    }
    public void onClick(View v){
        String namest,authorst,publisherst,infost;
        namest=name.getText().toString();
        authorst=author.getText().toString();
        publisherst=publisher.getText().toString();
        infost=info.getText().toString();
        if(namest.equals("")||authorst.equals("")||publisherst.equals("")){
            Toast.makeText(getApplicationContext(),"Неверные данные",Toast.LENGTH_LONG).show();
            return;
        }

        Request newreq=new Request(namest,authorst,publisherst,new Date().toString(),currentworker.getWorker_id(),infost);
        dbr= FirebaseDatabase.getInstance().getReference("requests");
        dbr.push().setValue(newreq);
        Toast.makeText(getApplicationContext(),"Запрос отправлен",Toast.LENGTH_LONG).show();
        name.setText("");
        author.setText("");
        publisher.setText("");
        info.setText("");
    }
    public void onClick2(View v){
        this.finish();
    }
}