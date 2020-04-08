package com.eiv.das;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.eiv.das.TipoDocumentoDas;
import com.eiv.entities.TipoDocumentoEntity;
import com.eiv.exceptions.NotFoundServiceException;
import com.eiv.interfaces.TipoDocumento;
import com.eiv.repository.TipoDocumentoRepository;

@RunWith(MockitoJUnitRunner.class)
public class TipoDocumentoDasTest {

    @InjectMocks private TipoDocumentoDas tipoDocumentoDas;
    
    @Test
    public void givenTipoDocumentoId_whenFindById_thenOptionalTipoDocumento() {
        
        tipoDocumentoDas.findById(1L);
        Mockito.verify(tipoDocumentoRepository).findById(Mockito.anyLong());
    }

    @Test
    public void whenFindAll_thenListTipoDocumento() {
        
        tipoDocumentoDas.findAll();
        Mockito.verify(tipoDocumentoRepository).findAll();
    }

    @Test
    public void givenTipoDocumentoForm_whenSave_thenNewTipoDocumento() {
        
        TipoDocumento tipoDocumento = new TipoDocumento() {
            
            @Override
            public Boolean getValidarComoCuit() {
                return false;
            }
            
            @Override
            public String getNombre() {
                return "TEST";
            }
            
            @Override
            public String getAbreviatura() {
                return "TEST";
            }
        };
        
        TipoDocumentoEntity tipoDocumentoEntity = tipoDocumentoDas.save(tipoDocumento);
        
        Assertions.assertThat(tipoDocumentoEntity.getId()).isNull();
        Assertions.assertThat(tipoDocumentoEntity.getValidarComoCuit()).isEqualTo(
                tipoDocumento.getValidarComoCuit());
        Assertions.assertThat(tipoDocumentoEntity.getNombre()).isEqualTo(
                tipoDocumento.getNombre());
        Assertions.assertThat(tipoDocumentoEntity.getAbreviatura()).isEqualTo(
                tipoDocumento.getAbreviatura());
        
        Mockito.verify(tipoDocumentoRepository).save(Mockito.any(TipoDocumentoEntity.class));
    }
    
    @Test
    public void givenTipoDocumentoForm_whenSave_thenUpdateTipoDocumento() {

        final TipoDocumento tipoDocumento = new TipoDocumento() {
            
            @Override
            public Boolean getValidarComoCuit() {
                return false;
            }
            
            @Override
            public String getNombre() {
                return "TEST";
            }
            
            @Override
            public String getAbreviatura() {
                return "TEST";
            }
        };
        
        TipoDocumentoEntity mock = new TipoDocumentoEntity();
        
        mock.setId(1L);
        mock.setNombre("ORIGEN");
        mock.setAbreviatura("ORIGEN");
        mock.setValidarComoCuit(true);
        
        Mockito.when(tipoDocumentoRepository.findById(1L)).thenReturn(Optional.of(mock));

        TipoDocumentoEntity tipoDocumentoEntity = tipoDocumentoDas.save(1L, tipoDocumento);
        
        Assertions.assertThat(tipoDocumentoEntity.getId()).isNotNull();
        Assertions.assertThat(tipoDocumentoEntity.getValidarComoCuit()).isEqualTo(
                tipoDocumento.getValidarComoCuit());
        Assertions.assertThat(tipoDocumentoEntity.getNombre()).isEqualTo(
                tipoDocumento.getNombre());
        Assertions.assertThat(tipoDocumentoEntity.getAbreviatura()).isEqualTo(
                tipoDocumento.getAbreviatura());
        
        Mockito.verify(tipoDocumentoRepository).findById(Mockito.anyLong());
        Mockito.verify(tipoDocumentoRepository).save(Mockito.any(TipoDocumentoEntity.class));
    }

    @Test
    public void givenTipoDocumentoFormIdNoExiste_whenSave_thenUpdateTipoDocumento() {

        final TipoDocumento tipoDocumento = new TipoDocumento() {
            
            @Override
            public Boolean getValidarComoCuit() {
                return false;
            }
            
            @Override
            public String getNombre() {
                return "TEST";
            }
            
            @Override
            public String getAbreviatura() {
                return "TEST";
            }
        };
        
        Assertions.assertThatThrownBy(() -> {
            tipoDocumentoDas.save(1L, tipoDocumento);
        }).isInstanceOf(NotFoundServiceException.class);
                
        Mockito.verify(tipoDocumentoRepository).findById(
                Mockito.anyLong());
        Mockito.verify(tipoDocumentoRepository, Mockito.never()).save(
                Mockito.any(TipoDocumentoEntity.class));
    }

    @Test
    public void givenTipoDocumentoPorId_whenDelete_thenDeleteTipoDocumento() {
        
        final TipoDocumentoEntity expected = new TipoDocumentoEntity();
        
        expected.setId(1L);
        expected.setNombre("ORIGEN");
        expected.setAbreviatura("ORIGEN");
        expected.setValidarComoCuit(true);
        
        Mockito.when(tipoDocumentoRepository.findById(1L)).thenReturn(
                Optional.of(expected));
        
        tipoDocumentoDas.delete(1L);

        Mockito.verify(tipoDocumentoRepository).findById(Mockito.anyLong());
        Mockito.verify(tipoDocumentoRepository).delete(Mockito.any(TipoDocumentoEntity.class));
    }

    @Test
    public void givenProvinciaNoExiste_whenDelete_thenThrowException() {

        assertThatThrownBy(() -> {
            tipoDocumentoDas.delete(Mockito.anyLong());
        }).isInstanceOf(NotFoundServiceException.class);
        
        Mockito.verify(tipoDocumentoRepository).findById(Mockito.anyLong());
        
        Mockito.verify(tipoDocumentoRepository, Mockito.never()).save(
                Mockito.any(TipoDocumentoEntity.class));
    }
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Mock private TipoDocumentoRepository tipoDocumentoRepository;
}
