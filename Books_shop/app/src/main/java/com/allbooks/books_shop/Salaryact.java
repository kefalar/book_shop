package com.allbooks.books_shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Salaryact extends AppCompatActivity {
    private String getextra;
    private String[] getextramass;
    private Worker currentworker;
    private DatabaseReference dbr;
    private TextView nameid,currentdate,datesalary,salary;
    private ListView listbookssale;
    private ArrayList<String> listdata;
    private ArrayList<Soldbook> listsales;
    private ArrayAdapter<String> adapter;
    private final int Bonusforsale=50;
    private SimpleDateFormat formatForDateNow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salaryact);
        dbr= FirebaseDatabase.getInstance().getReference("book_sale");
        Bundle arguments = getIntent().getExtras();
        getextra=arguments.get("user").toString();
        getextramass=getextra.split(";:");
        currentworker=new Worker("user",getextramass[2],getextramass[3],getextramass[0],getextramass[1],Integer.parseInt(getextramass[8]),Integer.parseInt(getextramass[5]),
                Integer.parseInt(getextramass[6]),Integer.parseInt(getextramass[7]),getextramass[4]);
        nameid=findViewById(R.id.tvnameid);
        currentdate=findViewById(R.id.tvdate);
        datesalary=findViewById(R.id.tvdatesal);
        salary=findViewById(R.id.tvsalarytotal);
        listbookssale=findViewById(R.id.listbookssale);
        String input="Имя: "+currentworker.getName()+" "+currentworker.getSurname()+", логин:"+currentworker.getLogin()+"\n"
                +"Id: "+currentworker.getWorker_id();
        nameid.setText(input);
        Date dateNow = new Date();
        formatForDateNow = new SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.ENGLISH);
        Calendar cal=Calendar.getInstance();
        int day=cal.DAY_OF_MONTH;
        int month=cal.MONTH;
        String datesalarystring="Дата получения зарплаты: "+currentworker.getDay_salary();
        listdata=new ArrayList<>();
        listsales=new ArrayList<>();
        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listdata);
        listbookssale.setAdapter(adapter);
        if(currentworker.getDay_salary()== day){
            datesalarystring="Сегодня зарплата";
        }
        else if(currentworker.getDay_salary()< day){
            datesalarystring+="."+month;
        }
        else datesalarystring+=" числа следующего месяца";
        datesalary.setText(datesalarystring);
        currentdate.setText(formatForDateNow.format(dateNow));
        int salaryint=currentworker.getSalary()+currentworker.getSales()*Bonusforsale;
        datesalarystring=""+currentworker.getSalary()+"+"+currentworker.getSales()+"*"+Bonusforsale+"="+salaryint;
        salary.setText(datesalarystring);
        getDatafromDB();
    }
    @Override
    protected void onResume() {

        super.onResume();
    }
    private void getDatafromDB(){
        ValueEventListener vel=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(listdata.size()>0)listdata.clear();
                if(listsales.size()>0)listsales.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    Soldbook temp=ds.getValue(Soldbook.class);
                    if(temp.getWorkerid()==currentworker.getWorker_id() && temp.getSurnameworker().equals(currentworker.getSurname())){
                        listsales.add(temp);
                        Date dd=new Date(temp.getDatesale());
                        listdata.add(temp.getName()+" "+temp.getAuthor()+"\n"+"Дата:"+formatForDateNow.format(dd));
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        dbr.addValueEventListener(vel);
    }
    public void onClick(View v){
        finish();
    }
}