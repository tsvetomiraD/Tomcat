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
import java.util.stream.Collectors;

public class RegisterUser extends HttpServlet {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Dao d = new Dao();
        String js = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        User user = gson.fromJson(js, User.class);
        String password = user.password;
        String sha1Pass = DigestUtils.sha1Hex(password);
        user.password = sha1Pass;
        d.registerUser(user);
        out.println(gson.toJson(user.id));
    }
}
