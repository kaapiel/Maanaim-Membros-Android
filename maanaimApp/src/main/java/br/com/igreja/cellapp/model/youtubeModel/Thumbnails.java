
package br.com.igreja.cellapp.model.youtubeModel;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Thumbnails {

    private Default _default;
    private Medium medium;
    private High high;
    private Standard standard;
    private Maxres maxres;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The _default
     */
    public Default getDefault() {
        return _default;
    }

    /**
     * 
     * @param _default
     *     The default
     */
    public void setDefault(Default _default) {
        this._default = _default;
    }

    public Thumbnails withDefault(Default _default) {
        this._default = _default;
        return this;
    }

    /**
     * 
     * @return
     *     The medium
     */
    public Medium getMedium() {
        return medium;
    }

    /**
     * 
     * @param medium
     *     The medium
     */
    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public Thumbnails withMedium(Medium medium) {
        this.medium = medium;
        return this;
    }

    /**
     * 
     * @return
     *     The high
     */
    public High getHigh() {
        return high;
    }

    /**
     * 
     * @param high
     *     The high
     */
    public void setHigh(High high) {
        this.high = high;
    }

    public Thumbnails withHigh(High high) {
        this.high = high;
        return this;
    }

    /**
     * 
     * @return
     *     The standard
     */
    public Standard getStandard() {
        return standard;
    }

    /**
     * 
     * @param standard
     *     The standard
     */
    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    public Thumbnails withStandard(Standard standard) {
        this.standard = standard;
        return this;
    }

    /**
     * 
     * @return
     *     The maxres
     */
    public Maxres getMaxres() {
        return maxres;
    }

    /**
     * 
     * @param maxres
     *     The maxres
     */
    public void setMaxres(Maxres maxres) {
        this.maxres = maxres;
    }

    public Thumbnails withMaxres(Maxres maxres) {
        this.maxres = maxres;
        return this;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Thumbnails withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
