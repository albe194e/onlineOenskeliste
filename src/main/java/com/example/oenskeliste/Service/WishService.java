package com.example.oenskeliste.Service;
import com.example.oenskeliste.Model.Wish;
import com.example.oenskeliste.Repository.WishRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;

@Service
public class WishService {

    private WishRepository wishRepository = new WishRepository();


    public String getPassword(HttpSession session){
        return wishRepository.getPassword((String) session.getAttribute("user"));
    }



    public void addWish(Wish wish, HttpSession session){

        wishRepository.addWish(wish, (String) session.getAttribute("user"));
    }

    public void deleteWish(Wish wish){

        wishRepository.deleteWish(wish);
    }

    public void deleteAllWishes(String name){
        wishRepository.deleteAllWishes(name);
    }

}