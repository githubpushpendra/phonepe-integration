package com.phonepe.phonepepi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping
public class PhonePeCon {

    @Autowired
    private InitiatePayment initiatePayment;

    @GetMapping("/")
    public RedirectView InitiateP() {
        System.out.println("Payment initiation is requested");
        String url = initiatePayment.initiate(1000);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(url);
        return redirectView;
    }
    @PostMapping("/payment-callback/{mid}/{mtId}")
    public String handlePaymentResonse(@PathVariable String mid, @PathVariable String mtId){
        System.out.println("payment-callback url is fired" );
        return initiatePayment.getStatus();
    }
}

