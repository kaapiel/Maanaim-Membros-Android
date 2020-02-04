
package br.com.igreja.cellapp.model.youtubeModel;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Snippet {

    private String publishedAt;
    private String channelId;
    private String title;
    private String description;
    private Thumbnails thumbnails;
    private String channelTitle;
    private String playlistId;
    private int position;
    private ResourceId resourceId;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The publishedAt
     */
    public String getPublishedAt() {
        return publishedAt;
    }

    /**
     * 
     * @param publishedAt
     *     The publishedAt
     */
    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Snippet withPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
        return this;
    }

    /**
     * 
     * @return
     *     The channelId
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * 
     * @param channelId
     *     The channelId
     */
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public Snippet withChannelId(String channelId) {
        this.channelId = channelId;
        return this;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public Snippet withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public Snippet withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * 
     * @return
     *     The thumbnails
     */
    public Thumbnails getThumbnails() {
        return thumbnails;
    }

    /**
     * 
     * @param thumbnails
     *     The thumbnails
     */
    public void setThumbnails(Thumbnails thumbnails) {
        this.thumbnails = thumbnails;
    }

    public Snippet withThumbnails(Thumbnails thumbnails) {
        this.thumbnails = thumbnails;
        return this;
    }

    /**
     * 
     * @return
     *     The channelTitle
     */
    public String getChannelTitle() {
        return channelTitle;
    }

    /**
     * 
     * @param channelTitle
     *     The channelTitle
     */
    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public Snippet withChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
        return this;
    }

    /**
     * 
     * @return
     *     The playlistId
     */
    public String getPlaylistId() {
        return playlistId;
    }

    /**
     * 
     * @param playlistId
     *     The playlistId
     */
    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public Snippet withPlaylistId(String playlistId) {
        this.playlistId = playlistId;
        return this;
    }

    /**
     * 
     * @return
     *     The position
     */
    public int getPosition() {
        return position;
    }

    /**
     * 
     * @param position
     *     The position
     */
    public void setPosition(int position) {
        this.position = position;
    }

    public Snippet withPosition(int position) {
        this.position = position;
        return this;
    }

    /**
     * 
     * @return
     *     The resourceId
     */
    public ResourceId getResourceId() {
        return resourceId;
    }

    /**
     * 
     * @param resourceId
     *     The resourceId
     */
    public void setResourceId(ResourceId resourceId) {
        this.resourceId = resourceId;
    }

    public Snippet withResourceId(ResourceId resourceId) {
        this.resourceId = resourceId;
        return this;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Snippet withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
