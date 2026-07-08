package codesgesture.app.askhoardingv2.Model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class MainCategoryModel implements Serializable {
    private float id;
    private String catname;
    private float isactive;


    // Getter Methods

    public float getId() {
        return id;
    }

    public String getCatname() {
        return catname;
    }

    public float getIsactive() {
        return isactive;
    }

    // Setter Methods

    public void setId(float id) {
        this.id = id;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public void setIsactive(float isactive) {
        this.isactive = isactive;
    }

    @NonNull
    @Override
    public String toString() {
        return catname;
    }

}