package com.example.utils;

import com.example.database.DatabaseProvider;
import com.example.models.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionUser {

    private static DatabaseProvider databaseProvider;

    private static final String ATT_NAME_USER_NAME = "ATTRIBUTE_FOR_STORE_USER_NAME_IN_COOKIE";
    private static final String LOGGED_IN_USER = "loggedInUser";


    public static User getUser(HttpSession session) {
        return (User) session.getAttribute(LOGGED_IN_USER);
    }

    public static void storeLoggedIndUser(HttpSession session, User user) {
        session.setAttribute(LOGGED_IN_USER, user);
    }

    public static boolean isLoggedInEarlierUser(HttpServletRequest request) throws NullPointerException {
        databaseProvider = DatabaseProvider.getInstance();

        return databaseProvider.userExistence(getUserNameInCookie(request));
    }

    public static void storeUserCookie(HttpServletResponse response, User user) {
        Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, user.getName());
        cookieUserName.setMaxAge(24 * 60 * 60);
        response.addCookie(cookieUserName);
    }

    public static String getUserNameInCookie(HttpServletRequest request) throws NullPointerException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (ATT_NAME_USER_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static void deleteUserCookie(HttpServletResponse response) {
        Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, null);
        cookieUserName.setMaxAge(0);
        response.addCookie(cookieUserName);
    }

    public static void endSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
    }
}