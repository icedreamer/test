package com.tlys.comm.util.page;

import java.util.List;

/**
 * ��ѯ��������������ݺ�����
 * 
 * @author fengym
 * @notes 
 */
public class QueryResult<T>
{
	/** ��ѯ�ó�������List **/
	private List<T> resultList;
	/** ��ѯ�ó������� **/
	private int totalRecord;

	public List<T> getResultList()
	{
		return resultList;
	}

	public void setResultList(List<T> resultList)
	{
		this.resultList = resultList;
	}

	public int getTotalRecord()
	{
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord)
	{
		this.totalRecord = totalRecord;
	}
}
