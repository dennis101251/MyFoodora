package fr.ecp.IS1220.group5.project.util;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <b>The class that stores the financial variables</b>
 */
public class Financial implements Serializable {

	private static final long serialVersionUID = 3789197273932947254L;
	/**
	 * The service fee of MyFoodora's system.
	 */
	public BigDecimal service_fee;
	/**
	 * The markup percentage of MyFoodora's system.
	 */
	public BigDecimal markup_percentage;
	/**
	 * The delivery cost of MyFoodora's system.
	 */
	public BigDecimal delivery_cost;

	/**
	 * the profit policy
	 */
	public int profitPolicy;
}
