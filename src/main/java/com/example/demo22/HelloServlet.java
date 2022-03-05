package com.example.demo22;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

//This is for testing using Java Servlet
//@WebServlet(name = "gameServlet", value = "/gameByServlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String gamename = request.getParameter("gamename");
        response.getWriter().println(gamename + " is being queried.");
    }

    public void destroy() {
    }
}