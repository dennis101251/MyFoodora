package fr.ecp.IS1220.group5.project.menu;

import java.math.BigDecimal;

import fr.ecp.IS1220.group5.project.menu.Item;
import fr.ecp.IS1220.group5.project.menu.ItemCategory;
import fr.ecp.IS1220.group5.project.menu.ItemType;
import org.junit.Test;

public class ItemTest {

	@Test
	public void test() {
		Item mushroomSoup = new Item("Mushroom Soup", new BigDecimal(2.50), ItemCategory.Starter, ItemType.Vegetarian);
		System.out.println(mushroomSoup);
	}

}
