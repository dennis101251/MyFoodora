package fr.ecp.IS1220.group5.project.menu;

import fr.ecp.IS1220.group5.project.exception.EmptyNameException;
import fr.ecp.IS1220.group5.project.user.Customer;
import fr.ecp.IS1220.group5.project.user.Restaurant;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <b>The class that represents an Item</b>
 * It can be created by a <b>Restaurant</b>, and chosen by a <b>Customer</b>.
 *
 * @see Restaurant
 * @see Customer
 */
public class Item  implements Serializable, Food {

	private static final long serialVersionUID = 455302071054673253L;
	/**
	 * The name of this item.
	 */
	private String name;
	/**
	 * The price of this item.
	 */
	private BigDecimal price = new BigDecimal(0);
	/**
	 * The item category of this item.
	 *
	 * @see ItemCategory
	 */
	private ItemCategory itemCategory;
	/**
	 * the item type of this item.
	 *
	 * @see FoodType
	 */
	private FoodType foodType;

	/**
	 * The constructor of this item.
	 * @param name the name of this item.
	 * @param price the price of this item.
	 * @param itemCategory the item category of this item.
	 * @param foodType the item type of this item.
	 */
	public Item(String name, BigDecimal price, ItemCategory itemCategory, FoodType foodType) throws EmptyNameException {
		this.itemCategory = itemCategory;
		this.price = price;

		if (name.equals("")) {
			throw new EmptyNameException();
		} else {
			this.name = name;
		}

		this.foodType = foodType;
	}

	/**
	 * Returns the name of the item.
	 * @return the name of the item.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of this item.
	 * @param name the new name of this item.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the price of this item.
	 * @return the price of this item.
	 */
	@Override
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * Sets the price of this item.
	 * @param price the new price of this item.
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * Returns the item category of this item.
	 * @return the item category of this item.
	 */
	public ItemCategory getItemCategory() {
		return itemCategory;
	}

	/**
	 * Sets the item category of this item.
	 * @param itemCategory the new item category.
	 */
	public void setItemCategory(ItemCategory itemCategory) {
		this.itemCategory = itemCategory;
	}

	/**
	 * Returns the item type of this item.
	 * @return the item type of this item.
	 */
	public FoodType getFoodType() {
		return foodType;
	}

	/**
	 * Sets the item type of this item.
	 * @param foodType the new item type of this item.
	 */
	public void setFoodType(FoodType foodType) {
		this.foodType = foodType;
	}

	@Override
	public String toString() {
		return "Item{" +
				"name='" + name + '\'' +
				", price=" + price +
				", itemCategory=" + itemCategory +
				", foodType=" + foodType +
				'}';
	}
}
