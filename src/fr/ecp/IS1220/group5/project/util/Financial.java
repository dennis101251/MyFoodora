package fr.ecp.IS1220.group5.project.util;

import java.io.File;
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

	/**
	 * The target profit
	 */
	public BigDecimal target_profit;

	public static void deleteFinancial(){
		File file = new File("src/tmp/financial.ser");

		if (file.exists()) {
			file.delete();
			System.out.println("financial file has been formatted");
		} else {
			System.out.println(">> There is no financial in system");
		}
	}
}
