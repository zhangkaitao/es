/**
 * auto code generation
 */
package ${packageName}.${sysName}.${moduleName}.repository;

import org.springframework.stereotype.Repository;
import com.sishuok.es.common.repository.BaseRepository;
import ${packageName}.${sysName}.${moduleName}.entity.${ClassName};

/**
 * ${functionName}Repository接口
 * @author ${classAuthor}
 * @version ${classVersion}
 */
@Repository
public interface ${ClassName}Repository extends BaseRepository<${ClassName}, Long> {
	
}
