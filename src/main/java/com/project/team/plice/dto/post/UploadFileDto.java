package com.project.team.plice.dto.post;

import com.project.team.plice.domain.post.Post;
import com.project.team.plice.domain.post.UploadFile;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UploadFileDto {

    private Long id;
    private Post post;
    private String originalFilename;
    private String storeFileName;
    private String fileDir = "/upload-img/";
    private String fullPath;

    @Builder
    public UploadFileDto(Long id, Post post, String originalFilename, String storeFileName, String fileDir, String fullPath) {
        this.id = id;
        this.post = post;
        this.originalFilename = originalFilename;
        this.storeFileName = storeFileName;
        this.fileDir = fileDir;
        this.fullPath = fullPath;
    }

    public UploadFile toEntity(){
        return UploadFile.builder()
                .id(this.id)
                .post(this.post)
                .originalFilename(this.originalFilename)
                .storeFileName(this.storeFileName)
                .fullPath(this.fullPath)
                .build();
    }
}
