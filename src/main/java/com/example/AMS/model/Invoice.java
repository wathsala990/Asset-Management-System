package com.example.AMS.model;
import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Invoice {
    @Id
    private String invoiceId;
    private Date invoiceDate;
    private int itemCount;

    @ManyToOne
    @JoinColumn(name = "assetId")
    private Asset asset;

    @ManyToOne
    @JoinColumn(name = "venderId")
    private Vender vender;

    // Getters and Setters
    public String getInvoiceId() { return invoiceId; }
    public void setInvoiceId(String invoiceId) { this.invoiceId = invoiceId; }
    public Date getInvoiceDate() { return invoiceDate; }
    public void setInvoiceDate(Date invoiceDate) { this.invoiceDate = invoiceDate; }
    public int getItemCount() { return itemCount; }
    public void setItemCount(int itemCount) { this.itemCount = itemCount; }
    public Asset getAsset() { return asset; }
    public void setAsset(Asset asset) { this.asset = asset; }
    public Vender getVender() { return vender; }
    public void setVender(Vender vender) { this.vender = vender; }
}
