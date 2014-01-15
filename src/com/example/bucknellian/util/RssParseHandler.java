package com.example.bucknellian.util;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import com.example.bucknellian.data.RssItem;

public class RssParseHandler extends DefaultHandler {
	
    private List<RssItem> rssItems;
    // We have a local reference to an object which is constructed while parser is working on an item tag
    // Used to reference item while parsing
    private RssItem currentItem;
    // We have two indicators which are used to differentiate whether a tag title or link is being processed by the parser
    // Parsing title indicator
    private boolean parsingTitle;
    // Parsing link indicator
    private boolean parsingLink;
    
    public RssParseHandler() {
        rssItems = new ArrayList<RssItem>();
    }
    
    // We have an access method which returns a list of items that are read from the RSS feed. This method will be called when parsing is done.
    public List<RssItem> getItems() {
        return rssItems;
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if ("item".equals(qName)) {
            currentItem = new RssItem();
        } else if ("title".equals(qName)) {
            parsingTitle = true;
        } else if ("link".equals(qName)) {
            parsingLink = true;
        }
    }
    
    // The EndElement method adds the  current RssItem to the list when a closing item tag is processed. It sets appropriate indicators to false -  when title and link closing tags are processed
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("item".equals(qName)) {
            rssItems.add(currentItem);
            currentItem = null;
        } else if ("title".equals(qName)) {
            parsingTitle = false;
        } else if ("link".equals(qName)) {
            parsingLink = false;
        }
    }
    
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (parsingTitle) {
            if (currentItem != null)
                currentItem.setTitle(new String(ch, start, length));
        } else if (parsingLink) {
            if (currentItem != null) {
                currentItem.setLink(new String(ch, start, length));
                parsingLink = false;
            }
        }
    }
	
}
