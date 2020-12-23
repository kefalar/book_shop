package com.allbooks.books_shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bookslist extends AppCompatActivity {
    private ListView bookslist;
    private ArrayAdapter<String> adapter;
    private List<String> listdata;
    private List<Book> listdatabooks;
    private String BOOKS="book";
    private DatabaseReference mdb;
    private Worker currentworker;
    private String getextra;
    private String[] getextramass;
    private AlertDialog.Builder bibe;
    private AlertDialog.Builder agreeinsale;
    private AlertDialog.Builder changeplase;
    private EditText edsearch;
    private Book tempbook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookslist);
        bibe=new AlertDialog.Builder(Bookslist.this);
        agreeinsale=new AlertDialog.Builder(Bookslist.this);
        changeplase=new AlertDialog.Builder(Bookslist.this);
        init();
        setOnClickItem();
    }
    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
    }
    private void init(){
        edsearch=findViewById(R.id.etforsearch);
        bookslist=findViewById(R.id.bookslist);
        Bundle arguments = getIntent().getExtras();
        getextra=arguments.get("user").toString();
        getextramass=getextra.split(";:");
        currentworker=new Worker("user",getextramass[2],getextramass[3],getextramass[0],getextramass[1],Integer.parseInt(getextramass[8]),Integer.parseInt(getextramass[5]),
                Integer.parseInt(getextramass[6]),Integer.parseInt(getextramass[7]),getextramass[4]);
        listdata=new ArrayList<>();
        listdatabooks=new ArrayList<>();
        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listdata);
        bookslist.setAdapter(adapter);
        mdb= (DatabaseReference) FirebaseDatabase.getInstance().getReference(BOOKS);
    }
    private void getDatafromDB(String search){
        ValueEventListener vel=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(listdata.size()>0)listdata.clear();
                if(listdatabooks.size()>0)listdatabooks.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    Book temp=ds.getValue(Book.class);
                    if(temp.getName().contains(search) && temp.isAvailable()){
                        listdatabooks.add(temp);
                        listdata.add(temp.getName()+" "+temp.getAuthor()+"\n"+"зал:"+temp.getHall()+" стеллаж:"+temp.getRack());
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mdb.addValueEventListener(vel);
    }
    public void onClick(View v){
        if(!edsearch.getText().toString().equals("")){
            String search=edsearch.getText().toString();
            getDatafromDB(search);
        }
    }
    private void setOnClickItem(){
        bookslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tempbook=listdatabooks.get(position);
                bibe.setTitle("Книга "+tempbook.getBook_id());
                String infobook=""+tempbook.getName()+", "+tempbook.getAuthor()+"\n"
                        +"Жанр: "+tempbook.getGenre()+", Издательство: "+tempbook.getPublisher()+", Год: "+tempbook.getYear()+"\n"
                        +"Зал: "+tempbook.getHall()+", Стеллаж: "+tempbook.getRack()+"\n"
                        +"Цена: "+tempbook.getCost()+" руб.";
                bibe.setMessage(infobook);
                bibe.setCancelable(true);
                bibe.setPositiveButton("Продать книгу", new DialogInterface.OnClickListener() { // Кнопка ОК
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        agreeinsale.setTitle("Продажа книги "+tempbook.getBook_id());
                        String current_message="Записать продажу данной книги на ваш id:"+currentworker.getWorker_id()+"\n"+
                                "Дата:"+new Date().toString()+"\n"+
                                "Цена:"+tempbook.getCost();
                        agreeinsale.setMessage(current_message);
                        agreeinsale.setCancelable(true);
                        agreeinsale.setPositiveButton("Продать", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                currentworker.addsale();
                                tempbook.setAvailable();
                                updatevalues();
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Книга была продана успешно",Toast.LENGTH_LONG).show();
                            }
                        });
                        agreeinsale.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog ad=agreeinsale.create();
                        ad.show();
                        dialog.dismiss(); // Отпускает диалоговое окно
                        //Добавить переход на страницу с покупкой книги
                    }
                });
                bibe.setNegativeButton("Переместить книгу", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText inputEditTextField = new EditText(getApplicationContext());
                        changeplase
                                .setTitle("Title")
                                .setMessage("Введите новое место для книги в формате \"а 6\", где а это зал а 6 это стеллаж")
                                .setView(inputEditTextField)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String editTextInput = inputEditTextField.getText().toString();
                                        DatabaseReference newdbref=(DatabaseReference)FirebaseDatabase.getInstance().getReference("book");
                                        newdbref.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (DataSnapshot ds : snapshot.getChildren()) {
                                                    Book temp = ds.getValue(Book.class);
                                                    if (temp.getBook_id() == tempbook.getBook_id() && temp.getName().equals(tempbook.getName())) {
                                                        ds.child("hall").getRef().setValue(editTextInput.split(" ")[0]);
                                                        ds.child("rack").getRef().setValue(Integer.parseInt(editTextInput.split(" ")[1]));
                                                        break;
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                        Toast.makeText(getApplicationContext(),"Книга перемещена",Toast.LENGTH_LONG).show();
                                    }
                                })
                                .setNegativeButton("Cancel", null);
                        AlertDialog ad=changeplase.create();
                        ad.show();
                        dialog.dismiss();
                    }
                });
                AlertDialog ad=bibe.create();
                ad.show();
            }
        });
    }
    public void updatevalues(){
        DatabaseReference newdbref=(DatabaseReference) FirebaseDatabase.getInstance().getReference("users");
                newdbref.addListenerForSingleValueEvent(new ValueEventListener(){
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            Worker temp=ds.getValue(Worker.class);
                            if(temp.getWorker_id()==currentworker.getWorker_id() && temp.getLogin().equals(currentworker.getLogin())) {
                                ds.child("sales").getRef().setValue(currentworker.getSales());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
        newdbref=(DatabaseReference)FirebaseDatabase.getInstance().getReference("book");
        newdbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Book temp = ds.getValue(Book.class);
                    if (temp.getBook_id() == tempbook.getBook_id() && temp.getName().equals(tempbook.getName())) {
                        ds.getRef().removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        newdbref=(DatabaseReference)FirebaseDatabase.getInstance().getReference("book_sale");
        Soldbook sb=new Soldbook(tempbook.getName(),tempbook.getAuthor(),new Date().toString(),tempbook.getCost(),currentworker.getWorker_id(),currentworker.getSurname());
        newdbref.push().setValue(sb);
    }

}