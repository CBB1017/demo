package com.example.demo.service;

import com.example.demo.model.TableauBoardEntity;
import com.example.demo.model.mapper.TestMapper;
import com.example.demo.persistence.TableauBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TableauService {
    private final TableauBoardRepository repository;

    private final TestMapper testMapper;

    public List<TableauBoardEntity> getAllDataList() {
        return testMapper.getAllDataList();
    }

    public String testService() {
        TableauBoardEntity entity = TableauBoardEntity.builder().title("My first todo item").build();
        repository.save(entity);
        TableauBoardEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getTitle();
    }
    public List<TableauBoardEntity> retrieveByEmail(final String email) {
        return repository.findByEmail(email);
    }
    public List<TableauBoardEntity> retrieveByContentPath(final String contentPath) {
        return repository.findByContentPath(contentPath);
    }

    public List<TableauBoardEntity> create(final TableauBoardEntity entity) {

        validate(entity);

        repository.save(entity);

        log.info("Entity id : {} is saved.", entity.getId());

        return repository.findByEmail(entity.getEmail());
    }

    public List<TableauBoardEntity> update(final TableauBoardEntity entity) {

        validate(entity);

        final Optional<TableauBoardEntity> original = repository.findById(entity.getId());
        original.ifPresent(todo -> {
                todo.setTitle(entity.getTitle());
                todo.setContentPath(entity.getContentPath());
                todo.setEmail(entity.getEmail());

                repository.save(entity);
        });

        return retrieveByEmail(entity.getId());
    }
    public List<TableauBoardEntity> delete(final TableauBoardEntity entity) {
        validate(entity);
        try {
            repository.delete(entity);
        }catch (Exception e){
            log.error("error deleting entity", entity.getId(), e);
            throw new RuntimeException("error deleting entity" + entity.getId());
        }

        return retrieveByEmail(entity.getId());
    }
    private void validate(final TableauBoardEntity entity) {
        if (entity == null) {
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }

        if (entity.getId() == null) {
            log.warn("Unknown id.");
            throw new RuntimeException("Unknown id.");
        }
    }


}
