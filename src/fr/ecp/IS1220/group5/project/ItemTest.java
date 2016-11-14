package fr.ecp.IS1220.group5.project;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

public class ItemTest {

	@Test
	public void test() {
		Item mushroomSoup = new Item("Mushroom Soup", new Money(2.50), ItemCategory.Starter, ItemType.Vegetarian);
		System.out.println(mushroomSoup);
	}

}
