package com.groupsavings.model.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecentActivityDTO {
	private String id;
	private String type;
	private String title;
	private UserDTO user;
	private Double amount;
	private String loanCode;
	private Double loanAmount;
	private Integer term;
	private Integer totalTerms;
	private String timestamp;
	private String timeAgo;
	private String status;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UserDTO {
		private String memberCode;
		private String firstName;
		private String lastName;
	}
}
