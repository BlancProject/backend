package com.blanc.market.domain.user.entity;

import com.blanc.market.domain.order.entity.Order;
import com.blanc.market.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private SkinType skinType;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<SkinConcerns> skinConcerns = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Order> order = new ArrayList<>();

    public void changeNickname(String name) {
        this.nickname = name;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeAddress(String address) {
        this.address = address;
    }

    public void changeRole(Role role) {
        this.role = role;
    }

    public void changeGender(Gender gender) {
        this.gender = gender;
    }

    public void changeSkinType(SkinType skinType) {
        this.skinType = skinType;
    }

    public void changeSkinConcerns(List<SkinConcerns> skinConcerns) {
        this.skinConcerns = skinConcerns;
    }
}
