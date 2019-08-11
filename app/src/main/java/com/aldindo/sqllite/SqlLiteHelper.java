package com.aldindo.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="records.db";
    public static final String TABLE_NAME="my_Table";
    public static final String COL_1="ID";
    public static final String COL_2="NAME";
    public static final String COL_3="EMAIL";
    public static final String COL_4="COURSE_COUNT";


    public SqlLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " NAME TEXT, " +
                " EMAIL TEXT, " +
                " COURSE_COUNT INTERGER )");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public boolean insertData(String name, String email, String CC){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,email);
        contentValues.put(COL_4,CC);
        long result=db.insert(TABLE_NAME, null, contentValues);

        if (result==-1){
            return false;
        }
        else {
            return true;

        }
    }

    public boolean updateDate(String id,String name, String email, String CC){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,email);
        contentValues.put(COL_4,CC);

        database.update(TABLE_NAME,contentValues,"ID=?", new String[] {id});

        return true;
    }

    public Cursor readData(String id){
        SQLiteDatabase database=this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE ID='"+id+"'";
        Cursor cursor=database.rawQuery(query, null);
        return cursor;
    }

    public Integer delData(String id){
        SQLiteDatabase database=this.getWritableDatabase();
        return database.delete(TABLE_NAME,"ID=?",new String[] {id});
    }
    public Cursor getAllData(){
        SQLiteDatabase database=this.getWritableDatabase();
        String sql="SELECT * FROM "+TABLE_NAME;
        Cursor cursor=database.rawQuery(sql, null);
        return cursor;
    }

}
