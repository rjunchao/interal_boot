package cn.rdp.common.controller;

import org.springframework.stereotype.Controller;

import cn.rdp.common.utils.ShiroUtils;
import cn.rdp.system.domain.UserDO;
import io.swagger.annotations.Api;

@Controller
@Api(value="控制器基类，主要是获取user")
public class BaseController {
	public UserDO getUser() {
		return ShiroUtils.getUser();
	}

	public Long getUserId() {
		return getUser().getUserId();
	}

	public String getUsername() {
		return getUser().getUsername();
	}
}