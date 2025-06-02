package com.newstoss.favorite;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "favorites")
@ToString
@Getter
@Setter
@NoArgsConstructor
public class Favorite {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID groupId;

    @Column(nullable = false)
    private UUID memberId;

    @Column(nullable = false)
    private String groupName;

    @Column(nullable = false)
    private Integer groupSequence;

    @Column(nullable = false)
    private boolean main;
}
