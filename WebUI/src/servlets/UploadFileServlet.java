package servlets;

import engine.UserManager;
import exception.StockNegPriceException;
import exception.StockSymbolLowercaseException;
import exception.XMLException;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Scanner;

@WebServlet("/uploadFile")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class UploadFileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Collection<Part> parts = request.getParts();

        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        String usernameFromSession = SessionUtils.getUsername(request);

        for (Part part : parts) {

            if (!part.getSubmittedFileName().endsWith(".xml")) {
                try {
                    throw new XMLException("File must be .xml format");
                } catch (XMLException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getOutputStream().println(e.getMessage());
                    return;
                }
            }

            try {
                userManager.loadXML(part.getInputStream(), usernameFromSession);
            } catch (StockNegPriceException | XMLException | JAXBException | StockSymbolLowercaseException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getOutputStream().println(e.getMessage());
                return;
            }
            response.setStatus(HttpServletResponse.SC_OK);
            response.getOutputStream().println("File Loaded!");
        }
    }
    private String readFromInputStream(InputStream inputStream) {
        return new Scanner(inputStream).useDelimiter("\\Z").next();
    }
}
