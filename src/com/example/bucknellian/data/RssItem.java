package com.example.bucknellian.data;

public class RssItem implements com.example.bucknellian.util.ListItem{
	private String title;
	private String link;
	private String icon;
	
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
		return title;
	}
	@Override
	public String setIcon() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
