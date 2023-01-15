package com.project.team.plice.repository.contents;

import com.project.team.plice.domain.contents.Contents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentsRepository extends JpaRepository<Contents, Long> {
}
