package com.eiv.das;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.eiv.das.ProvinciaDas;
import com.eiv.entities.ProvinciaEntity;
import com.eiv.enums.RegionEnum;
import com.eiv.interfaces.Provincia;
import com.eiv.repository.ProvinciaRepository;
import com.eiv.testutils.ITestCfg;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProvinciaDasIT.TestCfg.class)
public class ProvinciaDasIT {

    @Autowired private ProvinciaDas provinciaDas;
    
    @Test
    @Transactional
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
        
        ProvinciaEntity provinciaEntity = provinciaDas.save(provincia);
        Optional<ProvinciaEntity> expected = provinciaRepository.findById(
                provinciaEntity.getId());

        assertThat(expected).isNotEmpty();
        assertThat(expected.get()).isEqualTo(provinciaEntity);

        assertThat(provinciaEntity.getNombre()).isEqualTo(
                expected.get().getNombre());
        assertThat(provinciaEntity.getRegion()).isEqualTo(
                expected.get().getRegion());
    }

    @Test
    @Transactional
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
        
        ProvinciaEntity provinciaEntity = provinciaDas.save(1L, provincia);
        Optional<ProvinciaEntity> expected = provinciaRepository.findById(1L);

        assertThat(expected).isNotEmpty();
        assertThat(expected.get()).isEqualTo(provinciaEntity);

        assertThat(provinciaEntity.getNombre()).isEqualTo(
                expected.get().getNombre());
        assertThat(provinciaEntity.getRegion()).isEqualTo(
                expected.get().getRegion());
    }
    
    @Test
    @Transactional
    public void givenProvinciaById_whenDelete_thenProvinciaNoExiste() {
        
        final Optional<ProvinciaEntity> optional = provinciaRepository.findById(1L);
        
        provinciaDas.delete(optional.get().getId());
        
        final Optional<ProvinciaEntity> expected = provinciaRepository.findById(1L);
        assertThat(expected).isEmpty();
    }
    
    @ComponentScan(basePackages = "com.eiv.das")
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
    
    @Autowired private ProvinciaRepository provinciaRepository;
}
