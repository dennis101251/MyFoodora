package fr.ecp.IS1220.group5.project;

import java.io.*;
import java.util.ArrayList;
import java.util.Observable;

/**
 * <b>The class that stores the list of registerd users.</b>
 */
public class Userlist extends Observable{
    private ArrayList<User> users =  new ArrayList<>();

    //Editing my own
    public static String usersPath = "tmp/users.ser";


    public ArrayList<User> getUsers(){
        return users;
    }

    public void saveAndNotify(){
        saveUsers();
        this.setChanged();
        notifyObservers();
    }

    public void saveUsers(){
        try {
            FileOutputStream fileOut = new FileOutputStream(usersPath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(users);

            out.close();
            fileOut.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void retrieveUsers(){

        File file = new File(usersPath);

        if (file.exists()){
            try {
                FileInputStream fileIn = new FileInputStream(usersPath);
                ObjectInputStream in = new ObjectInputStream(fileIn);

                users = (ArrayList<User>) in.readObject();

                in.close();
                fileIn.close();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("There is no User in myFoodora");
        }
    }

    public void addUser(User user){
        users.add(user);
        saveAndNotify();
    }

    public void removeUser(User user) throws UserNotFoundException{
        users.remove(user);
        saveAndNotify();
    }

    public void activateUser(User user) throws UserNotFoundException{
        if (users.contains(user)){
            users.get(users.lastIndexOf(user)).status = true;
        }
        else {
            throw new UserNotFoundException();
        }
        saveAndNotify();
    }

    public void disactivateUser(User user) throws UserNotFoundException {
        if (users.contains(user)){
            users.get(users.lastIndexOf(user)).status = false;
        }
        else {
            throw new UserNotFoundException();
        }
        saveAndNotify();
    }

    @Override
    public String toString() {
        String str = new String();
        for (User user: users
             ) {
            str += user.toString() + "\n";
        }
        return str;
    }
}
