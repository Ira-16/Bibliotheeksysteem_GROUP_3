package com.library.repository;

import com.library.model.Member;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MemberRepository {
    private final Map<String, Member> byId = new ConcurrentHashMap<>();

    public void save (Member m) {
        byId.put(m.getMembershipId(), m);
    }
    public Optional<Member> findByMembershipId (String membershipId) {
        return Optional.ofNullable(byId.get(membershipId));
    }
    public List<Member> findAll() {
        return new ArrayList<>(byId.values());
    }
}
