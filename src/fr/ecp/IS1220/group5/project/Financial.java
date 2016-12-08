package fr.ecp.IS1220.group5.project;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <b>The class that stores the financial variables</b>
 */
public class Financial implements Serializable {

	private static final long serialVersionUID = 3789197273932947254L;

	public BigDecimal service_fee;
	public BigDecimal markup_percentage;
	public BigDecimal delivery_cost;
	
}
