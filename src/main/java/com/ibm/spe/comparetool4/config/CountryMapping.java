package com.ibm.spe.comparetool4.config;

/**
 * Created by luis on 2015/2/6.
 */
public class CountryMapping {

    private String country;
    private String countryCode;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {
        return String.format("CountryMapping[%s - %s]\n", countryCode, country);
    }
}
