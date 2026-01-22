package com.guru2.memody.repository;

import com.guru2.memody.entity.Record;
import com.guru2.memody.entity.RecordImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordImageRepository extends JpaRepository<RecordImage, Long> {
    List<RecordImage> findAllByRecord(Record record);
}
