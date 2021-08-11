package my.vono.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import my.vono.web.exception.BasicFolderRenameException;
import my.vono.web.exception.FolderAlreadyExistException;
import my.vono.web.exception.FolderNotFoundException;
import my.vono.web.model.folder.FolderDto;
import my.vono.web.model.folder.FolderSimpleDto;
import my.vono.web.model.response.DefaultResponseDto;
import my.vono.web.service.FolderService;

@Controller
@RequiredArgsConstructor
public class FolderController {

	private final FolderService folderService;

	@ResponseBody
	@GetMapping("/folders")
	public ResponseEntity<?> folders(@RequestParam Long id) {
		

		return new ResponseEntity<>(new DefaultResponseDto<>(true,"폴더 리스트 조회 성공",folderService.findFolders(id)),HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping("/folder/{id}")
	public ResponseEntity<?> detailFolder(@PathVariable("id") Long id) {
		try {
			FolderDto folderDto = folderService.detailFolder(id);
			return new ResponseEntity<>(new DefaultResponseDto<>(true,"폴더 조회 성공",folderDto),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new DefaultResponseDto<>(false,"폴더 조회 실패",null),HttpStatus.OK);

		}

	}
	@ResponseBody
	@PostMapping("/folder")
	public ResponseEntity<?> createFolder(@RequestBody FolderDto folderDto) {

		try {
	        folderService.createFolder(folderDto);
	        return new ResponseEntity<>(new DefaultResponseDto<>(true,"폴더 생성 성공",null),HttpStatus.CREATED);
		} catch (Exception e) {
			 return new ResponseEntity<>(new DefaultResponseDto<>(false,"폴더 생성 실패",null),HttpStatus.OK);

		}

	}
	
	@ResponseBody
	@PutMapping("/folder/trash")
	public ResponseEntity<?> trashFolder(@RequestBody FolderDto folderDto) {

		try {
	        folderService.trashFolder(folderDto);
	        return new ResponseEntity<>(new DefaultResponseDto<>(true,"휴지통 이동 성공",null),HttpStatus.OK);
		} catch (Exception e) {
			 return new ResponseEntity<>(new DefaultResponseDto<>(false,"휴지통 이동 실패",null),HttpStatus.OK);

		}

	}
	
	
	
	
	

	@PutMapping("/folder")
	@ResponseBody
	public ResponseEntity<?> renameFolder(@RequestBody FolderDto folderDto){
		try {
			
			folderService.renameFolder(folderDto);
			 return new ResponseEntity<>(new DefaultResponseDto<>(true,"폴더 이름 변경 성공",null),HttpStatus.OK);
			
		} catch (FolderNotFoundException e) {
			
			 return new ResponseEntity<>(new DefaultResponseDto<>(false,"폴더를 찾을 수 없습니다.",null),HttpStatus.OK);
		}
       catch (FolderAlreadyExistException e) {
			
    	   return new ResponseEntity<>(new DefaultResponseDto<>(false,"같은 이름의 폴더가 이미 존재합니다.",null),HttpStatus.OK);
		}
    catch (BasicFolderRenameException e) {
			
    	 return new ResponseEntity<>(new DefaultResponseDto<>(false,"기본폴더의 이름을 바꿀 수없습니다.",null),HttpStatus.OK);
		}
		
	}

}
