package com.library.Library.service;

import com.library.Library.entity.Member;
import com.library.Library.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository mRepo;

    public void saveMember(Member member){
        mRepo.save(member);
    }

    public List<Member> getAllMembers(){
        return mRepo.findAll();
    }
    public Member getMemberById(int id){

        return mRepo.findById(id).get();
    }
    public void deleteById(int id){
        mRepo.deleteById(id);
    }


}
