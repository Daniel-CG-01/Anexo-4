package com.hibernate.model;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter

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

	public Empleado() {
		super();
	}
	
	public Empleado(String nombre, int edad, String foto) {
		super();
		this.nombre = nombre;
		this.edad = edad;
		this.foto = foto;
	}
}