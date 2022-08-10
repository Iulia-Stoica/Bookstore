package com.db.bookstore.controller;

import com.db.bookstore.model.Book;
import com.db.bookstore.model.User;

import com.db.bookstore.service.BookService;
import com.db.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @GetMapping("/register")
    public ModelAndView getRegisterForm() {
        ModelAndView modelAndView = new ModelAndView("register-form");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView addUser(User user) {
        user.setRole("client");
        userService.insertUser(user);
        ModelAndView modelAndView = new ModelAndView("redirect:/login");
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView getLoginForm() {
        ModelAndView modelAndView = new ModelAndView("login-form");
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView verifyUser(User user, HttpServletResponse response){
        try {
            User user1 = userService.findByUsernameOrEmailAndPassword(user);
            response.addCookie(new Cookie("id", "" + user1.getId()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");

        return modelAndView;
    }

    @GetMapping("/dashboard")
    public ModelAndView getDashBoard(@CookieValue int id) {
        User user = new User();
        user.setId(id);
        ModelAndView modelAndView=new ModelAndView("dashboard");
        try {
            modelAndView.addObject("user", userService.findById(user));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        modelAndView.addObject("bookList","");
        return modelAndView;
    }

    @GetMapping("/addbook")
    public ModelAndView addBook(){
        Book book = new Book();
        ModelAndView modelAndView=new ModelAndView("addbook");
        modelAndView.addObject("book", book);
        return modelAndView;
    }

    @PostMapping("/book/th-new")
    public ModelAndView addThBook (@ModelAttribute Book book) {
        ModelAndView modelAndView = new ModelAndView("added-book");
        modelAndView.addObject("book", book);
        bookService.insertBook(book);
        return modelAndView;
    }


}
