package my.vono.web.wasteBasket;

import java.time.LocalDateTime;

public class WasteBasketVO {
	private String fileTestName;
	private LocalDateTime fileTestTime;
	
	public WasteBasketVO(String fileTestName) {
		super();
		this.fileTestName = fileTestName;
		this.fileTestTime =  LocalDateTime.now();
	}
	
	public String getFileTestName() {
		return fileTestName;
	}

	public void setFileTestName(String fileTestName) {
		this.fileTestName = fileTestName;
	}

	public LocalDateTime getFileTestTime() {
		return fileTestTime;
	}

	public void setFileTestTime(LocalDateTime fileTestTime) {
		this.fileTestTime = fileTestTime;
	}

	@Override
	public String toString() {
		return "WasteBasketVO [fileTestName=" + fileTestName + ", fileTestTime=" + fileTestTime + "]";
	}

}
