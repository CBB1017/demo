package com.example.demo.persistence;

import com.example.demo.model.TableauBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableauBoardRepository extends JpaRepository<TableauBoardEntity, String> {
    @Query("select t from TableauBoardEntity t where t.contentPath LIKE '%?1%'")
    List<TableauBoardEntity> findByContentPath(String userId);
    @Query("select t from TableauBoardEntity t where t.email LIKE '%?1%'")
    List<TableauBoardEntity> findByEmail(String email);
    Boolean existsByEmail(String email);
}
