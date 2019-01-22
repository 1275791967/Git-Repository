package com.irongteng.persistence.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.irongteng.persistence.PartitionVO;

@Repository
public interface PartitionMapper{
    
    List<String> findPartition(PartitionVO pv);
    
    Integer findNumPartition(PartitionVO pv);
    
    void addPartition(PartitionVO pv);
    
    void removePartition(PartitionVO pv);
}
