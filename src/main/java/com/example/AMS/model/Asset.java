package com.example.AMS.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Asset {
    @Id
    private String assetID;

    @ManyToOne
    @JoinColumn(name = "locationID")
    private Location location;

    private String assetType;
    private String assetName;
    private String brand;
    private String model;
    private Date purchaseDate;
    private String specification;
    private Boolean activeStatus;
    private String warrantyID;
    private Date warrantyDate;
    private String warrantyPeriod;
    private String purchaseStore;

    // relationships
    @OneToMany(mappedBy = "asset")
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "asset")
    private List<Maintain> maintenances;

    @OneToMany(mappedBy = "asset")
    private List<Transfer> transfers;

    @OneToMany(mappedBy = "asset")
    private List<Condemn> condemns;

    @OneToMany(mappedBy = "asset")
    private List<YearlyVerification> verifications;

    @OneToMany(mappedBy = "asset")
    private List<User> users;

    public String getAssetID() {
        return assetID;
    }

    public void setAssetID(String assetID) {
        this.assetID = assetID;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getWarrantyID() {
        return warrantyID;
    }

    public void setWarrantyID(String warrantyID) {
        this.warrantyID = warrantyID;
    }

    public Date getWarrantyDate() {
        return warrantyDate;
    }

    public void setWarrantyDate(Date warrantyDate) {
        this.warrantyDate = warrantyDate;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getPurchaseStore() {
        return purchaseStore;
    }

    public void setPurchaseStore(String purchaseStore) {
        this.purchaseStore = purchaseStore;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public List<Maintain> getMaintenances() {
        return maintenances;
    }

    public void setMaintenances(List<Maintain> maintenances) {
        this.maintenances = maintenances;
    }

    public List<Transfer> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<Transfer> transfers) {
        this.transfers = transfers;
    }

    public List<Condemn> getCondemns() {
        return condemns;
    }

    public void setCondemns(List<Condemn> condemns) {
        this.condemns = condemns;
    }

    public List<YearlyVerification> getVerifications() {
        return verifications;
    }

    public void setVerifications(List<YearlyVerification> verifications) {
        this.verifications = verifications;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
