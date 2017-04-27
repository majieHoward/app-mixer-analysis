package com.howard.www.analysis.entity;

import java.util.Vector;

import com.howard.www.core.web.util.FrameworkStringUtil;

import net.sf.json.JSONObject;

public class BaseMenu {
	private String menuId;
	private String menuText;
	private Vector<BaseMenu> childOfMenu = new Vector<BaseMenu>();
	private String parentOfMenuId;
	private String menuPathOfURI;
	private String menuSeq;

	public void initMenuEntity(JSONObject menuEntity) {
		String menuTextValue = FrameworkStringUtil.asString(menuEntity.get("MENU_TEXT"));
		String menuSeqValue = FrameworkStringUtil.asString(menuEntity.get("MENU_SEQ"));
		String upMenuIdValue = FrameworkStringUtil.asString(menuEntity.get("UP_MENU_ID"));
		String filePathValue = FrameworkStringUtil.asString(menuEntity.get("FILE_PATH"));
		String menuIdValue = FrameworkStringUtil.asString(menuEntity.get("MENU_ID"));
		setMenuText(menuTextValue);
		setMenuSeq(menuSeqValue);
		setMenuId(menuIdValue);
		setParentOfMenuId(upMenuIdValue);
		setMenuPathOfURI(filePathValue);
	}

	public String getMenuSeq() {
		return menuSeq;
	}

	public void setMenuSeq(String menuSeq) {
		this.menuSeq = menuSeq;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuText() {
		return menuText;
	}

	public void setMenuText(String menuText) {
		this.menuText = menuText;
	}

	public Vector<BaseMenu> getChildOfMenu() {
		return childOfMenu;
	}

	public void setChildOfMenu(BaseMenu childOfMenuItem) {
		childOfMenu.add(childOfMenuItem);
	}

	public String getParentOfMenuId() {
		return parentOfMenuId;
	}

	public void setParentOfMenuId(String parentOfMenuId) {
		this.parentOfMenuId = parentOfMenuId;
	}

	public String getMenuPathOfURI() {
		return menuPathOfURI;
	}

	public void setMenuPathOfURI(String menuPathOfURI) {
		this.menuPathOfURI = menuPathOfURI;
	}

}
