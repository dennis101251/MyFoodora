package fr.ecp.IS1220.group5.project.util;

import java.io.Serializable;

/**
 * <b>The class that generates a unique ID</b>
 * It uses the <i>singelton</i> pattern.
 *
 * @version 1.0
 */
public class IDGenerator implements Serializable{

    private static final long serialVersionUID = 3250207107664573253L;

    private static IDGenerator instance = new IDGenerator();
    private int id;

    private IDGenerator() {}

    public static synchronized IDGenerator getInstance() {
        return instance;
    }

    private Object readResolve() {
        return instance;
    }

    public int getNextID(){
        return id++;
    }

}
