package my.vono.web.service;



import lombok.RequiredArgsConstructor;
import my.vono.web.entity.Member;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class Init {
    private final InitService initService;


    @PostConstruct
    public void init(){
        initService.Init();

    }


    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService{
        private final EntityManager em;

        public void Init(){

//         Member me=Member.createMemeber("d");
//         em.persist(me);
//         
//         Member me1=Member.createMemeber("d2");
//         em.persist(me1);

         
           
          

        }
        }


}
