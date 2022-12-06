import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CommentsInfo extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String postId = request.getParameter("postId");
        Dao d = new Dao();
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if (postId != null) {
            int id = Integer.parseInt(postId);
            List<Comment> c = d.getCommentsByPostId(id);
            String jsonInString = gson.toJson(c);
            out.println(jsonInString);
            return;
        }
        List<Comment> c = d.getAllComments();
        String jsonInString = gson.toJson(c);
        out.println(jsonInString);
    }
}
