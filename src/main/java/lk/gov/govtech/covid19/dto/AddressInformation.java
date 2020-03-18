package lk.gov.govtech.covid19.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;

/**
 * Represents the address information.
 */
@Data
public class AddressInformation {
    String fullAddress;
    String addressLine1;
    String addressLine2;
    String city;
    String postalCode;
    String stateProvince;
    String country;
    
    private String generateFromComponents() {
        List<String> lines = new ArrayList<>();
        if (!StringUtils.isEmpty(this.addressLine1)) {
            lines.add(this.addressLine1);
        }
        if (!StringUtils.isEmpty(this.addressLine2)) {
            lines.add(this.addressLine2);
        }
        List<String> otherComponents = new ArrayList<>();
        if (!StringUtils.isEmpty(this.city)) {
            otherComponents.add(this.city);
        }
        if (!StringUtils.isEmpty(this.stateProvince)) {
            otherComponents.add(this.stateProvince);
        }
        if (!StringUtils.isEmpty(this.postalCode)) {
            otherComponents.add(this.postalCode);
        }
        if (!otherComponents.isEmpty()) {
            lines.add(String.join(",", otherComponents));
        }
        if (!StringUtils.isEmpty(this.country)) {
            lines.add(this.country);
        }
        if (!lines.isEmpty()) {
            return String.join("\n", lines);
        } else {
            return null;
        }
    }
    
    @Override
    public String toString() {
        // if full address is available, we will just use that
        if (!StringUtils.isEmpty(this.fullAddress)) {
            return this.fullAddress;
        }
        // or else, we will construct it using the components
        return this.generateFromComponents();
    }
    
}
