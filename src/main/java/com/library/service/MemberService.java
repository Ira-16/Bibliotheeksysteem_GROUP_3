package com.library.service;

import com.library.model.Member;
import com.library.repository.InMemoryMemberRepository;

public class MemberService {
    private final InMemoryMemberRepository inMemoryMemberRepository = new InMemoryMemberRepository();

    public void register(Member member) {
        if (member == null || member.getMembershipId() == null) {
            throw new IllegalArgumentException("Member or Membership ID must not be null.");
        }

        if (inMemoryMemberRepository.existsById(member.getMembershipId())) {
            throw new RuntimeException("Member with ID " + member.getMembershipId() + " already exists.");
        }

        inMemoryMemberRepository.save(member);
        System.out.println("✅ Member registered: " + member.getName());
    }

    public void delete(String membershipId) {
        if (membershipId == null || !inMemoryMemberRepository.existsById(membershipId)) {
            throw new RuntimeException("Member with ID " + membershipId + " does not exist.");
        }

        inMemoryMemberRepository.deleteById(membershipId);
        System.out.println("✅ Member deleted: " + membershipId);
    }

    public void edit(Member member) {
        if (member == null || member.getMembershipId() == null) {
            throw new IllegalArgumentException("Member or Membership ID must not be null.");
        }

        if (!inMemoryMemberRepository.existsById(member.getMembershipId())) {
            throw new RuntimeException("Member with ID " + member.getMembershipId() + " does not exist.");
        }

        inMemoryMemberRepository.save(member);
        System.out.println("✅ Member updated: " + member.getName());
    }

    public void listAll() {
        System.out.println("📋 All Members:");
        for (Member member : inMemoryMemberRepository.findAll()) {
            System.out.println(member);
        }
    }

    public Member findById(String membershipId) {
        return inMemoryMemberRepository.findById(membershipId)
                .orElseThrow(() -> new RuntimeException("Member with ID " + membershipId + " not found."));
    }
}
