package codesgesture.app.askhoardingv2.Model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class CityModel implements Serializable {
    private String id;
    private String stateid;
    private String cityname;


    // Getter Methods

    public String getId() {
        return id;
    }

    public String getStateid() {
        return stateid;
    }

    public String getCityname() {
        return cityname;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setStateid(String stateid) {
        this.stateid = stateid;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    @NonNull
    @Override
    public String toString() {
        return cityname;
    }
}