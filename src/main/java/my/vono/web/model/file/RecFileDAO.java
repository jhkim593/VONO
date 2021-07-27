package my.vono.web.model.file;

import org.springframework.data.jpa.repository.JpaRepository;

import my.vono.web.entity.RecFile;

public interface RecFileDAO extends JpaRepository<RecFile, Long>{

}
