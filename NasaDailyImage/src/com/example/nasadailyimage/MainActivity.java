package com.example.nasadailyimage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.handler.IotdHandler;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

	//	protected LinearLayout mainLayout;
	//	protected TextView imageTitle;
	//	protected TextView imageDate;
	//	protected ImageView imageDisplay;
	//	protected TextView imageDesc;
	//	protected ProgressDialog dlgLoad;
	//	protected Bitmap nasaImg;

	protected Bitmap nasaImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		IotdHandler handler = new IotdHandler();
		handler.processFeed();
		resetDisplay(handler.getTitle(),handler.getDate(), handler.getImage(),handler.getDescription());

	}

	private void resetDisplay(String title,String date,Bitmap bitmap,StringBuffer stringBuffer){

		TextView titleView = (TextView) findViewById(R.id.imageTitle);
		titleView.setText(title);

		TextView dataview = (TextView) findViewById(R.id.imageDate);
		dataview.setText(date);

		ImageView imageView = (ImageView) findViewById(R.id.imageDisplay);
		imageView.setImageBitmap(bitmap);

		TextView descriptionView = (TextView) findViewById(R.id.imageDesc);
		descriptionView.setText(stringBuffer);

	}

		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
