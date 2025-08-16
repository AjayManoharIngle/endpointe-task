package org.endpointe.exception.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TBL_ERROR_LOGS")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorLog {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "N_ID")
    private Long id;
	
	@Column(name = "DT_CREATED_ON")
    private Instant createdOn;
	
	@Column(name = "S_PATH")
    private String path;
	
	@Lob
	@Column(name = "S_ERROR_MESSAGE", columnDefinition = "CLOB")
    private String errorMessage;
	
	@Column(name="N_STATUS")
    private int status;
}
