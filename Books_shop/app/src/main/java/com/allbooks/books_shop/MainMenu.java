package com.allbooks.books_shop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

//Главное меню приложения
public class MainMenu extends AppCompatActivity {
    Button btn;
    String getextra;
    Worker currentworker;
    String[] getextramass;
    Fragment topfrag;
    TextView tv1,tv2,tv3;
    DatabaseReference db;
    private final int Bonusforsale=50;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Bundle arguments = getIntent().getExtras();
        getextra=arguments.get("user").toString();
        getextramass=getextra.split(";:");
        currentworker=new Worker("user",getextramass[2],getextramass[3],getextramass[0],getextramass[1],Integer.parseInt(getextramass[8]),Integer.parseInt(getextramass[5]),
                Integer.parseInt(getextramass[6]),Integer.parseInt(getextramass[7]),getextramass[4]);
        topfrag= getFragmentManager().findFragmentById(R.id.topfragment);
        tv1=((TextView)findViewById(R.id.tvnumber));
        tv2=((TextView)findViewById(R.id.tvsalary));
        tv3=((TextView)findViewById(R.id.tvidandname));
        db= FirebaseDatabase.getInstance().getReference("users");
        updatetop();
        updatesalary();
        Runnable task = new Runnable() {
            public void run() {
                while(true){
                    updatet();
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }
    public void onClicksalary(View v){
        updatet();
        String data=currentworker.getLogin()+";:"+currentworker.getPassword()+";:"
                +currentworker.getName()+";:"+currentworker.getSurname()+";:"+currentworker.getEmail()+";:"
                +currentworker.getDay_salary()+";:"+currentworker.getSalary()+";:"+currentworker.getSales()+";:"+currentworker.getWorker_id();
        Intent intent=new Intent(this,Salaryact.class);
        intent.putExtra("user",data);
        startActivity(intent);
    }
    public void onClickreq(View v){
        Intent intent=new Intent(this,Requestactivity.class);
        intent.putExtra("user",getextra);
        startActivity(intent);
    }
    public void onClick_Allbooks(View v){
        Intent intent= new Intent(this,Allbooks.class);
        startActivity(intent);
    }
    private void updatetop(){

        tv1.setText("id:"+currentworker.getWorker_id());
        int salary=currentworker.getSalary()+currentworker.getSales()*Bonusforsale;
        tv2.setText("зп:"+currentworker.getSalary()+"+"+currentworker.getSales()+"*"+Bonusforsale+"="+salary);
        tv3.setText("Продавец:"+currentworker.getName()+" "+currentworker.getSurname());
    }
    public void updatet(){
        db.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Worker temp=ds.getValue(Worker.class);
                    if(temp.getWorker_id()==currentworker.getWorker_id() && temp.getLogin().equals(currentworker.getLogin())) {
                        currentworker.setnewsale(temp.getSales());
                        updatetop();
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
    public void searchbook(View v){
        Intent booksearch=new Intent(this,Bookslist.class);
        booksearch.putExtra("user",getextra);
        startActivity(booksearch);
    }
    public void updatesalary(){
        Calendar cal=Calendar.getInstance();
        int day=cal.DAY_OF_MONTH;
        if(currentworker.getDay_salary()==day){
            DatabaseReference setsal=FirebaseDatabase.getInstance().getReference("info_salary");
            Date newdate=new Date();
            int newsal=currentworker.getSalary()+(currentworker.getSales()*Bonusforsale);
            Salinf nsale=new Salinf(newdate.toString(),currentworker.getWorker_id(),currentworker.getSurname(),newsal);
            setsal.push().setValue(nsale);
            setsal=(DatabaseReference) FirebaseDatabase.getInstance().getReference("users");
            setsal.addListenerForSingleValueEvent(new ValueEventListener(){
                @Override
                public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        Worker temp=ds.getValue(Worker.class);
                        if(temp.getWorker_id()==currentworker.getWorker_id() && temp.getLogin().equals(currentworker.getLogin())) {
                            ds.child("sales").getRef().setValue(0);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
            updatet();
            Toast.makeText(getApplicationContext(),"День зарплаты, получите ее сегодня", Toast.LENGTH_LONG).show();
        }
    }
}