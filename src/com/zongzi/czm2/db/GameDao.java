package com.zongzi.czm2.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GameDao {

	DBHelper helper = null;

	public GameDao(Context cxt) {
		helper = new DBHelper(cxt);
	}

	/**
	 * 当Activity中调用此构造方法，传入一个版本号时，系统会在下一次调用数据库时调用Helper中的onUpgrade()方法进行更新
	 * 
	 * @param cxt
	 * @param version
	 */
	public GameDao(Context cxt, int version) {
		helper = new DBHelper(cxt, version);
	}

	// 插入操作
	public void insertData(int myGmame) {
		SQLiteDatabase localSQLiteDatabase = this.helper.getWritableDatabase();
		localSQLiteDatabase.execSQL("insert into myGmame(myCount)values(?)", new Object[]{myGmame});
	}

	// 更新操作
	public void upateData(int myGame,int id){
	SQLiteDatabase localDatabase = this.helper.getWritableDatabase();
		localDatabase.execSQL("update myGame set myCount=? where id = ?",new Object[]{myGame,id} );
	}
	//查询操作
	public int selectData(){
		SQLiteDatabase localDatabase = this.helper.getWritableDatabase();
		Cursor cursor = localDatabase.rawQuery("select myCount from myGame where id = 1",null);
		if(cursor.moveToNext()){
			return cursor.getColumnIndex("myCount");
		}else {
			return -1;
		}
	}
}