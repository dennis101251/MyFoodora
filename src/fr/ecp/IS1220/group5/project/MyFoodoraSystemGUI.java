package fr.ecp.IS1220.group5.project;

/**
 * Created by dennis101251 on 2016/12/19.
 */
public class MyFoodoraSystemGUI extends MyFoodoraSystem{

    private static MyFoodoraSystemGUI myFoodoraSystemGUI;
    private MyFoodoraSystemGUI(){
        super();
    }

    public static MyFoodoraSystemGUI getInstance(){
        if (myFoodoraSystemGUI == null){
            myFoodoraSystemGUI = new MyFoodoraSystemGUI();
            return myFoodoraSystemGUI;
        }
        else {
            return myFoodoraSystemGUI;
        }
    }
}
