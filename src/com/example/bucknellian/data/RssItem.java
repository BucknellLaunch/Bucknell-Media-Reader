package com.example.bucknellian.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

import com.example.bucknellian.util.RelativeDate;


public class RssItem implements com.example.bucknellian.data.ListItem{
	private String title;
	private String link;
	private String icon;
	private String pubDate;
	private String category;

	private static SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
	
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

	public String getPubDate() {
		Date d = this.getPubDateObject();
		if (d != null)
			return RelativeDate.getRelativeDate(d);
		else
			return this.getPubDate();
		//return pubDate;
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
		return "[" + category + "] " + title + " " + this.getPubDate();
	}
	@Override
	public String setIcon() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
