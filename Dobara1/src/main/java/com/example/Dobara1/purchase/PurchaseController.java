package com.example.Dobara1.purchase;

import com.example.Dobara1.listingitem.ListingEntity;
import com.example.Dobara1.listingitem.ListingRepository;
import com.example.Dobara1.usermaster.UserMasterEntity;
import com.example.Dobara1.usermaster.UserRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Date;

@Controller
public class PurchaseController {

    @Autowired
    ListingRepository listingRepository;
    @Autowired
    UserRepo userRepo;
    @Autowired
    PurchaseRepository purchaseRepository;

    @PostMapping("/purchase")
    public String purchase(@RequestParam("itemId") int itemId ,
                           @RequestParam String mode ,
                           @RequestParam int quantity , Model model,
                           @ModelAttribute PurchaseEntity purchase,
                           HttpSession session){
        //    get listing itm id
        //set buyer -- get buyer id (login user)
        //  seller id (through lid -->> find seller id)
        //  save data

        System.out.println("===================inside purchase mapping====================");

        //getting listing + seller id
        ListingEntity listing = listingRepository.findById(itemId).orElseThrow();
        model.addAttribute("listingIdItem" , listing);//no use

        UserMasterEntity seller = listing.getUser();

        //getting buyer id
        Integer userId = (Integer) session.getAttribute("user_id");
        UserMasterEntity userMaster= userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        System.out.println("Mode is " + mode);
        System.out.println("quantity is " + quantity);
        System.out.println("listing id is : " + listing.getId());
        System.out.println("===================inside purchase mapping getting id ====================");

        purchase.setPurchase_date(LocalDate.now());
        purchase.setListingEntity(listing);
        purchase.setBuyer(userMaster);
        purchase.setSeller(seller);
        purchaseRepository.save(purchase);
        System.out.println("data is getting saved.");
        return "redirect:/user/dashboard";
    }
}
