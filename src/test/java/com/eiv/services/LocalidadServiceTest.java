package com.eiv.services;

import static org.assertj.core.api.Assertions.assertThat;

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

import com.eiv.entities.LocalidadEntity;
import com.eiv.entities.ProvinciaEntity;
import com.eiv.enums.RegionEnum;
import com.eiv.exceptions.NotFoundServiceException;
import com.eiv.interfaces.Localidad;
import com.eiv.repositories.LocalidadRepository;
import com.eiv.repositories.ProvinciaRepository;
import com.eiv.services.dal.LocalidadService;

@RunWith(MockitoJUnitRunner.class)
public class LocalidadServiceTest {

    @InjectMocks 
    private LocalidadService localidadService;
    
    @Mock private LocalidadRepository localidadRepository;
    @Mock private ProvinciaRepository provinciaRepository;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenLocalidadForm_whenCreate_thenNewLocalidad() {
        
        final Localidad localidad = new Localidad() {
            
            @Override
            public Long getProvinciaId() {
                return 1L;
            }
            
            @Override
            public String getNombre() {
                return "--TEST--";
            }
            
            @Override
            public String getCodigoPostal() {
                return "--CP--";
            }
        };
        
        final ProvinciaEntity provinciaMock = new ProvinciaEntity();
        
        provinciaMock.setId(1L);
        provinciaMock.setNombre("ORIGEN");
        provinciaMock.setRegion(RegionEnum.NORDESTE);
        
        Mockito.when(provinciaRepository.findById(1L)).thenReturn(
                Optional.of(provinciaMock));
        
        
        LocalidadEntity localidadEntity = localidadService.save(localidad);
        
        assertThat(localidadEntity.getNombre()).isEqualTo(
                localidad.getNombre());
        assertThat(localidadEntity.getCodigoPostal()).isEqualTo(
                localidad.getCodigoPostal());
        assertThat(localidadEntity.getProvincia().getId()).isEqualTo(
                localidad.getProvinciaId());
                
        Mockito.verify(provinciaRepository).findById(Mockito.anyLong());
        Mockito.verify(localidadRepository).save(Mockito.any(LocalidadEntity.class));
    }
    
    @Test
    public void givenLocalidadFormProvinciaIdNoExiste_whenCreate_thenThrowException() {
        
        final Localidad localidad = new Localidad() {
            
            @Override
            public Long getProvinciaId() {
                return 1L;
            }
            
            @Override
            public String getNombre() {
                return "--TEST--";
            }
            
            @Override
            public String getCodigoPostal() {
                return "--CP--";
            }
        };
        
        Throwable throwable = Assertions.catchThrowable(() -> {
            localidadService.save(localidad);
        });
        
        assertThat(throwable).isInstanceOf(NotFoundServiceException.class);
                
        Mockito.verify(provinciaRepository).findById(
                Mockito.anyLong());
        Mockito.verify(localidadRepository, Mockito.never()).save(
                Mockito.any(LocalidadEntity.class));
    }

    @Test
    public void givenLocalidadFormProvinciaCambia_whenUpdate_thenUpdateLocalidad() {
        
        final Localidad localidad = new Localidad() {
            
            @Override
            public Long getProvinciaId() {
                return 2L;
            }
            
            @Override
            public String getNombre() {
                return "--TEST--";
            }
            
            @Override
            public String getCodigoPostal() {
                return "--CP--";
            }
        };
        
        final ProvinciaEntity provinciaActual = new ProvinciaEntity();
        
        provinciaActual.setId(1L);
        provinciaActual.setNombre("ORIGEN");
        provinciaActual.setRegion(RegionEnum.NORDESTE);
        
        final ProvinciaEntity provinciaDestino = new ProvinciaEntity();
        
        provinciaDestino.setId(2L);
        provinciaDestino.setNombre("ORIGEN");
        provinciaDestino.setRegion(RegionEnum.NORDESTE);
        
        Mockito.when(provinciaRepository.findById(2L)).thenReturn(
                Optional.of(provinciaDestino));
        

        final LocalidadEntity expected = new LocalidadEntity();
        
        expected.setId(1L);
        expected.setNombre("ORIGEN");
        expected.setCodigoPostal("CP");
        expected.setProvincia(provinciaActual);
        
        Mockito.when(localidadRepository.findById(1L)).thenReturn(
                Optional.of(expected));
        
        
        LocalidadEntity localidadEntity = localidadService.save(1L, localidad);
        
        assertThat(localidadEntity.getNombre()).isEqualTo(
                localidad.getNombre());
        assertThat(localidadEntity.getCodigoPostal()).isEqualTo(
                localidad.getCodigoPostal());
        assertThat(localidadEntity.getProvincia().getId()).isEqualTo(
                localidad.getProvinciaId());
                
        Mockito.verify(localidadRepository).findById(Mockito.anyLong());
        Mockito.verify(provinciaRepository).findById(Mockito.anyLong());
        Mockito.verify(localidadRepository).save(Mockito.any(LocalidadEntity.class));
    }

