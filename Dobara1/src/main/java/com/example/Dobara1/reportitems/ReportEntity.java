package com.example.Dobara1.reportitems;

import com.example.Dobara1.listingitem.ListingEntity;
import com.example.Dobara1.usermaster.UserMasterEntity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="item_report")
public class ReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String reason;

    String status ="pending";

    @Column(name = "report_date")
    LocalDate reportDate = LocalDate.now();


    @ManyToOne
    @JoinColumn(name = "admin_id" , nullable = false)
    UserMasterEntity userMaster;

    @ManyToOne
    @JoinColumn(name = "item_id" , nullable = false)
    ListingEntity listing;



    public ReportEntity(int id, String reason, String status, LocalDate reportDate, UserMasterEntity userMaster, ListingEntity listing) {
        this.id = id;
        this.reason = reason;
        this.status = status;
        this.reportDate = reportDate;
        this.userMaster = userMaster;
        this.listing = listing;
    }

    public ReportEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public UserMasterEntity getUserMaster() {
        return userMaster;
    }

    public void setUserMaster(UserMasterEntity userMaster) {
        this.userMaster = userMaster;
    }

    public ListingEntity getListing() {
        return listing;
    }

    public void setListing(ListingEntity listing) {
        this.listing = listing;
    }
}
