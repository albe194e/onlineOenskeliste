package com.example.oenskeliste.Controller;
import com.example.oenskeliste.Model.Wish;
import com.example.oenskeliste.Service.UserService;
import com.example.oenskeliste.Service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class ShowWishListController {

    private WishlistService wls = new WishlistService();

    @GetMapping("/getList")
    public String getList(WebRequest req, Model model) {

        if (wls.checkIfListExists(req.getParameter("password"))){
            ArrayList<Wish> wishes = wls.getAllByPassword(req);
            //Thymeleaf
            model.addAttribute("wish", wishes);
            model.addAttribute("name", wls.getName(req));
            return "/reservedWishList";
        } else {

            model.addAttribute("errorMessage", "Der findes ingen ønskeliste med det password");

            return "index";
        }






    }
}