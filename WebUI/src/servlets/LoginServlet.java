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

    String STAM = "/web/pages/login/stam.html";
    String ERROR = "/web/pages/login/error.html";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        Gson gson= new Gson();
        String json;

        //String jsonResponse = gson.toJson(cav);
        String usernameFromSession = SessionUtils.getUsername(request);
        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        String usernameFromParameter = request.getParameter(Constants.USERNAME);
        String isAdmin= request.getParameter(Constants.ISADMIN);
        Pair<Boolean, String> res;
        if (usernameFromSession == null) {
            //user is not logged in yet
            if (usernameFromParameter == null || usernameFromParameter.isEmpty()) {
                //no username in session and no username in parameter -
                //redirect back to the index page
                //this return an HTTP code back to the browser telling it to load

                //response.sendRedirect(ERROR);

                try (PrintWriter out = response.getWriter()) {
                    res = new Pair<>(false, "No User name entered!");
                    json = gson.toJson(res);
                    out.println(json);
                    out.flush();
                }
                //TODO: ERROR MESSAGE - NO USER ENTERED
            } else {
                //normalize the username value
                usernameFromParameter = usernameFromParameter.trim();

                /*
                One can ask why not enclose all the synchronizations inside the userManager object ?
                Well, the atomic action we need to perform here includes both the question (isUserExists) and (potentially) the insertion
                of a new user (addUser). These two actions needs to be considered atomic, and synchronizing only each one of them, solely, is not enough.
                (of course there are other more sophisticated and performable means for that (atomic objects etc) but these are not in our scope)

                The synchronized is on this instance (the servlet).
                As the servlet is singleton - it is promised that all threads will be synchronized on the very same instance (crucial here)

                A better code would be to perform only as little and as necessary things we need here inside the synchronized block and avoid
                do here other not related actions (such as request dispatcher\redirection etc. this is shown here in that manner just to stress this issue
                 */
                synchronized (this) {
                    if (userManager.isExists(usernameFromParameter)) {
                        String errorMessage = "Username " + usernameFromParameter + " already exists. Please enter a different username.";
                        // username already exists, forward the request back to index.jsp
                        // with a parameter that indicates that an error should be displayed
                        // the request dispatcher obtained from the servlet context is one that MUST get an absolute path (starting with'/')
                        // and is relative to the web app root
                        // see this link for more details:
                        // http://timjansen.github.io/jarfiller/guide/servlet25/requestdispatcher.xhtml

                        //TODO: ERROR MESSAGE- "USER ALREADY EXISTS"
                        try (PrintWriter out = response.getWriter()) {
                            res = new Pair<>(false, "User name already exists");
                            json = gson.toJson(res);
                            out.println(json);
                            out.flush();
                        }
                        //response.sendRedirect(ERROR);

                        //request.setAttribute(Constants.USER_NAME_ERROR, errorMessage);
                        //getServletContext().getRequestDispatcher(LOGIN_ERROR_URL).forward(request, response);
                    }
                    else {

                        if( isAdmin == "on")
                            userManager.addAdmin(usernameFromParameter);
                        else
                            userManager.addUser(usernameFromParameter);

                       //create session
                        request.getSession(true).setAttribute(Constants.USERNAME, usernameFromParameter);

                        //redirect the request to the chat room - in order to actually change the URL

                        //TODO: REDIRECT TO NEXT PAGE AND SHOW MESSAGE- "USER LOGGED IN!"
                        try (PrintWriter out = response.getWriter()) {
                            res = new Pair<>(true, "You are logged in!");
                            json = gson.toJson(res);
                            out.println(json);
                            out.flush();
                        }
                        //response.sendRedirect(STAM);
                    }
                }
            }
        } else {
            //user is already logged in
           // response.sendRedirect(CHAT_ROOM_URL);
            //TODO: REDIRECT TO NEXT PAGE
            if(usernameFromSession.equals(usernameFromParameter)) {
                //response.sendRedirect(STAM);
                try (PrintWriter out = response.getWriter()) {
                    res = new Pair<>(true, "You are logged in!");
                    json = gson.toJson(res);
                    out.println(json);
                    out.flush();
                }
            }
            //TODO: ERROR message that the user is connected
            else {
                try (PrintWriter out = response.getWriter()) {
                    res = new Pair<>(false, "You are already logged in with user name " + usernameFromSession);
                    json = gson.toJson(res);
                    out.println(json);
                    out.flush();
                }
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
