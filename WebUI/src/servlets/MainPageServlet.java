package servlets;

import com.google.gson.Gson;
import constants.Constants;
import engine.UserManager;
import javafx.util.Pair;
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

public class MainPageServlet extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Gson gson = new Gson();
        String json = "";
        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        String userNameFromSession = SessionUtils.getUsername(request);
        String actionFromParam = request.getParameter(Constants.ACTION);

        try (PrintWriter out = response.getWriter()) {
            switch (actionFromParam) {
                case ("getUsers"): {
                    response.setContentType("application/json");
                    json = gson.toJson(this.getUsers());
                    out.println(json);
                    out.flush();
                    break;
                }
                case ("getStocks"): {
                    response.setContentType("application/json");
                    json = gson.toJson(userManager.getStocks());
                    out.println(json);
                    out.flush();

                    break;
                }
                case("isAdmin"): {
                    if (!SessionUtils.isAdmin(request)){
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    }
                    else{
                        response.setStatus(HttpServletResponse.SC_OK);
                    }
                    break;
                }
                case("userFunds"):{
                    response.setContentType("application/json");
                    int funds = userManager.getUserFunds(userNameFromSession);
                    json = gson.toJson(funds);
                    out.println(json);
                    out.flush();
                    break;
                }
                case("addFunds"):{
                    String userFundsFromParameter = request.getParameter(Constants.USER_FUNDS);
                    int funds = Integer.parseInt(userFundsFromParameter);
                    userManager.addUserFunds(userNameFromSession ,funds);
                    break;
                }
            }
        }
    }

    public List<Pair<String, String>> getUsers() {
        List<Pair<String, String>> res = new ArrayList<>();
        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        Pair<String, String> userPair;

        for (String name : userManager.getUsers().keySet()) {
            userPair = new Pair(name, "Consumer");
            res.add(userPair);
        }

        for (int i = 0; i < userManager.getAdmins().size(); i++) {
            userPair = new Pair(userManager.getAdmins().get(i), "Admin");
            res.add(userPair);
        }

        return res;
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
