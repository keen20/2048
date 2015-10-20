package com.ken.game2048;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
//card类是可以上下左右运动的，所以继承FrameLayout
public class Card extends FrameLayout {
	private int num = 0;
	private TextView label;
	private LayoutParams lp;
	private View background;
	public Card(Context context) {
		super(context);
		label = new TextView(getContext());
		label.setTextSize(26);
		background = new View(getContext());
		background.setBackgroundColor(0x33ffffff);
		label.setGravity(Gravity.CENTER);
		lp = new LayoutParams(-1,-1);
		lp.setMargins(15, 15, 0, 0);
		addView(label,lp);
		setNum(0);
	}
	public int getNum() {
		return num;
	}
	public TextView getLabel() {
		return label;
	}
	public boolean equals(Card obj) {
		return getNum()==obj.getNum();
	}
	public void setNum(int num) {
		this.num = num;
		if(num<=0){
			label.setText("");
		}else{
			label.setText(num+"");
		}
		switch (num) {
		case 0:
			label.setBackgroundColor(0x00000000);
			break;
		case 2:
			label.setBackgroundColor(0xffeee4da);
			break;
		case 4:
			label.setBackgroundColor(0xffede0c8);
			break;
		case 8:
			label.setBackgroundColor(0xfff2b179);
			break;
		case 16:
			label.setBackgroundColor(0xfff59563);
			break;
		case 32:
			label.setBackgroundColor(0xfff67c5f);
			break;
		case 64:
			label.setBackgroundColor(0xfff65e3b);
			break;
		case 128:
			label.setBackgroundColor(0xffedcf72);
			break;
		case 256:
			label.setBackgroundColor(0xffedcc61);
			break;
		case 512:
			label.setBackgroundColor(0xffedc850);
			break;
		case 1024:
			label.setBackgroundColor(0xffedc53f);
			break;
		case 2048:
			label.setBackgroundColor(0xffedc22e);
			break;
		default:
			label.setBackgroundColor(0xff3c3a32);
			break;
		}
	}

}