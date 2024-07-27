package nightclub.web.nightclub.services.impl;

import nightclub.web.nightclub.entities.Job;
import nightclub.web.nightclub.entities.User;
import nightclub.web.nightclub.services.JobAddedEvent;
import nightclub.web.nightclub.services.UserService;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class JobAddedListener {


    private final JavaMailSender mailSender;


    private final UserService userService;

    public JobAddedListener(JavaMailSender mailSender, UserService userService) {
        this.mailSender = mailSender;
        this.userService = userService;
    }

    @EventListener
    public void handleJobAddedEvent(JobAddedEvent event) {
        Job job = event.getJob();
        // Get all users
        Iterable<User> users = userService.getAllUsers();

        for (User user : users) {
            sendJobAddedEmail(user, job);
        }
    }

    private void sendJobAddedEmail(User user, Job job) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("New Job Posting Added");
        message.setText("A new job has been added: \n\n" +
                "Job Title: " + job.getTitle() + "\n" +
                "Description: " + job.getDescription() + "\n" +
                "Requirements: " + job.getRequirements() + "\n\n" +
                "Visit our website to view the latest job postings.");

        mailSender.send(message);
    }
}