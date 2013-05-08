package com.metagx.foundation.HUD;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

public abstract class HUDPanel {
	protected final Rect bounds;
	protected final List<HUDItem> hudItems;
	
	public HUDPanel(Rect bounds) {
		this.bounds = bounds;
		this.hudItems = new ArrayList<HUDItem>();
	}
	
	public void addHUDItem(HUDItem obj) {
		this.hudItems.add(obj);
	}
	
	public void removeHUDItem(HUDItem obj) {
		this.hudItems.remove(obj);
	}
	
	public abstract void draw(Canvas c);
	
	public boolean onTouchEvent(float x, float y) {
		for(HUDItem i : hudItems) {
			if(i.getPos().intersect((int)x-10, (int)y-10, (int)x+10, (int)y+10)) {
				i.handleTouchEvent();
				return true;
			}
		}
		return false;
	}
	
}

