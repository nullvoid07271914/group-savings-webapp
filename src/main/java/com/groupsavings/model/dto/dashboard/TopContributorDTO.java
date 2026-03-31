package com.groupsavings.model.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopContributorDTO {
	private int rank;
	private String memberCode;
	private String firstName;
	private String lastName;
	private double totalContribution;
	private String avatarColor;
}
