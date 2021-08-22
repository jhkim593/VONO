package my.vono.web.model.folder;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import my.vono.web.entity.Folder;


public interface FolderDAO extends JpaRepository<Folder, Long> {
	
	
	
//	@Query("select f from Folder f where f.level=:level and f.name=:name and f.member.id=:id")
//	Optional<Folder>findFolderByLevelAndName(@Param("level")int level,@Param("name")String name,@Param("id")Long id);
//	
//
//	@Query("select f from Folder f where f.level=:level and f.member.id=:id")
//     List<Folder>findFolderByLevel(@Param("level")int level,@Param("id")Long id);
	
	@Query("select f from Folder f where f.name=:name and f.member.id = :memberId")
	Optional<Folder>findFolderByName(@Param("name")String name,@Param("memberId")Long memberId);
	
	@Query("select f from Folder f where f.member.id=:id and f.is_trash=false")
	List<Folder>findFolderByMemberId(@Param("id")Long id);
	
	@Query("select f from Folder f where f.member.id=:id and f.is_trash=true")
	List<Folder>findTrashFolderByMemberId(@Param("id")Long id);
	

	
}
