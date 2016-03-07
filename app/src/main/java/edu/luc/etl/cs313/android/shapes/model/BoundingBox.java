package edu.luc.etl.cs313.android.shapes.model;

/**
 * A shape visitor for calculating the bounding box, that is, the smallest
 * rectangle containing the shape. The resulting bounding box is returned as a
 * rectangle at a specific location.
 */
public class BoundingBox implements Visitor<Location> {

	// TODO entirely your job (except onCircle)

	@Override
	public Location onCircle(final Circle c) {
		final int radius = c.getRadius();
		return new Location(-radius, -radius, new Rectangle(2 * radius, 2 * radius));
	}

	@Override
	//unconfirmed
	public Location onFill(final Fill f) {
		return f.getShape().accept(this);
	}

	@Override
	public Location onGroup(final Group g) {
		//This needs to figure out mins and maxes among all of the children in the group
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;
		for (Shape s:g.getShapes())
		{
			Location loc = s.accept(this);
			int x = loc.getX();
			int y = loc.getY();
			Rectangle r = (Rectangle) loc.getShape();
			int width = r.getWidth();
			int height = r.getHeight();
			if (x < minX) { minX = x; }
			if (y < minY) { minY = y; }
			if (x + width > maxX) { maxX = x + width; }
			if (y + height > maxY) { maxY = y + height; }
		}
		return new Location(minX, minY, new Rectangle(maxX-minX, maxY-minY));
	}

	@Override
	public Location onLocation(final Location l) {
		final int x = l.getX();
		final int y = l.getY();
		return new Location(x, y, new Rectangle(x, y));
	}

	@Override
	public Location onRectangle(final Rectangle r) {
		final int width = r.getWidth();
		final int height = r.getHeight();
		return new Location(0,0, new Rectangle(width,height));
	}

	@Override
	//unconfirmed
	public Location onStroke(final Stroke c) {
		return c.getShape().accept(this);
	}

	@Override
	//unconfirmed
	public Location onOutline(final Outline o) {
		return o.getShape().accept(this);
	}

	@Override
	//unconfirmed
	public Location onPolygon(final Polygon s) {
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;
		for (Point pt:s.getPoints())
		{
			Location loc = pt.accept(this);
			int x = loc.getX();
			int y = loc.getY();
			if (x < minX) { minX = x; }
			if (y < minY) { minY = y; }
			if (x > maxX) { maxX = x; }
			if (y > maxY) { maxY = y; }
		}
		return new Location(minX, minY, new Rectangle(maxX-minX, maxY-minY));
	}
}
