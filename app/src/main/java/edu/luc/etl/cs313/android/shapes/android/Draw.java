package edu.luc.etl.cs313.android.shapes.android;

import java.util.List;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import edu.luc.etl.cs313.android.shapes.model.*;

/**
 * A Visitor for drawing a shape to an Android canvas.
 */
public class Draw implements Visitor<Void> {

	// TODO entirely your job (except onCircle)

	private final Canvas canvas;

	private final Paint paint;

	public Draw(final Canvas canvas, final Paint paint) {
		this.canvas = canvas; // FIXED
		this.paint = paint; // FIXED
		paint.setStyle(Style.STROKE);
	}

	@Override
	public Void onCircle(final Circle c) {
		canvas.drawCircle(0, 0, c.getRadius(), paint);
		return null;
	}

	@Override
	public Void onStroke(final Stroke c) {
		paint.setStyle(Style.STROKE);
		paint.setColor(c.getColor());
		c.getShape().accept(this);
		return null;
	}

	@Override
	public Void onFill(final Fill f) {
		paint.setStyle(Style.FILL);
		f.getShape().accept(this);
		return null;
	}

	@Override
	public Void onGroup(final Group g) {
		for (Shape shape:g.getShapes()) {
			shape.accept(this);
		}
		return null;
	}

	@Override
	public Void onLocation(final Location l) {
		canvas.translate(l.getX(), l.getY());
		l.getShape().accept(this);
		canvas.translate(-l.getX(), -l.getY());
		return null;
	}


	@Override
	public Void onRectangle(final Rectangle r) {
		canvas.drawRect(0, 0, r.getWidth(), r.getHeight(), paint);
		return null;
	}

	@Override
	public Void onOutline(Outline o) {
		paint.setStyle(Style.STROKE);
		o.getShape().accept(this);
		return null;
	}

	@Override
	public Void onPolygon(final Polygon s) {
		List<? extends Point> points = s.getPoints();
		final float[] pts = new float[2 * points.size() + 2];
		int counter = 0;
		for (int i = 0; i < points.size(); i++) {
			pts[counter] = points.get(i).getX();
			counter++;
			pts[counter] = points.get(i).getY();
			counter++;
		}
		pts[counter] = points.get(0).getX();
		counter++;
		pts[counter] = points.get(0).getY();

		canvas.drawLines(pts, paint);
		return null;
	}
}