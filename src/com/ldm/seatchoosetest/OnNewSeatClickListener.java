package com.ldm.seatchoosetest;

public abstract interface OnNewSeatClickListener {
	/**
	 * ȡ��ѡ��
	 * 
	 * @param position
	 * @param Column
	 * @param Raw
	 * @param status
	 * @return
	 */
	public abstract boolean unClick(int position, int Column, int Raw, int status);

	/**
	 * ���ѡ��
	 * 
	 * @param position
	 * @param Column
	 * @param Raw
	 * @param status
	 * @return
	 */
	public abstract boolean onClick(int position, int Column, int Raw, int status);
}