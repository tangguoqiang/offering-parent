package com.offering.constant;


/**
 * 全局常量表
 * @author gtang
 *
 */
public class GloabConstant {
	
	/**
	 * 产品状态 0：测试 1:生产
	 */
	public final static String PRODUCT_MODEL = "0";
	
	/**
	 *  是否 0：是  1：否
	 */
	public final static String YESNO_YES = "0";
	public final static String YESNO_NO = "1";
	
	/**
	 *  有效状态 0：无效 1：有效
	 */
	public final static String STATUS_INVALID = "0";
	public final static String STATUS_EFFECT = "1";
	
	/**
	 *  验证码类型 0：注册  1：找回密码
	 */
	public final static String CODE_REG = "0";
	public final static String CODE_FINDPASS = "1";
	
	/**
	 *  固定响应参数
	 */
	public final static String REP_CODE = "code";
	public final static String REP_MSG = "msg";
	public final static String REP_DATA = "data";
	
	/**
	 *  响应编码 0：成功  1：参数错误  2：其他错误
	 */
	public final static String CODE_SUCCESS = "0";
	public final static String CODE_ERR_PARAM = "1";
	public final static String CODE_ERR_OTHER = "2";

	/**
	 * 用户类型  0：管理员 2：大拿 1：普通用户
	 */
	public final static String USER_TYPE_ADMIN = "0";
	public final static String USER_TYPE_GREATER = "2";
	public final static String USER_TYPE_NORMAL = "1";
	
	/**
	 * 登陆类型  0：手机 1：微信 2：新浪 3：qq
	 */
	public final static String LOGIN_TYPE_PHONE = "0";
	public final static String LOGIN_TYPE_WX = "1";
	public final static String LOGIN_TYPE_SINA = "2";
	public final static String LOGIN_TYPE_QQ = "3";
	
	/**
	 * 用户状态  0：启用 1：停用 2:申请成为大拿
	 */
	public final static String USER_STATUS_QY = "0";
	public final static String USER_STATUS_TY = "1";
	public final static String USER_STATUS_GREATER = "2";
	
	/**
	 * 活动状态  0：未开始 1：正在进行 2:已结束
	 */
	public final static String ACTIVITY_STATUS_WKS = "0";
	public final static String ACTIVITY_STATUS_JX = "1";
	public final static String ACTIVITY_STATUS_JS = "2";
	public final static String ACTIVITY_STATUS_CG = "3";
	
	/**
	 * 加入群聊状态 0:成功 1:失败
	 */
	public final static String JOIN_GROUP_SUCCESS = "0";
	public final static String JOIN_GROUP_FAITURE = "1";
	/**
	 * 聊天类型  0：群聊 1：私聊
	 */
	public final static String CHART_GROUP = "0";
	public final static String CHART_PRIVATE = "1";
	
	/**
	 * 活动类型  0：求职咨询 1：线上宣讲会 2:线下宣讲会 3:分享会精华
	 */
	public final static String ACTIVITY_TYPE_QZZX = "0";
	public final static String ACTIVITY_TYPE_XSXJH = "1";
	public final static String ACTIVITY_TYPE_XXXJH = "2";
	public final static String ACTIVITY_TYPE_FXHJH = "3";
	
	/**
	 * 系统字典分组-年级,行业
	 */
	public final static String GROUP_GRADE = "grade";
	public final static String GROUP_INDUSTRY = "industry";
	
	/**
	 * 活动一次返回数量
	 */
	public final static int LIMIT_ACTIVITY = 3;
	
	/**
	 * 活动加载操作 0:初始 1:下拉 2:上拉
	 */
	public final static String OP_INIT = "0";
	public final static String OP_DOWN = "1";
	public final static String OP_UP = "2";
	
	/**
	 * 私聊类型 0:问大拿 1:话题咨询
	 */
	public final static String PRIVATECHAT_0 = "0";
	public final static String PRIVATECHAT_1 = "1";
	
	/**
	 * 未读消息类型  1:评论 2:点赞
	 */
	public final static String COMMENT_TYPE_1 = "1";
	public final static String COMMENT_TYPE_2 = "2";
	
	/**
	 * 通知消息类型  1:新话题 2:新评论
	 */
	public final static String NOTIFY_TYPE_1 = "1";
	public final static String NOTIFY_TYPE_2 = "2";
	
	/**
	 * APP服务版本
	 */
	public final static int APP_SERVICE_VERSION = 1;
	
	/**
	 * 文件系统根目录 /home/offering/images/ and C:\\gtang\\picture\\
	 */
	public final static String ROOT_DIR = "/home/offering/images/";
	
}
