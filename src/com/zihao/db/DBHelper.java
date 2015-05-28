package com.zihao.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DBHelper extends SQLiteOpenHelper {

 private final static String DB_NAME ="songzi.db";//数据库名
	private final static int VERSION = 1;// 版本号

 //自带的构造方法
 public DBHelper(Context context, String name, CursorFactory factory,
   int version) {
  super(context, name, factory, version);
 }

 //为了每次构造时不用传入dbName和版本号，自己得新定义一个构造方法
 public DBHelper(Context cxt){
  this(cxt, DB_NAME, null, VERSION);//调用上面的构造方法
 }

 //版本变更时
 public DBHelper(Context cxt,int version) {
  this(cxt,DB_NAME,null,version);
 }

 //当数据库创建的时候调用
 public void onCreate(SQLiteDatabase db) {
  String sql = "create table myGmame(" +
      "id integer primary key autoincrement," +
      "myCount int,name char(20))";

  db.execSQL(sql);
  db.execSQL("insert into myGmame(myCount)values(1)");
 }

 //版本更新时调用
 public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 }

}