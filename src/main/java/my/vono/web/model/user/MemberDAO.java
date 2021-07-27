package my.vono.web.model.user;

import org.springframework.data.jpa.repository.JpaRepository;

import my.vono.web.entity.Member;

public interface MemberDAO extends JpaRepository<Member, Long>{


}
