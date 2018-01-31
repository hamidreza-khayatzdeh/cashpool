package com.invia.challenge.cashpool.service.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by khayatzadeh on 1/25/2018.
 */
public class TravelerDto implements Serializable {

    private static final String NAME_PATTERN = "^[a-zA-Z\\s0-9.-]+";

    private Long id;
    @NotBlank
    @Size(min = 2, max = 255)
    @Pattern(regexp = NAME_PATTERN)
    private String name;
    @Email
    private String email;
    private BigDecimal totalSpentAmount;

    public TravelerDto() {
    }

    public TravelerDto(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getTotalSpentAmount() {
        return totalSpentAmount;
    }

    public void setTotalSpentAmount(BigDecimal totalSpentAmount) {
        this.totalSpentAmount = totalSpentAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TravelerDto that = (TravelerDto) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
