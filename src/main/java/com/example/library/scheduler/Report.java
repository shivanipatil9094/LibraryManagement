package com.example.library.scheduler;

import com.example.library.model.Book;
import com.example.library.model.helper.Email;
import com.example.library.repository.BookRepository;
import com.example.library.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class Report {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private EmailService emailService;


//    @Scheduled(fixedRate = 5000)
//    public void getReport(){
//
//        System.out.println("Report generating" + LocalDateTime.now());
//    }
////
////    @Scheduled(fixedDelay = 6000)
////    public void getReport2(){
////        System.out.println("Second Report generating " + LocalDateTime.now());
////    }
//
//    @Scheduled(initialDelay = 2000)
//    public void getReport3(){
//        System.out.println("Second Report generating " + LocalDateTime.now());
//    }
//    //***** ....min..hrs...day.....month...year
//    //@Scheduled(cron = "0*/5 * * * * ?") //every 5 min  0*/5 ...0 = 1min
//    @Scheduled(cron = "0*/30 0*/2 * * * ?")
//    //@Scheduled(cron = "0 0 3 * * ?") //daily at 3
//    //@Scheduled(cron = "0 0 0 1 * ?") //1st day of every month //last star reserved for time
//    public void getReport4(){
//        System.out.println("Second Report generating " + LocalDateTime.now());
//    }
    //@Scheduled(cron = "0 * * * * ?")
   // @Scheduled(fixedRate = 50000)
    public void getLowStock(){
        List<Book> all = bookRepository.findAll();
        List<Book> list  = all.stream().filter(
                b->Integer.valueOf(0).equals(b.getQuantity())
        ).toList();
        if(list.size()>0){
            list.forEach(book -> {
                String message = "The Book -" + book.getBookTitle()+"is out of stock";
                Email email = new Email();
                email.setTo(new String[]{"shivanipatil9094@gmail.com"});
                email.setHeader("the book "+book.getBookTitle()+"+  " + LocalDateTime.now().toString());
                email.setMessage(message);
                emailService.sendEmail(email);
            });
        }







    }




}
