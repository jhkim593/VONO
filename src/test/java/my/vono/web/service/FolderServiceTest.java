package my.vono.web.service;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import my.vono.web.entity.Folder;
import my.vono.web.entity.Member;

@SpringBootTest
@Transactional
class FolderServiceTest {

	@Autowired
    EntityManager em;
	
	@Autowired
	FolderService folderService;
	
	//구조
	//folder1-------folder2
	//       -------folder3
	@BeforeEach
	public void beforeEach() {
		Member member1=Member.createMemeber("1", "1", null, "test", null, null, null);
		em.persist(member1);
		
		Folder folder1=Folder.createFolder("1", member1);
		em.persist(folder1);
		
		Folder folder2=Folder.createFolder("2", member1);
		em.persist(folder2);
		
		Folder folder3=Folder.createFolder("3", member1);
		em.persist(folder3);
		
		
		
	}
	
//	@Test
//	public void moveFolder() {
//		folderService.moveFolder(null);
//		
//	}

}
