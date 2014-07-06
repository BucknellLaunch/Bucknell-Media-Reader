/**
 * 
 */
package sync;

import java.util.ArrayList;

import models.RssItem;
import models.SortedArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Base RssHandler class.
 * 
 * @author Li Li
 *
 */
public class RssHandler extends DefaultHandler {
	protected SortedArrayList<RssItem> rssItems;
	protected RssItem currentItem;
	protected boolean parsingTitle;
	protected boolean parsingLink;
	protected boolean parsingPubDate;
	protected boolean parsingCategory;
	protected String icon;
	
	public RssHandler(){
		super();
		this.rssItems = new SortedArrayList<RssItem>();
	}

	
	public void setIcon(String icon){
		this.icon = icon;
	}
	
	public SortedArrayList<RssItem> getItems() {
		return this.rssItems;
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if ("item".equals(qName)) {
			currentItem.setIcon(icon);
			rssItems.insertSorted(currentItem);
		} else if ("title".equals(qName)) {
			parsingTitle = false;
		} else if ("link".equals(qName)) {
			parsingLink = false;
		} else if ("pubDate".equals(qName)) {
			parsingPubDate = false;
		} else if ("category".equals(qName)) {
			parsingCategory = false;
		}
	}
	
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if ("item".equals(qName)) {
			currentItem = new RssItem();
		} else if ("title".equals(qName)) {
			parsingTitle = true;
		} else if ("link".equals(qName)) {
			parsingLink = true;
		} else if ("pubDate".equals(qName)) {
			parsingPubDate = true;
		} else if ("category".equals(qName)) {
			parsingCategory = true;
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (parsingTitle) {
			if (currentItem != null)
				currentItem.setTitle(new String(ch, start, length));
		} else if (parsingLink) {
			if (currentItem != null) {
				currentItem.setLink(new String(ch, start, length));
				parsingLink = false;
			}
		} else if (parsingPubDate) {
			if (currentItem != null) {
				currentItem.setPubDate(new String(ch, start, length));
				parsingPubDate = false;
			}
		} else if (parsingCategory) {
			if (currentItem != null) {
				currentItem.setCategory(new String(ch, start, length));
				parsingCategory = false;
			}
		}
	}
}
