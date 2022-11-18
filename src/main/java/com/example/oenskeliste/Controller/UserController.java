package com.example.oenskeliste.Controller;
import com.example.oenskeliste.Misc.DaysToChristmasCalc;
import com.example.oenskeliste.Model.Wish;
import com.example.oenskeliste.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class UserController {

    private UserService userService = new UserService();


    @GetMapping("/")
    public String index(Model model, HttpSession session) {

        model.addAttribute("date", DaysToChristmasCalc.calculateDaysToCristmas() + " DAGE   TIL   JUL");
        if (session.getAttribute("user") != null){
            model.addAttribute("login", "Logget ind som: " + session.getAttribute("user"));

            model.addAttribute("hiddenUser", session.getAttribute("user"));
            model.addAttribute("hiddenEmail", session.getAttribute("email"));
        }
        return "index";
    }

    @PostMapping("/create")
    public String create(WebRequest req, Model model, HttpSession session) {

        if (req.getParameter("name").length() < 3){
            model.addAttribute("errorMessage", "Navn skal mindst være 3 bogstaver");
            return "index";
        }
        if (!req.getParameter("email").contains("@")){
            model.addAttribute("errorMessage", "Email skal indeholde: @");
            return "index";
        }
        if (userService.createUser(req)) {
            session.setAttribute("user", req.getParameter("name"));

            session.setAttribute("wishes", new ArrayList<Wish>());
            model.addAttribute("login", "Logget ind som: " + session.getAttribute("user"));
            model.addAttribute("password", "Kode til ønskelisten " + userService.getPassword((String) session.getAttribute("user")));
            session.setAttribute("password",userService.getPassword((String) session.getAttribute("user")) );

            return "/create";
        }
        else {
            model.addAttribute("errorMessage", "En bruger med samme navn findes");
            return "index";
        }
    }
    @PostMapping("/login")
    public String login(HttpSession session, WebRequest req, Model model){

        if (userService.login(req,session)) {

            session.setAttribute("user", req.getParameter("name"));
            session.setAttribute("email", req.getParameter("email"));



            if (session.getAttribute("user") != null){
                model.addAttribute("login", "Logget ind som: " + session.getAttribute("user"));
            }
            ArrayList<Wish> wishes = userService.getWishes(req.getParameter("name"));

            session.setAttribute("wishes", wishes);
            model.addAttribute("wish",wishes);

            String password = userService.getPassword((String) session.getAttribute("user"));
            session.setAttribute("password", password);
            model.addAttribute("password", "Kode til ønskelisten: " + password);

            return "/create";
        }
        else {
            model.addAttribute("doesntExist", "Bruger findes ikke");
            return "/index";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();

        return "redirect:/";
    }
    @PostMapping("/deleteUser")
    public String deleteUser(HttpSession session){
        userService.deleteUser(session);
        session.invalidate();
        return "redirect:/";
    }
}