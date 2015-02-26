package ru.zapoebad.pwd.objects;

/**
 * Created by DuMkA on 19.02.2015.
 */
public class Post extends Origin {

    enum PostStatus { NEW, ACTIVE, DELETED }

    private PostStatus status = PostStatus.NEW;

    private int author;

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }
}
