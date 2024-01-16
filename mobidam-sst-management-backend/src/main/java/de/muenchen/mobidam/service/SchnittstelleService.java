package de.muenchen.mobidam.service;

import de.muenchen.mobidam.domain.dtos.SchnittstelleCreateDTO;
import de.muenchen.mobidam.domain.dtos.SchnittstelleDTO;
import de.muenchen.mobidam.domain.mappers.SchnittstelleMapper;
import de.muenchen.mobidam.repository.SchnittstelleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class SchnittstelleService {

    private final SchnittstelleRepository schnittstelleRepository;
    private final SchnittstelleMapper schnittstelleMapper;

    public SchnittstelleDTO create(SchnittstelleCreateDTO schnittstelleCreateDTO){
        return schnittstelleMapper.toDTO(schnittstelleRepository.save(schnittstelleMapper.toEntity(schnittstelleCreateDTO)));
    }

    public boolean exists(SchnittstelleDTO schnittstelleDTO){
        return schnittstelleRepository.existsById(schnittstelleDTO.getId());
    }

    public Iterable<SchnittstelleDTO> getAll(){
        List<SchnittstelleDTO> dtos = new ArrayList<>();
        schnittstelleRepository.findAll().forEach(schnittstelle -> dtos.add(schnittstelleMapper.toDTO(schnittstelle)));

        return dtos;
    }

    public boolean deleteById(String id){
        UUID uuid = UUID.fromString(id);
        if(schnittstelleRepository.existsById(uuid)){
            schnittstelleRepository.deleteById(UUID.fromString(id));
            return true;
        } else {
            return false;
        }
    }

    public SchnittstelleDTO update(SchnittstelleDTO schnittstelleDTO){
        return schnittstelleMapper.toDTO(schnittstelleRepository.save(schnittstelleMapper.toEntityWithId(schnittstelleDTO)));
    }

}
