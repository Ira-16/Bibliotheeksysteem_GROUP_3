package com.library.service;

import com.library.model.Member;
import com.library.repository.MemberRepository;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }
    public void register(Member member) {
        try{
        if (member == null || member.getMembershipId() == null) {
            throw new IllegalArgumentException("Member or Membership ID must not be null.");
        }
        if(!isValidEmail(member.getContactDetails())){
            throw new IllegalArgumentException("Invalid email address: " + member.getContactDetails());
        }
        if (memberRepository.existsById(member.getMembershipId())) {
            throw new IllegalArgumentException("Member with ID " + member.getMembershipId() + " already exists.");
        }
        memberRepository.save(member);
        System.out.println("Thank you for information, " + member.getName() + "!");
        System.out.println("You will receive a confirmation by email.");
        System.out.println("✅ Member registered successfully!");
    }catch(IllegalArgumentException e){
            System.out.println("⚠Registration Error! " + e.getMessage());
            return;
        }}

    public boolean isValidEmail(String email){
        return email !=null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"); //for gmail info
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
            System.out.println("📋 All Members: ");
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
        }}
}
