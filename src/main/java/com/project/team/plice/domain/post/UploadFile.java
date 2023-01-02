package com.project.team.plice.domain.post;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadFile {

    @Id
    @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String originalFilename;

    private String storeFileName;

    private String fileDir;

    private String fullPath;


    @PrePersist
    public void prePersist() {
        this.fileDir = this.fileDir == null ? "/upload-img/" : this.fileDir;
        this.originalFilename = this.originalFilename == null ? this.storeFileName : this.originalFilename;
        this.fullPath = this.fullPath == null ? this.fileDir + this.storeFileName : this.fullPath;
    }

    @Builder
    public UploadFile(Long id, Post post, String originalFilename, String storeFileName, String fileDir, String fullPath) {
        this.id = id;
        this.post = post;
        this.originalFilename = originalFilename;
        this.storeFileName = storeFileName;
        this.fileDir = fileDir;
        this.fullPath = fullPath;
    }

    public void changePost(Post post){
        this.post = post;
    }
    public void changeStoreFileName(String storeFileName){
        this.storeFileName = storeFileName;
    }
    public void changeFullPath(String fullPath){
        this.fullPath = fullPath;
    }
}
