package com.company.service;

import com.company.dto.CategoryDTO;
import com.company.entity.CategoryEntity;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO createCategory(CategoryDTO categoryDTO){
        CategoryEntity entity=toEntity(new CategoryEntity(),categoryDTO);
        categoryRepository.save(entity);
        categoryDTO.setId(entity.getId());
        return categoryDTO;
    }

    private  CategoryEntity toEntity(CategoryEntity entity,CategoryDTO dto){
        entity.setName_ru(dto.getName_ru());
        entity.setName_uz(dto.getName_uz());
        if (dto.getParentId()!=null) {
            entity.setParent(get(dto.getParentId()));
        }
        return entity;
    }

    public CategoryEntity get(Integer id){
        return categoryRepository.findById(id).orElseThrow(
                ()->new ItemNotFoundException("Category not found"));
    }

    public CategoryDTO toDTO(CategoryEntity entity){
        CategoryDTO dto=new CategoryDTO();
        dto.setId(entity.getId());
        dto.setName_ru(entity.getName_ru());
        dto.setName_uz(entity.getName_uz());
        dto.setParentId(entity.getParent().getId());
        dto.setKey(entity.getKey());
        return dto;
    }

    public CategoryDTO update(CategoryDTO dto,Integer id){
        CategoryEntity entity=categoryRepository.getById(id);
        entity.setName_ru(dto.getName_ru());
        entity.setName_uz(dto.getName_uz());
        entity.setKey(dto.getKey());
        entity.setParent(get(dto.getParentId()));
        categoryRepository.save(entity);
        return dto;
    }

    public void delete(Integer id){
        if (categoryRepository.existsById(id)){
            categoryRepository.deleteById(id);
        }else{
            throw new ItemNotFoundException("Category not found");
        }
    }

    public List<CategoryDTO> getCategoryParents(){
        return categoryRepository.getParent()
                .stream()
                .map(this::toDTO).toList();
    }

    public List<CategoryDTO> getCategoryByParentId(Integer parentId){
        if (categoryRepository.existsById(parentId)){
            return categoryRepository.findAllByParent(get(parentId))
                    .stream()
                    .map(this::toDTO)
                    .toList();
        }else throw new ItemNotFoundException("Not Found");
    }
    public List<CategoryEntity> getAllCategoryByParentId(Integer parentId){
        if (categoryRepository.existsById(parentId)){
            return categoryRepository.findAllByParent(get(parentId));
        }else throw new ItemNotFoundException("Not Found");
    }
}
