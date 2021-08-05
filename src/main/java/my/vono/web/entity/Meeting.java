package my.vono.web.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
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
    
	
	@OneToMany(mappedBy = "meeting")
	private List<RecToText> recToTexts=new ArrayList<>();
	
	@OneToMany(mappedBy = "meeting")
	private List<RecFile>recFiles=new ArrayList<>();
	
	

}
