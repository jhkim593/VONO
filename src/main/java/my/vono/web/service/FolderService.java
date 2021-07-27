package my.vono.web.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import my.vono.web.model.folder.FolderDAO;

@Service
@RequiredArgsConstructor
public class FolderService {
	private final FolderDAO folderDAO;

	
}
