package com.hrious.store.pojo;

import java.util.List;

public class PageModel {
	
	private List<?> list; // 集合
	private int rows; // 一页显示多少条信息
	private int quantity; // 总条数
	private int currentPage; // 当前页
	private int maxPage; // 最大页
	private String url; // 补充分页信息
	@Override
	public String toString() {
		return "PageModel [list=" + list + ", rows=" + rows + ", quantity=" + quantity + ", currentPage=" + currentPage
				+ ", maxPage=" + maxPage + ", url=" + url + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + currentPage;
		result = prime * result + ((list == null) ? 0 : list.hashCode());
		result = prime * result + maxPage;
		result = prime * result + quantity;
		result = prime * result + rows;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PageModel other = (PageModel) obj;
		if (currentPage != other.currentPage)
			return false;
		if (list == null) {
			if (other.list != null)
				return false;
		} else if (!list.equals(other.list))
			return false;
		if (maxPage != other.maxPage)
			return false;
		if (quantity != other.quantity)
			return false;
		if (rows != other.rows)
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getMaxPage() {
		return maxPage;
	}
	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public PageModel(List<?> list, int rows, int quantity, int currentPage, int maxPage, String url) {
		super();
		this.list = list;
		this.rows = rows;
		this.quantity = quantity;
		this.currentPage = currentPage;
		this.maxPage = maxPage;
		this.url = url;
	}
	public PageModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
