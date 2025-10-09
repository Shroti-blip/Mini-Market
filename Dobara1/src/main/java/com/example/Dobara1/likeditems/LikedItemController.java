package com.example.Dobara1.likeditems;

import com.example.Dobara1.listingitem.ListingEntity;
import com.example.Dobara1.listingitem.ListingRepository;
import com.example.Dobara1.usermaster.UserMasterEntity;
import com.example.Dobara1.usermaster.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
public class LikedItemController {


    @Autowired
    LikedRepository likedRepository;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ListingRepository listingRepository;


    @PostMapping("/{listingId}")
    @Transactional
    public ResponseEntity<?> toggleLike(@PathVariable int listingId ,
                                        @RequestParam("userId") int user_id){
        //current user ?
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        //from db
        UserMasterEntity user = userRepo.findById(user_id)
                .orElseThrow();



        ListingEntity listing = listingRepository.findById(listingId)
                .orElseThrow();

        //if already liked

        if(likedRepository.existsByUserAndListing(user , listing)){
            likedRepository.deleteByUserAndListing(user , listing);
            return ResponseEntity.ok("unliked");
        }
        else{
            LikedEntity likedEntity = new LikedEntity();
            likedEntity.setUser(user);
            likedEntity.setListing(listing);
            likedRepository.save(likedEntity);
            return ResponseEntity.ok("Liked");
        }
    }

    @GetMapping("/showlikeditem")
    public String show(Model model){
        List<LikedEntity> list = likedRepository.findAll();
        model.addAttribute("likeditem" , list);
        return "";
    }
}
