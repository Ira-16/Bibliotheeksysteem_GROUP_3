package com.library.repository;

import com.library.model.Member;

import java.util.*;

public class InMemoryMemberRepository implements MemberRepository{
    private final Map<String, Member> members = new HashMap<>();

    public Member save(Member member) {
        members.put(member.getMembershipId(), member);
        return member;
    }

    public void deleteById(String membershipId) {
        members.remove(membershipId);
    }

    public boolean existsById(String membershipId) {
        return members.containsKey(membershipId);
    }

    public Optional<Member> findById(String membershipId) {
        return Optional.ofNullable(members.get(membershipId));
    }

    public List<Member> findAll() {
        return new ArrayList<>(members.values());
    }

    public void deleteAll() {
        members.clear();
    }

    public int count() {
        return members.size();
    }
}