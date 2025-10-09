package com.example.Dobara1.purchase;

import com.example.Dobara1.listingitem.ListingEntity;
import com.example.Dobara1.usermaster.UserMasterEntity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="purchase")
public class PurchaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    int quantity;

    String mode;

    public PurchaseEntity(int quantity, String mode) {
        this.quantity = quantity;
        this.mode = mode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    LocalDate purchase_date;

    @ManyToOne
    @JoinColumn(name = "listing_id", referencedColumnName = "id" )
     ListingEntity listingEntity;

    @ManyToOne
    @JoinColumn(name = "buyer_id", referencedColumnName = "user_id")
     UserMasterEntity buyer;

    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "user_id")
      UserMasterEntity seller;

    public PurchaseEntity() {
    }

    public PurchaseEntity(int id, LocalDate purchase_date, ListingEntity listingEntity, UserMasterEntity buyer, UserMasterEntity seller) {
        this.id = id;
        this.purchase_date = purchase_date;
        this.listingEntity = listingEntity;
        this.buyer = buyer;
        this.seller = seller;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(LocalDate purchase_date) {
        this.purchase_date = purchase_date;
    }

    public ListingEntity getListingEntity() {
        return listingEntity;
    }

    public void setListingEntity(ListingEntity listingEntity) {
        this.listingEntity = listingEntity;
    }

    public UserMasterEntity getBuyer() {
        return buyer;
    }

    public void setBuyer(UserMasterEntity buyer) {
        this.buyer = buyer;
    }

    public UserMasterEntity getSeller() {
        return seller;
    }

    public void setSeller(UserMasterEntity seller) {
        this.seller = seller;
    }
}
