package cn.iocoder.yudao.framework.datapermission.core.rule.warehouse;

import cn.iocoder.yudao.framework.datapermission.core.rule.dept.DeptDataPermissionRule;

@FunctionalInterface
public interface WarehouseDataPermissionRuleCustomizer {
    void customize(WarehouseDataPermissionRule rule);
}
