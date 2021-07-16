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

    private UserManager userManager;
    private String userNameFromSession;
    private Gson gson = new Gson();
    private String json = "";
    String StockSymbol = "";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String actionFromParam = request.getParameter(Constants.ACTION);
        this.userManager = ServletUtils.getUserManager(getServletContext());
        this.userNameFromSession = SessionUtils.getUsername(request);
        this.StockSymbol = request.getParameter(Constants.SYMBOL);

        switch (actionFromParam) {
            case ("getStocks"): {
                try (PrintWriter out = response.getWriter()) {
                    response.setContentType("application/json");
                    json = gson.toJson(userManager.getStocks());
                    out.println(json);
                    out.flush();
                }
                break;
            }
            case ("getStock"): {
                try (PrintWriter out = response.getWriter()) {
                    response.setContentType("application/json");
                    json = gson.toJson(userManager.getStock(StockSymbol));
                    out.println(json);
                    out.flush();
                }
                break;
            }
            case ("getBuyCommandList"): {
                try (PrintWriter out = response.getWriter()) {
                    response.setContentType("application/json");
                    json = gson.toJson(userManager.getStockBuyCommandsList(StockSymbol));
                    out.println(json);
                    out.flush();
                }
            }
            case ("getSellCommandList"): {
                try (PrintWriter out = response.getWriter()) {
                    response.setContentType("application/json");
                    json = gson.toJson(userManager.getStockSellCommandsList(StockSymbol));
                    out.println(json);
                    out.flush();
                }
            }
            case ("getTransactionList"): {
                try (PrintWriter out = response.getWriter()) {
                    response.setContentType("application/json");
                    json = gson.toJson(userManager.getStockTransactionsList(StockSymbol));
                    out.println(json);
                    out.flush();
                }
                break;
            }
            case ("addStock"): {
                this.addStock(request, response);
                break;
            }

        }
    }

    private void addStock(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String CompanyName = request.getParameter("newStockCompanyName");
        String StockSymbol = request.getParameter("newStockSymbol");
        String StockQuantityFromParam = request.getParameter("newStockQuantity");
        int StockQuantity = Integer.parseInt(StockQuantityFromParam);
        String StockPriceFromParam = request.getParameter("newStockPrice");
        int StockPrice = Integer.parseInt(StockPriceFromParam);

        try {
            userManager.addStock(userNameFromSession, CompanyName, StockSymbol, StockPrice, StockQuantity);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getOutputStream().println("Congratulations! A new stock was Added!!");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getOutputStream().println(e.getMessage());
        }
    }

    private Set<String> getUserStocks(HttpServletRequest request) {
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
