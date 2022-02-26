package com.albamon.auth.auth.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.ToString;

@Table(name = "refresh_token")
@Entity
@ToString
public class RefreshToken {

    @Id
    @Column(name = "refresh_key", nullable = false)
    private String refreshKey;
    @Column(name = "refresh_value")
    private String refreshValue;

    public RefreshToken(){

    }

    @Builder
    public RefreshToken(String refreshKey, String refreshValue) {
        this.refreshKey = refreshKey;
        this.refreshValue = refreshValue;
    }

    public String getRefreshKey() {
        return refreshKey;
    }

    public String getRefreshValue() {
        return refreshValue;
    }

    public RefreshToken updateValue(String token) {
        this.refreshValue = token;
        return this;
    }


}

