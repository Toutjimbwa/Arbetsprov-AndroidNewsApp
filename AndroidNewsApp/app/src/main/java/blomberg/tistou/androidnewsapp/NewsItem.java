package blomberg.tistou.androidnewsapp;

import java.net.URL;

/**
 * Created by umyhblomti on 2016-11-09.
 */
public class NewsItem {

    private String title;
    private String content;
    private URL pictureURL;

    public NewsItem(String title, String content, URL pictureURL) {
        this.title = title;
        this.content = content;
        this.pictureURL = pictureURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public URL getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(URL pictureURL) {
        this.pictureURL = pictureURL;
    }
}
