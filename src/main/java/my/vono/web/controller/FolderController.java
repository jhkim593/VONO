package my.vono.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import my.vono.web.exception.FolderNotFoundException;
import my.vono.web.model.folder.FolderDto;
import my.vono.web.service.FolderService;

@Controller
@RequiredArgsConstructor
public class FolderController {
	
	private final FolderService folderService;
	
	@PostMapping("/folder")
	public void renameFolder(FolderDto folderVO){
		try {
			
			folderService.renameFolder(folderVO);
			
		} catch (FolderNotFoundException e) {
			
			
		}
		
	}
	
	
}
