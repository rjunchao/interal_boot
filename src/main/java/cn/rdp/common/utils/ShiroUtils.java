package cn.rdp.common.utils;

import java.security.Principal;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import cn.rdp.system.domain.UserDO;

public class ShiroUtils {
   
//	@Autowired
//    private static SessionDAO sessionDAO;

    public static Subject getSubjct() {
        return SecurityUtils.getSubject();
    }
    public static UserDO getUser() {
        Object object = getSubjct().getPrincipal();
        return (UserDO)object;
    }
    public static Long getUserId() {
        return getUser().getUserId();
    }
    public static void logout() {
        getSubjct().logout();
    }

    public static List<Principal> getPrinciples() {
        List<Principal> principals = null;
       /* Collection<Session> sessions = sessionDAO.getActiveSessions();
        Iterator<Session> iterator = sessions.iterator();
        Principal p = null;
        while(iterator.hasNext()) {
        	Session s = iterator.next();
        	
        }*/
        return principals;
    }
}
