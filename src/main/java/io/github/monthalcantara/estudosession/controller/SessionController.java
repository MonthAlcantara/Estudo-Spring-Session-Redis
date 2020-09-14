package io.github.monthalcantara.estudosession.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("v1/session")
public class SessionController extends HttpServlet {

    @Autowired
    Jedis j;


    @GetMapping("/contador")
    public String contaRefresh(HttpServletRequest request) {
        Integer pageViews = 1;
        if (request.getSession().getAttribute("pageViews") != null) {
            pageViews += (Integer) request.getSession().getAttribute("pageViews");
        }
        request.getSession().setAttribute("pageViews", pageViews);

        return String.valueOf(pageViews);
    }
}
