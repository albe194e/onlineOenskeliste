package com.example.oenskeliste.Service;
import com.example.oenskeliste.Model.User;
import com.example.oenskeliste.Model.Wish;
import com.example.oenskeliste.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Random;

@Service
public class UserService {

    private UserRepository userRepository = new UserRepository();

    public boolean createUser(WebRequest req) {

        String name = req.getParameter("name");
        String email = req.getParameter("email");

        User userNoPass = new User(email, name);

        if (!checkUser(userNoPass)) {
            User newUser = new User(email, name, createPassword());
            userRepository.createUser(newUser);
            return true;
        }
        else return false;
    }
    public boolean login(WebRequest req, HttpSession session){

        String name = req.getParameter("name");
        String email = req.getParameter("email");

        User user = new User(email, name);

        if (checkUser(user)){
            session.setAttribute("user", name);
            return true;
        }
        else return false;
    }

    private boolean checkUser(User user){

        return userRepository.checkIfUserExist(user);
    }
    public void deleteUser(HttpSession session){
        userRepository.deleteUser((String) session.getAttribute("user"));
    }

    private String createPassword(){
        Random r = new Random();

        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\\\|;:\\'\\\",<.>/?";

        String password = "";

        for (int i = 0; i <10; i++) {
            password += chars.charAt(r.nextInt(chars.length()));
            }

        return password;
    }
    public ArrayList<Wish> getWishes(String name){

        return userRepository.getWishes(name);
    }

    public String getPassword(String name){
        return userRepository.getPassword(name);
    }
}