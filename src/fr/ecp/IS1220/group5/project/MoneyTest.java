package fr.ecp.IS1220.group5.project;

/**
 * Created by Alexandre on 16/11/2016.
 */
public class MoneyTest {

    public static void main(String[] args) {
        Money price = new Money("2.52");
        System.out.println(price);
        Money price2 = new Money("5");
        price = price.add(price2);
        System.out.println(price);


    }

}
