package my.vono.web.entity;

import java.util.ArrayList;
import java.util.List;

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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)  //폴더와 member 다대일 관계 설정
	@JoinColumn(name ="member_id" )     //외래키 
	private Member member;
	
	@OneToMany(mappedBy = "folder")
	private List<Meeting> meetings=new ArrayList<>();
	
	@ManyToOne(fetch =FetchType.LAZY)
	@JoinColumn(name="parent_id")
	private Folder folder;
	
	@OneToMany(mappedBy = "folder")
	private List<Folder>folders=new ArrayList<>();
	
	private Boolean is_trash;
	
	
	
	
	
}
