package com.example.demo.dto;

import com.example.demo.model.TableauBoardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TableauBoardCrawlListDTO<T> {
    private String id;
    private String title;
    private String contentPath;
    private String email;

    public TableauBoardCrawlListDTO(final TableauBoardEntity entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.contentPath = entity.getContentPath();
        this.email = entity.getEmail();
    }

    public static TableauBoardEntity toEntity(final TableauBoardCrawlListDTO dto){
        return TableauBoardEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .contentPath(dto.getContentPath())
                .email(dto.getEmail())
                .build();
    }
}
