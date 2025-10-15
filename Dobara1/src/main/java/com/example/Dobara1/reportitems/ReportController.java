package com.example.Dobara1.reportitems;

import com.example.Dobara1.listingitem.ListingEntity;
import com.example.Dobara1.listingitem.ListingRepository;
import com.example.Dobara1.usermaster.UserMasterEntity;
import com.example.Dobara1.usermaster.UserRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class ReportController {

    @Autowired
    ListingRepository listingRepository;

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    UserRepo userRepo;


@PostMapping("/admin/reportitem")
    public String removeItem(@RequestParam("itemId") int itemId,
                             @RequestParam("reason") String reason,
                             Model model, HttpSession session,
                             RedirectAttributes redirectAttributes) {
        try {

            ListingEntity listing = listingRepository.findById(itemId).orElseThrow();
            model.addAttribute("listingIdItem" , listing);//no use

            UserMasterEntity seller = listing.getUser();

            //getting buyer id
            Integer userId = (Integer) session.getAttribute("user_id");
            UserMasterEntity userMaster= userRepo.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));



            ReportEntity reportEntity = new ReportEntity();
            reportEntity.setReason(reason);//set reason ,, date and status are already set.
            reportEntity.setListing(listing);// saving item id.
            reportEntity.setUserMaster(userMaster);//saving admin id
            System.out.println("Item ID " + itemId + " reported by admin. Reason: " + reason);

            reportRepository.save(reportEntity);

            // Flash message for dashboard
            redirectAttributes.addFlashAttribute("message", "Item reported successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to report item. Please try again.");
            e.printStackTrace();
        }

        return "redirect:/admin/dashboard";
    }

    @GetMapping("/showreport")
    public String showReport(Model model , HttpSession session){
        Integer userId = (Integer) session.getAttribute("user_id");
        ReportEntity reportEntity =  reportRepository.findById(userId)
               .orElseThrow(() -> new RuntimeException("user not found"));

        model.addAttribute("reports" , reportEntity);
    return "";
    }

}
