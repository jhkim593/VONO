package my.vono.web.model.user;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;
import org.springframework.stereotype.Repository;

import my.vono.web.entity.Member;

public interface MemberDAO extends JpaRepository<Member,Long>{
	
}
