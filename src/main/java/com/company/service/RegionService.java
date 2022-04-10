package com.company.service;

import com.company.dto.RegionDTO;
import com.company.entity.RegionEntity;
import com.company.enums.Language;
import com.company.enums.RegionType;
import com.company.exceptions.BadRequestException;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public RegionDTO createRegion(RegionDTO region) {
        RegionEntity entity = toEntity(new RegionEntity(), region);
        regionRepository.save(entity);
        region.setId(entity.getId());
        return region;
    }

    public RegionDTO updateRegion(RegionDTO dto) {
        if (dto.getId() == null) {
            throw new BadRequestException("Region-Id can not be null");
        }
        Optional<RegionEntity> optionalRegion = regionRepository.findById(dto.getId());
        if (optionalRegion.isEmpty()) {
            throw new ItemNotFoundException("region not found");
        }
        RegionEntity region = optionalRegion.get();
        toEntity(region, dto);
        regionRepository.save(region);
        return dto;
    }

    private RegionEntity toEntity(RegionEntity entity, RegionDTO dto) {
        entity.setName_ru(dto.getName_ru());
        entity.setName_uz(dto.getName_uz());
        entity.setType(dto.getType());
        if (dto.getParentId() != null) {
            entity.setParent(get(dto.getParentId()));
        }
        return entity;
    }

    private RegionDTO toDto(RegionEntity entity) {
        RegionDTO dto = new RegionDTO();
        dto.setId(entity.getId());
        dto.setName_ru(entity.getName_ru());
        dto.setName_uz(entity.getName_uz());
        dto.setType(entity.getType());
        dto.setParentId(entity.getParent().getId());
        return dto;
    }

    public void deleteRegion(Integer regionId) {
        if (regionRepository.existsById(regionId)) {
            regionRepository.deleteById(regionId);
        } else throw new BadRequestException("Region not found");
    }

    public RegionEntity get(Integer id) {
        return regionRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Region not found"));
    }

    public RegionDTO getById(Integer id, Language language) {
        if (language == null) {
            throw new BadRequestException("Language can not be null");
        }
        RegionEntity entity = get(id);
        if (language.equals(Language.UZ)) {
            entity.setName_ru(null);
            return toDto(entity);
        }
        entity.setName_uz(null);
        return toDto(entity);
    }

    public List<RegionDTO> getAll() {
        List<RegionEntity> entityList = regionRepository.findAll();
        return entityList.stream().map(this::toDto).toList();
    }

    public List<RegionDTO> getRegionAll(Language language) {
        List<RegionDTO> dtoList;
        if (language == null) {
            throw new BadRequestException("Language can not be null");
        }
        if (language.equals(Language.UZ)) {
            dtoList = regionRepository.findAllParent(RegionType.REGION).stream().map(r -> {
                r.setName_ru(null);
                return toDto(r);
            }).toList();
        } else {
            dtoList = regionRepository.findAllParent(RegionType.REGION).stream().map(r -> {
                r.setName_uz(null);
                return toDto(r);
            }).toList();
        }

        return dtoList;
    }

    public List<RegionDTO> getRegionListById(Integer parentId, Language language) {
        List<RegionEntity> entityList = regionRepository.findAllByParent(get(parentId));
        List<RegionDTO> dtoList=new LinkedList<>();
        if (!entityList.isEmpty()) {
            if (language.equals(Language.UZ)) {
                dtoList = entityList.stream().map(r -> {
                    r.setName_ru(null);
                    return toDto(r);
                }).toList();
                return dtoList;
            }else{
                dtoList=entityList.stream().map(r -> {
                    r.setName_uz(null);
                    return toDto(r);
                }).toList();
                return dtoList;
            }
        }
        dtoList.add(getById(parentId,language));
        return dtoList;
    }
    public List<RegionEntity> getRegionEntityListById(Integer parentId) {
        List<RegionEntity> entityList = regionRepository.findAllByParent(get(parentId));
        return entityList;
    }


    public List<RegionDTO> likeName(String name, Language language) {
        if (name == null || name.isEmpty() || language == null) {
            throw new BadRequestException("Field can not be null");
        }
        if (name.length() < 3) {
            throw new BadRequestException("name length>=3");
        }
        List<RegionDTO> regionList;
        if (language.equals(Language.UZ)) {
            regionList = regionRepository.findByName_uzLike(name + "%").stream().map(r -> {
                r.setName_ru(null);
                return toDto(r);
            }).toList();
        } else {
            regionList = regionRepository.findByName_ruLike(name + "%").stream().map(r -> {
                r.setName_uz(null);
                return toDto(r);
            }).toList();
        }
        return regionList;
    }
    /*public List<RegionDTO> filterUz(RegionFilterDto filterDto){

        Specification<RegionEntity>spec;
        if (filterDto.getRegionid()!=null){
          spec=Specification.where(RegionSpecification.id("id",filterDto.getRegionid()));
        }else{
          spec=Specification.where(RegionSpecification.idIsNotNull());
        }

        if (filterDto.getRegion_uz()!=null){
           spec=spec.and(RegionSpecification.string("region_uz", filterDto.getRegion_uz()));
        }
    }*/

}
