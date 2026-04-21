package com.groupsavings.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.groupsavings.model.entity.Member;
import com.groupsavings.model.enums.MemberStatus;
import com.groupsavings.model.enums.MemberType;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

	Page<Member> findAll(Pageable pageable);

	@Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Member m WHERE m.email = :email")
	boolean isEmailExist(@Param("email") String email);

	@Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Member m WHERE m.mobileNumber = :phone")
	boolean isPhoneNumberExist(@Param("phone") String phone);

	Member findByMemberCode(String memberCode);

	@Query("SELECT m FROM Member m WHERE m.memberType LIKE :type")
	Page<Member> findByMemberType(@Param("type") MemberType type, Pageable pageable);

	@Query("SELECT m FROM Member m WHERE m.memberStatus LIKE :status")
	Page<Member> findByMemberStatus(@Param("status") MemberStatus status, Pageable pageable);

	@Query("SELECT m FROM Member m WHERE m.memberStatus LIKE :status AND m.memberType LIKE :type")
	Page<Member> findByMemberStatusAndType(@Param("status") MemberStatus status, @Param("type") MemberType type, Pageable pageable);

	List<Member> findByJoinDateBetween(LocalDate startDate, LocalDate endDate);

	@Query("SELECT m FROM Member m WHERE m.firstname LIKE %:name% OR m.lastname LIKE %:name%")
	List<Member> searchByName(@Param("name") String name);

	@Query("SELECT m FROM Member m WHERE m.address.city = :city")
	List<Member> findByCity(@Param("city") String city);

	@Query("SELECT m FROM Member m WHERE SIZE(m.loans) > 0")
	List<Member> findMembersWithLoans();

	@Query("SELECT m FROM Member m WHERE SIZE(m.contributions) > 0")
	List<Member> findMembersWithContributions();

	boolean existsByMemberCode(String memberCode);

	boolean existsByEmail(String email);

	boolean existsByMobileNumber(String mobileNumber);
}