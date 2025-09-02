package com.library.repository;

import com.library.model.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    void deleteById(String membershipId);

    boolean existsById(String membershipId);

    Optional<Member> findById(String membershipId);

    List<Member> findAll();

    void deleteAll();

    int count();
}
