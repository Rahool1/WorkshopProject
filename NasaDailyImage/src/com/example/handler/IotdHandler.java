package com.example.handler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class IotdHandler extends DefaultHandler {

	private String url = "http://www.nasa.gov/rss/image_of_the_day.rss";
	private boolean inUrl = false;
	private boolean inTitle = false;
	private boolean inDescription = false;
	private boolean inItem = false;
	private boolean inDate = false;
	private Bitmap image = null;
	public Bitmap getImage() {
		return image;
	}

	public String getTitle() {
		return title;
	}

	public StringBuffer getDescription() {
		return description;
	}

	public String getDate() {
		return date;
	}

	private String title = null;
	private StringBuffer description = new StringBuffer();
	private String date = null;

	public void processFeed(){

		try {
			// Configuring the reader and parser
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			XMLReader reader = parser.getXMLReader();
			reader.setContentHandler(this);

			//Make an input stream from the feed URl
			InputStream inputstream = new URL(url).openStream();
			reader.parse(new InputSource(inputstream)); //start the parsing

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Bitmap getBitmap(String url){

		try {

			HttpURLConnection connection =
					(HttpURLConnection) new URL(url).openConnection();
			connection.setDoInput(true);
			connection.connect();

			InputStream input = connection.getInputStream();
			Bitmap bitmap = BitmapFactory.decodeStream(input);
			input.close();

			return bitmap;

		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		if (localName.equals("url")) {
			inUrl = true;
		}else{
			inUrl = false;
		}

		if (localName.startsWith("item")) {
			inItem = true;
		} else {
			if (inItem) {
				if (localName.equals("title")) {
					inTitle = true;
				} else {
					inTitle = false;
				}

				if (localName.equals("description")) {
					inDescription = true;
				} else {
					inDescription = false;
				}

				if (localName.equals("pubDate")) {
					inDate = true;
				} else {
					inDate = false;
				}
			}
		}
	}
	
	@Override
	public void characters(char ch[], int start, int length) {
		String chars = (new String(ch).substring(start, start + length));

		if (inDescription) {
			description.append(chars);
		}
		if (inTitle) {
			title = chars;
		}

		if (inDate && date == null) {
			// Tuesday 02 September 2014 09:30 PM
			String rawDate = chars;
			try {
				SimpleDateFormat parseFormat = new SimpleDateFormat(
						"EEE, dd MMM yyyy HH:mm:ss");
				Date sourceDate = parseFormat.parse(rawDate);

				SimpleDateFormat outputFormat = new SimpleDateFormat(
						"EEE, dd MMM yyyy");
				date = outputFormat.format(sourceDate);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}



}
