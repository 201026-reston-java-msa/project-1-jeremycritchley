package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Reim_Type")
public class ReimType {
	
	@Id
	@Column(name="type_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int typeId;
	
	@Column(name="type_id")
	private String type;
	
	private ReimType() {
		super();
	}
	
	private ReimType(int typeId) {
		super();
		this.setTypeId(typeId);
	}
	
	private ReimType(String type) {
		super();
		this.setType(type);
	}
	
	private ReimType(int typeId, String type) {
		super();
		this.setTypeId(typeId);
		this.setType(type);
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
