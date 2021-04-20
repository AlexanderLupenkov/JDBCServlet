package com.example.servlet;

import com.example.database.DatabaseProvider;
import com.example.models.User;
import com.example.utils.SessionUser;

import java.io.*;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import static com.example.utils.EmployeePuller.PullEntity;

@WebServlet("/data")
public class DataServlet extends HttpServlet {

    private final static Logger LOG = Logger.getLogger(DataServlet.class.getName());
    private final DatabaseProvider databaseProvider;
    private final String LOGIN_PATH = "/login";

    public DataServlet() {
        databaseProvider = DatabaseProvider.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User loggedInUser = SessionUser.getUser(session);
        boolean isLogged = false;
        try {
            isLogged = SessionUser.isLoggedInEarlierUser(req);
        } catch (NullPointerException e) {
            LOG.info(e.getMessage());
        }

        String SUCCESSFUL_PATH = "/WEB-INF/view/successfulValidation.jsp";
        if (isLogged) {
            req.setAttribute("user", loggedInUser);
            try {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher(SUCCESSFUL_PATH);
                requestDispatcher.forward(req, resp);
            } catch (ServletException | IOException e) {
                LOG.info(e.getMessage());
            }
        }
        if (loggedInUser == null) {
            try {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher(LOGIN_PATH);
                requestDispatcher.forward(req, resp);
            } catch (ServletException | IOException e) {
                LOG.info(e.getMessage());
            }
        } else {
            req.setAttribute("user", loggedInUser);
            try {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher(SUCCESSFUL_PATH);
                requestDispatcher.forward(req, resp);
            } catch (ServletException | IOException e) {
                LOG.info(e.getMessage());
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getParameter("sent") != null && req.getParameter("sent").equals("add")) {
            HttpSession session = req.getSession();
            User loggedInUser = SessionUser.getUser(session);

            if (loggedInUser == null && !SessionUser.isLoggedInEarlierUser(req)) {
                try {
                    RequestDispatcher requestDispatcher = req.getRequestDispatcher(LOGIN_PATH);
                    requestDispatcher.forward(req, resp);
                } catch (ServletException | IOException e) {
                    LOG.info(e.getMessage());
                }
            } else {
                if (PullEntity(req) == null) {
                    try {
                        String WRONG_INPUT_PATH = "/WEB-INF/view/wrongInputEmployee.jsp";
                        RequestDispatcher requestDispatcher = req.getRequestDispatcher(WRONG_INPUT_PATH);
                        requestDispatcher.forward(req, resp);
                    } catch (ServletException | IOException e) {
                        LOG.info(e.getMessage());
                    }
                } else {
                    databaseProvider.pushData(PullEntity(req));
                    try {
                        resp.sendRedirect(req.getContextPath() + "/data");
                    } catch (IOException e) {
                        LOG.info(e.getMessage());
                    }
                }
            }
        }
        if (req.getParameter("logout") != null && req.getParameter("logout").equals("logout")) {
            SessionUser.deleteUserCookie(resp);
            SessionUser.endSession(req);

            try {
                resp.sendRedirect(req.getContextPath() + LOGIN_PATH);
            } catch (IOException e) {
                LOG.info(e.getMessage());
            }
        }
    }

    @Override
    public void destroy() {
    }
}