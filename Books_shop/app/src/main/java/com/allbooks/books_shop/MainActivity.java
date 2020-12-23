package com.allbooks.books_shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private EditText login;
    private EditText password;
    private Button btn;
    private DatabaseReference mdb;
    private String USER="users";
    private ArrayList<Worker> listdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=(EditText)findViewById(R.id.loginenter);
        password=(EditText)findViewById(R.id.passwordenter);
        btn=(Button)findViewById(R.id.btnenter);
        mdb= FirebaseDatabase.getInstance().getReference(USER);
        listdata=new ArrayList<>();
        getUsersFromDB();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String log=login.getText().toString();
                String pas=password.getText().toString();
                String res=log+" "+pas;
                if(log.equals("admin")&&pas.equals("admin")){
                    Intent intent=new Intent(getApplicationContext(), ManagerAct.class);
                    startActivity(intent);
                    finish();
                    return;
                }
                for(Worker temp : listdata){
                    if(log.equals(temp.getLogin())&&pas.equals(temp.getPassword())){
                        Intent mainmenu=new Intent(getApplicationContext(),MainMenu.class);
                        String data=temp.getLogin()+";:"+temp.getPassword()+";:"
                                +temp.getName()+";:"+temp.getSurname()+";:"+temp.getEmail()+";:"
                                +temp.getDay_salary()+";:"+temp.getSalary()+";:"+temp.getSales()+";:"+temp.getWorker_id();
                        mainmenu.putExtra("user",data);
                        startActivity(mainmenu);
                        finish();

                        return;
                    }
                }
                login.setText("");
                password.setText("");
                Toast.makeText(getApplicationContext(),"Неверный логин или пароль",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void getUsersFromDB(){
        ValueEventListener vel=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(listdata.size()>0)listdata.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    Worker temp=ds.getValue(Worker.class);
                    listdata.add(temp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mdb.addValueEventListener(vel);
    }
}