/**
 * 
 */
package models;

import java.util.Date;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

/**
 * @author boolli
 *
 */
public class RssLatestDate {
	
	public Date latestDate;
	private Activity context;
	private SharedPreferences sharedPreferences;
	private Gson gson;
	
	private static RssLatestDate instance = null;
	
	protected RssLatestDate(Activity context){
		this.context = context;
		this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		this.gson = new Gson();
	}
	
	
	public Date get(){
		return getDateFromSharedPreferences();
	}
	
	public void update(Date date){
		String json = gson.toJson(date);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("rssLatestDate", json);
		editor.apply();
	}
	
	
	private Date getDateFromSharedPreferences(){
		String json = sharedPreferences.getString("rssLatestDate", "");
		Date date = gson.fromJson(json,Date.class);
		return date;
	}
	

	public static RssLatestDate getInstance(Activity context){
		if (instance == null){
			instance = new RssLatestDate(context);
		}
		return instance;
	}

}
