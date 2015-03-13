/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 *
 * @author Kasper
 */
class DataObject {
    private String fName;
    private String lName;
    private int phone;

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public DataObject(String fName, String lName, int phone, String email, String city, String street, int zip) {
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
        this.email = email;
        this.city = city;
        this.street = street;
        this.zip = zip;
    }

    @Override
    public String toString() {
        return "DataObject{" + "fName=" + fName + ", lName=" + lName + ", phone=" + phone + ", email=" + email + ", city=" + city + ", street=" + street + ", zip=" + zip + '}';
    }
    private String email;
    private String city;
    private String street;
    private int zip;
}
