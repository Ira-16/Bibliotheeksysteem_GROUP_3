package com.library.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.model.Member;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class InMemoryMemberRepository implements MemberRepository{

    private final File file2 = new File("member.json");
    private final ObjectMapper mapper2 = new ObjectMapper();

    private final Map<String, Member> members = new HashMap<>();

    public InMemoryMemberRepository(){
        load();
    }
    private void load(){
        if(file2.exists()){
            try{
                List<Member> list = mapper2.readValue(file2, new TypeReference<List<Member>>() {});
                members.clear();
                list.forEach((b->members.put(b.getMembershipId(), b)));
            }catch(IOException e){
                throw new RuntimeException("Error! Can not download list of members");
            }
        }
    }
    private void saveFile(){
        try{
            mapper2.writerWithDefaultPrettyPrinter().writeValue(file2, members.values());
        }catch (IOException e){
            throw new RuntimeException("Error! Can not save books");
        }
    }

    public void save(Member member) {
        members.put(member.getMembershipId(), member);
        saveFile();
    }

    public void deleteById(String membershipId) {
        members.remove(membershipId);
        saveFile();
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
        saveFile();
    }

    public int count() {
        return members.size();
    }
}