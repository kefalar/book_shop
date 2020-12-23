package com.allbooks.books_shop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class Addnewbook extends AppCompatActivity {
    EditText name,author,genre,year,publisher,info,hall,rack,cost;
    private DatabaseReference mdb;
    private String BOOKS="book";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewbook);
        name=findViewById(R.id.namebook);
        author=findViewById(R.id.authorbook);
        genre=findViewById(R.id.genrebook);
        year=findViewById(R.id.yearbook);
        publisher=findViewById(R.id.publisherbook);
        info=findViewById(R.id.infobook);
        hall=findViewById(R.id.hallbook);
        rack=findViewById(R.id.rackbook);
        cost=findViewById(R.id.costbook);
        mdb= FirebaseDatabase.getInstance().getReference(BOOKS);
        Button btn=findViewById(R.id.btnbook);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=mdb.getKey();
                String name1,author1,genre1,year1,publisher1,info1,hall1,rack1,cost1;
                name1=name.getText().toString();
                author1=author.getText().toString();
                genre1=genre.getText().toString();
                year1=year.getText().toString();
                publisher1=publisher.getText().toString();
                info1=info.getText().toString();
                hall1=hall.getText().toString();
                rack1=rack.getText().toString();
                cost1=cost.getText().toString();
                //public Book(String name, String author, String genre, int book_id,
                //int year, String publisher, String info, char hall, int rack, int cost)
                Random rnd=new Random();
                int idb=rnd.nextInt(100000);
                Book newbook=new Book(id,name1,author1,genre1,idb
                        ,Integer.parseInt(year1),publisher1,info1,hall1,
                        Integer.parseInt(rack1),Integer.parseInt(cost1));
                Toast.makeText(getApplicationContext(), "Книга создана",Toast.LENGTH_LONG).show();
               mdb.push().setValue(newbook);
            }
        });
    }
}