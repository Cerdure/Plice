package com.project.team.plice.service.classes;

import com.project.team.plice.domain.post.Post;
import com.project.team.plice.domain.post.UploadFile;
import com.project.team.plice.dto.post.PostDto;
import com.project.team.plice.repository.post.FileRepository;
import com.project.team.plice.repository.post.PostRepository;
import com.project.team.plice.service.interfaces.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadFileServiceImpl implements UploadFileService {

    private final FileRepository fileRepository;
    private final PostRepository postRepository;
    private String fileDir = System.getProperty("user.dir") + "/src/main/resources/static/upload-img/";

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    @Override
    public void saveFiles(PostDto postDto, Long postId) throws IOException {
        for (MultipartFile file : postDto.getImageFiles()) {
            saveFile(postId, file);
        }
    }

    @Override
    public void saveFile(Long postId, MultipartFile file) throws IOException {
        String storeFileName = createStoreFileName(file.getOriginalFilename());
        Post post = postRepository.findById(postId).get();

        UploadFile uploadFile = UploadFile.builder()
                                        .post(post)
                                        .originalFilename(file.getOriginalFilename())
                                        .storeFileName(storeFileName)
                                        .build();

        fileRepository.save(uploadFile);
        file.transferTo(new File(getFullPath(storeFileName)));
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        if(ext=="" || ext==null){
           return null;
        } else {
            String uuid = UUID.randomUUID().toString();
            return uuid + "." + ext;
        }

    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    @Override
    public void deleteFilesByPostId(Long postId) {
        List<UploadFile> uploadFiles = fileRepository.findAllByPostId(postId);
        uploadFiles.forEach(uploadFile -> new File(uploadFile.getFullPath()).delete());
        uploadFiles.forEach(uploadFile -> fileRepository.delete(uploadFile));
    }

}
