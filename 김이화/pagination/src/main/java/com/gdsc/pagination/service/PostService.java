package com.gdsc.pagination.service;

import com.gdsc.pagination.domain.Post;
import com.gdsc.pagination.dto.PostDto;
import com.gdsc.pagination.dto.PostPagingDto;
import com.gdsc.pagination.dto.PostResponseDto;
import com.gdsc.pagination.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public void save(PostDto postDTO) {
        Post post = Post.toEntity(postDTO);
        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public PostResponseDto findAllPosts(PostPagingDto postPagingDto) {

        Sort sort = Sort.by(Sort.Direction.fromString(postPagingDto.getSort()), "id");
        Pageable pageable = PageRequest.of(postPagingDto.getPage(), postPagingDto.getSize(), sort);

        Page<Post> postPages = postRepository.findAll(pageable);

        return new PostResponseDto(postPages);

    }

}
