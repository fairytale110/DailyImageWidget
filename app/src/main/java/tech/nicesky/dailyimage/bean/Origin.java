/**
 * Copyright 2018 bejson.com
 */
package tech.nicesky.dailyimage.bean;

import java.util.List;

/**
 * Auto-generated: 2018-10-08 16:13:58
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Origin {

    private String title;
    private String dynasty;
    private String author;
    private List<String> content;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDynasty(String dynasty) {
        this.dynasty = dynasty;
    }

    public String getDynasty() {
        return dynasty;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return "---- " + author;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public List<String> getContent() {
        return content;
    }

}