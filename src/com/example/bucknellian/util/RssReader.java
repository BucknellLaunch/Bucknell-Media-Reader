package com.example.bucknellian.util;

import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.example.bucknellian.data.RssItem;

public class RssReader {
	
	private String rssUrl;
	private String icon;
	
	public RssReader(String rssUrl, String icon) {
		this.rssUrl = rssUrl;
		this.icon = icon;
	}
	
	public List<RssItem> getItems() throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		
		SAXParser saxParser = factory.newSAXParser();
		RssParseHandler handler = new RssParseHandler(this.icon);
		saxParser.parse(rssUrl, handler);
		return handler.getItems();
		
		
	}

}
