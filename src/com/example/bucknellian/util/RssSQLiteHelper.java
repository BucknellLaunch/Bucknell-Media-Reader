package com.example.bucknellian.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RssSQLiteHelper extends SQLiteOpenHelper {
	public static final String TABLE_RSS_ITEMS = "RssItems";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_LINK = "link";
	public static final String COLUMN_ICON = "icon";
	public static final String COLUMN_PUB_DATE = "pubDate";

	public static final String DATABASE_NAME = "rss_items.db";
	public static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_RSS_ITEMS + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_TITLE
			+ " text not null, " + COLUMN_LINK + " text not null, "
			+ COLUMN_ICON + " text not null, " + COLUMN_PUB_DATE
			+ " text not null);";

	public RssSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_RSS_ITEMS);
	    onCreate(db);
	}
}
