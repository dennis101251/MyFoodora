package fr.ecp.IS1220.group5.project.util;

import java.io.Serializable;

/**
 * <b>The class that represents a point on a 2-dimensional map.</b>
 *
 * @version 1.0
 */
public class Coordinate implements Serializable{

	private static final long serialVersionUID = -7726249259128496946L;
	
	
	private double x;
	private double y;
	
	public Coordinate(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Coordinate(String x, String y){
		this.x = Double.parseDouble(x);
		this.y = Double.parseDouble(y);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

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
