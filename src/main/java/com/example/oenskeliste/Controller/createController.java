package com.example.oenskeliste.Controller;

import com.example.oenskeliste.Model.Wish;
import com.example.oenskeliste.Service.WishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class createController {

    private WishService wishService = new WishService();

    @PostMapping("/submitList")
    public String submitList(Model model, HttpSession session) {


        String password = wishService.getPassword(session);
        session.setAttribute("password", password);

        //Thymeleaf
        model.addAttribute("name", session.getAttribute("user"));
        model.addAttribute("password", password);

        return "/getPassword";
    }



    @PostMapping("/tilføjØnske")
    public String addWish(WebRequest req, Model model, HttpSession session) {

        ArrayList<Wish> wishes = (ArrayList<Wish>) session.getAttribute("wishes");

        Wish wish = new Wish(req.getParameter("ønske"),
                req.getParameter("pris"),
                req.getParameter("link"),
                req.getParameter("note"));

        wishService.addWish(wish, session);
        wishes.add(wish);
        session.setAttribute("wishes", wishes);
        model.addAttribute("wish", wishes);
        model.addAttribute("login", "Logget ind som: " + session.getAttribute("user"));
        model.addAttribute("password", "Kode til ønskelisten " + session.getAttribute("password"));


        return "/create";
    }
    @PostMapping("/deleteWish")
    public String deleteWish(WebRequest req, HttpSession session, Model model){

        ArrayList<Wish> wishes = (ArrayList<Wish>) session.getAttribute("wishes");

        Wish wish = new Wish(req.getParameter("wish"),
                             req.getParameter("pris"),
                             req.getParameter("link"),
                             req.getParameter("note"));

        wishService.deleteWish(wish);

        for (int i = 0; i < wishes.size(); i++) {
            if (wishes.get(i).getName().equals(wish.getName()) && wishes.get(i).getPrice().equals(wish.getPrice())){
                wishes.remove(i);
            }
        }
        session.setAttribute("wishes", wishes);
        model.addAttribute("wish",wishes);
        model.addAttribute("login", "Logget ind som: " + session.getAttribute("user"));
        model.addAttribute("password", "Kode til ønskelisten " + session.getAttribute("password"));


        return "/create";
    }

    @PostMapping("/deleteAllWishes")
    public String deleteAllWishes(HttpSession session, Model model){

        wishService.deleteAllWishes((String) session.getAttribute("user"));

        ArrayList<Wish> wishes = (ArrayList<Wish>) session.getAttribute("wishes");
        wishes.clear();
        session.setAttribute("wishes", wishes);

        model.addAttribute("wish", wishes);
        model.addAttribute("login", "Logget ind som: " + session.getAttribute("user"));
        model.addAttribute("password", "Kode til ønskelisten " + session.getAttribute("password"));

        return "/create";
    }

}