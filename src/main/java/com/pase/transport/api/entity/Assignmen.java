package com.pase.transport.api.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "assignments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Assignmen {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne
	private Driver driver;

	@OneToOne
	private Order order;

	private String pdfPath;

	private String imagePath;
}
