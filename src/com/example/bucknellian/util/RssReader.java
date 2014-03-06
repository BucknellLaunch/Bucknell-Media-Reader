package com.example.bucknellian.util;

import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.example.bucknellian.data.RssItem;

public class RssReader {
	
	private String rssUrl;
	private String icon;
	private List<RssItem> rssItems;
	private RssItemAdapter<RssItem> rssItemAdapter;
	
	
	public RssReader(String rssUrl, String icon, List<RssItem> rssItems, RssItemAdapter<RssItem> rssItemAdapter) {
		this.rssUrl = rssUrl;
		this.icon = icon;
		this.rssItemAdapter = rssItemAdapter;
	}
	
	public List<RssItem> getItems() throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		
		SAXParser saxParser = factory.newSAXParser();
		RssParseHandler handler = new RssParseHandler(this.icon, this.rssItems, this.rssItemAdapter);
		saxParser.parse(rssUrl, handler);
		return handler.getItems();
		
		
	}

}
