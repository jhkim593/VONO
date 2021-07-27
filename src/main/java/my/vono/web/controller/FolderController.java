package my.vono.web.controller;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import my.vono.web.service.FolderService;

@Controller
@RequiredArgsConstructor
public class FolderController {

	
	private final FolderService folderService;
	
	
}
