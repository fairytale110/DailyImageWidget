/**
 * Copyright 2018 bejson.com
 */
package tech.nicesky.dailyimage.bean;

import java.util.List;
import java.util.Date;

/**
 * Auto-generated: 2018-10-08 16:13:58
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class PoetryData {

    private String id;
    private String content;
    private Long popularity;
    private Origin origin;
    private List<String> matchTags;
    private String recommendedReason;
    private String cacheAt;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setPopularity(long popularity) {
        this.popularity = popularity;
    }

    public long getPopularity() {
        return popularity;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setMatchTags(List<String> matchTags) {
        this.matchTags = matchTags;
    }

    public List<String> getMatchTags() {
        return matchTags;
    }

    public void setRecommendedReason(String recommendedReason) {
        this.recommendedReason = recommendedReason;
    }

    public String getRecommendedReason() {
        return recommendedReason;
    }

    public void setCacheAt(String cacheAt) {
        this.cacheAt = cacheAt;
    }

    public String getCacheAt() {
        return cacheAt;
    }


    @Override
    public String toString() {
        return "PoetryData{" +
                "content='" + content + '\'' +
                '}';
    }
}