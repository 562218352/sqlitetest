package com.zh.test;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SQLit3TestActivity extends Activity {
	
	private OnClickListener listener1;
	private OnClickListener listener2;
	private OnClickListener listener3;
	private OnClickListener listener4;
	private OnClickListener listener5;
	
	private Button button1;
	private Button button2;
	private Button button3;
	private Button button4;
	private Button button5;
	
	private final static String DATABASE_NAME = "MyTest.db";
	private final static int DATABASE_VERSION = 1;
	private final static String TABLE_NAME = "MyTable";
	private final static String TITLE = "title";
	private final static String BODY = "body";
	
	DataBaseHelper myOpDataBaseHelper;
	
	private  class DataBaseHelper extends SQLiteOpenHelper{
		DataBaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		@Override
		public void onCreate(SQLiteDatabase db) {
			String sql = "CREATE TABLE " + TABLE_NAME + "(" + TITLE
			+ " text not null," + BODY + "text not null " + ");";
			db.execSQL(sql);
			
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initListeners();
        initWidget();
        myOpDataBaseHelper = new DataBaseHelper(SQLit3TestActivity.this);
        
    }
    
    public void initWidget(){
    	button1 = (Button)findViewById(R.id.button1);
    	button2 = (Button)findViewById(R.id.button2);
    	button3 = (Button)findViewById(R.id.button3);
    	button4 = (Button)findViewById(R.id.button4);
    	button5 = (Button)findViewById(R.id.button5);
    	
    	button1.setText(R.string.createTable);
    	button2.setText(R.string.dropTable);
    	button3.setText(R.string.insertItem);
    	button4.setText(R.string.deleteItem);
    	button5.setText(R.string.updateItem);
    	
    	button1.setOnClickListener(listener1);
    	button2.setOnClickListener(listener2);
    	button3.setOnClickListener(listener3);
    	button4.setOnClickListener(listener4);
    	button5.setOnClickListener(listener5);
    }
    
    public void initListeners(){
    	listener1 = new OnClickListener() {
			@Override
			public void onClick(View v) {
				createTable();
			}
			private void createTable() {
				SQLiteDatabase db = myOpDataBaseHelper.getWritableDatabase();
				String sql = "CREATE TABLE " + TABLE_NAME + "(" + TITLE
				+ " text not null," + BODY + " text not null " + ");";
				try {
					db.execSQL("DROP TABLE IF EXISTS MyTable ");
					db.execSQL(sql);
					setTitle("创建表成功！");
				} catch (Exception e) {
					setTitle("创建表失败！");
				}
			}
		};
		
		listener2 = new OnClickListener() {
			@Override
			public void onClick(View v) {
				dropTable();
			}
			private void dropTable() {
				SQLiteDatabase db = myOpDataBaseHelper.getWritableDatabase();
				String sql = "DROP TABLE " + TABLE_NAME + ";";
				try {
					db.execSQL(sql);
					setTitle("删除表成功！");
				} catch (Exception e) {
					setTitle("删除表失败！");
				}
			}
		};
		
		listener3 = new OnClickListener() {
			@Override
			public void onClick(View v) {
				insertItem();
			}
			private void insertItem() {
				SQLiteDatabase db = myOpDataBaseHelper.getWritableDatabase();
				String sql1 = "INSERT INTO " + TABLE_NAME + "(" + TITLE + "," + BODY + ") values(" +
						"'张飞','learning android');";
				String sql2 = "INSERT INTO " + TABLE_NAME + "(" + TITLE + "," + BODY + ") values(" +
				"'黄忠','learning android');";
				try {
					
					db.execSQL(sql1);
					db.execSQL(sql2);
					setTitle("插入数据成功！");
				} catch (Exception e) {
					setTitle("插入数据失败！");
				}
			}
		};
		
		listener4 = new OnClickListener() {
			@Override
			public void onClick(View v) {
				deleteItem();
			}
			private void deleteItem() {
				SQLiteDatabase db = myOpDataBaseHelper.getWritableDatabase();
				try {
					db.delete(TABLE_NAME, "title='张飞'", null);
					setTitle("删除数据（张飞）成功！");
				} catch (Exception e) {
					setTitle("删除数据（张飞）失败！");
				}
			}
		};
		
		listener5 = new OnClickListener() {
			@Override
			public void onClick(View v) {
				updateItem();
			}
			private void updateItem() {
				SQLiteDatabase db = myOpDataBaseHelper.getWritableDatabase();
				String[] cols = {TITLE,BODY};
				Cursor cs = db.query(TABLE_NAME, cols, null, null, null, null, null);
				int rowCount = cs.getCount();
				cs.moveToFirst();
				while(!cs.isAfterLast()){
					System.out.println("cols:"+cs.getString(0) + "----" + cs.getString(1));
					cs.moveToNext();	//有两个作用：一，将游标向下移动一个位置；二，判断下一行是否还有记录
				}
				
				setTitle("一共" + rowCount + "条记录");
			}
		};
    }
}