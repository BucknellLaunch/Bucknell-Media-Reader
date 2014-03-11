package com.example.bucknellian.util;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.bucknellian.data.RssItem;

// DAO for accessing RssItems Database
public class RssItemsDataSource {
	private SQLiteDatabase database;
	private RssSQLiteHelper dbHelper;

	public RssItemsDataSource(Context context) {
		dbHelper = new RssSQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}
	
	
	public RssItem createRssItem(){
		return null;
		// to do: create a new RssItem
		
	}
	
	public void RssItem(){
		// to do: delete a RssItem from the database
	}
	
	public List<RssItem> getAllRssItems(){
		// to do: get all items from the database
		return null;
	}
	
	private RssItem cursorToRssItem(Cursor cursor){
		// to do: convert a cursor to a RssItem
		return null;
	}
	
	
}
