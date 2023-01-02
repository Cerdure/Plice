package com.project.team.plice.service.interfaces;

import com.project.team.plice.dto.post.PostDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadFileService {
    public String getFullPath(String name);
    public void saveFiles(PostDto postDto, Long postId) throws IOException;
    public void saveFile(Long postId, MultipartFile file) throws IOException;
    private String createStoreFileName(String originalFilename) {
        return null;
    }
    private String extractExt(String originalFilename) {
        return null;
    }
    public void deleteFilesByPostId(Long postId);
}
