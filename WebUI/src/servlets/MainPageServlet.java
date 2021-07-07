package servlets;

import com.google.gson.Gson;
import com.sun.org.apache.xpath.internal.operations.Bool;
import constants.Constants;
import engine.User;
import engine.UserManager;
import exception.StockNegPriceException;
import exception.StockSymbolLowercaseException;
import exception.*;
import javafx.util.Pair;
import objects.StockDTO;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.jnlp.ClipboardService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MainPageServlet extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        Gson gson = new Gson();
        String json = "";
        UserManager userManager = ServletUtils.getUserManager(getServletContext());

        String actionFromParam = request.getParameter(Constants.ACTION);

        try (PrintWriter out = response.getWriter()) {
            switch (actionFromParam) {
                case ("getUsers"): {
                    json = gson.toJson(this.getUsers());
                    break;
                }
                case ("getStocks"): {
                    json = gson.toJson(userManager.getStocks());

                    break;
                }
                case ("loadXML"): {
                    this.userLoadXML(request);
                    break;
                }
            }

            out.println(json);
            out.flush();
        }
    }

    public void userLoadXML(HttpServletRequest request) {
        String usernameFromSession = SessionUtils.getUsername(request);
        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        String filenameFromParameter = request.getParameter(Constants.FILE_NAME);

        try {
            userManager.loadXML(filenameFromParameter, usernameFromSession);
        } catch (StockNegPriceException e) {
            e.printStackTrace();
        } catch (XMLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (StockSymbolLowercaseException e) {
            e.printStackTrace();
        }
    }

      public List<Pair<String, String>> getUsers(){
        List<Pair<String, String>> res = new ArrayList<>();
        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        Pair<String, String> userPair;

        for (String name : userManager.getUsers().keySet()){
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
