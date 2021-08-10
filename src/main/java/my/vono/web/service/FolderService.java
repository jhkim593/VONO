package my.vono.web.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import my.vono.web.entity.Folder;
import my.vono.web.entity.Member;
import my.vono.web.exception.FolderNotFoundException;
import my.vono.web.exception.MemberNotFoundException;
import my.vono.web.model.folder.FolderDAO;
import my.vono.web.model.folder.FolderDetailDto;
import my.vono.web.model.folder.FolderDto;
import my.vono.web.model.user.MemberDAO;

@Service
@RequiredArgsConstructor
@Transactional
public class FolderService {
	private final FolderDAO folderDAO;
	private final MemberDAO memberDAO;

	public void createFolder(FolderDto folderVO) {
		folderVO.getMember_id();
		Member member=memberDAO.findById(folderVO.getMember_id()).orElseThrow(MemberNotFoundException::new);
		
		Folder parentFolder=null;
		
		//확인
		if(folderVO.getParent_id()!=null) {
		
	      parentFolder=folderDAO.findById(folderVO.getParent_id()).orElseThrow(FolderNotFoundException::new);
		
		}
	
		folderDAO.save(Folder.createFolder(folderVO.getName(),member ,parentFolder));
		
		
	}

	public void deleteFolder(FolderDto folderVO) {
		
		Folder folder = folderDAO.findById(folderVO.getId()).orElseThrow(FolderNotFoundException::new);
//		folderDAO.delete(folder);
		
	   
	}

	public void renameFolder(FolderDto folderVO) {

		Folder folder = folderDAO.findById(folderVO.getId()).orElseThrow(FolderNotFoundException::new);
		folder.changeFolderName(folderVO.getName());

	}

	public void moveFolder(FolderDto folderVO) {
//		Folder folder = folderDAO.findById(folderVO.getId()).orElseThrow(FolderNotFoundException::new);
//		folder.
		

	}

	public FolderDetailDto detailFolder(FolderDto folderVO) {
		Folder folder = folderDAO.findById(folderVO.getId()).orElseThrow(FolderNotFoundException::new);
		return new FolderDetailDto(folder);
		
	}

}
