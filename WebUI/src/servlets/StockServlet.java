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

public class StockServlet extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Gson gson = new Gson();
        String json = "";
        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        String userNameFromSession = SessionUtils.getUsername(request);
        String actionFromParam = request.getParameter(Constants.ACTION);

        switch (actionFromParam) {
//            case ("getUsers"): {
//                try (PrintWriter out = response.getWriter()) {
//                    response.setContentType("application/json");
//                    json = gson.toJson(this.getUsers());
//                    out.println(json);
//                    out.flush();
//                }
//                break;
//            }
            case ("getStocks"): {
                try (PrintWriter out = response.getWriter()) {
                    response.setContentType("application/json");
                    json = gson.toJson(userManager.getStocks());
                    out.println(json);
                    out.flush();
                }
                break;
            }
//            case ("isAdmin"): {
//                if (!SessionUtils.isAdmin(request)) {
//                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                } else {
//                    response.setStatus(HttpServletResponse.SC_OK);
//                }
//                break;
//            }
//            case ("userFunds"): {
//                try (PrintWriter out = response.getWriter()) {
//                    response.setContentType("application/json");
//                    int funds = userManager.getUserFunds(userNameFromSession);
//                    json = gson.toJson(funds);
//                    out.println(json);
//                    out.flush();
//                }
//                break;
//            }
//            case ("addFunds"): {
//                String userFundsFromParameter = request.getParameter(Constants.USER_FUNDS);
//                int funds = Integer.parseInt(userFundsFromParameter);
//                userManager.addUserFunds(userNameFromSession, funds);
//                break;
//            }
//            case ("userAccountMovements"): {
//                try (PrintWriter out = response.getWriter()) {
//                    response.setContentType("application/json");
//                    json = gson.toJson(userManager.getUserAccountMovementList(userNameFromSession));
//                    out.println(json);
//                    out.flush();
//                }
//                break;
//            }
            case ("addStock"): {
                this.addStock(request, response);
                break;
            }
//            case ("getUserStocks"): {
//                try (PrintWriter out = response.getWriter()) {
//                    response.setContentType("application/json");
//                    json = gson.toJson(this.getUserStocks(request));
//                    out.println(json);
//                    out.flush();
//                }
//
//                break;
//            }
//            case ("reportTransactions"): {
//                try (PrintWriter out = response.getWriter()) {
//                    response.setContentType("application/json");
//                    json = gson.toJson(userManager.reportTransactions(userNameFromSession));
//                    out.println(json);
//                    out.flush();
//                }
//
//                break;
//            }
//            case ("addCommand"): {
//                this.addCommand(request, response);
//                break;
//            }
        }
    }
//    public void addCommand(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        Gson gson = new Gson();
//        String json = "";
//        String userNameFromSession = SessionUtils.getUsername(request);
//        String StockSymbol = request.getParameter("newCommandSymbol");
//        String CmdType = request.getParameter("CmdType");
//        String CmdDirection = request.getParameter("CmdDirection");
//        String StockQuantityFromParam = request.getParameter("newCommandQuantity");
//        int StockQuantity = Integer.parseInt(StockQuantityFromParam);
//        String StockPriceFromParam = request.getParameter("newCommandPrice");
//        int StockPrice = Integer.parseInt(StockPriceFromParam);
//
//        UserManager userManager = ServletUtils.getUserManager(getServletContext());
//
//        try (PrintWriter out = response.getWriter()) {
//            response.setContentType("application/json");
//            NewCmdOutcomeDTO outcome = userManager.addNewCommand(userNameFromSession, StockSymbol, CmdType, CmdDirection, StockPrice, StockQuantity);
//            json = gson.toJson(outcome);
//            out.println(json);
//            out.flush();
//        } catch (IOException | NoSuchCmdTypeException | UserHoldingQuntityNotEnough | StockNegQuantityException | CommandNegPriceException | NoSuchStockException e) {
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            response.getOutputStream().println(e.getMessage());
//        }
//    }
    public void addStock(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String CompanyName = request.getParameter("newStockCompanyName");
        String StockSymbol = request.getParameter("newStockSymbol");
        String StockQuantityFromParam = request.getParameter("newStockQuantity");
        int StockQuantity = Integer.parseInt(StockQuantityFromParam);
        String StockPriceFromParam = request.getParameter("newStockPrice");
        int StockPrice = Integer.parseInt(StockPriceFromParam);

        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        String userNameFromSession = SessionUtils.getUsername(request);
        try {
            userManager.addStock(userNameFromSession, CompanyName, StockSymbol, StockPrice, StockQuantity);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getOutputStream().println("Congratulations! A new stock was Added!!");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getOutputStream().println(e.getMessage());
        }
    }

//    public List<Pair<String, String>> getUsers() {
//        List<Pair<String, String>> res = new ArrayList<>();
//        UserManager userManager = ServletUtils.getUserManager(getServletContext());
//        Pair<String, String> userPair;
//
//        for (String name : userManager.getUsers().keySet()) {
//            userPair = new Pair(name, "Consumer");
//            res.add(userPair);
//        }
//
//        for (int i = 0; i < userManager.getAdmins().size(); i++) {
//            userPair = new Pair(userManager.getAdmins().get(i), "Admin");
//            res.add(userPair);
//        }
//
//        return res;
//    }

    public Set<String> getUserStocks(HttpServletRequest request) {
        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        String userNameFromSession = SessionUtils.getUsername(request);
        return userManager.getUserStocksSymbols(userNameFromSession);
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
