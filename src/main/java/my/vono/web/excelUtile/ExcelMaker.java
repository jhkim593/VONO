package my.vono.web.excelUtile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelMaker {
	public static void writeExcelFile(List<MeetingLogVO> list ,List<String>memoList,String URL) throws EncryptedDocumentException, IOException {
		String filePath = "C:\\VONO\\"+URL;    // 저장할 파일 경로
    
		FileOutputStream fos = new FileOutputStream(filePath);
    
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("MeetingLog");    // sheet 생성
    
		XSSFRow curRow;
		
		
    
		int row = list.size();    // list 크기
		for (int i = 0; i < row; i++) {
			curRow = sheet.createRow(i);    // row 생성
			curRow.createCell(0).setCellValue(list.get(i).getTime());    // row에 각 cell 저장
			curRow.createCell(1).setCellValue(list.get(i).getSpeaker());
			curRow.createCell(2).setCellValue(list.get(i).getContent());
		}
		
		XSSFSheet sheet2 = workbook.createSheet("memo"); 
		
	  
		int row2 = memoList.size();    // list 크기
		for (int i = 0; i < row2; i++) {
			curRow = sheet2.createRow(i);    // row 생성
			curRow.createCell(0).setCellValue(memoList.get(i));
		}
		
		
		
		 for(String s :memoList) {
			   System.out.println(s);
		   }
		
		//추가적인 입력사항 필요 
		workbook.write(fos);
		fos.close();
	}
}
