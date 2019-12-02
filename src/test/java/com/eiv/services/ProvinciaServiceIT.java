package com.eiv.services;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eiv.entities.ProvinciaEntity;
import com.eiv.enums.RegionEnum;
import com.eiv.interfaces.Provincia;
import com.eiv.testutils.ITestCfg;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProvinciaServiceIT.TestCfg.class)
public class ProvinciaServiceIT {

    @Autowired private ProvinciaService provinciaService;
    
    @Test
    public void givenProvinciaForm_whenCreate_thenNewProvincia() {
        
        Provincia provincia = new Provincia() {
            
            @Override
            public RegionEnum getRegion() {
                return RegionEnum.CUYO;
            }
            
            @Override
            public String getNombre() {
                return "TEST";
            }
        };
        
        ProvinciaEntity provinciaEntity = provinciaService.nueva(provincia);
        
        assertThat(provinciaEntity.getId()).isEqualTo(6L);
        assertThat(provinciaEntity.getNombre()).isEqualTo("TEST");
        assertThat(provinciaEntity.getRegion()).isEqualTo(RegionEnum.CUYO);
    }

    @Test
    public void givenProvinciaForm_whenUpdate_thenUpdateProvincia() {
        
        Provincia provincia = new Provincia() {
            
            @Override
            public RegionEnum getRegion() {
                return RegionEnum.CUYO;
            }
            
            @Override
            public String getNombre() {
                return "TEST";
            }
        };
        
        ProvinciaEntity provinciaEntity = provinciaService.actualizar(1L, provincia);
        
        assertThat(provinciaEntity.getId()).isEqualTo(1L);
        assertThat(provinciaEntity.getNombre()).isEqualTo("TEST");
        assertThat(provinciaEntity.getRegion()).isEqualTo(RegionEnum.CUYO);
    }
    
    @ComponentScan(basePackages = "com.eiv.services")
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
