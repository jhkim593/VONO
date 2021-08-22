package my.vono.web.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@AllArgsConstructor  //생성자
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본생성자 protected
public class Folder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)  //폴더와 member 다대일 관계 설정
	@JoinColumn(name ="member_id" )     //외래키 
	private Member member;
	
	@OneToMany(mappedBy = "folder" ,cascade = CascadeType.ALL)
	private List<Meeting> meetings=new ArrayList<>();
	
//	@ManyToOne(fetch =FetchType.LAZY)
//	@JoinColumn(name="parent_id")
//	private Folder parentFolder;
	
//	@OneToMany(mappedBy = "folder",cascade = CascadeType.ALL)
//	private List<Folder>folders=new ArrayList<>();
	
	private Boolean is_trash;
	
    private LocalDateTime create_Date;
	
	private LocalDateTime edit_date;
	
	
	

	public static Folder createFolder(String name,Member member) {
		
		Folder folder=new Folder();
		folder.name=name;
		folder.addMember(member);
		
		
//		if(parent!=null) {
//			folder.addParent(folder);
//			folder.level=parent.level+1;
//		}
//		
		
		folder.is_trash=false;
		folder.create_Date=LocalDateTime.now();
		folder.edit_date=LocalDateTime.now();
		return folder;
		
	}
//	public void addParent(Folder folder) {
//		this.parentFolder=folder;
//		folder.getFolders().add(this);
//		
//	}
	//연관 메소드
	public void addMember(Member member) {
		this.member=member;
		member.getFolders().add(this);
	}
	public void moveFolder(Folder folder) {
		
	}
	
	public void changeFolderName(String name) {
		this.name=name;
	}

	public void changeIs_trashTrue() {
		this.is_trash=true;
		
	}
	
	public void changeIs_trashFalse() {
		this.is_trash=false;
		
	}
	public void changeEdit_date() {
		this.edit_date=LocalDateTime.now();
	}
	
	
	
	
	
}
