package edu.luc.etl.cs313.android.shapes.model;

/**
 * A decorator for specifying the stroke (foreground) color for drawing the
 * shape.
 * This is an interesting change
 */
public class Stroke implements Shape {

	protected Shape shape; //Why not use final on these two lines?
	protected int color;

	public Stroke(final int color, final Shape shape) {
		this.shape = shape;
	}

	public int getColor() {
		return color;
	}

	public Shape getShape() {
		return shape;
	}

	@Override
	public <Result> Result accept(Visitor<Result> v) {
		return null;
	}
}
