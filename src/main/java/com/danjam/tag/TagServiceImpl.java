package com.danjam.tag;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TagServiceImpl {

    private final TagRepository TAGREPOSITORY;

    public List<TagDto> list() {

        List<Tag> tags = TAGREPOSITORY.findAll();

        return tags.stream()
                .map(tag -> TagDto.builder()
                        .id(tag.getId())
                        .name(tag.getName())
                        .PN(tag.getPN())
                        .build())
                .collect(Collectors.toList());

    }
}

