package fr.ecp.IS1220.group5.project;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <b>The class that represents an Item</b>
 * It can be created by a <b>Restaurant</b>, and chosen by a <b>Customer</b>.
 *
 * @see Restaurant
 * @see Customer
 */
public class Item  implements Serializable {

	private static final long serialVersionUID = 455302071054673253L;

	private String name;
	private BigDecimal price;
	private ItemCategory itemCategory;
	private ItemType itemType;
	
	
	public Item(String name, BigDecimal price, ItemCategory itemCategory, ItemType itemtype) {
		this.itemCategory = itemCategory;
		this.price = price;
		this.name = name;
		this.itemType = itemType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public ItemCategory getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(ItemCategory itemCategory) {
		this.itemCategory = itemCategory;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	@Override
	public String toString() {
		return "Item{" +
				"name='" + name + '\'' +
				", price=" + price +
				", itemCategory=" + itemCategory +
				", itemType=" + itemType +
				'}';
	}
}
