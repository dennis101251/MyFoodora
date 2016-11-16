package fr.ecp.IS1220.group5.project;

import java.io.*;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by dennis101251 on 2016/11/15.
 */
public class Userlist extends Observable{
    private ArrayList<User> users =  new ArrayList<>();


//    public Userlist(ArrayList<User> users) {
//        this.users = users;
////        retrieveUsers();
//    }

    public ArrayList<User> getUsers(){
        return users;
    }

    public void saveAndNotify(){
        saveUsers();
        this.setChanged();
        notifyObservers();
    }

//    public void setUserArrayList(ArrayList<User> users){
//        this.users = users;
//        saveAndNotify();
//    }

    public void saveUsers(){
        try {
            FileOutputStream fileOut = new FileOutputStream("tmp/users.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(users);

            out.close();
            fileOut.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void retrieveUsers(){

        try {
            FileInputStream fileIn = new FileInputStream("tmp/users.ser");
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

    public void addUser(User user){
        users.add(user);
//        saveAndNotify();
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
