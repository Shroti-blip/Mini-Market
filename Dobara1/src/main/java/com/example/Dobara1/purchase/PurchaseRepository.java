package com.example.Dobara1.purchase;

import com.example.Dobara1.listingitem.ListingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity  , Integer> {
    List<PurchaseEntity> findBySeller_UserId(Integer sellerId);
    List<PurchaseEntity> findByBuyerId(Integer userId);

}
