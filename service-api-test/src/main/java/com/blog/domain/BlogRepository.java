package com.blog.domain;

import java.util.Optional;
import java.util.UUID;

public interface BlogRepository{
    void save(Blog blog);

    Optional<Blog> findById(UUID id);

    boolean existsById(UUID id);

    void deleteById(UUID id);

}
