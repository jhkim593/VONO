package my.vono.web.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import my.vono.web.model.file.RecFileDAO;
import my.vono.web.model.file.RecToTextDAO;
import my.vono.web.model.file.RefFileDAO;

@Service
@RequiredArgsConstructor
public class FileService {

	private final RecFileDAO recFileDAO;
	private final RecToTextDAO recToTextDAO;
	private final RefFileDAO refFileDAO;

	
	
}
