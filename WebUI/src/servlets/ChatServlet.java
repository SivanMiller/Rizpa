package servlets;

import com.google.gson.Gson;
import constants.Constants;
import engine.Command;
import engine.UserManager;
import exception.*;
import javafx.util.Pair;
import objects.NewCmdOutcomeDTO;
import objects.UserCommandDTO;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ChatServlet extends HttpServlet {

    private UserManager userManager;
    private String userNameFromSession;
    private Gson gson = new Gson();
    private String json = "";


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String actionFromParam = request.getParameter(Constants.ACTION);
        this.userManager = ServletUtils.getUserManager(getServletContext());
        this.userNameFromSession = SessionUtils.getUsername(request);

        switch (actionFromParam) {
            case ("getChatList"): {
                try (PrintWriter out = response.getWriter()) {
                    response.setContentType("application/json");
                    json = gson.toJson(userManager.getChatList());
                    out.println(json);
                    out.flush();
                }
                break;
            }
            case ("addMessage"): {
                addMessage(request,response);
            }

        }

    }

    private void addMessage(HttpServletRequest request, HttpServletResponse response)
    {
        String newMessage = request.getParameter("newMessage");
        try {
            userManager.addMessage(newMessage,userNameFromSession);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Login Servlet";
    }
}
