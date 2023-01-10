package com.project.team.plice.repository.post;

import com.project.team.plice.domain.post.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<UploadFile, Long> {
    public List<UploadFile> findAllByPostId(Long reviewId);
}
