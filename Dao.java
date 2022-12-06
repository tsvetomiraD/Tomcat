import org.apache.ibatis.session.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class Dao {
    SqlSessionFactory factory;

    public Dao() throws IOException {
        File f = new File("C:\\Users\\TD\\MyBatis\\config.xml");
        Reader reader = new FileReader(f);

        factory = new SqlSessionFactoryBuilder().build(reader);
        factory.getConfiguration().addMapper(Mapper.class);
        reader.close();
    }

    public Post getPostById(int id) {
        try (SqlSession session = factory.openSession()) {
            Mapper postMapper = session.getMapper(Mapper.class);
            return postMapper.getPostById(id);
        }
    }

    public List<Post> getAllPosts() {
        try (SqlSession session = factory.openSession()) {
            Mapper postMapper = session.getMapper(Mapper.class);
            return Arrays.asList(postMapper.getAllPosts());
        }
    }

    public int insertPost(Post post) {
        try (SqlSession session = factory.openSession()) {
            Mapper postMapper = session.getMapper(Mapper.class);
            return postMapper.insertPost(post);
        }
    }

    public int updatePost(Post post, int id) {

        try (SqlSession session = factory.openSession()) {
            Mapper postMapper = session.getMapper(Mapper.class);
            return postMapper.updatePost(post, id);
        }
    }

    public int deletePost(int id) {
        try (SqlSession session = factory.openSession()) {
            Mapper postMapper = session.getMapper(Mapper.class);
            return postMapper.deletePost(id);
        }
    }

    public List<Comment> getAllComments() {
        try (SqlSession session = factory.openSession()) {
            Mapper postMapper = session.getMapper(Mapper.class);
            return Arrays.asList(postMapper.getAllComments());
        }
    }

    public List<Comment> getCommentsByPostId(int id) {
        try (SqlSession session = factory.openSession()) {
            Mapper postMapper = session.getMapper(Mapper.class);
            return Arrays.asList(postMapper.getCommentsByPostId(id));
        }
    }

    public boolean validUser(String user, String pass) {
        try (SqlSession session = factory.openSession()) {
            Mapper postMapper = session.getMapper(Mapper.class);
            return postMapper.validUser(user, pass);
        }
    }

}

