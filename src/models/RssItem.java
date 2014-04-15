package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.util.Log;



public class RssItem implements  Comparable<RssItem>{
	private String title;
	private String link;
	private String icon;
	private String pubDate;
	private String category;

	@SuppressLint("SimpleDateFormat")
	private static SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
	//"EEE, dd MMM yyyy HH:mm:ss zzz"
	//"HH:mm aa MMM dd, yyyy"
	
	public Date getPubDateObject(){
		try {
			return RssItem.sdf.parse(this.pubDate);
		} catch (ParseException e) {
			Log.e("Date format error","Date format error");
			return null;
		}
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getRelativePubDate() {
		Date d = this.getPubDateObject();
		if (d != null)
			return RelativeDate.getRelativeDate(d);
		else
			return this.getPubDate();
		//return pubDate;
	}
	
	public String getPubDate(){
		return this.pubDate;
	}
	
	
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public void setLastBuildDate(String lastBuildDate) {
		this.pubDate = lastBuildDate;
	}

	public RssItem(RssItem another){
		this.title = another.title;
		this.link = another.link;
		this.icon = another.icon;
		this.pubDate = another.pubDate;
		this.category = another.category;
	}
	
	public RssItem(){
		
	}
	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	public String toString(){
		return "[" + category + "] " + title + " " + this.getRelativePubDate();
	}

	@Override
	public int compareTo(RssItem another) {
		Date currentDate = this.getPubDateObject();
		Date otherDate = another.getPubDateObject();
		return currentDate.compareTo(otherDate);
	}


	
	
}
