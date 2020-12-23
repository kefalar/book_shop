package com.allbooks.books_shop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.DatabaseMetaData;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {
    private DatabaseReference mdb;
    private String USER="users";
    EditText login,password,email,name,surname,date,sal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        mdb= FirebaseDatabase.getInstance().getReference(USER);
        Button btn=(Button)findViewById(R.id.button2);
        login=(EditText)findViewById(R.id.loginuser);
        password=(EditText)findViewById(R.id.passworduser);
        email=(EditText)findViewById(R.id.email);
        name=(EditText)findViewById(R.id.name);
        surname=(EditText)findViewById(R.id.surname);
        date=(EditText)findViewById(R.id.datesal);
        sal=(EditText)findViewById(R.id.sal);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=mdb.getKey();
                String login1,password1,email1,name1,surname1,date1,sal1;
                login1=login.getText().toString();
                password1=password.getText().toString();
                email1=email.getText().toString();
                name1=name.getText().toString();
                surname1=surname.getText().toString();
                date1=date.getText().toString();
                sal1=sal.getText().toString();
                Random random = new Random();
                int id1=random.nextInt(10000);
                Worker bob=new Worker(id,name1,surname1,
                        login1,password1,
                        id1,Integer.parseInt(date1),Integer.parseInt(sal1),0,
                        email1);
                mdb.push().setValue(bob);
                Toast.makeText(getApplicationContext(), "Человек добавлен",Toast.LENGTH_LONG).show();
                login.setText("");
                password.setText("");
                email.setText("");
                name.setText("");
                surname.setText("");
                date.setText("");
                sal.setText("");
            }
        });
    }
}