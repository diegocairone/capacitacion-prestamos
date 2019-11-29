package com.eiv.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eiv.entities.ProvinciaEntity;
import com.eiv.testutils.ITestCfg;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProvinciaRepositoryIT.TestCfg.class)
public class ProvinciaRepositoryIT {

    @Autowired ProvinciaRepository provinciaRepository;
    
    @Test
    public void givenAllProvincias_whenFindById_thenFindProvincia() {
        
        List<ProvinciaEntity> provinciaEntities = provinciaRepository.findAll();
        
        assertThat(provinciaEntities).hasSize(5);
        
        provinciaEntities.forEach(item -> {
            
            Optional<ProvinciaEntity> optional = provinciaRepository.findById(item.getId());
            
            assertThat(optional)
                    .contains(item);
        });
    }

    public static class TestCfg extends ITestCfg {
        
        @Bean
        public DataSource getDataSource() {
            JdbcDataSource ds = new JdbcDataSource();
            ds.setUrl("jdbc:h2:mem:testdb"
                    + ";INIT=runscript from 'src/test/resources/test-provincias.sql'");
            ds.setUser("sa");
            return ds;
        }
    }
}
