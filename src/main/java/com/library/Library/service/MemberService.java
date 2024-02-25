package com.library.Library.service;

import com.library.Library.entity.Member;
import com.library.Library.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository mRepo;

    public void saveMember(Member member){
        mRepo.save(member);
    }
}
