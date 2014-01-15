package com.example.bucknellian.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.bucknellian.R;

public class PaperPadItemView extends TextView {
	private Paint marginPaint;
	private Paint linePaint;
	private int paperColor;
	private float margin;

	public PaperPadItemView(Context context, AttributeSet ats, int ds) {
		super(context, ats, ds);
		init();
	}

	public PaperPadItemView(Context context) {
		super(context);
		init();
	}

	public PaperPadItemView(Context context, AttributeSet ats) {
		super(context, ats);
		init();
	}

	private void init() {

		// Get a reference to our resource table.
		Resources myResources = getResources();
		// Create the paint brushes we will use in the onDraw method.
		marginPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		marginPaint.setColor(myResources.getColor(R.color.notepad_margin));
		linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		linePaint.setColor(myResources.getColor(R.color.notepad_lines));
		// Get the paper background color and the margin width.
		paperColor = myResources.getColor(R.color.notepad_paper);
		margin = myResources.getDimension(R.dimen.notepad_margin);
	}

	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawColor(paperColor);
		canvas.drawLine(0, 0, 0, getMeasuredHeight(), linePaint);
		canvas.drawLine(0, getMeasuredHeight(), getMeasuredWidth(),
				getMeasuredHeight(), linePaint);
		canvas.drawLine(margin, 0, margin, getMeasuredHeight(), marginPaint);

		canvas.save();
		canvas.translate(margin, 0);
		super.onDraw(canvas);

		canvas.restore();

	}

}
