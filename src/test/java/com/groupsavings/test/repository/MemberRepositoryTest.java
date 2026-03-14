package com.groupsavings.test.repository;

import com.groupsavings.model.entity.Member;
import com.groupsavings.model.enums.MemberStatus;
import com.groupsavings.repository.MemberRepository;
import com.groupsavings.test.config.BaseRepositoryTest;
import com.groupsavings.test.util.TestEntityFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MemberRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    private Member testMember;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        testMember = TestEntityFactory.createMember("M001", "Juan", "Dela Cruz");
    }

    @Test
    void saveMember_ShouldPersistAllFields() {
        // When
        Member savedMember = memberRepository.save(testMember);

        // Then
        assertThat(savedMember.getMemberId()).isNotNull();
        assertThat(savedMember.getMemberCode()).isEqualTo("M001");
        assertThat(savedMember.getFirstname()).isEqualTo("Juan");
        assertThat(savedMember.getLastname()).isEqualTo("Dela Cruz");
        assertThat(savedMember.getEmail()).isEqualTo("M001@email.com");
    }

    @Test
    void saveMember_WithDuplicateMemberCode_ShouldThrowException() {
        // Given
        memberRepository.save(testMember);

        // When/Then
        Member duplicateMember = TestEntityFactory.createMember("M001", "Pedro", "Santos");

        assertThatThrownBy(() -> memberRepository.saveAndFlush(duplicateMember))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}