/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Alex
 */
@Entity
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String street;
    private String additionalInfo;
    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private List<InfoEntity> infoEntitys;
    
    @ManyToOne
    private CityInfo cityInfo;
    
    public void addInfoEntity(InfoEntity ie){
        infoEntitys.add(ie);
    }

    public Address() {
    }

    public Address(String street, String additionalInfo) {
        this.street = street;
        this.additionalInfo = additionalInfo;
    }

    public List<InfoEntity> getInfoEntitys() {
        return infoEntitys;
    }

    public void setInfoEntitys(List<InfoEntity> infoEntitys) {
        this.infoEntitys = infoEntitys;
    }

    
    
    public void setCityInfo(CityInfo cityInfo) {
        this.cityInfo = cityInfo;
    }

    
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
    
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    
}
