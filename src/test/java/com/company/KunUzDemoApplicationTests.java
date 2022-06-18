package com.company;

import com.company.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KunUzDemoApplicationTests {

    @Autowired
    private ArticleRepository articleRepository;
    @Test
    void contextLoads() {

      //  articleRepository.getLast4ArticleByType()
    }

}
