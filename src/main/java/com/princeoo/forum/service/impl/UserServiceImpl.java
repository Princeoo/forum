package com.princeoo.forum.service.impl;

import com.princeoo.forum.pojo.User;
import com.princeoo.forum.mapper.UserMapper;
import com.princeoo.forum.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
