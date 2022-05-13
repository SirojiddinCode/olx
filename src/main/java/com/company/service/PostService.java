package com.company.service;

import com.company.dto.AttachDTO;
import com.company.dto.post.Post;
import com.company.dto.post.PostDTO;
import com.company.dto.post.PostFilterDto;
import com.company.entity.CategoryEntity;
import com.company.entity.PostEntity;
import com.company.entity.ProfileEntity;
import com.company.entity.RegionEntity;
import com.company.enums.PostStatus;
import com.company.enums.ProfileStatus;
import com.company.exceptions.BadRequestException;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.PostRepository;
import com.company.spec.PostSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Lazy
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private CategoryService categoryService;
    @Lazy
    @Autowired
    private PostAttachService postAttachService;

    /*public PostDTO createPost(PostDTO postDTO, Integer profileId) {
        Optional<ProfileEntity> optionalProfile = profileRepository.findById(profileId);
        Optional<RegionEntity> optionalRegion= regionRepository.findById(postDTO.getRegionId());
        if (optionalProfile.isEmpty()) {
            throw new BadRequestException("User Not Found");
        }
        if (optionalProfile.get().getStatus().equals(ProfileStatus.BLOCK)){
            throw new BadRequestException("This user is Blocked");
        }
        if (optionalRegion.isEmpty()){
            throw new BadRequestException("Region not found");
        }

    }*/
    public PostDTO createPost(PostDTO postDTO, Integer profileId) {
        ProfileEntity profile = profileService.get(profileId);
        RegionEntity region = regionService.get(postDTO.getRegionId());
        CategoryEntity category = categoryService.get(postDTO.getCategoryId());
        if (profile.getStatus().equals(ProfileStatus.BLOCK) || profile.getStatus().equals(ProfileStatus.CREATED)) {
            throw new BadRequestException("This user is Blocked");
        }
        PostEntity entity = new PostEntity();
        entity.setContent(postDTO.getContent());
        entity.setTitle(postDTO.getTitle());
        entity.setCondition(postDTO.getCondition());
        entity.setCategory(category);
        entity.setPaymentType(postDTO.getPaymentType());
        entity.setPrice(postDTO.getPrice());
        entity.setProfile(profile);
        entity.setExpirationDate(LocalDateTime.now().plusDays(30));
        entity.setStatus(PostStatus.ACTIVE);
        entity.setRegion(region);
        entity.setOwnerPhoneNumber(postDTO.getOwnerPhoneNumber());
        postRepository.save(entity);
        postAttachService.save(postDTO.getAttachList(), entity);
        postDTO.setId(entity.getId());
        return postDTO;
    }

    public List<Post> filter(int page, int size, PostFilterDto dto) {
        Specification<PostEntity> spec;
        if (dto.getCategoryId() != null) {
            CategoryEntity category = categoryService.get(dto.getCategoryId());
            List<CategoryEntity> categoryList = categoryService.getAllCategoryByParentId(dto.getCategoryId());
            if (categoryList.isEmpty()) {
                spec = Specification.where(PostSpecification.getbyCategory(category));
            } else {
                spec = Specification.where(PostSpecification.getByAllCategory(categoryList));
            }
        } else {
            spec = Specification.where(PostSpecification.isnotNull());
        }
        if (dto.getRegionId() != null) {
            RegionEntity region = regionService.get(dto.getRegionId());
            List<RegionEntity> regionList = regionService.getRegionEntityListById(dto.getRegionId());
            if (regionList.isEmpty()) {
                spec = spec.and(PostSpecification.getByRegion(region));
            } else {
                spec = spec.and(PostSpecification.getByAllRegion(regionList));
            }
        }
        if (dto.getCondition() != null) {
            spec = spec.and(PostSpecification.getByCondition(dto.getCondition()));
        }
        if (dto.getFromprice() != null) {
            spec = spec.and(PostSpecification.fromPrice(dto.getFromprice()));
        }
        if (dto.getFromprice() != null) {
            spec = spec.and(PostSpecification.toPrice(dto.getFromprice()));
        }
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdDate");

        Page<PostEntity> pageEntity = postRepository.findAll(spec, pageable);
        List<PostEntity> entityList = pageEntity.getContent();
        List<Post> postList = new LinkedList<>();
        for (PostEntity entity : entityList) {
            Post post = new Post();
            post.setPostId(entity.getId());
            post.setCreatedDate(entity.getCreatedDate());
            post.setPaymentType(entity.getPaymentType());
            post.setPrice(entity.getPrice());
            post.setTitle(entity.getTitle());
            List<AttachDTO> attachDTOList = postAttachService.getByPost(entity);
            if (!attachDTOList.isEmpty()) {
                post.setAttach(attachDTOList.get(0));
            }
            postList.add(post);
        }
        return postList;
    }

    public PostEntity get(Integer id) {
        return postRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Post Not found"));
    }

    public PostDTO getById(Integer id) {
        PostEntity entity = get(id);
        PostDTO postDTO = new PostDTO();
        postDTO.setId(entity.getId());
        postDTO.setCondition(entity.getCondition());
        postDTO.setContent(entity.getContent());
        postDTO.setPrice(entity.getPrice());
        postDTO.setCreatedDate(entity.getCreatedDate());
        postDTO.setPaymentType(entity.getPaymentType());
        postDTO.setStatus(entity.getStatus());
        postDTO.setExpirationDate(entity.getExpirationDate());
        postDTO.setTitle(entity.getTitle());
        postDTO.setCategoryId(entity.getCategory().getId());
        postDTO.setRegionId(entity.getRegion().getId());
        postDTO.setOwnerPhoneNumber(entity.getOwnerPhoneNumber());
        postDTO.setAttachList(postAttachService.getByPost(entity));
        return postDTO;
    }

    public PostDTO update(PostDTO postDTO, Integer ownerId) {
        if (postDTO.getId() == null) {
            throw new BadRequestException("Id can not be null");
        }
        PostEntity entity = get(postDTO.getId());
        if (!entity.getProfile().getId().equals(ownerId)) {
            throw new BadRequestException("Not owner");
        }
        entity.setOwnerPhoneNumber(postDTO.getOwnerPhoneNumber());
        entity.setStatus(postDTO.getStatus());
        entity.setRegion(regionService.get(postDTO.getRegionId()));
        entity.setTitle(postDTO.getTitle());
        entity.setContent(postDTO.getContent());
        entity.setPaymentType(postDTO.getPaymentType());
        entity.setPrice(postDTO.getPrice());
        entity.setCondition(postDTO.getCondition());
        entity.setCurrencyType(postDTO.getCurrencyType());
        postRepository.save(entity);
        postAttachService.update(postDTO.getAttachList(), entity);
        return null;
    }
}
