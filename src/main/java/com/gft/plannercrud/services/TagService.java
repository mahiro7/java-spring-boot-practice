package com.gft.plannercrud.services;

import com.gft.plannercrud.entities.Tag;
import com.gft.plannercrud.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public Tag getTagById(Long id) throws Exception {
        Optional<Tag> tag = tagRepository.findById(id);

        if (tag.isEmpty()) {
            throw new Exception("Tag não encontrada!");
        }

        return tag.get();
    }

    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }
}
