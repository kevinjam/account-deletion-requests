package com.codecraftwithkevin.account_deletion_requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeletionRequest implements Serializable {
	@Id
	private String id;
	private String name;
	private String email;
	private String reason;
}
