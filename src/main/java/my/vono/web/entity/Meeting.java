package my.vono.web.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meeting {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String name;
	
	private LocalDateTime create_Date;
	
	private LocalDateTime edit_date;
	
//	private String participant;
	
	private String content;
	
//	private String re
	
	private Boolean is_trash;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="folder_id")
	private Folder folder;
    
	
	@OneToMany(mappedBy = "meeting" ,cascade = CascadeType.ALL)
	private List<RecToText> recToTexts=new ArrayList<>();
	
	@OneToMany(mappedBy = "meeting",cascade = CascadeType.ALL)
	private List<RecFile>recFiles=new ArrayList<>();
	
	public static Meeting createMeeting(String name,String content,Folder folder) {
		Meeting meeting=new Meeting();
		meeting.name=name;
		meeting.content=content;
		if(folder!=null) {
		meeting.addFolder(folder);
		
		}
		//참석자
		return meeting;
		
	}
	
	public void addFolder(Folder folder) {
		this.folder=folder;
		folder.getMeetings().add(this);
	}

}
