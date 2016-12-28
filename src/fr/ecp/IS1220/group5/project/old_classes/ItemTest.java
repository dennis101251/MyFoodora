package fr.ecp.IS1220.group5.project.old_classes;

import java.math.BigDecimal;

import fr.ecp.IS1220.group5.project.exception.EmptyNameException;
import fr.ecp.IS1220.group5.project.menu.Item;
import fr.ecp.IS1220.group5.project.menu.ItemCategory;
import fr.ecp.IS1220.group5.project.menu.ItemType;
import org.junit.Test;

public class ItemTest {

	@Test
	public void test() {
		Item mushroomSoup = null;
		try {
			mushroomSoup = new Item("Mushroom Soup", new BigDecimal(2.50), ItemCategory.Starter, ItemType.Vegetarian);
		} catch (EmptyNameException e) {
			e.printStackTrace();
		}
		System.out.println(mushroomSoup);
	}

}
