package lk.gov.govtech.covid19.dto;

import lombok.Data;

import java.util.List;

@Data
public class Patients {

    private String name;
    private String email;
    private String address;
    private String longitude;
    private String lattitude;
    private String mobileImei;
    private List<String> caseList;
    private String nic;
    private String passport;
    private String country;
    private String age;
    private String gender;

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getAddress() {

        return address;
    }

    public void setAddress(String address) {

        this.address = address;
    }

    public String getLongitude() {

        return longitude;
    }

    public void setLongitude(String longitude) {

        this.longitude = longitude;
    }

    public String getLattitude() {

        return lattitude;
    }

    public void setLattitude(String lattitude) {

        this.lattitude = lattitude;
    }

    public String getMobileImei() {

        return mobileImei;
    }

    public void setMobileImei(String mobileImei) {

        this.mobileImei = mobileImei;
    }

    public List<String> getCaseList() {

        return caseList;
    }

    public void setCaseList(List<String> caseList) {

        this.caseList = caseList;
    }

    public String getNic() {

        return nic;
    }

    public void setNic(String nic) {

        this.nic = nic;
    }

    public String getPassport() {

        return passport;
    }

    public void setPassport(String passport) {

        this.passport = passport;
    }

    public String getCountry() {

        return country;
    }

    public void setCountry(String country) {

        this.country = country;
    }

    public String getAge() {

        return age;
    }

    public void setAge(String age) {

        this.age = age;
    }

    public String getGender() {

        return gender;
    }

    public void setGender(String gender) {

        this.gender = gender;
    }
}
