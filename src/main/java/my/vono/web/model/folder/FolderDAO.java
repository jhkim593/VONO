package my.vono.web.model.folder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import my.vono.web.entity.Folder;


public interface FolderDAO extends JpaRepository<Folder, Long> {
	
	
	
}
