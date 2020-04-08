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

import com.eiv.dao.TipoDocumentoDao;
import com.eiv.das.TipoDocumentoDas;
import com.eiv.entities.TipoDocumentoEntity;
import com.eiv.interfaces.TipoDocumento;
import com.eiv.testutils.ITestCfg;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TipoDocumentoDasIT.TestCfg.class)
public class TipoDocumentoDasIT {

    @Autowired private TipoDocumentoDas tipoDocumentoDas;
    
    @Test
    @Transactional
    public void givenTipoDocumentoForm_whenCreate_thenNewTipoDocumento() {
        
        TipoDocumento tipoDocumento = new TipoDocumento() {
            
            @Override
            public Boolean getValidarComoCuit() {
                return true;
            }
            
            @Override
            public String getNombre() {
                return "CLAVE UNICA DE IDENTIFICACION TRIBUTARIA";
            }
            
            @Override
            public String getAbreviatura() {
                return "CUIT";
            }
        };
        
        TipoDocumentoEntity tipoDocumentoEntity = tipoDocumentoDas.save(tipoDocumento);
        Optional<TipoDocumentoEntity> expected = tipoDocumentoDao.findById(
                tipoDocumentoEntity.getId());

        assertThat(expected).isNotEmpty();
        assertThat(expected.get()).isEqualTo(tipoDocumentoEntity);

        assertThat(tipoDocumentoEntity.getNombre()).isEqualTo(
                tipoDocumento.getNombre());
        assertThat(tipoDocumentoEntity.getAbreviatura()).isEqualTo(
                tipoDocumento.getAbreviatura());
        assertThat(tipoDocumentoEntity.getValidarComoCuit()).isEqualTo(
                tipoDocumento.getValidarComoCuit());
    }
    
    @Test
    @Transactional
    public void givenTipoDocumentoForm_whenUpdate_thenUpdateTipoDocumento() {

        TipoDocumento tipoDocumento = new TipoDocumento() {
            
            @Override
            public Boolean getValidarComoCuit() {
                return true;
            }
            
            @Override
            public String getNombre() {
                return "--TEST--";
            }
            
            @Override
            public String getAbreviatura() {
                return "TEST";
            }
        };

        TipoDocumentoEntity tipoDocumentoEntity = tipoDocumentoDas.save(1L, tipoDocumento);
        Optional<TipoDocumentoEntity> expected = tipoDocumentoDao.findById(1L);

        assertThat(expected).isNotEmpty();
        assertThat(expected.get()).isEqualTo(tipoDocumentoEntity);
        
        assertThat(tipoDocumentoEntity.getNombre()).isEqualTo(
                tipoDocumento.getNombre());
        assertThat(tipoDocumentoEntity.getAbreviatura()).isEqualTo(
                tipoDocumento.getAbreviatura());
        assertThat(tipoDocumentoEntity.getValidarComoCuit()).isEqualTo(
                tipoDocumento.getValidarComoCuit());
    }
    
    @Test
    @Transactional
    public void givenTipoDocumentoId_whenDelete_thenNoExisteTipoDocumento() {
        
        Optional<TipoDocumentoEntity> expected = tipoDocumentoDao.findById(1L);
        assertThat(expected).isNotEmpty();
        
        tipoDocumentoDas.delete(1L);
        
        boolean exist = tipoDocumentoDao.existsById(1L);
        assertThat(exist).isFalse();
    }

    @ComponentScan(basePackages = "com.eiv.das")
    public static class TestCfg extends ITestCfg {
        
        @Bean
        public DataSource getDataSource() {
            JdbcDataSource ds = new JdbcDataSource();
            ds.setUrl("jdbc:h2:mem:testdb"
                    + ";INIT=runscript from 'src/test/resources/test-tipos-documentos.sql'");
            ds.setUser("sa");
            return ds;
        }
    }
    
    @Autowired private TipoDocumentoDao tipoDocumentoDao;
}
