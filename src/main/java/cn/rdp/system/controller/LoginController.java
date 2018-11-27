package cn.rdp.system.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.rdp.common.annotation.Log;
import cn.rdp.common.controller.BaseController;
import cn.rdp.common.domain.FileDO;
import cn.rdp.common.domain.Tree;
import cn.rdp.common.service.FileService;
import cn.rdp.common.utils.MD5Utils;
import cn.rdp.common.utils.R;
import cn.rdp.common.utils.ShiroUtils;
import cn.rdp.system.domain.MenuDO;
import cn.rdp.system.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(value="用户登录", tags="LoginController")
public class LoginController extends BaseController {
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MenuService menuService;
	
	@Autowired
	FileService fileService;
	
	@GetMapping({ "/", "" })
	String welcome(Model model) {

		return "redirect:/login";
//		return "redirect:/blog";
	}

	/**
	 * 查询当前用户的菜单和当前用户的头像
	 * @param model
	 * @return
	 */
	@ApiOperation(notes="主页请求", value = "主页请求")
	@Log("请求访问主页")
	@GetMapping({ "/index" })
	String index(Model model) {
		List<Tree<MenuDO>> menus = menuService.listMenuTree(getUserId());
		model.addAttribute("menus", menus);
		model.addAttribute("name", getUser().getName());
		FileDO fileDO = fileService.get(getUser().getPicId());
		if(fileDO!=null&&fileDO.getUrl()!=null){
			if(fileService.isExist(fileDO.getUrl())){
				model.addAttribute("picUrl",fileDO.getUrl());
			}else {
				model.addAttribute("picUrl","/img/photo_s.jpg");
			}
		}else {
			model.addAttribute("picUrl","/img/photo_s.jpg");
		}
		model.addAttribute("username", getUser().getUsername());
		return "index_v1";
	}

	@GetMapping("/login")
	String login() {
		return "login";
	}

	/**
	 * 处理登录
	 * @param username
	 * @param password
	 * @return
	 */
	@ApiOperation(value="处理登录", notes="处理登录")
	@Log("登录")
	@PostMapping("/login")
	@ResponseBody
	R ajaxLogin(String username, String password) {
		password = MD5Utils.encrypt(username, password);
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			return R.ok();
		} catch (AuthenticationException e) {
			return R.error("用户或密码错误");
		}
	}

	@ApiOperation(value="退出登录", notes="退出登录")
	@GetMapping("/logout")
	String logout() {
		ShiroUtils.logout();
		return "redirect:/login";
	}

	@GetMapping("/main")
	String main() {
		return "main";
	}

}
