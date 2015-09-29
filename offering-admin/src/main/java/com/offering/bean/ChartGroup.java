package com.offering.bean;

/**
 * 群聊信息
 * @author surfacepro3
 *
 */
public class ChartGroup {

	private String id;
	private String groupName;
	private String createTime;
	private String groupInfo;
	private String status;
	private String share_group_image;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getGroupInfo() {
		return groupInfo;
	}
	public void setGroupInfo(String groupInfo) {
		this.groupInfo = groupInfo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getShare_group_image() {
		return share_group_image;
	}
	public void setShare_group_image(String share_group_image) {
		this.share_group_image = share_group_image;
	}
}
