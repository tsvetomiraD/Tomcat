import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class Login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String sha1Pass = DigestUtils.sha1Hex(password);
        Dao d = new Dao();
        PrintWriter out = response.getWriter();

        if (d.validUser(username, sha1Pass)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("username", username);
            out.println("Welcome " + username);
        }
        else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            out.println("Forbidden");
        }
    }
}
