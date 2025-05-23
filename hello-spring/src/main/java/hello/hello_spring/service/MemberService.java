package hello.hello_spring.service;

import hello.hello_spring.repository.MemberRepository;
// import hello.hello_spring.repository.MemoryMemberRepository;
import hello.hello_spring.domain.Member;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public class MemberService {
    private final MemberRepository memberRepository;

    /*
        기존에는 회원 서비스가 메모리 회원 리포지토리를 직접 생성하게 했다.
        private final MemberRepository memberRepository = new MemoryMemberRepository();
        
        하지만 아래처럼 회원 서비스 코드를 DI 가능하게 변경한다.
     */
    
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public Long join(Member member){
        // 같은 이름이 있는 중복 회원 X
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member){
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }


}
