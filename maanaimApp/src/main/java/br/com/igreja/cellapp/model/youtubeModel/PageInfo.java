
package br.com.igreja.cellapp.model.youtubeModel;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class PageInfo {

    private int totalResults;
    private int resultsPerPage;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The totalResults
     */
    public int getTotalResults() {
        return totalResults;
    }

    /**
     * 
     * @param totalResults
     *     The totalResults
     */
    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public PageInfo withTotalResults(int totalResults) {
        this.totalResults = totalResults;
        return this;
    }

    /**
     * 
     * @return
     *     The resultsPerPage
     */
    public int getResultsPerPage() {
        return resultsPerPage;
    }

    /**
     * 
     * @param resultsPerPage
     *     The resultsPerPage
     */
    public void setResultsPerPage(int resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }

    public PageInfo withResultsPerPage(int resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
        return this;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public PageInfo withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
