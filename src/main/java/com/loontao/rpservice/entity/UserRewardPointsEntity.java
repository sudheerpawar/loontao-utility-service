package com.loontao.rpservice.entity;

import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.*;

/* 
* Entity class for UserRewardPoints.
* An entity is a lightweight persistence domain object. Typically, an entity represents a table in a relational database, and each entity instance corresponds to a row in that table.
* @Entity - Specifies that the class is an entity. This annotation is applied to the entity class.
* @Table - Specifies the primary table for the annotated entity. Additional tables may be specified using SecondaryTable or SecondaryTables annotation.
* @Id - Specifies the primary key of an entity. The field or property to which the Id annotation is applied should be one of the following types: any Java primitive type, any primitive wrapper type, String, java.util.Date, java.sql.Date, java.math.BigDecimal, java.math.BigInteger.
*/ 
@Entity
@Table(name = "user_reward_points")
public class UserRewardPointsEntity {

    // @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    // @Column(nullable = false, name = "user_id")
    // private Long userId;

    // @Column(nullable = false, unique = true, name = "email_id")
    // private String emailId;

    @Id
    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;

    @Column(nullable = false, name = "reward_points")
    private Integer rewardPoints;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    // public String getUsername() {
    //     return emailId;
    // }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public Integer getRewardPoints() {
        return rewardPoints;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRewardPoints(Integer rewardPoints) {
        this.rewardPoints = rewardPoints;
    }
}