package com.example.Dobara1.likeditems;

import com.example.Dobara1.listingitem.ListingEntity;
import com.example.Dobara1.usermaster.UserMasterEntity;
import jakarta.persistence.*;

@Entity
@Table(name="likeditem")
public class LikedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "uid", nullable = false)
    private UserMasterEntity user;

    @ManyToOne
    @JoinColumn(name = "lid", nullable = false)
    private ListingEntity listing;

    // Constructor without id (Hibernate will generate id)
    public LikedEntity(UserMasterEntity user, ListingEntity listing) {
        this.user = user;
        this.listing = listing;
    }

    // Default constructor required by JPA
    public LikedEntity() {}

    // Getters & Setters
    public int getId() { return id; }

    public UserMasterEntity getUser() { return user; }
    public void setUser(UserMasterEntity user) { this.user = user; }

    public ListingEntity getListing() { return listing; }
    public void setListing(ListingEntity listing) { this.listing = listing; }
}