    @Test
    public void givenLocalidadFormProvinciaNoCambia_whenUpdate_thenUpdateLocalidad() {
        
        final Localidad localidad = new Localidad() {
            
            @Override
            public Long getProvinciaId() {
                return 1L;
            }
            
            @Override
            public String getNombre() {
                return "--TEST--";
            }
            
            @Override
            public String getCodigoPostal() {
                return "--CP--";
            }
        };
        
        final ProvinciaEntity provinciaActual = new ProvinciaEntity();
        
        provinciaActual.setId(1L);
        provinciaActual.setNombre("ORIGEN");
        provinciaActual.setRegion(RegionEnum.NORDESTE);
        
        final LocalidadEntity expected = new LocalidadEntity();
        
        expected.setId(1L);
        expected.setNombre("ORIGEN");
        expected.setCodigoPostal("CP");
        expected.setProvincia(provinciaActual);
        
        Mockito.when(localidadRepository.findById(1L)).thenReturn(
                Optional.of(expected));
        
        
        LocalidadEntity localidadEntity = localidadService.save(1L, localidad);
        
        assertThat(localidadEntity.getNombre()).isEqualTo(
                localidad.getNombre());
        assertThat(localidadEntity.getCodigoPostal()).isEqualTo(
                localidad.getCodigoPostal());
        assertThat(localidadEntity.getProvincia().getId()).isEqualTo(
                localidad.getProvinciaId());
        
        Mockito.verify(localidadRepository).findById(Mockito.anyLong());
        Mockito.verify(provinciaRepository, Mockito.never()).findById(Mockito.anyLong());
        Mockito.verify(localidadRepository).save(Mockito.any(LocalidadEntity.class));
    }

    @Test
    public void givenLocalidadFormLocalidadIdNoExiste_whenUpdate_thenUpdateLocalidad() {
        
        final Localidad localidad = new Localidad() {
            
            @Override
            public Long getProvinciaId() {
                return 1L;
            }
            
            @Override
            public String getNombre() {
                return "--TEST--";
            }
            
            @Override
            public String getCodigoPostal() {
                return "--CP--";
            }
        };

        Throwable throwable = Assertions.catchThrowable(() -> {
            localidadService.save(1L, localidad);
        });
        
        assertThat(throwable)
                .isInstanceOf(NotFoundServiceException.class)
                .hasMessageContaining("LOCALIDAD");
                
        Mockito.verify(localidadRepository).findById(
                Mockito.anyLong());
        Mockito.verify(provinciaRepository, Mockito.never()).findById(
                Mockito.anyLong());
        Mockito.verify(localidadRepository, Mockito.never()).save(
                Mockito.any(LocalidadEntity.class));
    }

    @Test
    public void givenLocalidadFormProvinciaIdNoExiste_whenUpdate_thenUpdateLocalidad() {
        
        final Localidad localidad = new Localidad() {
            
            @Override
            public Long getProvinciaId() {
                return 1L;
            }
            
            @Override
            public String getNombre() {
                return "--TEST--";
            }
            
            @Override
            public String getCodigoPostal() {
                return "--CP--";
            }
        };

        final ProvinciaEntity provinciaExpected = new ProvinciaEntity();
        
        provinciaExpected.setId(0L);
        provinciaExpected.setNombre("ORIGEN");
        provinciaExpected.setRegion(RegionEnum.NORDESTE);
        
        final LocalidadEntity expected = new LocalidadEntity();
        
        expected.setId(1L);
        expected.setNombre("ORIGEN");
        expected.setCodigoPostal("CP");
        expected.setProvincia(provinciaExpected);
        
        Mockito.when(localidadRepository.findById(1L)).thenReturn(
                Optional.of(expected));
        
        Throwable throwable = Assertions.catchThrowable(() -> {
            localidadService.save(1L, localidad);
        });
        
        assertThat(throwable)
                .isInstanceOf(NotFoundServiceException.class)
                .hasMessageContaining("PROVINCIA");
                
        Mockito.verify(localidadRepository).findById(
                Mockito.anyLong());
        Mockito.verify(provinciaRepository).findById(
                Mockito.anyLong());
        Mockito.verify(localidadRepository, Mockito.never()).save(
                Mockito.any(LocalidadEntity.class));
    }
    
    @Test
    public void givenLocalidadPorId_whenDelete_thenDeleteLocalidad() {

        final LocalidadEntity expected = new LocalidadEntity();
        expected.setId(1L);
        
        Mockito.when(localidadRepository.findById(1L)).thenReturn(
                Optional.of(expected));

        localidadService.delete(1L);

        Mockito.verify(localidadRepository).findById(Mockito.anyLong());
        Mockito.verify(localidadRepository).delete(Mockito.any(LocalidadEntity.class));
    }

}
