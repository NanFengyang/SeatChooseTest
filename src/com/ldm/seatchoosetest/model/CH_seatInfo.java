package com.ldm.seatchoosetest.model;

public class CH_seatInfo {
	private String id;// ��λID
	private Integer position;// ����
	private Integer raw;// ����
	private Integer column;// ����
	private Integer status;// ��λ״̬

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getRaw() {
		return raw;
	}

	public void setRaw(Integer raw) {
		this.raw = raw;
	}

	public Integer getColumn() {
		return column;
	}

	public void setColumn(Integer column) {
		this.column = column;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CH_seatInfo [id=" + id + ", position=" + position + ", raw=" + raw + ", column="
				+ column + ", status=" + status + "]";
	}

}
