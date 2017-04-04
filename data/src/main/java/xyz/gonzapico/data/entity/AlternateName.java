
package xyz.gonzapico.data.entity;


public class AlternateName {

    private String name;
    private String lang;
    private Boolean isShortName;
    private Boolean isPreferredName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Boolean getIsShortName() {
        return isShortName;
    }

    public void setIsShortName(Boolean isShortName) {
        this.isShortName = isShortName;
    }

    public Boolean getIsPreferredName() {
        return isPreferredName;
    }

    public void setIsPreferredName(Boolean isPreferredName) {
        this.isPreferredName = isPreferredName;
    }

}
