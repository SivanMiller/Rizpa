package servlets;

import com.google.gson.Gson;
import engine.UserManager;
import javafx.util.Pair;
import utils.ServletUtils;
import utils.SessionUtils;
import constants.Constants;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Get request parameters
        String usernameFromSession = SessionUtils.getUsername(request);
        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        String usernameFromParameter = request.getParameter(Constants.USERNAME);
        //normalize the username value
        usernameFromParameter = usernameFromParameter.trim();
        String isAdmin= request.getParameter(Constants.ISADMIN);

        // No session created for this user yet
        if (usernameFromSession == null) {
            //Check if user name was entered
            if (usernameFromParameter == null || usernameFromParameter.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getOutputStream().println("No User name entered!");
            } else { //User name entered
                synchronized (this) {
                    //Check if the user name already exists in server
                    if (userManager.isExists(usernameFromParameter)) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.getOutputStream().println("Username " + usernameFromParameter + " already exists. Please enter a different username.");
                    }
                    else { //User name valid, create session
                        if( isAdmin.equals(Boolean.TRUE.toString()))
                            userManager.addAdmin(usernameFromParameter);
                        else
                            userManager.addUser(usernameFromParameter);

                       //create session
                        request.getSession(true).setAttribute(Constants.USERNAME, usernameFromParameter);

                        response.setStatus(HttpServletResponse.SC_OK);
                        response.getOutputStream().println("You are logged in!");
                    }
                }
            }
        } else { //Session exists
            //Check if user name in session matches entered user name
            if(usernameFromSession.equals(usernameFromParameter)) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getOutputStream().println("You are logged in!");
            }
            else { //Session user name doesn't match entered user name
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getOutputStream().println("You are already logged in with user name " + usernameFromSession);
            }
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
