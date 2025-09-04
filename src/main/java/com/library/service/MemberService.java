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
        if (membershipId == null || membershipId.isBlank() || !memberRepository.existsById(membershipId)) {//also empty blank
            throw new NoSuchElementException("Member with ID " + membershipId + " does not exist.");
        }

        memberRepository.deleteById(membershipId);
        System.out.println("✅ Member deleted: " + membershipId);
    }

    public void edit(Member member) {
        if (member == null || member.getMembershipId() == null || member.getMembershipId().isBlank()) {//also it can be empty
            throw new IllegalArgumentException("Member or Membership ID must not be null.");
        }

        if (!memberRepository.existsById(member.getMembershipId())) {
            throw new NoSuchElementException("Member with ID " + member.getMembershipId() + " does not exist.");
        }

        memberRepository.save(member);
        System.out.println("✅ Member updated: " + member.getName());
    }

    public List<Member> listAll() { //with list it is better and short
        System.out.println("📋 All Members:");
        return memberRepository.findAll();
    }

    public Member findById(String membershipId) {
        return memberRepository.findById(membershipId)
                .orElseThrow(() -> new NoSuchElementException("Member with ID " + membershipId + " not found."));
    }
}
