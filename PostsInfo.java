import java.io.*;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SuppressWarnings("serial")
public class PostsInfo extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = request.getPathInfo();
        Dao ed = new Dao();
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        if (path == null) {
            List<Post> eL = ed.getAllPosts();
            String jsonInString = gson.toJson(eL);
            out.println(jsonInString);
            return;
        }

        String[] pathParts = Arrays.stream(path.split("/")).filter(p -> !p.isEmpty()).toArray(String[]::new);
        int id = Integer.parseInt(pathParts[0]);

        if (pathParts.length == 1) {
            Post e = ed.getPostById(id);
            String jsonInString = gson.toJson(e);
            out.println(jsonInString);
            return;
        }

        if (!pathParts[1].equalsIgnoreCase("comments")) {
            String jsonInString = gson.toJson(null);
            out.println(jsonInString);
            return;
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/comments?postId=" + id);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.println("Post");

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        Dao ed = new Dao();
        PrintWriter out = resp.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        if (path == null) {
            String jsonInString = gson.toJson(null);
            out.println(jsonInString);
            return;
        }

        String[] pathParts = Arrays.stream(path.split("/")).filter(p -> !p.isEmpty()).toArray(String[]::new);
        int id = Integer.parseInt(pathParts[0]);

        if (pathParts.length != 1) {
            String jsonInString = gson.toJson(null);
            out.println(jsonInString);
            return;
        }
        int n = ed.deletePost(id);
        String jsonInString = gson.toJson(n);
        out.println(jsonInString);
    }
}
