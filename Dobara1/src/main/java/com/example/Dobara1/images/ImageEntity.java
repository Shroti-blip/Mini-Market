package com.example.Dobara1.images;

import com.example.Dobara1.listingitem.ListingEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

//    @Lob
//    @Basic(fetch = FetchType.LAZY)
    @Column(name = "image", columnDefinition = "bytea") // PostgreSQL binary
    private byte[] image;

    @ManyToOne
    @JoinColumn(name="listing_id", referencedColumnName = "id")
    private ListingEntity listingEntity;


//    @Transient
//    private String base64Image;
//
//    public String getBase64Image() {
//        return base64Image;
//    }
//
//    public void setBase64Image(String base64Image) {
//        this.base64Image = base64Image;
//    }

    public ImageEntity(int id, byte[] image, ListingEntity listingEntity) {
        this.id = id;
        this.image = image;
        this.listingEntity = listingEntity;
    }

    public ImageEntity() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public ListingEntity getListingEntity() {
        return listingEntity;
    }

    public void setListingEntity(ListingEntity listingEntity) {
        this.listingEntity = listingEntity;
    }
}
