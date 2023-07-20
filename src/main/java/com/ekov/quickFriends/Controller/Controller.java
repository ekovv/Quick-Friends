package com.ekov.quickFriends.Controller;

import com.ekov.quickFriends.DAO.RowsAndCols;
import com.ekov.quickFriends.Models.User;
import com.ekov.quickFriends.Usecase.Usecase;
import com.fasterxml.uuid.Generators;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@org.springframework.stereotype.Controller
@RequestMapping("/da")
public class Controller {

    private Usecase usecase;

    public Controller(Usecase usecase) {
        this.usecase = usecase;
    }


    @GetMapping("/login")
    public ModelAndView login(HttpServletRequest request) {
        String uuid = getUUID(request);
        if (uuid == null) {
            uuid = Generators.timeBasedGenerator().generate().toString();
            HttpSession session = request.getSession();
            session.setAttribute("uuid", uuid);
        }
        return new ModelAndView("login");
    }


    @PostMapping("/login")
    public ModelAndView loginPost(String email, String password, HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession();
        session.setAttribute("email", email);
        String uuid = getUUID(request);
        if (uuid == null) {
            return new ModelAndView("redirect:/da/login");
        }

        if (usecase.getLogin(email, password)) {
            return new ModelAndView("redirect:/da/main");
        }

        return new ModelAndView("redirect:/da/login");
    }

    @GetMapping("/register")
    public ModelAndView register(HttpServletRequest request) {
        String uuid = getUUID(request);
        if (uuid == null) {
            return new ModelAndView("redirect:/da/login");
        }
        return new ModelAndView("register");
    }

    @PostMapping("/register")
    public ModelAndView registerPost(String firstName, String lastName, String fatherName, String city, String school, String classNumber, String email, String phoneNumber, String password, HttpServletRequest request) throws SQLException {
        String uuid = getUUID(request);
        if (uuid == null) {
            return new ModelAndView("redirect:/da/login");
        }
        usecase.getRegistration(firstName, lastName,fatherName, city, school, classNumber, email,phoneNumber, password);
        return new ModelAndView("redirect:/da/main");

    }


//    @PostMapping("/main")
//    public ModelAndView homePost(@RequestBody String number, HttpServletRequest request, Model model) throws SQLException, IOException {
//        String uuid = getUUID(request);
//        if (uuid == null) {
//            return new ModelAndView("redirect:/da/login");
//        }
//        Integer otherNumber = 0;
//        String pattern = "\\d+"; // Регулярное выражение для поиска чисел
//        Pattern p = Pattern.compile(pattern);
//        Matcher m = p.matcher(number);
//        if (m.find()) {
//            String numberStr = m.group(); // Найденное число в виде строки
//            int a = Integer.parseInt(numberStr); // Преобразование строки в число
//            // Присваивание числа другой переменной
//            otherNumber = a;
//        }
//
//        return new ModelAndView("redirect:/da/about");
//    }

    @GetMapping("/main")
    public ModelAndView home(HttpServletRequest request, Model model) throws SQLException {
        String uuid = getUUID(request);
        if (uuid == null) {
            return new ModelAndView("redirect:/da/login");
        }
        RowsAndCols rowsAndCols = usecase.getQuery();
        model.addAttribute("rows", rowsAndCols.getRows());
        model.addAttribute("column", rowsAndCols.getCols());
        return new ModelAndView("main");
    }

    @GetMapping("/about/{number}")
    public ModelAndView about(HttpServletRequest request, Model model, @PathVariable String number) throws SQLException {
        String uuid = getUUID(request);
        if (uuid == null) {
            return new ModelAndView("redirect:/da/login");
        }

        Integer otherNumber = 0;
        String pattern = "\\d+"; // Регулярное выражение для поиска чисел
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(number);
        if (m.find()) {
            String numberStr = m.group(); // Найденное число в виде строки
            int a = Integer.parseInt(numberStr); // Преобразование строки в число
            // Присваивание числа другой переменной
            otherNumber = a;
        }
//        String aboutZap = usecase.getProfileRider(otherNumber);
        User user = usecase.getProfileRider(otherNumber);
        model.addAttribute("aboutZap", user);
        return new ModelAndView("about");
    }


    @GetMapping("/profile")
    public ModelAndView profile(HttpServletRequest request, Model model, String bout) throws SQLException {
        String uuid = getUUID(request);
        if (uuid == null) {
            return new ModelAndView("redirect:/da/login");
        }
        HttpSession session = request.getSession();
        Object email = session.getAttribute("email");
        usecase.getUpdateAbout(bout, email.toString());
        return new ModelAndView("profile");
    }



    public String getUUID(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object uuid = session.getAttribute("uuid");

        if (uuid == null) {
            return null;
        }

        return uuid.toString();
    }

}
















