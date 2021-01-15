package com.princeoo.forum.service.impl;

import com.princeoo.forum.pojo.UserInfo;
import com.princeoo.forum.mapper.UserInfoMapper;
import com.princeoo.forum.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author princeoo
 * @since 2021-01-13
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {



}
