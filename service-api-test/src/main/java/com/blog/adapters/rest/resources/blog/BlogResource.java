package com.blog.adapters.rest.resources.blog;

import com.blog.application.EditBlogUseCase;
import com.blog.domain.Blog;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/blog", produces = APPLICATION_JSON_VALUE)
public class BlogResource {

    private final EditBlogUseCase editBlogUseCase;

    public BlogResource(EditBlogUseCase editBlogUseCase) {
        this.editBlogUseCase = editBlogUseCase;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> post(@RequestBody CreateBlogRequest data, UriComponentsBuilder uriComponentsBuilder) {
        Blog blog = editBlogUseCase.create(data.title, data.body, UUID.fromString(data.authorId));
        UriComponents uriComponents = uriComponentsBuilder.path("/blog/{id}").buildAndExpand(blog.getId());
        return ResponseEntity.created(uriComponents.toUri()).body(BlogDto.of(blog));
    }
}
