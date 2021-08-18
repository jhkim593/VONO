package my.vono.web.excelUtile;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class ExcelReader {
		static List<MeetingLogVO> list = new ArrayList<MeetingLogVO>();
		public static List<MeetingLogVO> excelReader(String URL) throws Exception{
			String fileURL= URL;
			FileInputStream file = new FileInputStream(URL);
	        XSSFWorkbook workbook = new XSSFWorkbook(file);
	        int columnindex=0;
	        int rowindex=0;
	        //시트 수 회의록 시트 
	        XSSFSheet sheet=workbook.getSheetAt(0);
	        //행의 수
	        int rows=sheet.getPhysicalNumberOfRows();
	        for(rowindex=0;rowindex<rows;rowindex++){
	            //행을읽는다
	            XSSFRow row=sheet.getRow(rowindex);
	            MeetingLogVO vo = new MeetingLogVO();
	            if(row !=null){
	            	//셀의 수
	                int cells=row.getPhysicalNumberOfCells();
	                for(columnindex=0; columnindex<=cells; columnindex++){
	                    //셀값을 읽는다
	                    XSSFCell cell=row.getCell(columnindex);
	                    String value="";
	                    //셀이 빈값일경우를 위한 널체크
	                    if(cell==null){
	                        continue;
	                    }else{
	                        //타입별로 내용 읽기
	                    	 //타입별로 내용 읽기
	                      switch (cell.getCellType()){
	                      case STRING:
	                          value=cell.getStringCellValue()+"";
	                          break;
	                      case ERROR:
	                          value=cell.getErrorCellValue()+"";
	                          break;
	                      }
	                    }
	                    if(columnindex==0) {
	                    	vo.setSpeaker(value);
	                    }else if(columnindex==1){
	                    	vo.setContent(value);
	                    }else {
	                    	vo.setTime(value);
	                    	list.add(vo);
	                    }
	                    System.out.println(columnindex);
	                    System.out.println(value);
	                }

	                }
	            }
	        XSSFSheet sheet1=workbook.getSheetAt(1);
	        //추가적인 메모 내용이생길시 시트를 바꿔 저장 저장 위치만 보고 특정 저장위치에 서 값을 읽어옴 
	        return list;
		}
}
