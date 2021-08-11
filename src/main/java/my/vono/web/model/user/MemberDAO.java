package my.vono.web.model.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import my.vono.web.entity.Member;


public interface MemberDAO extends JpaRepository<Member,Long>{
	
//	@Query("select m from Member m where m.=:lo")
//	Optional<Member>findByMemberWithLoginIdAndPw(@Param("login_id")String login_id,@Param("pw")String pw);
//	
}

