package com.te.productwebapp.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Admin implements Serializable{
	
	@Id
	@Column
	private int aid;
	
	@Column
	private String aname;
	
	@Column
	private String password;

}
