package com.library.service;

import com.library.model.Member;
import com.library.repository.MemberRepository;

import java.util.List;
import java.util.NoSuchElementException;

public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public void register(Member member) {
        try {
            if (member == null || member.getMembershipId() == null) {
                System.err.println("❌ Member or Membership ID must not be null.");
                return;
            }

            if (memberRepository.existsById(member.getMembershipId())) {
                System.err.println("❌ Member with ID " + member.getMembershipId() + " already exists!");
                return;
            }

            memberRepository.save(member);
            System.out.println("✅ Member registered: " + member.getName());

        } catch (Exception e) {
            System.err.println("❌ Unexpected error during registration: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void delete(String membershipId) {
        try {
            if (membershipId == null || membershipId.isBlank()) {
                System.err.println("❌ Membership ID is null or blank.");
                return;
            }

            if (!memberRepository.existsById(membershipId)) {
                System.err.println("❌ Member with ID " + membershipId + " does not exist.");
                return;
            }

            memberRepository.deleteById(membershipId);
            System.out.println("✅ Member deleted: " + membershipId);

        } catch (Exception e) {
            System.err.println("❌ Unexpected error during deletion: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void edit(Member member) {
        try {
            if (member == null || member.getMembershipId() == null || member.getMembershipId().isBlank()) {
                System.err.println("❌ Member or Membership ID must not be null or blank.");
                return;
            }

            if (!memberRepository.existsById(member.getMembershipId())) {
                System.err.println("❌ Member with ID " + member.getMembershipId() + " does not exist.");
                return;
            }

            memberRepository.save(member);
            System.out.println("✅ Member updated: " + member.getName());

        } catch (Exception e) {
            System.err.println("❌ Unexpected error during update: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Member> listAll() {
        try {
            System.out.println("📋 All Members:");
            return memberRepository.findAll();
        } catch (Exception e) {
            System.err.println("❌ Error fetching members: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Member findById(String membershipId) {
        try {
            return memberRepository.findById(membershipId).orElse(null);
        } catch (Exception e) {
            System.err.println("❌ Error finding member with ID " + membershipId + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }

    }
}
