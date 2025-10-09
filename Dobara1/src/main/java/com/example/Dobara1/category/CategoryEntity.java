package com.example.Dobara1.category;

//import com.example.Dobara.listingitem.ListingEntity;
import com.example.Dobara1.listingitem.ListingEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="category_master")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int cat_id;

    String name;

    // One category → Many listings
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<ListingEntity> listings;

    public CategoryEntity() {
    }

    public CategoryEntity(int cat_id, String name, List<ListingEntity> listings) {
        this.cat_id = cat_id;
        this.name = name;
        this.listings = listings;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ListingEntity> getListings() {
        return listings;
    }

    public void setListings(List<ListingEntity> listings) {
        this.listings = listings;
    }
}