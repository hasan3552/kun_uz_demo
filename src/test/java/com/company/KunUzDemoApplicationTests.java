package com.company;

import com.company.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@SpringBootTest
class KunUzDemoApplicationTests {

    @Autowired
    private ArticleRepository articleRepository;
    @GetMapping("")
    private String test() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String currentPrincipalName = authentication.getName();
        System.out.println(currentPrincipalName);
        System.out.println(authentication.getDetails());
        // authentication.getAuthorities()

        User user = (User) authentication.getPrincipal();
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());

        Collection<GrantedAuthority> roles = user.getAuthorities();
        for (GrantedAuthority role : roles) {
            System.out.println(role.getAuthority());
        }

        return "Profile";
    }


    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

}
