package com.example.servlet;

import com.example.database.DatabaseProvider;
import com.example.models.User;
import com.example.utils.SessionUser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.logging.Logger;


@WebServlet("/login")
public class AuthorizationServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(Executor.class.getName());

    private final DatabaseProvider databaseProvider;
    private final String LOGIN_PATH = "/WEB-INF/view/login.jsp";
    private final String SUCCESSFUL_LOGIN_PATH = "/WEB-INF/view/successfulValidation.jsp";
    private final String ERROR_LOGIN_PATH = "/WEB-INF/view/errorLogin.jsp";

    public AuthorizationServlet() {
        databaseProvider = DatabaseProvider.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(LOGIN_PATH);
            requestDispatcher.forward(req, resp);
        } catch (ServletException | IOException e) {
            LOG.info(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String userName = req.getParameter("name");
        String userPassword = req.getParameter("pass");
        String rememberNeeding = req.getParameter("rememberMe");
        boolean remember = "yes".equals(rememberNeeding);

        User user = new User(userName, userPassword);

        if (databaseProvider.getUserByName(userName) != null && databaseProvider.getUserByName(userName).equals(user)) {
            HttpSession session = req.getSession();

            SessionUser.storeLoggedIndUser(session, user);
            if (remember) {
                SessionUser.storeUserCookie(resp, user);
            }
            try {
                resp.sendRedirect(req.getContextPath() + "/data");
            } catch (IOException e) {
                LOG.info(e.getMessage());
            }
        } else {
            try {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher(ERROR_LOGIN_PATH);
                requestDispatcher.forward(req, resp);
            } catch (ServletException | IOException e) {
                LOG.info(e.getMessage());
            }
        }
    }
}
