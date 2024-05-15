package cn.iocoder.yudao.module.system.api.permission.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Schema(description = "RPC 服务 - 仓库的数据权限 Response DTO")
@Data
public class WarehouseDataPermissionRespDTO {

    @Schema(description = "是否可查看全部数据", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean all;

    @Schema(description = "可查看的仓库编号数组", requiredMode = Schema.RequiredMode.REQUIRED, example = "[1, 3]")
    private Set<Long> warehouseIds;

    public WarehouseDataPermissionRespDTO() {
        this.all = false;
        this.warehouseIds = new HashSet<>();
    }

}
