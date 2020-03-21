package com.eiv.services;

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

import com.eiv.entities.LocalidadEntity;
import com.eiv.interfaces.Localidad;
import com.eiv.repositories.LocalidadRepository;
import com.eiv.services.dal.LocalidadService;
import com.eiv.testutils.ITestCfg;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LocalidadServiceIT.TestCfg.class)
public class LocalidadServiceIT {

    @Autowired private LocalidadService localidadService;
    
    @Test
    @Transactional
    public void givenLocalidadForm_whenCreate_thenNewProvincia() {
        
        Localidad localidad = new Localidad() {
            
            @Override
            public Long getProvinciaId() {
                return 1L;
            }
            
            @Override
            public String getNombre() {
                return "TEST";
            }
            
            @Override
            public String getCodigoPostal() {
                return "0000";
            }
        };
        
        LocalidadEntity localidadEntity = localidadService.save(localidad);
        Optional<LocalidadEntity> expected = localidadRepository.findById(
                localidadEntity.getId());

        assertThat(expected).isNotEmpty();
        assertThat(expected.get()).isEqualTo(localidadEntity);

        assertThat(localidadEntity.getNombre()).isEqualTo(
                expected.get().getNombre());
        assertThat(localidadEntity.getCodigoPostal()).isEqualTo(
                expected.get().getCodigoPostal());
        assertThat(localidadEntity.getProvincia()).isEqualTo(
                expected.get().getProvincia());
    }

    @Test
    @Transactional
    public void givenLocalidadForm_whenSave_thenUpdateProvincia() {
        
        Localidad localidad = new Localidad() {
            
            @Override
            public Long getProvinciaId() {
                return 2L;
            }
            
            @Override
            public String getNombre() {
                return "TEST";
            }
            
            @Override
            public String getCodigoPostal() {
                return "0000";
            }
        };
        
        LocalidadEntity localidadEntity = localidadService.save(1L, localidad);
        Optional<LocalidadEntity> expected = localidadRepository.findById(
                localidadEntity.getId());

        assertThat(expected).isNotEmpty();
        assertThat(expected.get()).isEqualTo(localidadEntity);

        assertThat(localidadEntity.getNombre()).isEqualTo(
                expected.get().getNombre());
        assertThat(localidadEntity.getCodigoPostal()).isEqualTo(
                expected.get().getCodigoPostal());
        assertThat(localidadEntity.getProvincia()).isEqualTo(
                expected.get().getProvincia());
    }

    @Test
    @Transactional
    public void givenLocalidadById_whenDelete_thenLocalidadNoExiste() {
        
        final Optional<LocalidadEntity> optional = localidadRepository.findById(1L);
        
        localidadService.delete(optional.get().getId());
        
        final Optional<LocalidadEntity> expected = localidadRepository.findById(1L);
        assertThat(expected).isEmpty();
    }
    
    @ComponentScan(basePackages = "com.eiv.services")
    public static class TestCfg extends ITestCfg {
        
        @Bean
        public DataSource getDataSource() {
            JdbcDataSource ds = new JdbcDataSource();
            ds.setUrl("jdbc:h2:mem:testdb"
                    + ";INIT=runscript from 'src/test/resources/test-localidades.sql'");
            ds.setUser("sa");
            return ds;
        }
    }
    
    @Autowired private LocalidadRepository localidadRepository;
}
