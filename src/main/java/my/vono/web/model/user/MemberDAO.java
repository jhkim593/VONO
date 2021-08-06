package my.vono.web.model.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import my.vono.web.entity.Member;


public interface MemberDAO extends JpaRepository<Member,Long>{
	
	@Query("select * from Member where id=:login_id and pw=:pw" )
	Optional<Member>findByMemberWithLoginIdAndPw(@Param("login_id")String login_id,@Param("pw")String pw);
	

}
