package com.misis.laba6.component;

import com.misis.laba6.gateway.HelloWorldGateway;
import com.misis.laba6.dto.HelloWorldDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ComandLineComponent implements CommandLineRunner {

    private final HelloWorldGateway helloWorldGateway;

    @Autowired
    public ComandLineComponent(HelloWorldGateway helloWorldGateway)
    {
        this.helloWorldGateway = helloWorldGateway;
    }

    @Override
    public void run(String[] args) throws Exception {
        Random random = new Random();

        for (int i = 0; i < 10; ++i)
        {
            HelloWorldDto dto = new HelloWorldDto();
            int rand = random.nextInt(100) + 1;
            System.out.println("rand: " + rand);
            dto.setMessage("Fedor Kotikov", "18.08.2002", rand);
            helloWorldGateway.send(dto);

        }

    }

}
