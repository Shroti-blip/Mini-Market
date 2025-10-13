package com.example.Dobara1.listingitem;

import com.example.Dobara1.category.CategoryEntity;
import com.example.Dobara1.category.CategoryRepository;
import com.example.Dobara1.images.ImageEntity;
import com.example.Dobara1.images.ImageRepository;
import com.example.Dobara1.purchase.PurchaseEntity;
import com.example.Dobara1.usermaster.UserMasterEntity;
import com.example.Dobara1.usermaster.UserRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ListingController {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ListingRepository listingRepository;
    @Autowired
    UserRepo userRepo;
    @Autowired
    ImageRepository imageRepository;

    //working without it

    @GetMapping("/listingitem")
    public String createListingForm(Model model , HttpSession session) {
        model.addAttribute("listing", new ListingEntity());
//        CategoryEntity category = (CategoryEntity) categoryRepository.findAll();
        model.addAttribute("categorydata" , categoryRepository.findAll());
        return "listingitem/additem";
    }



    @PostMapping("listing/save")
    public String save(Model model, HttpSession session ,
                       @ModelAttribute("listing") ListingEntity listingEntity){
        CategoryEntity category = (CategoryEntity) categoryRepository.findById(listingEntity.getCategory().getCat_id()).orElse(null);
        listingEntity.setCategory(category);

       Integer userId = (Integer) session.getAttribute("user_id");
       UserMasterEntity userMaster = userRepo.findById(userId).orElse(null);
       if(userMaster !=null){
           listingEntity.setUser(userMaster);//fk set here
       }

       ListingEntity listing =  listingRepository.save(listingEntity);
        System.out.println("CAT ID: "+category.getCat_id());
        System.out.println("ID: "+listingEntity.getId());

        session.setAttribute("listingId", listingEntity.getId());
        session.setAttribute("listingData" , listingEntity);
        System.out.println("Id of LISTING ITEM: " + listingEntity.getId());
        System.out.println("Info of LISTING ITEM: " +listingEntity.getCategory() +" " + listingEntity.getColor());
        return "redirect:/uploadimages";
//        return  "redirect:/user/dashboard";
//        return "redirect:/user/dashboard?sellTab=true&listingId=" + listingEntity.getId();
    }

@GetMapping("/showUserItem")
public String showUserItems(Model model , HttpSession session){
   Integer userId = (Integer) session.getAttribute("user_id");
    System.out.println("userid is : "+userId);
        ListingEntity listing = listingRepository.findById(userId).orElse(null);
        model.addAttribute("listingUserData" , listing);
        return "listingitem/showallitems";
}

    //usefull
    @GetMapping("images/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable int id ){
        ImageEntity imageEntity = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageEntity.getImage());
    }

    @GetMapping("/moreinfo")
    public String getMoreInfo(@RequestParam("itemId") int itemId ,
                              Model model , HttpSession session){
        ListingEntity list = listingRepository.findById(itemId)
                        .orElseThrow(() -> new RuntimeException("Not Found"));
//        if (list != null && list.getCategory() != null) {
//            list.getCategory().getName(); // forces loading
//        }
        model.addAttribute("listing" , list);


//      add a model obj for purchase entity like for saving data.
        model.addAttribute("purchase" , new PurchaseEntity());

        Integer userId = (Integer) session.getAttribute("user_id");
        UserMasterEntity userMaster= userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        model.addAttribute("user_master" , userMaster);
        return "listingitem/moreinfo";
    }



//    private String type; if problem related to image type then use that method.
//     .contentType(MediaType.parseMediaType(imageEntity.getType()))




//    @GetMapping("/showallitems1")
//    public String showAllItems(Model model, HttpSession session) {
//        Integer userId = (Integer) session.getAttribute("user_id");
//        if (userId == null) {
//            return "redirect:/login";
//        }
//        UserMasterEntity userMaster = userRepo.findById(userId).orElse(null);
//        List<ListingEntity> list = listingRepository.findByUser(userMaster);
//        model.addAttribute("listdata", list);
//        return "listingitem/showallitems";
//    }



//    @GetMapping("/listing/search/category")
//    public String searchWithCategory(@RequestParam int categoryId){
//          return   "index";
//    }

//    @GetMapping("/listing/search/price")
//    public String searchWithPrice(@RequestParam int price , Model model){
//        List<ListingEntity> list = listingRepository.getByPrice(price);
//        model.addAttribute("byPrice" , list);
//        System.out.println("list data is : " + list);
//        return "redirect:/user/dashboard";
//    }












}




















//      try {
//              for(MultipartFile file:files){
//        if(!file.isEmpty()){
//ImageEntity image = new ImageEntity();
//                    image.setImage(file.getBytes());
//        image.setListingEntity(listingEntity);
////                    if (listingEntity.getImages() == null) {
////                        listingEntity.setImages(new ArrayList<>());
////                    }
//                    listingEntity.getImages().add(image);
//
//                }
//                        }
//                        }catch (Exception e){
//        System.out.println("problem is here in image:-"+e);
//        }
