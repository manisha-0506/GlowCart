package com.example.skinCareApp.config;

import com.stripe.Stripe;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {


    public void configureStripe() {
        Stripe.apiKey = "your_stripe_secret_key"; // Add your Stripe secret key here
    }
}
