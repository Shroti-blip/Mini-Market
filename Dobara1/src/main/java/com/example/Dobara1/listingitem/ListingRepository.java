package com.example.Dobara1.listingitem;

import com.example.Dobara1.usermaster.UserMasterEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ListingRepository extends JpaRepository<ListingEntity , Integer> {
    List<ListingEntity> findByUser(UserMasterEntity user);//for relation.
    List<ListingEntity> findByUserId(int id);

//    List<ListingEntity> getByCategoryName(String name);
//    List<ListingEntity> getByCategoryId(int categoryId);
//    List<ListingEntity> getByCategoryCat_id(int  categoryId);
    List<ListingEntity> getByPrice(int price);

    @Transactional
    @Query("SELECT l FROM ListingEntity l WHERE l.category.cat_id = :catId")
    List<ListingEntity> findByCategoryId(@Param("catId") int catId);

//    Key point: JPQL parameter name (:something) and @Param("something") must match. The Java variable
//    name can be anything.


}
