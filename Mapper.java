import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface Mapper {
    @Select("SELECT * FROM posts")
    public Post[] getAllPosts();

    @Select("SELECT * FROM posts WHERE id=#{id}")
    public Post getPostById(int id);

    @Delete("DELETE FROM posts WHERE id=#{id}")
    public int deletePost(int id);

    @Insert("INSERT INTO posts(userId, body, title) VALUES (#{userId},#{body},#{title}")
    public int insertPost(Post post);

    @Update("UPDATE posts SET body = {body} WHERE id = #{id}")
    int updatePost(Post post, int id);

    @Select("SELECT * FROM comments")
    public Comment[] getAllComments();

    @Select("SELECT * FROM comments WHERE postId=#{id}")
    public Comment[] getCommentsByPostId(int id);
    @Select("SELECT * FROM users WHERE username=#{name} AND PASSWORD=#{password}")
    public boolean validUser(String name, String password);
}
