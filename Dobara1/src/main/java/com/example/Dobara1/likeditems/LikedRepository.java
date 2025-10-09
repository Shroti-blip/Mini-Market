package com.example.Dobara1.likeditems;

import com.example.Dobara1.listingitem.ListingEntity;
import com.example.Dobara1.usermaster.UserMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikedRepository extends JpaRepository<LikedEntity , Integer> {

boolean existsByUserAndListing(UserMasterEntity user , ListingEntity listing);
    void deleteByUserAndListing(UserMasterEntity user , ListingEntity listing);
    List<LikedEntity> findByUser(UserMasterEntity user);

}
