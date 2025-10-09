package com.example.Dobara1.listingitem;


import com.example.Dobara1.category.CategoryEntity;
import com.example.Dobara1.images.ImageEntity;
import com.example.Dobara1.likeditems.LikedEntity;
import com.example.Dobara1.usermaster.UserMasterEntity;
//import com.example.dobara.category.CategoryEntity;
//import com.example.dobara.images.ImageEntity;
//import com.example.dobara.transaction.TransactionEntity;
//import com.example.dobara.user.UserMasterEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "listing_master")
public class ListingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // user_id relation (assuming user_master table exists)
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserMasterEntity user;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "condition", length = 255)
    private String condition;

    @Column(name = "status", length = 200)
    private String status;

    @Column(name = "price")
    private Integer price;

    @Column(name = "color", length = 200)
    private String color;

    @Column(name = "company_name", length = 200)
    private String companyName;

    @Column(name = "location", length = 200)
    private String location;


    public ListingEntity(Integer id, UserMasterEntity user, String description, String condition, String status, Integer price, String color, String companyName, String location, CategoryEntity category, List<ImageEntity> images) {
        this.id = id;
        this.user = user;
        this.description = description;
        this.condition = condition;
        this.status = status;
        this.price = price;
        this.color = color;
        this.companyName = companyName;
        this.location = location;
        this.category = category;
        this.images = images;
    }

    public ListingEntity() {
    }

    public List<ImageEntity> getImages() {
        return images;
    }

    public void setImages(List<ImageEntity> images) {
        this.images = images;
    }

    // category relation
    @ManyToOne
    @JoinColumn(name = "cat_id", referencedColumnName = "cat_id")
    private CategoryEntity category;

    // listing has many images
//    @OneToMany(mappedBy = "listingEntity", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ImageEntity> images;


    @OneToMany(mappedBy = "listingEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER ,orphanRemoval = true)
      List<ImageEntity> images ; // initialize here

    public List<LikedEntity> getLikedItems() {
        return likedItems;
    }

    public void setLikedItems(List<LikedEntity> likedItems) {
        this.likedItems = likedItems;
    }

    @OneToMany(mappedBy = "listing", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikedEntity> likedItems;



    // listing has many transactions
//    @OneToMany(mappedBy = "listing", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<TransactionEntity> transactions;

    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public UserMasterEntity getUser() { return user; }
    public void setUser(UserMasterEntity user) { this.user = user; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public CategoryEntity getCategory() { return category; }
    public void setCategory(CategoryEntity category) { this.category = category; }

//    public List<ImageEntity> getImages() { return images; }
//    public void setImages(List<ImageEntity> images) { this.images = images; }
//
//    public List<TransactionEntity> getTransactions() { return transactions; }
//    public void setTransactions(List<TransactionEntity> transactions) { this.transactions = transactions; }

}
