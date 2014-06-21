package database;

import java.util.ArrayList;
import java.util.List;


import models.RssItem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


// DAO for accessing RssItems Database
public class RssItemsDataSource {
	private SQLiteDatabase database;
	private RssSQLiteHelper dbHelper;
	private String[] allColumns = { RssSQLiteHelper.COLUMN_ID,
			RssSQLiteHelper.COLUMN_TITLE, RssSQLiteHelper.COLUMN_LINK,
			RssSQLiteHelper.COLUMN_ICON, RssSQLiteHelper.COLUMN_PUB_DATE,
			RssSQLiteHelper.COLUMN_CATEGORY };

	public RssItemsDataSource(Context context) {
		dbHelper = new RssSQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	/**
	 * adds a RssItem into the database. Returns the item if it is successfully
	 * added. Otherwise returns null.
	 * 
	 * @param item
	 * @return
	 */
	public RssItem addRssItem(RssItem item) {
		ContentValues values = new ContentValues();
		values.put(RssSQLiteHelper.COLUMN_TITLE, item.getTitle());
		values.put(RssSQLiteHelper.COLUMN_LINK, item.getLink());
		values.put(RssSQLiteHelper.COLUMN_ICON, item.getIcon());
		values.put(RssSQLiteHelper.COLUMN_PUB_DATE, item.getPubDate());
		values.put(RssSQLiteHelper.COLUMN_CATEGORY, item.getCategory());

		long insertId = database.insert(RssSQLiteHelper.TABLE_RSS_ITEMS, null,
				values);
		if (insertId > 0)
			return item;
		else
			return null;
	}

	public void addRssItems(List<RssItem> items) {
		for (RssItem item : items) {
			addRssItem(item);
		}
	}

	public void deleteRssItemBeforeDate() {
		// to do: delete a RssItem from the database
	}
	
	public void clearTable(){
		if (database != null){
			database.delete(RssSQLiteHelper.TABLE_RSS_ITEMS, null, null);
		}
	}

	public List<RssItem> getAllRssItems() {
		List<RssItem> items = new ArrayList<RssItem>();

		Cursor cursor = database.query(RssSQLiteHelper.TABLE_RSS_ITEMS,
				allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			RssItem item = cursorToRssItem(cursor);
			items.add(item);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();

		return items;
	}

	public boolean isDatabaseEmpty() {
		boolean isEmpty = true;
		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ RssSQLiteHelper.TABLE_RSS_ITEMS, null);
		if (cursor.moveToFirst())
			isEmpty = false;
		return isEmpty;
	}

	private RssItem cursorToRssItem(Cursor cursor) {
		RssItem item = new RssItem();
		item.setTitle(cursor.getString(1));
		item.setLink(cursor.getString(2));
		item.setIcon(cursor.getString(3));
		item.setPubDate(cursor.getString(4));
		item.setCategory(cursor.getString(5));

		return item;
	}

}
