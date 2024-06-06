package com.wanted.market.repository;

import com.wanted.market.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query()
    Optional<Member> findByName(@Param("name")String name);
}
