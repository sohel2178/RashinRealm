package com.example.sohel.rashinrealm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sohel.rashinrealm.Model.Dog;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etName,etAge;
    private Button btnSave,btnPrintAll;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();
        initView();
    }

    private void initView() {
        etName = (EditText) findViewById(R.id.etName);
        etAge = (EditText) findViewById(R.id.etAge);
        btnSave = (Button) findViewById(R.id.save);
        btnPrintAll = (Button) findViewById(R.id.print_all);

        btnSave.setOnClickListener(this);
        btnPrintAll.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        
        switch (view.getId()){
            case R.id.save:
                Log.d("FFF",Thread.currentThread().getName());
                saveDataInDatabase();
                break;
            
            case R.id.print_all:
                printAllData();
                break;
        }
        
    }

    private void printAllData() {
        List<Dog> dogs = realm.where(Dog.class)
                .between("id",1,3).findAll();

        for(Dog x: dogs){
            Log.d("FFF","Name: "+x.getName());
            Log.d("FFF","Age: "+x.getAge());
            Log.d("FFF","");
        }


    }

    private void saveDataInDatabase() {
        final String name = etName.getText().toString();
        String txtAge = etAge.getText().toString();

        final int age = Integer.parseInt(txtAge);

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number currentIdNum = realm.where(Dog.class).max("id");

                Log.d("FFF",currentIdNum+"");
                int nextId;
                if(currentIdNum==null){
                    Log.d("FFF","Data not yet Insert");
                    nextId=1;


                }else{
                    Log.d("FFF","There must have Some Data");
                    nextId = currentIdNum.intValue()+1;
                }

                Dog dog =realm.createObject(Dog.class,nextId);
                dog.setName(name);
                dog.setAge(age);

                Log.d("FFF",Thread.currentThread().getName());
            }
        });
    }
}
