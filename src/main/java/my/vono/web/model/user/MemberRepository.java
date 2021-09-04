package my.vono.web.model.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import my.vono.web.entity.Member;


public interface MemberRepository extends JpaRepository<Member,Long>{
	
//	@Query("select m from Member m where m.=:lo")
//	Optional<Member>findByMemberWithLoginIdAndPw(@Param("login_id")String login_id,@Param("pw")String pw);
//
	@Query("select m from Member m where m.login_id=:LoginId")
	Optional<Member> findByLoginId(@Param("LoginId")String LoginId);

	Member findByEmail(String email);
	
//	@Query("select m from Member m where m.email=:email")
//	Optional<Member> findByEmail(@Param("email")String email);
	
}

