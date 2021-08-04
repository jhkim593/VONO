package my.vono.web.model.user;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import my.vono.web.entity.Member;

@Repository
public class MemberDAO{
	
	@PersistenceContext
	EntityManager em;
	
	public Long save(Member member) {
		System.out.println("DAO");

		em.persist(member);
		return member.getId();
	}

}
