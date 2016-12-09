package fr.ecp.IS1220.group5.project.util;

import java.io.Serializable;

/**
 * <b>The class that represents a point on a 2-dimensional map.</b>
 *
 * @version 1.0
 */
public class Coordinate implements Serializable{

	private static final long serialVersionUID = -7726249259128496946L;

	/**
	 * the x-coordinate of the point.
	 */
	private double x;
	/**
	 * the y-coordinate of the point.
	 */
	private double y;

	/**
	 * the constructor of the coordinate point
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 */
	public Coordinate(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the x-coordinate of the point.
	 * @return the x-coordinate of the point.
	 */
	public double getX() {
		return x;
	}

	/**
	 * Sets the x-coordinate of the point to a given value.
	 * @param x the new x-ccordinate.
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Returns the y-coordinate of the point.
	 * @return the y-coordinate of the point.
	 */
	public double getY() {
		return y;
	}

	/**
	 * Sets the y-coordinate ot the point to a given value.
	 * @param y the new y-coordinate.
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Retruns the euclidian distance between two points.
	 * @param a the first coordinate
	 * @param b the second coordinate
	 * @return the distance between a and b.
	 */
	public static Double getDistance(Coordinate a, Coordinate b){
		return Math.sqrt(Math.pow(a.getX()-b.getX(),2)+Math.pow(a.getY()-b.getY(),2));
	}

	@Override
	public String toString() {
		return "Coordinate [x=" + x + ", y=" + y + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}
	
	
	
	
}
