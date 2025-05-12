package com.hibernate.model;

import jakarta.persistence.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor

@Entity
@Table(name="empleado")
public class Empleado {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	int id;
	
	@Column(name="nombre")
	String nombre;
	
	@Column(name="edad")
	int edad;
	
	@Column(name="foto")
	String foto;
}