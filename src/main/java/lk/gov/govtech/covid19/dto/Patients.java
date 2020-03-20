package lk.gov.govtech.covid19.dto;

import lombok.Data;

import java.util.List;

@Data
public class Patients {

    private String name;
    private String mobileNumber;
    private String email;
    private String address;
    private String longitude;
    private String lattitude;
    private String mobileImei;
    private List<String> caseList;

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getMobileNumber() {

        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {

        this.mobileNumber = mobileNumber;
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
}
