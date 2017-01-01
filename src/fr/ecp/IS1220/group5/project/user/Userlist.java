package fr.ecp.IS1220.group5.project.user;

import fr.ecp.IS1220.group5.project.exception.UserNotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.Observable;

/**
 * <b>The class that stores the list of registerd users.</b>
 */
public class Userlist extends Observable{


    private ArrayList<User> users =  new ArrayList<>();

    //Editing my own
    public static String usersPath = "src/tmp/users.ser";

    /**
     * Returns the registered Users of the system
     * @return the list of registered users.
     */
    public ArrayList<User> getUsers(){
        return users;
    }

    public void saveAndNotify(){
        saveUsers();
        this.setChanged();
        notifyObservers();
    }

    /**
     * Saves the list of registered Users in a .ser file.
     */
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

    /**
     * Retrieves the list of registered Users from a .ser file.
     */
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
            System.out.println(">> There is no User in myFoodora");
        }
    }

    /**
     * provide a static method to delete the userlist file when we do the test
     */
    public static void delateUserFile(){
        File file = new File(usersPath);

        if (file.exists()){
            file.delete();
            System.out.println("user file has been formatted");
        }
        else {
            System.out.println(">> There is no User in myFoodora");
        }
    }

    /**
     * Adds a user to the list of registered users.
     * @param user the user to add to the list.
     */
    public void addUser(User user){
        users.add(user);
        saveAndNotify();
    }

    /**
     * Removes a user from the list of registered users.
     * @param user the user to remove from the list.
     * @throws UserNotFoundException
     */
    public void removeUser(User user) throws UserNotFoundException {
        users.remove(user);
        saveAndNotify();
    }

    /**
     * Activates a given user.
     * @param user the user to activate.
     * @throws UserNotFoundException
     */
    public void activateUser(User user) throws UserNotFoundException{
        if (users.contains(user)){
            users.get(users.lastIndexOf(user)).status = true;
        }
        else {
            throw new UserNotFoundException();
        }
        saveAndNotify();
    }

    /**
     * Disactivates a given user.
     * @param user the user to disactivate.
     * @throws UserNotFoundException
     */
    public void disactivateUser(User user) throws UserNotFoundException {
        if (users.contains(user)){
            users.get(users.lastIndexOf(user)).status = false;
        }
        else {
            throw new UserNotFoundException();
        }
        saveAndNotify();
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    /**
     * update the state of one user who already exists in the system
     *
     * @param user the user you want to update
     * @throws UserNotFoundException
     */
    public void updateUser(User user) throws UserNotFoundException {
        removeUser(user);
        addUser(user);
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
