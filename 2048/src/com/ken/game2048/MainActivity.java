package com.ken.game2048;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView tv;
	private int score = 0;
	private static MainActivity mainActivity = null;
	public MainActivity() {
		mainActivity = this;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView) findViewById(R.id.TvScore);
	}
	public static MainActivity getMainActivity(){
		return mainActivity;
	}
	public void clearScore(){
		score = 0;
		showScore();
	}
	public void showScore(){
		tv.setText(score+"");
	}
	public void addScore(int s){
		score+=s;
		showScore();
	}
}
