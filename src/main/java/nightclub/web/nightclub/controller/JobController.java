package nightclub.web.nightclub.controller;

import nightclub.web.nightclub.entities.Job;
import nightclub.web.nightclub.entities.UserDetails.UserDetailsEntity;
import nightclub.web.nightclub.services.JobService;
import nightclub.web.nightclub.services.impl.EmailService;
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
   private final EmailService emailService;

    public JobController(JobService jobService, EmailService emailService) {
        this.jobService = jobService;
        this.emailService = emailService;
    }

    @GetMapping("/admin/add-job")
    public String showAddJobForm() {
        return "add-job";
    }
    @PostMapping("/admin/add-job")
    public String addJob(@RequestParam("title") String title,
                         @RequestParam("description") String description,
                         @RequestParam("requirements") String requirements) {

        Job newJob = new Job();
        newJob.setTitle(title);
        newJob.setDescription(description);
        newJob.setRequirements(requirements);

        jobService.addJob(newJob);

        return "redirect:/careers";
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

                String subject = "Application Received: " + title;
                String message = "Dear " + name + ",\n\nThank you for applying for the position of " + title + ". We have received your application and will review it shortly.\n\nBest regards,\nNightclub Team";
                emailService.sendEmail(email, subject, message);
            } catch (IOException e) {
                e.printStackTrace();
                return "error";
            }
        }



        return "redirect:/home";
    }
}