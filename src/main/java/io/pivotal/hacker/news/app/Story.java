package io.pivotal.hacker.news.app;

public class Story {

    private String id;
    private String title;
    private String url;


    public Story(String id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }
    public Story() {
        this.id = "";
        this.title = "";
        this.url = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Story{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

}
