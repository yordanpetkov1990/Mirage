package nightclub.web.nightclub.controller;

import nightclub.web.nightclub.entities.Job;
import nightclub.web.nightclub.entities.UserDetails.UserDetailsEntity;
import nightclub.web.nightclub.services.JobService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
public class JobController {

   private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/careers")
    public String showJobListings(Model model) {
        model.addAttribute("jobs", jobService.getAllJobs());
        return "careers";
    }

    @GetMapping("/apply/{id}")
    public String showApplyForm(@PathVariable Long id, Model model,  @AuthenticationPrincipal UserDetailsEntity userDetailsEntity) {
        model.addAttribute("jobId", id);
        model.addAttribute("name", userDetailsEntity.getFullName());
        model.addAttribute("email", userDetailsEntity.getEmail());
        return "apply-for-a-job";
    }

    @PostMapping("/apply")
    public String handleJobApplication(@RequestParam("name") String name,
                                       @RequestParam("email") String email,
                                       @RequestParam("cv") MultipartFile cv,
                                       @RequestParam("jobId") Long id) {
        Optional<Job> byId = this.jobService.findById(id);
        if(byId.isPresent()){
            try {
                Job job = byId.get();
                String title = job.getTitle();
                String uploadDirectory = "C:\\Users\\qnida\\Desktop\\NightClub\\src\\main\\resources\\static\\jobApplications\\" + title + "\\" + name + "\\";
                File uploadDir = new File(uploadDirectory);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                String filePath = uploadDirectory + cv.getOriginalFilename();
                cv.transferTo(new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
                return "error";
            }
        }


        // You can add more logic here to save application details to the database
        return "redirect:/home";
    }
}