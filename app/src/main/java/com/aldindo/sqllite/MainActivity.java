package com.aldindo.sqllite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SqlLiteHelper database;
    Button add,view,update,del,view_all;
    EditText id,name,email,cc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database=new SqlLiteHelper(this);
        //BUttons
        add=findViewById(R.id.button_add);
        view=findViewById(R.id.button_view);
        update=findViewById(R.id.button_update);
        del=findViewById(R.id.button_delete);
        view_all=findViewById(R.id.button_viewAll);
        //EDIT_TEXTS
        id=findViewById(R.id.editText_id);
        name=findViewById(R.id.editText_name);
        email=findViewById(R.id.editText_email);
        cc=findViewById(R.id.editText_CC);

        //CRUD_METHODS
        addData();
        viewData();
        viewALl();
        updateData();
        delData();


    }


    public void addData(){
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isInserted=database.insertData(name.getText().toString(),email.getText().toString(),cc.getText().toString());
                if(isInserted==true){
                    Toast.makeText(MainActivity.this, "DATA INSERTED", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Something WEnt Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void viewData(){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mID=id.getText().toString();
                if (mID.equals("")){
                    id.setError("Enter ID");
                }
                Cursor cursor=database.readData(mID);
                String data=null;
                if(cursor.moveToNext()){
                    data="ID: "+cursor.getString(0)+"\n"+
                    "NAME: "+cursor.getString(1)+"\n"+
                    "EMAIL: "+cursor.getString(2)+"\n"+
                    "COURSE COUNT: "+cursor.getString(3)+"\n";

                }
                showMessage("Data:",data);
            }

        });
    }
    public void updateData(){
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdate=database.updateDate(
                        id.getText().toString(),
                        name.getText().toString(),
                        email.getText().toString(),
                        cc.getText().toString());

                if(isUpdate==true){
                    Toast.makeText(MainActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void delData(){
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer delRow=database.delData(id.getText().toString());
                if (delRow>0){
                    Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "OPPS", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void viewALl(){
        view_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor=database.getAllData();
                StringBuffer buffer=new StringBuffer();
                if(cursor.getCount()==0){
                    showMessage("Error","No Data Found");
                }

                while (cursor.moveToNext()){
                    buffer.append("ID: "+cursor.getString(0)+"\n");
                    buffer.append("NAME: "+cursor.getString(1)+"\n");
                    buffer.append("EMAIL: "+cursor.getString(2)+"\n");
                    buffer.append("COURSE COUNT: "+cursor.getString(3)+"\n\n");
                }

                showMessage("ALL DATA", buffer.toString());



            }
        });
    }


    public void showMessage(String title,String message){

        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
       builder.create();
       builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
