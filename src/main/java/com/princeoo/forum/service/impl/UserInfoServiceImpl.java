package com.princeoo.forum.service.impl;

import com.princeoo.forum.pojo.UserInfo;
import com.princeoo.forum.mapper.UserInfoMapper;
import com.princeoo.forum.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
