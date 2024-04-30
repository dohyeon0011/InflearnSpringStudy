package com.example.StudyDemo.Member;

public interface MemberRepository {

    void save(Member member);

    Member findById(Long memberId);
}
