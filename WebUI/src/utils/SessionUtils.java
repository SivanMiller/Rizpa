package utils;

import constants.Constants;
import org.omg.CORBA.TRANSACTION_MODE;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils {

    public static String getUsername (HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object sessionAttribute = session != null ? session.getAttribute(Constants.USERNAME) : null;
        return sessionAttribute != null ? sessionAttribute.toString() : null;
    }
    public static boolean isAdmin(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Object sessionAttribute = session != null ? session.getAttribute(Constants.IS_ADMIN) : null;
        return sessionAttribute == "TRUE" ? true : false;
    }
    
    public static void clearSession (HttpServletRequest request) {
        request.getSession().invalidate();
    }
}