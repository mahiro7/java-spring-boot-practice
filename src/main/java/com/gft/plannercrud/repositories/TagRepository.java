package com.gft.plannercrud.repositories;

import com.gft.plannercrud.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
