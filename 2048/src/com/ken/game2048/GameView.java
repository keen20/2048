package com.ken.game2048;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

/*
 * 1,init方法初始化GridLayout界面
 * 2,onSizeChanged初始化时addCards方法给GridLayout界面添加4*4个卡片
 * 3,startGame开始生成随机数
 */
//让该GameView类绑定activity_main.xml文件，将类全路径放置在布局上
public class GameView extends GridLayout {

	// 避免错误，添加三个构造方法
	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initGameView();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initGameView();
	}

	public GameView(Context context) {
		super(context);
		initGameView();
	}

	private void initGameView() {
		setColumnCount(4);
		setBackgroundColor(0xffbbada0);
		setOnTouchListener(new View.OnTouchListener() {
			private float startX, startY, offsetX, offsetY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = event.getX();
					startY = event.getY();
					break;
				case MotionEvent.ACTION_UP:
					offsetX = event.getX() - startX;
					offsetY = event.getY() - startY;
					if (Math.abs(offsetX) > Math.abs(offsetY)) {
						if (offsetX < -5) {
							moveLeft();
						} else if (offsetX > 5) {
							moveRight();
						}
					} else {
						if (offsetY < -5) {
							moveUp();
						} else if (offsetY > 5) {
							moveDown();
						}
					}
					break;
				}
				return true;
			}
		});
	}

	// 动态根据手机屏幕计算卡片的宽高
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		int cardWidth = (Math.min(w, h) - 10) / 4;
		addCards(cardWidth, cardWidth);
		startGame();
	}

	private void startGame() {
		MainActivity.getMainActivity().clearScore();
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				cardsMap[x][y].setNum(0);
			}
		}
		addRandomNum();
		addRandomNum();
	}

	private Card[][] cardsMap = new Card[4][4];

	private void addCards(int cardWidth, int cardWidth2) {
		Card c;
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				c = new Card(getContext());
				c.setNum(0);
				addView(c, cardWidth, cardWidth);
				cardsMap[x][y] = c;
			}
		}
	}

	private List<Point> emptyPoint = new ArrayList<Point>();

	private void addRandomNum() {
		emptyPoint.clear();
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if (cardsMap[x][y].getNum() <= 0) {
					emptyPoint.add(new Point(x, y));
				}
			}
		}
		Point p = emptyPoint.remove((int) (Math.random() * emptyPoint.size()));
		cardsMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);
	}

	private void moveLeft() {
		boolean merge = false;
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				for (int x1 = x + 1; x1 < 4; x1++) {
					if (cardsMap[x1][y].getNum() > 0) {

						if (cardsMap[x][y].getNum() <= 0) {
							cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
							cardsMap[x1][y].setNum(0);
							merge = true;
							x--;
						} else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
							cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
							MainActivity.getMainActivity().addScore(cardsMap[x1][y].getNum());
							cardsMap[x1][y].setNum(0);
							merge = true;
						}
						break;
					}
				}
			}
		}
		if(merge){
			addRandomNum();checkFail();
		}
	}

	private void moveRight() {
		boolean merge = false;
		for (int y = 0; y < 4; y++) {
			for (int x = 3; x >= 0; x--) {
				for (int x1 = x - 1; x1 >= 0; x1--) {
					if (cardsMap[x1][y].getNum() > 0) {
						if (cardsMap[x][y].getNum() <= 0) {
							cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
							cardsMap[x1][y].setNum(0);
							x++;
							merge = true;
						} else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
							cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
							MainActivity.getMainActivity().addScore(cardsMap[x1][y].getNum());
							cardsMap[x1][y].setNum(0);
							merge = true;
						}
						break;
					}
				}
			}
		}
		if(merge){
			addRandomNum();checkFail();
		}
	}

	private void moveUp() {
		boolean merge = false;
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				for (int y1 = y + 1; y1 < 4; y1++) {// 只操作一下就会break出去，而不是将后面的都执行一遍
					if (cardsMap[x][y1].getNum() > 0) {
						if (cardsMap[x][y].getNum() <= 0) {
							cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
							cardsMap[x][y1].setNum(0);
							y--;// 防止后面两个一样的卡片不合并
							merge = true;
						} else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
							cardsMap[x][y].setNum(cardsMap[x][y1].getNum() * 2);
							MainActivity.getMainActivity().addScore(cardsMap[x][y1].getNum());
							cardsMap[x][y1].setNum(0);
							merge = true;
						}
						break;
					}
				}
			}
		}
		if(merge){
			addRandomNum();checkFail();
		}
	}
	private void moveDown() {
		boolean merge = false;
		for (int x = 0; x < 4; x++) {
			for (int y = 3; y >= 0; y--) {
				for (int y1 = y - 1; y1 >= 0; y1--) {// 只操作一下就会break出去，而不是将后面的都执行一遍
					if (cardsMap[x][y1].getNum() > 0) {
						if (cardsMap[x][y].getNum() <= 0) {
							cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
							cardsMap[x][y1].setNum(0);
							y++;// 防止后面两个一样的卡片不合并
							merge = true;
						} else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
							cardsMap[x][y].setNum(cardsMap[x][y1].getNum() * 2);
							MainActivity.getMainActivity().addScore(cardsMap[x][y1].getNum());
							cardsMap[x][y1].setNum(0);
							merge = true;
						}
						break;
					}
				}
			}
		}
		if(merge){
			addRandomNum();
			checkFail();
		}
	}
	
	private void checkFail(){
		//下面只要满足一个就没完
		boolean  fail = true;
		ALL:
		for(int x = 0;x < 4; x++){
			for(int y = 0;y < 4;y++){
				if(cardsMap[x][y].getNum()==0
						||x>0&&cardsMap[x][y].equals(cardsMap[x-1][y])
						||x<3&&cardsMap[x][y].equals(cardsMap[x+1][y])
						||y>0&&cardsMap[x][y].equals(cardsMap[x][y-1])
						||y<3&&cardsMap[x][y].equals(cardsMap[x][y+1])){
					fail = false;
					break ALL;
				}
			}
		}
		
		if(fail){
			new AlertDialog.Builder(getContext()).setTitle("提示").setMessage("游戏结束")
			.setNegativeButton("退出游戏", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					System.exit(0);
				}
			}).setPositiveButton("重新开始", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					startGame();
				}
			}).show();
	}
	}
}
