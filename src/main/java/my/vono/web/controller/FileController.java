package my.vono.web.controller;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import my.vono.web.service.FileService;

@Controller
@RequiredArgsConstructor

public class FileController {
	
	private final FileService fileService;
	

}
