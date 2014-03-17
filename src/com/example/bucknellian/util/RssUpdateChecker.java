package com.example.bucknellian.util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.os.AsyncTask;

import com.example.bucknellian.data.RssItem;
import com.example.bucknellian.data.SortedArrayList;

public class RssUpdateChecker extends AsyncTask<String, Void, Void>{
	private String[] urls;
	private SortedArrayList<RssItem> rssItems;
	// an inner Handler class to handle RSS data.
	private class RssUpdateHandler extends DefaultHandler{
		private int numOfItemParsed;
		
		public RssUpdateHandler(){
			this.numOfItemParsed = 0;
		}
		
		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {

			
		}
		
		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {

				
		}
		
		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			
		}
	}
	
	
	
	
	public RssUpdateChecker(String[] urls, SortedArrayList<RssItem> rssItems) {
		this.urls = urls;
		this.rssItems = rssItems;
	}




	@Override
	protected Void doInBackground(String... params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {

	}
}
