package com.example.simpledrone.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.simpledrone.domain.Drone;

public interface DroneMapper {
    List<Drone> selectList(@Param("keyword") String keyword);
    Drone selectById(@Param("id") Long id);
    int insert(Drone drone);
    int update(Drone drone);
    int deleteById(@Param("id") Long id);
}
