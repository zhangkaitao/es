/**
 * auto code generation
 */
package ${packageName}.${sysName}.${moduleName}.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sishuok.es.common.web.controller.BaseCRUDController;
import ${packageName}.${sysName}.${moduleName}.entity.${ClassName};

/**
 * ${functionName}Controller
 * @author ${classAuthor}
 * @version ${classVersion}
 */
 
@Controller("admin${ClassName}Controller")
@RequestMapping(value = "/admin/${sysName}/${folderName}")
public class ${ClassName}Controller extends BaseCRUDController<${ClassName}, Long> {

    public ${ClassName}Controller() {
        setResourceIdentity("${permissionName}");
    }
}