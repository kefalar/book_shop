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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Allbooks extends AppCompatActivity {
    private ListView bookslist;
    private ArrayAdapter<String> adapter;
    private List<String> listdata;
    private List<Book> listdatabooks;
    private String BOOKS="book";
    private DatabaseReference mdb;
    AlertDialog.Builder bibe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allbooks);
        bookslist=findViewById(R.id.allbookslist);
        bibe=new AlertDialog.Builder(Allbooks.this);
        listdata=new ArrayList<>();
        listdatabooks=new ArrayList<>();
        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listdata);
        bookslist.setAdapter(adapter);
        mdb= FirebaseDatabase.getInstance().getReference(BOOKS);
        getDatafromDB();
        setOnClickItem();
    }
    @Override
    protected void onStop() {
        this.finish();
        super.onStop();
    }
    private void getDatafromDB(){
        ValueEventListener vel=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(listdata.size()>0)listdata.clear();
                if(listdatabooks.size()>0)listdatabooks.clear();
                for(DataSnapshot ds: snapshot.getChildren()) {
                    Book temp = ds.getValue(Book.class);
                    if (temp.isAvailable()) {
                        listdatabooks.add(temp);
                        listdata.add(temp.getName() + " " + temp.getAuthor() + "\n" + "Автор:" + temp.getAuthor() + " Цена:" + temp.getCost());
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
    private void setOnClickItem(){
        bookslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book tempbook=listdatabooks.get(position);
                bibe.setTitle("Книга "+tempbook.getBook_id());
                String infobook=""+tempbook.getName()+", "+tempbook.getAuthor()+"\n"
                        +"Жанр: "+tempbook.getGenre()+", Издательство: "+tempbook.getPublisher()+", Год: "+tempbook.getYear()+"\n"
                        +"Зал: "+tempbook.getHall()+", Стеллаж: "+tempbook.getRack()+"\n"
                        +"Цена: "+tempbook.getCost()+" руб.";
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
    public void back(View v){
        finish();
    }
}