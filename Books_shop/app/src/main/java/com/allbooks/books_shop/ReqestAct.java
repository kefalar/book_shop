package com.allbooks.books_shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReqestAct extends AppCompatActivity {
    private ListView reqlist;
    private ArrayList<String> listdata;
    private ArrayList<Request> listreq;
    private DatabaseReference mdb;
    private ArrayAdapter<String> adapter;
    AlertDialog.Builder bibe;
    private Request tempreq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reqest);
        reqlist=findViewById(R.id.requestslist);
        mdb= FirebaseDatabase.getInstance().getReference("requests");
        listdata=new ArrayList<>();
        listreq=new ArrayList<>();
        bibe=new AlertDialog.Builder(ReqestAct.this);
        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listdata);
        reqlist.setAdapter(adapter);
        getDatafromDB();
        setOnClickItem();
    }
    private void getDatafromDB(){
        ValueEventListener vel=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(listdata.size()>0)listdata.clear();
                if(listreq.size()>0)listreq.clear();
                for(DataSnapshot ds: snapshot.getChildren()) {
                    Request temp = ds.getValue(Request.class);
                    listreq.add(temp);
                    listdata.add(temp.getName() + " " + temp.getAutor());

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mdb.addValueEventListener(vel);
    }
    private void setOnClickItem(){
        reqlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tempreq=listreq.get(position);
                bibe.setTitle("Запрос о книге "+tempreq.getName());
                String infobook=""+tempreq.getName()+", Автор:"+tempreq.getAutor()+"\n"
                        +"Издательство: "+tempreq.getPublisher()+", id работника: "+tempreq.getWorker_id()+"\n"
                        +"дата запроса: "+tempreq.getDate()+"\n"
                        +"инфо: "+tempreq.getInfo();
                bibe.setMessage(infobook);
                bibe.setCancelable(true);
                bibe.setPositiveButton("Принять", new DialogInterface.OnClickListener() { // Кнопка ОК
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference newdbref=(DatabaseReference)FirebaseDatabase.getInstance().getReference("requests");
                        newdbref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    Request temp = ds.getValue(Request.class);
                                    if (temp.getWorker_id()==tempreq.getWorker_id() && temp.getName().equals(tempreq.getName())) {
                                        ds.getRef().removeValue();
                                        return;
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        Toast.makeText(getApplicationContext(), "Удалено", Toast.LENGTH_SHORT).show();
                        dialog.dismiss(); // Отпускает диалоговое окно
                        //Добавить переход на страницу с покупкой книги
                    }
                });
                AlertDialog ad=bibe.create();
                ad.show();
            }
        });
    }
    public void back(View v){
        finish();
    }
}