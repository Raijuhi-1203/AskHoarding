package codesgesture.app.askhoardingv2.Model;

import java.io.Serializable;

public class JobModel implements Serializable {
    private String id;
    private String job_name;
    private String location;
    private String city;
    private String state;
    private String jobdesp;
    private String qualification;
    private String company_nm;
    private String company_info;
    private String isactive;
    private String ad_img;
    private String contactno;
    private String create_date;
    private String jobcat;
    private String state_nm;
    private String cityname;

    public String getState_nm() {
        return state_nm;
    }

    public void setState_nm(String state_nm) {
        this.state_nm = state_nm;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }


    // Getter Methods

    public String getId() {
        return id;
    }

    public String getJob_name() {
        return job_name;
    }

    public String getLocation() {
        return location;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getJobdesp() {
        return jobdesp;
    }

    public String getQualification() {
        return qualification;
    }

    public String getCompany_nm() {
        return company_nm;
    }

    public String getCompany_info() {
        return company_info;
    }

    public String getIsactive() {
        return isactive;
    }

    public String getAd_img() {
        return ad_img;
    }

    public String getContactno() {
        return contactno;
    }

    public String getCreate_date() {
        return create_date;
    }

    public String getJobcat() {
        return jobcat;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setJobdesp(String jobdesp) {
        this.jobdesp = jobdesp;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public void setCompany_nm(String company_nm) {
        this.company_nm = company_nm;
    }

    public void setCompany_info(String company_info) {
        this.company_info = company_info;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

    public void setAd_img(String ad_img) {
        this.ad_img = ad_img;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public void setJobcat(String jobcat) {
        this.jobcat = jobcat;
    }
}