package com.rishiqing.common.util.http;

import com.rishiqing.common.exception.RsqSyncException;
import com.rishiqing.common.model.RsqCommonUserVO;
import com.rishiqing.common.model.RsqDepartmentVO;
import com.rishiqing.common.model.RsqTeamVO;

import java.util.Map;

/**
 * @author Wallace Mao
 * 将组织结构和成员信息同步到日事清需要实现的接口
 */
public interface HttpUtilRsqSync {

    /**
     * 创建公司
     * @param appName
     * @param appToken
     * @param rsqTeamVO
     * @return
     * @throws RsqSyncException
     */
    public RsqTeamVO createCorp(String appName, String appToken, RsqTeamVO rsqTeamVO) throws RsqSyncException;

    /**
     * 创建部门
     * @param appName
     * @param appToken
     * @param rsqTeamVO
     * @param rsqDepartmentVO
     * @return
     * @throws RsqSyncException
     */
    public RsqDepartmentVO createDepartment(String appName, String appToken, RsqTeamVO rsqTeamVO, RsqDepartmentVO rsqDepartmentVO) throws RsqSyncException;

    /**
     * 更新部门
     * @param appName
     * @param appToken
     * @param rsqTeamVO
     * @param rsqDepartmentVO
     * @return
     * @throws RsqSyncException
     */
    public RsqDepartmentVO updateDepartment(String appName, String appToken, RsqTeamVO rsqTeamVO, RsqDepartmentVO rsqDepartmentVO) throws RsqSyncException;

    /**
     * 删除部门
     * @param appName
     * @param appToken
     * @param rsqTeamVO
     * @param rsqDepartmentVO
     * @return
     * @throws RsqSyncException
     */
    public RsqDepartmentVO deleteDepartment(String appName, String appToken, RsqTeamVO rsqTeamVO, RsqDepartmentVO rsqDepartmentVO) throws RsqSyncException;

    /**
     * 创建用户
     * @param appName
     * @param appToken
     * @param rsqTeamVO
     * @param rsqCommonUserVO
     * @return
     * @throws RsqSyncException
     */
    public RsqCommonUserVO createUser(String appName, String appToken, RsqTeamVO rsqTeamVO, RsqCommonUserVO rsqCommonUserVO) throws RsqSyncException;

    /**
     * 更新用户
     * @param appName
     * @param appToken
     * @param rsqTeamVO
     * @param rsqCommonUserVO
     * @return
     * @throws RsqSyncException
     */
    public RsqCommonUserVO updateUser(String appName, String appToken, RsqTeamVO rsqTeamVO, RsqCommonUserVO rsqCommonUserVO) throws RsqSyncException;

    /**
     * 从团队中移除用户
     * @param appName
     * @param appToken
     * @param rsqTeamVO
     * @param rsqCommonUserVO
     * @return
     * @throws RsqSyncException
     */
    public RsqCommonUserVO userLeaveTeam(String appName, String appToken, RsqTeamVO rsqTeamVO, RsqCommonUserVO rsqCommonUserVO) throws RsqSyncException;

    /**
     * 更新用户的管理员状态
     * @param appName
     * @param appToken
     * @param rsqTeamVO
     * @param rsqCommonUserVO
     * @return
     * @throws RsqSyncException
     */
    public RsqCommonUserVO setUserAdmin(String appName, String appToken, RsqTeamVO rsqTeamVO, RsqCommonUserVO rsqCommonUserVO) throws RsqSyncException;
}
