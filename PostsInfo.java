import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SuppressWarnings("serial")
public class PostsInfo extends HttpServlet {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    Dao dao = new Dao();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //if(!checkForAuthorization(request, response))
            //return;

        String path = request.getPathInfo();
        PrintWriter out = response.getWriter();

        if (path == null) {
            sendAllPost(out);
            return;
        }

        Pattern pattern = Pattern.compile("([a-zA-z1-9]+)*");
        Matcher match = pattern.matcher(path);
        switch (match.groupCount()) {
            case 1 -> showPostById(match.group(), out);
            case 2 -> showCommentsForPost(match.group(), request, response);
            default -> sendError(out);
        }
    }

    private void sendAllPost(PrintWriter out) {
        List<Post> eL = dao.getAllPosts();
        String jsonInString = gson.toJson(eL);
        out.println(jsonInString);
    }

    private void showCommentsForPost(String idString, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(idString);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/comments?postId=" + id);
        dispatcher.forward(request, response);
    }

    private void showPostById(String idString, PrintWriter out) {
        int id = Integer.parseInt(idString);
        Post e = dao.getPostById(id);
        String jsonInString = gson.toJson(e);
        out.println(jsonInString);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!checkForAuthorization(request, response))
            return;

        PrintWriter out = response.getWriter();
        Dao d = new Dao();
        String js = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Post post = gson.fromJson(js, Post.class);
        d.insertPost(post);
        out.println(gson.toJson(post.id));
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!checkForAuthorization(request, response))
            return;

        PrintWriter out = response.getWriter();
        Dao d = new Dao();
        String path = request.getPathInfo();


        if (path == null) {
            String jsonInString = gson.toJson(null);
            out.println(jsonInString);
            return;
        }
        Pattern pattern = Pattern.compile("([a-zA-z1-9]+)*");
        Matcher match = pattern.matcher(path);
        int id = Integer.parseInt(match.group());

        String js = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Post post = gson.fromJson(js, Post.class);
        post.id = id;
        d.updatePost(post, id);
        out.println(gson.toJson(post));
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!checkForAuthorization(request, response))
            return;

        String path = request.getPathInfo();
        Dao ed = new Dao();
        PrintWriter out = response.getWriter();

        if (path == null) {
            sendError(out);
            return;
        }

        Pattern pattern = Pattern.compile("([a-zA-z1-9]+)*");
        Matcher match = pattern.matcher(path);
        int id = Integer.parseInt(match.group());

        int n = ed.deletePost(id);
        String jsonInString = gson.toJson(n);
        out.println(jsonInString);
    }

    boolean checkForAuthorization(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            sendJson(response, "Unauthorised user");
            return false;
        }
        return true;
    }

    private void sendJson(HttpServletResponse response, String message) throws IOException {
        PrintWriter out = response.getWriter();
        String jsonInString = gson.toJson(message);
        out.println(jsonInString);
    }

    private void sendError(PrintWriter out) {
        String jsonInString = gson.toJson(null);
        out.println(jsonInString);
    }
}
