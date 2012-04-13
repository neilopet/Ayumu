package com.pangaeaapps.ayumu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class AyumuActivity extends Activity {
	
	private static String TAG = "Ayumu";
	private int[] images = {R.drawable.num1, R.drawable.num2, R.drawable.num3, R.drawable.num4, R.drawable.num5, R.drawable.num6, R.drawable.num7, R.drawable.num8, R.drawable.num9, R.drawable.num10};
	private static int imgW = 80;
	private static int imgH = 80;
	private int order = 1;
	private int views = 1;
	private Bitmap blank;
	
	private RelativeLayout primary;
	private View touchScreen;
	private int x, y;
	private HashMap<Integer, ImageView> imageViews = new HashMap<Integer, ImageView>(images.length);
	private ArrayList<Point> points = new ArrayList<Point>(images.length);
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        primary = (RelativeLayout) findViewById(R.id.primary);
        
        blank = BitmapFactory.decodeResource(getResources(), R.drawable.blank);
        
        long seed = System.nanoTime();
        ArrayList<Point> grid = createPoints();
        Point tmpPoint = new Point(0, 0);
        
    	for (int res : images) {
    		seed = System.nanoTime() + new Random().nextInt();
    		Collections.shuffle(grid, new Random(seed));
    		tmpPoint = grid.get(0);
    		points.add(tmpPoint);
        	addImage(tmpPoint.x, tmpPoint.y, res);
        	grid.remove(0);
        }
        
        touchScreen = findViewById(R.id.touchScreen);
        touchScreen.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				x = (int)Math.round(event.getX());
				y = (int)Math.round(event.getY());
				int offset = 1;
				for (Point p : points) {
					if (x >= p.x && x <= (p.x + imgW) &&
							y >= p.y && y <= (p.y + imgH))  {
						if (offset == order) {
							if (offset == 1) {
								coverImages();
							}
							imageViews.get(offset).setVisibility(View.INVISIBLE);
							
							if (offset == images.length) {
								// winner
								long completionTime = System.currentTimeMillis() - getIntent().getLongExtra("startTime", 0);
								Intent winner = new Intent(getBaseContext(), WinActivity.class);
								winner.putExtra("completionTime", completionTime);
								startActivity(winner);
								finish();
							}
							
							order++;
						}
						else {
							Log.d(TAG, "You lose!");
							startActivity(new Intent(getBaseContext(), YouLoseActivity.class));
							finish();
						}
						Log.d(TAG, "Touching " + offset);
					}
					offset++;
				}
								
				return false;
			}
			
		});
        
    }
    
    public void addImage( int x, int y, int res ) {
    	RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(imgW, imgH);
        params.setMargins(x, y, 0, 0);
        
        ImageView img = new ImageView(AyumuActivity.this);
        Bitmap image = BitmapFactory.decodeResource(getResources(), res);
        img.setImageBitmap(image);
        
        imageViews.put(views, img);
        views++;
        
        Log.d(TAG, "Added " + res + " at " + x + ", " + y);
        
        primary.addView(img, params);
    }
    
    public void coverImages() {
		for (int i = 1; i <= imageViews.size(); i++) {
			imageViews.get(i).setImageBitmap(blank);
		}
    }
    
    public ArrayList<Point> createPoints() {
    	ArrayList<Point> pts = new ArrayList<Point>(((800 - imgW) / imgW) * ((480 - imgH) / imgH));
    	for (int w = 0; w < (800 - imgW); w += imgW) {
    		for (int h = 0; h < (480 - imgH); h += imgH) {
    			pts.add(new Point(h, w));
    		}
    	}
    	return pts;
    }
}