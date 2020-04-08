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

import com.eiv.dao.ProvinciaDao;
import com.eiv.das.ProvinciaDas;
import com.eiv.entities.ProvinciaEntity;
import com.eiv.enums.RegionEnum;
import com.eiv.exceptions.NotFoundServiceException;
import com.eiv.interfaces.Provincia;
import com.querydsl.core.types.dsl.BooleanExpression;

@RunWith(MockitoJUnitRunner.class)
public class ProvinciaDasTest {

    @InjectMocks private ProvinciaDas provinciaDas;
    
    @Test
    public void givenProvinciaId_whenFindById_thenOptionalProvincia() {
        
        provinciaDas.findById(1L);
        Mockito.verify(provinciaDao).findById(Mockito.anyLong());
    }

    @Test
    public void whenFindAll_thenListProvincia() {
        
        provinciaDas.findAll();
        Mockito.verify(provinciaDao).findAll();
    }

    @Test
    public void givenBoolExp_whenFindAll_thenListProvincia() {
        
        provinciaDas.findAll(q -> q.id.isNotNull());
        Mockito.verify(provinciaDao).findAll(Mockito.any(BooleanExpression.class));
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
        
        Mockito.verify(provinciaDao).save(Mockito.any(ProvinciaEntity.class));
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
        
        Mockito.when(provinciaDao.findById(1L)).thenReturn(
                Optional.of(expected));
        
        ProvinciaEntity provinciaEntity = provinciaDas.save(1L, provincia);

        assertThat(provinciaEntity.getNombre()).isEqualTo(
                provincia.getNombre());
        assertThat(provinciaEntity.getRegion()).isEqualTo(
                provincia.getRegion());

        Mockito.verify(provinciaDao).save(Mockito.any(ProvinciaEntity.class));
    }
   
    @Test
    public void givenProvinciaNoExiste_whenUpdate_thenThrowException() {
        
        assertThatThrownBy(() -> {
            provinciaDas.save(1L, Mockito.any());
        }).isInstanceOf(NotFoundServiceException.class);
        
        Mockito.verify(provinciaDao, Mockito.never()).save(
                Mockito.any(ProvinciaEntity.class));
    }

    @Test
    public void givenProvinciaPorId_whenDelete_thenDeleteProvincia() {
        
        final ProvinciaEntity expected = new ProvinciaEntity();
        
        expected.setId(1L);
        expected.setNombre("ORIGEN");
        expected.setRegion(RegionEnum.NORDESTE);
        
        Mockito.when(provinciaDao.findById(1L)).thenReturn(
                Optional.of(expected));
        
        provinciaDas.delete(1L);

        Mockito.verify(provinciaDao).findById(Mockito.anyLong());
        Mockito.verify(provinciaDao).delete(Mockito.any(ProvinciaEntity.class));
    }

    @Test
    public void givenProvinciaNoExiste_whenDelete_thenThrowException() {

        assertThatThrownBy(() -> {
            provinciaDas.delete(Mockito.anyLong());
        }).isInstanceOf(NotFoundServiceException.class);
        
        Mockito.verify(provinciaDao).findById(Mockito.anyLong());
        
        Mockito.verify(provinciaDao, Mockito.never()).save(
                Mockito.any(ProvinciaEntity.class));
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Mock private ProvinciaDao provinciaDao;
}
