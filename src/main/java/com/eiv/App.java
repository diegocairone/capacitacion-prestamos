package com.eiv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.eiv.entities.ProvinciaEntity;
import com.eiv.enums.RegionEnum;
import com.eiv.services.ProvinciaService;

public class App {

    public static final ApplicationContext CONTEXT;
    
    static {
        CONTEXT = new ClassPathXmlApplicationContext("app-config.xml");
    }

    public static void main(String[] args) {
        App app = CONTEXT.getBean(App.class);
        app.run();
    }    

    public void run() {
        
        List<ProvinciaEntity> provinciaEntities = provinciaService.findAll(
                q -> q.region.eq(RegionEnum.PAMPEANA));
        
        provinciaEntities.forEach(provinciaEntity -> {
            System.out.println("Provincia: " +  provinciaEntity);
        });
    }
    
    @Autowired ProvinciaService provinciaService;
}
