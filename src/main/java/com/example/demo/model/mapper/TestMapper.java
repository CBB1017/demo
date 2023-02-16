package com.example.demo.model.mapper;

import com.example.demo.model.TableauBoardEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TestMapper {
    List<TableauBoardEntity> getAllDataList();
}
