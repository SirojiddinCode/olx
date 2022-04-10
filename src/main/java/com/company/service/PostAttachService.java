package com.company.service;

import com.company.dto.AttachDTO;
import com.company.entity.AttachEntity;
import com.company.entity.PostAttachEntity;
import com.company.entity.PostEntity;
import com.company.repository.PostAttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class PostAttachService {
    @Autowired
    private PostAttachRepository postAttachRepository;
    @Autowired
    private AttachService attachService;
    @Autowired
    private PostService postService;

    public void save(List<AttachDTO> attachList, PostEntity post) {
        for (AttachDTO a : attachList) {
            AttachEntity attach = attachService.get(a.getId());
            PostAttachEntity postAttachEntity = new PostAttachEntity();
            postAttachEntity.setPost(post);
            postAttachEntity.setAttach(attach);
            postAttachRepository.save(postAttachEntity);
        }
    }

    public List<AttachDTO> getByPost(PostEntity post) {
        List<AttachDTO> attachDTOList = new LinkedList<>();
        List<PostAttachEntity> postAttachEntities = postAttachRepository.findAllByPost(post);
        for (PostAttachEntity p : postAttachEntities) {
            attachDTOList.add(attachService.toAttachDto(p.getAttach()));
        }
        return attachDTOList;
    }

    public void update(List<AttachDTO> attachDTOList, PostEntity entity) {
        List<PostAttachEntity> postAttachList=postAttachRepository.findAllByPost(entity);
        boolean exists=false;
        for (AttachDTO dto : attachDTOList) {
            for (PostAttachEntity pa : postAttachList) {
                if (dto.getId().equals(pa.getAttach().getId())) {
                    exists=true;
                }
            }
            if (!exists){
                AttachEntity attach=attachService.get(dto.getId());
                PostAttachEntity postAttachEntity=new PostAttachEntity();
                postAttachEntity.setAttach(attach);
                postAttachEntity.setPost(entity);
                postAttachRepository.save(postAttachEntity);
            }
        }
    }
}
