package servlets;

import com.google.gson.Gson;
import constants.Constants;
import engine.UserManager;
import exception.*;
import javafx.util.Pair;
import objects.NewCmdOutcomeDTO;
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

public class CommandServlet extends HttpServlet {

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
            case ("addFunds"): {
                this.addFunds(request, response);
                break;
            }
            case ("reportTransactions"): {
                this.reportTransactions(request, response);
                break;
            }
            case ("addCommand"): {
                this.addCommand(request, response);
                break;
            }
        }
    }

    private void addFunds(HttpServletRequest request, HttpServletResponse response) {
        String userFundsFromParameter = request.getParameter(Constants.USER_FUNDS);
        int funds = Integer.parseInt(userFundsFromParameter);
        userManager.addUserFunds(userNameFromSession, funds);
    }

    private void reportTransactions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            response.setContentType("application/json");
            json = gson.toJson(userManager.reportTransactions(userNameFromSession));
            out.println(json);
            out.flush();
        }
    }

    private void addCommand(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userNameFromSession = SessionUtils.getUsername(request);
        String StockSymbol = request.getParameter("newCommandSymbol");
        String CmdType = request.getParameter("CmdType");
        String CmdDirection = request.getParameter("CmdDirection");
        String StockQuantityFromParam = request.getParameter("newCommandQuantity");
        int StockQuantity = Integer.parseInt(StockQuantityFromParam);
        String StockPriceFromParam = request.getParameter("newCommandPrice");
        int StockPrice = Integer.parseInt(StockPriceFromParam);

        UserManager userManager = ServletUtils.getUserManager(getServletContext());

        try (PrintWriter out = response.getWriter()) {
            response.setContentType("application/json");
            try {
                NewCmdOutcomeDTO outcome = userManager.addNewCommand(userNameFromSession, StockSymbol, CmdType, CmdDirection, StockPrice, StockQuantity);
                json = gson.toJson(outcome.toString());
            } catch (NoSuchCmdTypeException | UserHoldingQuntityNotEnough | StockNegQuantityException | CommandNegPriceException | NoSuchStockException | NoSuchHolding e) {
                json = gson.toJson(e.getMessage());
            }
            out.println(json);
            out.flush();
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
