package com.example.servlet;

import com.example.exception.WrongIDException;
import com.example.exception.WrongInputParametersException;
import com.example.service.DataService;

import java.io.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet
public class JDBCServlet extends HttpServlet {

    private final static Logger LOG = Logger.getLogger(JDBCServlet.class.getName());
    private final DataService dataService;
    private String outputMessage;

    public JDBCServlet() {
        dataService = new DataService();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        try (PrintWriter writer = response.getWriter()) {

            response.setContentType("application/json");
            setOutputMessage(dataService.getEmployees());
            writer.println(getOutputMessage());

        } catch (IOException e) {
            LOG.warning(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        try {
            String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            dataService.addEmployee(body);

        } catch (WrongInputParametersException e) {

            LOG.warning(e.getMessage());
            resp.setStatus(e.getCode());

        } catch (IOException e) {
            LOG.warning(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {

        try {
            String id = req.getParameter("id");
            dataService.deleteEmployee(id);

        } catch (WrongIDException e) {
            LOG.warning(e.getMessage());
            resp.setStatus(e.getCode());
        }
    }

    public void setOutputMessage(String msg) {
        this.outputMessage = msg;
    }

    public String getOutputMessage() {
        return this.outputMessage;
    }

    public void destroy() {
    }
}