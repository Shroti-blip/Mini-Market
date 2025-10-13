package com.example.Dobara1.usermaster;

import com.example.Dobara1.category.CategoryEntity;
import com.example.Dobara1.category.CategoryRepository;
import com.example.Dobara1.images.ImageEntity;
import com.example.Dobara1.likeditems.LikedEntity;
import com.example.Dobara1.likeditems.LikedRepository;
import com.example.Dobara1.listingitem.ListingEntity;
import com.example.Dobara1.listingitem.ListingRepository;
import com.example.Dobara1.purchase.PurchaseEntity;
import com.example.Dobara1.reportitems.ReportEntity;
import com.example.Dobara1.reportitems.ReportRepository;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.management.relation.Role;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @Autowired
    UserRepo userRepo;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ListingRepository listingRepository;
    @Autowired
    LikedRepository likedRepository;
    @Autowired
    ReportRepository reportRepository;

    @GetMapping("/getregister")
    public String getPage(Model model){
        model.addAttribute("userdata" , new UserMasterEntity());
        return "usermaster/register";
    }

    @GetMapping("/home")
    public String getHomePage(){
        return "dashboard/homepage";
    }


    @PostMapping("/process")
    @ResponseBody
    public String saveData(@RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String address,
                           @RequestParam String password,
                           @RequestParam String contact,
                           @RequestParam UserMasterEntity.Role role,
                           @RequestParam("profile_pic") MultipartFile file,
                           Model model) {

        UserMasterEntity entity = new UserMasterEntity();
        entity.setName(name);
        entity.setEmail(email);
        entity.setAddress(address);
        entity.setPassword(password);
        entity.setContact(contact);
        entity.setRole(role);

        try {
            if (file != null && !file.isEmpty()) {
                entity.setProfile_pic(file.getBytes());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        UserMasterEntity existingUser = userRepo.findByEmail(email).orElse(null);

        if (existingUser != null) {
            model.addAttribute("error", "Email already exists");
            System.out.println("Problem is here in email thing");
            return "usermaster/register";
        }


        userService.save(entity);
        return "usermaster/login";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "usermaster/login";
    }

    @PostMapping("/login")
    public String getLogin(@RequestParam String email ,@RequestParam String password ,
                           Model model , HttpSession session){
        UserMasterEntity userMaster = userService.login(email , password);

        if(userMaster ==null){
            model.addAttribute("error" , "Email And Password are incorrect.");
            return "usermaster/login";
        }

        session.setAttribute("user_id" , userMaster.getId());
        System.out.println("user id is : "+userMaster.getId());

        switch (userMaster.getRole()){
            case ADMIN:
                return "redirect:/admin/dashboard";
            case USER:
                return "redirect:/user/dashboard";
            default:
                model.addAttribute("error" , "Unknown Role.");
                return "login";
        }

    }


    @GetMapping("/admin/dashboard")
    public String getAdminDashboard(HttpSession session , Model model){
        Integer userId = (Integer) session.getAttribute("user_id");
        UserMasterEntity userMaster = userRepo.findById(userId).orElse(null);
        if(userMaster == null){
            model.addAttribute("error" , "user not found");
            return "login";
        }
        model.addAttribute("user_master" , userMaster);

//        for showing all items
        List<ListingEntity> list = listingRepository.findAll();
        model.addAttribute("listdata" , list);

        return "dashboard/admin";
    }

//    @PostMapping("/admin/removeitem")
//    public String removeItem(@RequestParam("itemId") int itemId,
//                             @RequestParam("reason") String reason,
//                             RedirectAttributes redirectAttributes) {
//        try {
//
//            listingRepository.deleteById(itemId);
//            System.out.println("Item ID " + itemId + " removed by admin. Reason: " + reason);
//
//            // Flash message for dashboard
//            redirectAttributes.addFlashAttribute("message", "Item removed successfully!");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", "Failed to remove item. Please try again.");
//            e.printStackTrace();
//        }
//
//        return "redirect:/admin/dashboard";
//    }


    //    User Dashboard .
    @GetMapping("/user/dashboard")
    public String getUserDashboard(HttpSession session , Model model ,
                                   @RequestParam(name = "categoryId", required = false) Integer categoryId){
    Integer userId = (Integer) session.getAttribute("user_id");
    UserMasterEntity userMaster= userRepo.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

    //set base data.
        model.addAttribute("listing", new ListingEntity());
        model.addAttribute("categorydata" , categoryRepository.findAll());
        model.addAttribute("user_master"  , userMaster);


    if(userMaster == null){
        model.addAttribute("error" , "UserID not found");
        return "login";
    }

    //for a particular user.
        List<ListingEntity> listing = listingRepository.findByUser(userMaster);
        model.addAttribute("listingUserData" , listing);

        // count of user’s listings
        int totalUploaded = listing.size();
        model.addAttribute("totalUploaded" , totalUploaded);

        System.out.println("category Id is : " + categoryId);

        //code for images and listing_data , for finding all.
        if(categoryId != null){
            List<ListingEntity> list = listingRepository.findByCategoryId(categoryId);
            model.addAttribute("listdata" , list);
            System.out.println("Inside category one");
        }
        else{
            List<ListingEntity> list = listingRepository.findAll();
            model.addAttribute("listdata" , list);
            System.out.println("For finding all");
        }



         //code for showing all liked items for purchase section.//??
        List<LikedEntity> likedItems = likedRepository.findAll();
        //extract ids from likedentity
        List<Integer> likedListingIds = likedItems.stream()
                        .map(like -> like.getListing().getId())
                                .toList();
        model.addAttribute("likedListingIds" , likedListingIds);
        model.addAttribute("likedItems2", likedItems);//no use


        //code for showing those liked item in purchase section
      List<LikedEntity> likedEntities =  likedRepository.findByUser(userMaster);
      int totalLikedItems = likedEntities.size();
      model.addAttribute("totalLikedItems" , totalLikedItems);
      model.addAttribute("likedItems" , likedEntities);


//      add a model obj for purchase entity like for saving data.
        model.addAttribute("purchase" , new PurchaseEntity());

//         for showing reported items
        List<ReportEntity> reportEntity =  reportRepository.findByListing_User_Id(userId);
        System.out.println("Report : ===========================================");

        model.addAttribute("reports" , reportEntity);

        // default tab = upload
        model.addAttribute("activeSellTab", "upload");

        return "dashboard/user";
    }


    @GetMapping("/user/findbycategory")
    public String getById(Model model,
                          @RequestParam(name = "categoryId", required = false) Integer categoryId){
        if(categoryId != null){
            List<ListingEntity> list = listingRepository.findByCategoryId(categoryId);
            model.addAttribute("listdata" , list);
            System.out.println("Inside category one");
        }
        else{
            List<ListingEntity> list = listingRepository.findAll();
            model.addAttribute("listdata" , list);
            System.out.println("For finding all");
        }
        return "redirect:/user/dashboard?categoryId=" + categoryId;
    }



    @GetMapping("/userphoto")
    public ResponseEntity<byte []> getProfilePic(HttpSession session , Model model){
        Integer userId = (Integer) session.getAttribute("user_id");
        UserMasterEntity userMaster = userRepo.findById(userId).orElse(null);
        if(userMaster == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(userMaster.getProfile_pic());
    }


    @GetMapping("/updateprofile")
    public String updateProfile( @RequestParam String name , @RequestParam String email,
                                @RequestParam String address , @RequestParam String contact,
                                HttpSession session , MultipartFile file){
        Integer userId = (Integer) session.getAttribute("user_id");

        try{
            Optional<UserMasterEntity> optional = userRepo.findById(userId);
            if(optional.isPresent()){
                UserMasterEntity userMaster = optional.get();
//                userMaster.setProfile_pic(file.getBytes());
                userMaster.setName(name);
                userMaster.setAddress(address);
                userMaster.setEmail(email);
                userMaster.setContact(contact);
                System.out.println("===============before update");
                userRepo.save(userMaster);
                System.out.println("===========after update================.");
            }else {
                System.out.println("User not found!");
            }
        }catch (Exception e){
            System.out.println("Exception here : " + e);
        }


        return "redirect:/user/dashboard";
    }




    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

}









//Not working.
//@GetMapping("/userphoto")
//@ResponseBody
//public byte[] getUserPhoto(HttpSession session) {
//    Long userId = (Long) session.getAttribute("user_id");
//    UserMasterEntity user = userRepo.findById(userId).orElse(null);
//    return user.getProfilePic(); // e.g., byte[] image from DB
//}



//For changing Tab code
//    User Dashboard .
//@GetMapping("/user/dashboard")
//public String getUserDashboard(HttpSession session , Model model,
//                               @RequestParam(value = "sellTab", required = false) Boolean sellTab,
//                               @RequestParam(value = "listingId", required = false) Integer listingId){
//    Integer userId = (Integer) session.getAttribute("user_id");
//    UserMasterEntity userMaster= userRepo.findById(userId).orElse(null);
//    model.addAttribute("listing", new ListingEntity());
//    model.addAttribute("categorydata" , categoryRepository.findAll());
//    if(userMaster == null){
//        model.addAttribute("error" , "UserID not found");
//        return "login";
//    }
//
    // Tell Thymeleaf which tab should be active
//        if (sellTab != null && sellTab) {
//            model.addAttribute("activeTab", "sell");
//        } else {
//            model.addAttribute("activeTab", "home");
//        }
//
//    // Send listingId if available
//    if (listingId != null) {
//        model.addAttribute("listingId", listingId);
//    }
//    model.addAttribute("user_master"  , userMaster);
//    return "dashboard/user";
//}




//Controller ka kaam sirf request/response handle karna hai.
//Service ka kaam business logic handle karna hai.
//Repository ka kaam database handle karna hai.
