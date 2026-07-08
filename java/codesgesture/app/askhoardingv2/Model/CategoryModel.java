package codesgesture.app.askhoardingv2.Model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class CategoryModel implements Serializable {
    private String id;
    private String cat_name;
    private String isactive;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    private String icon;


    // Getter Methods

    public String getId() {
        return id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public String getIsactive() {
        return isactive;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

    @NonNull
    @Override
    public String toString() {
        return cat_name;
    }
}