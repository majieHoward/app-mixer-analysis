package com.howard.www.analysis.service.impl;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.howard.www.analysis.dao.IAnalysisSystemMenuDao;
import com.howard.www.analysis.entity.BaseMenu;
import com.howard.www.analysis.service.IAnalysisSystemMenuService;
import com.howard.www.core.data.transfer.dto.IDataTransferObject;
import com.howard.www.core.web.util.FrameworkStringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Repository("analysisSystemMenuService")
public class AnalysisSystemMenuServiceImpl implements IAnalysisSystemMenuService {
	@Autowired
	private ApplicationContext cApplicationContext;

	/**
	 * 获取当前用户可以使用到的菜单
	 */
	public Vector obtainSystemMenuItems(IDataTransferObject queryParameters) throws Exception {
		JSONArray menuItems = getIAnalysisSystemMenuDao().obtainSystemMenuItems(queryParameters);
		return structureMenuItem(menuItems);
	}

	private Vector structureMenuItem(List<JSONObject> menuItems) throws Exception {
		/**
		 * menuMap保存所有的菜单对象
		 */
		ConcurrentHashMap<String, BaseMenu> menuMap = new ConcurrentHashMap<String, BaseMenu>();
		BaseMenu baseMenu = null;
		String menuId;
		String menuType;
		String parentOfMenuId;
		/**
		 * 遍历所有的菜单并且将抽象出的对象添加到menuMap中
		 */
		for (JSONObject menuItem : menuItems) {
			menuId = FrameworkStringUtil.asString(menuItem.get("MENU_ID"));
			menuType = FrameworkStringUtil.asString(menuItem.get("MENU_TYPE"));
			baseMenu = new BaseMenu();
			baseMenu.initMenuEntity(menuItem);
			menuMap.put(menuId, baseMenu);
		}
		/**
		 * 保存所有的一级菜单元素
		 */
		Vector menuListItems = new Vector();
		BaseMenu tempBaseMenu = null;
		BaseMenu tempParentBaseMenu = null;
		/**
		 * 保存所有的根节点元素
		 */
		ConcurrentHashMap<String, BaseMenu> rootMenuMap = new ConcurrentHashMap<String, BaseMenu>();
		/**
		 * 原来的写法(从map中remove)只能支持二级菜单所以对此处进行改造
		 */
		for (ConcurrentHashMap.Entry<String, BaseMenu> entry : menuMap.entrySet()) {
			tempBaseMenu = (BaseMenu) entry.getValue();
			parentOfMenuId = FrameworkStringUtil.asString(tempBaseMenu.getParentOfMenuId());
			menuId = FrameworkStringUtil.asString(tempBaseMenu.getMenuId());
			/**
			 * 如果存在parentOfMenuId并且parentOfMenuId为key的value在Map中存在的那么它一定不是一级菜单
			 */
			if (!"".equals(parentOfMenuId)) {
				tempParentBaseMenu = menuMap.get(parentOfMenuId);
			}
			if (!"".equals(parentOfMenuId) && tempParentBaseMenu != null) {
				tempParentBaseMenu.setChildOfMenu(tempBaseMenu);
			} else {
				/**
				 * 如果不存在parentOfMenuId或者parentOfMenuId为key的value在Map中不存在那么它就就是一级菜单
				 */
				rootMenuMap.put(menuId, tempBaseMenu);
			}
			tempBaseMenu = null;
			tempParentBaseMenu = null;
		}
		/**
		 * 遍历所有的根节点
		 */
		for (ConcurrentHashMap.Entry<String, BaseMenu> entry : rootMenuMap.entrySet()) {
			menuListItems.add(entry.getValue());
		}
		return menuListItems;
	}

	private IAnalysisSystemMenuDao getIAnalysisSystemMenuDao() throws Exception {
		return (IAnalysisSystemMenuDao) cApplicationContext.getBean("analysisSystemMenuDao");
	}

	public void obtainSystemMenuItemsModelMap(IDataTransferObject queryParameters, ModelMap model) throws Exception {
		queryParameters.evaluteRequiredParameter("menuType", "M");
		obtainSystemMenuItemsModelMapByPublic(queryParameters, model);
	}

	private void obtainSystemMenuItemsModelMapByPublic(IDataTransferObject queryParameters, ModelMap model)
			throws Exception {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		/**
		 * 从session中获取用户的staffId
		 */
		String staffId = FrameworkStringUtil.asString(session.getAttribute("loginStaffId"));
		queryParameters.evaluteRequiredParameter("staffId", staffId);

		model.put("menuItem", obtainSystemMenuItems(queryParameters));
	}

	public void obtainSystemMenuItemsModelMapByMobile(IDataTransferObject queryParameters, ModelMap model)
			throws Exception {
		queryParameters.evaluteRequiredParameter("menuType", "W");
		obtainSystemMenuItemsModelMapByPublic(queryParameters, model);
	}
}
