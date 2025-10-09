package com.example.Dobara1.images;

import com.example.Dobara1.listingitem.ListingEntity;
import com.example.Dobara1.listingitem.ListingRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class ImageController {

    @Autowired
    ImageRepository imageRepository;
    @Autowired
    ImageService imageService;
    @Autowired
    ListingRepository listingRepository;


    @GetMapping("/uploadimages")
    public String uploadImages( Model model , HttpSession session){
       Integer listingId = (Integer) session.getAttribute("listingId");
        model.addAttribute("listingId" , listingId);
        return "images/uploadimage";
    }


    @PostMapping("/images/upload")
    public String uploadImages(HttpSession session ,
                               @RequestParam("images") MultipartFile[] files) throws IOException {
       ListingEntity listingEntity = (ListingEntity) session.getAttribute("listingData");

        for (MultipartFile file : files) {
            System.out.println("HII");
            ImageEntity image = new ImageEntity();
            image.setImage(file.getBytes());
            image.setListingEntity(listingEntity);
            imageRepository.save(image);
        }

        return "redirect:/user/dashboard";
    }

    @GetMapping("/deleteitem/{id}")//not so sage method
    public String deleteItems(@PathVariable int id ){
//        System.out.println("==================Error===================");
        imageRepository.deleteById(id);
        listingRepository.deleteById(id);
        System.out.println("==================Done===================");
        return "redirect:/user/dashboard";
    }






//    @GetMapping("/uploadimages/{listingId}")
//    public String uploadImages(@PathVariable int listingId, Model model){
//        model.addAttribute("listingId" , listingId);
//        return "images/uploadimage";
//    }



//    @PostMapping("/images/upload")
//    public String uploadImages(@RequestParam("listingId") int listingId,
//                               @RequestParam("images") MultipartFile[] files) throws IOException {
//        ListingEntity listing = listingRepository.findById(listingId)
//                .orElseThrow(() -> new RuntimeException("Listing not found"));
//
//        for (MultipartFile file : files) {
//            System.out.println("HII");
//            ImageEntity image = new ImageEntity();
//            image.setImage(file.getBytes());
//            image.setListingEntity(listing);
//            imageRepository.save(image);
//        }
//
//        return "index";
//    }







}
