package com.allbooks.books_shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManagerAct extends AppCompatActivity {
    private ListView MANworkerlist;
    private ArrayList<String> listdata;
    private ArrayList<Worker> listworkers;
    private DatabaseReference mdb;
    private ArrayAdapter<String> adapter;
    AlertDialog.Builder bibe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        MANworkerlist=findViewById(R.id.MANworkerlist);
        mdb= FirebaseDatabase.getInstance().getReference("users");
        listdata=new ArrayList<>();
        listworkers=new ArrayList<>();
        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listdata);
        MANworkerlist.setAdapter(adapter);
        bibe=new AlertDialog.Builder(ManagerAct.this);
        getDatafromDB();
        setOnClickItem();
    }
    public void onClickaddworker(View v){
        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    public void onClickaddbook(View v){
        Intent intent=new Intent(this, Addnewbook.class);
        startActivity(intent);
    }
    public void onClickReq(View v){
        Intent intent=new Intent(this, ReqestAct.class);
        startActivity(intent);
    }
    private void getDatafromDB(){
        ValueEventListener vel=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(listdata.size()>0)listdata.clear();
                if(listworkers.size()>0)listworkers.clear();
                for(DataSnapshot ds: snapshot.getChildren()) {
                    Worker temp = ds.getValue(Worker.class);
                    listworkers.add(temp);
                    listdata.add(temp.getName() + " " + temp.getSurname());

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
        MANworkerlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Worker tempworker=listworkers.get(position);
                bibe.setTitle("Работник "+tempworker.getSurname());
                String infobook=""+tempworker.getName()+", "+tempworker.getSurname()+"\n"
                        +"id: "+tempworker.getWorker_id()+", email: "+tempworker.getEmail()+"\n"
                        +"login: "+tempworker.getLogin()+", password: "+tempworker.getPassword()+"\n"
                        +"Зарплата: "+tempworker.getSalary()+" руб."+" Продано книг за месяц: "+tempworker.getSales()+"\n"
                        +"День зарплаты: "+tempworker.getDay_salary();
                bibe.setMessage(infobook);
                bibe.setCancelable(true);
                bibe.setPositiveButton("Закрыть", new DialogInterface.OnClickListener() { // Кнопка ОК
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Отпускает диалоговое окно
                        //Добавить переход на страницу с покупкой книги
                    }
                });
                AlertDialog ad=bibe.create();
                ad.show();
            }
        });
    }
}