package com.eiv.das;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.eiv.das.ProvinciaDas;
import com.eiv.entities.ProvinciaEntity;
import com.eiv.enums.RegionEnum;
import com.eiv.exceptions.NotFoundServiceException;
import com.eiv.interfaces.Provincia;
import com.eiv.repository.ProvinciaRepository;
import com.querydsl.core.types.dsl.BooleanExpression;

@RunWith(MockitoJUnitRunner.class)
public class ProvinciaDasTest {

    @InjectMocks private ProvinciaDas provinciaDas;
    
    @Test
    public void givenProvinciaId_whenFindById_thenOptionalProvincia() {
        
        provinciaDas.findById(1L);
        Mockito.verify(provinciaRepository).findById(Mockito.anyLong());
    }

    @Test
    public void whenFindAll_thenListProvincia() {
        
        provinciaDas.findAll();
        Mockito.verify(provinciaRepository).findAll();
    }

    @Test
    public void givenBoolExp_whenFindAll_thenListProvincia() {
        
        provinciaDas.findAll(q -> q.id.isNotNull());
        Mockito.verify(provinciaRepository).findAll(Mockito.any(BooleanExpression.class));
    }

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
        
        ProvinciaEntity provinciaEntity = provinciaDas.save(provincia);
        
        assertThat(provinciaEntity.getNombre()).isEqualTo(
                provincia.getNombre());
        assertThat(provinciaEntity.getRegion()).isEqualTo(
                provincia.getRegion());
        
        Mockito.verify(provinciaRepository).save(Mockito.any(ProvinciaEntity.class));
    }

    @Test
    public void givenProvinciaForm_whenUpdate_thenUpdateProvincia() {
        
        final Provincia provincia = new Provincia() {
            
            @Override
            public RegionEnum getRegion() {
                return RegionEnum.CUYO;
            }
            
            @Override
            public String getNombre() {
                return "TEST";
            }
        };
        
        final ProvinciaEntity expected = new ProvinciaEntity();
        
        expected.setId(1L);
        expected.setNombre("ORIGEN");
        expected.setRegion(RegionEnum.NORDESTE);
        
        Mockito.when(provinciaRepository.findById(1L)).thenReturn(
                Optional.of(expected));
        
        ProvinciaEntity provinciaEntity = provinciaDas.save(1L, provincia);

        assertThat(provinciaEntity.getNombre()).isEqualTo(
                provincia.getNombre());
        assertThat(provinciaEntity.getRegion()).isEqualTo(
                provincia.getRegion());

        Mockito.verify(provinciaRepository).save(Mockito.any(ProvinciaEntity.class));
    }
   
    @Test
    public void givenProvinciaNoExiste_whenUpdate_thenThrowException() {
        
        assertThatThrownBy(() -> {
            provinciaDas.save(1L, Mockito.any());
        }).isInstanceOf(NotFoundServiceException.class);
        
        Mockito.verify(provinciaRepository, Mockito.never()).save(
                Mockito.any(ProvinciaEntity.class));
    }

    @Test
    public void givenProvinciaPorId_whenDelete_thenDeleteProvincia() {
        
        final ProvinciaEntity expected = new ProvinciaEntity();
        
        expected.setId(1L);
        expected.setNombre("ORIGEN");
        expected.setRegion(RegionEnum.NORDESTE);
        
        Mockito.when(provinciaRepository.findById(1L)).thenReturn(
                Optional.of(expected));
        
        provinciaDas.delete(1L);

        Mockito.verify(provinciaRepository).findById(Mockito.anyLong());
        Mockito.verify(provinciaRepository).delete(Mockito.any(ProvinciaEntity.class));
    }

    @Test
    public void givenProvinciaNoExiste_whenDelete_thenThrowException() {

        assertThatThrownBy(() -> {
            provinciaDas.delete(Mockito.anyLong());
        }).isInstanceOf(NotFoundServiceException.class);
        
        Mockito.verify(provinciaRepository).findById(Mockito.anyLong());
        
        Mockito.verify(provinciaRepository, Mockito.never()).save(
                Mockito.any(ProvinciaEntity.class));
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Mock private ProvinciaRepository provinciaRepository;
}
