package my.vono.web.model.meeting;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.vono.web.excelUtile.MeetingLogVO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingDetailDto {

	List<MeetingLogVO> mList;
	List<String>memo;
}
