package com.example.bucknellian.data;


public class RssItem implements com.example.bucknellian.data.ListItem{
	private String title;
	private String link;
	private String icon;
	private String pubDate;
	private String category;
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPubDate() {
		return pubDate;
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
