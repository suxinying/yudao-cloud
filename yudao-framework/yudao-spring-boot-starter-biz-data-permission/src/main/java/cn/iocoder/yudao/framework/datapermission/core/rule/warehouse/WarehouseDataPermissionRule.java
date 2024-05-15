package cn.iocoder.yudao.framework.datapermission.core.rule.warehouse;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.enums.UserTypeEnum;
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.framework.common.util.string.StrUtils;
import cn.iocoder.yudao.framework.datapermission.core.rule.DataPermissionRule;
import cn.iocoder.yudao.framework.datapermission.core.rule.dept.DeptDataPermissionRule;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.yudao.framework.mybatis.core.util.MyBatisUtils;
import cn.iocoder.yudao.framework.security.core.LoginUser;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.system.api.permission.dto.WarehouseDataPermissionRespDTO;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import lombok.AllArgsConstructor;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
public class WarehouseDataPermissionRule implements DataPermissionRule {
    // 缓存的key
    protected static final String CONTEXT_KEY = WarehouseDataPermissionRule.class.getSimpleName();
    // 通用仓库字段
    private static final String WAREHOUSE_NAME = "warehouse_id";

    /**
     * 基于仓库的表字段配置
     */
    private final Map<String, String> warehouseColumns = new HashMap<>();

    private final Set<String> TABLE_NAMES = new HashSet<>();

    public Set<String> getTableNames() {
        return TABLE_NAMES;
    }

    @Override
    public Expression getExpression(String tableName, Alias tableAlias) {
        // 获取登录用户
        LoginUser loginUser = SecurityFrameworkUtils.getLoginUser();
        if (loginUser == null) {
            return null;
        }
        // 非管理员用户不进行权限处理
        if (!loginUser.getUserType().equals(UserTypeEnum.ADMIN.getValue())) {
            return null;
        }
        // 从用户缓存获取仓库权限
        WarehouseDataPermissionRespDTO warehouseDataPermission = loginUser.getContext(CONTEXT_KEY, WarehouseDataPermissionRespDTO.class);
        if (null == warehouseDataPermission){
            // todo 从数据库获取仓库权限
            return null;
        }
        // 情况一，存在所有权限，直接返回
        if (warehouseDataPermission.getAll()) {
            return null;
        }
        // 情况二，无权限
        if (warehouseDataPermission.getWarehouseIds().isEmpty()) {
            return new EqualsTo(null,null);
        }
        // 情况三，拼接权限
        return buildWarehouseExpression(tableName,tableAlias,warehouseDataPermission.getWarehouseIds());
    }

    /**
     * 配置仓库的表达式
     * @param tableName 表名
     * @param tableAlias 表别名
     * @param warehouseIds id列表
     * @return 表达式
     */
    private Expression buildWarehouseExpression(String tableName, Alias tableAlias, Set<Long> warehouseIds) {
        String columnName = warehouseColumns.get(tableName);
        if (StrUtil.isEmpty(columnName)) {
            return null;
        }
        if (CollUtil.isEmpty(warehouseIds)) {
            return null;
        }
        return new InExpression(MyBatisUtils.buildColumn(tableName,tableAlias,columnName),
                new ExpressionList(CollectionUtils.convertList(warehouseIds, LongValue::new)));
    }
    /**
     * 添加表名和字段名到集合中
     */
    public void addWarehouseColumn(Class<? extends BaseDO> entityClass) {
        addWarehouseColumn(entityClass, WAREHOUSE_NAME);
    }

    public void addWarehouseColumn(Class<? extends BaseDO> entityClass, String columnName) {
        String tableName = TableInfoHelper.getTableInfo(entityClass).getTableName();
        addWarehouseColumn(tableName, columnName);
    }

    public void addWarehouseColumn(String tableName, String columnName) {
        warehouseColumns.put(tableName, columnName);
        TABLE_NAMES.add(tableName);
    }

}
