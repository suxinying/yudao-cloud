package cn.iocoder.mall.product.api.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户_商品_收藏记录表
 * @author xiaofeng
 * @date 2019-07-01 20:23:30
 */
@Data
@Accessors(chain = true)
public class UserProductSpuCollectionsBO implements Serializable {

    /**
     * id自增长
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户名称
     */
    private String nickname;

    /**
     * 商品id
     */
    private Integer spuId;

    /**
     * 商品名字
     */
    private String spuName;

    /**
     * 图片名字
     */
    private String spuImage;

    /**
     * 卖点
     */
    private String sellPoint;

    /**
     * 价格，单位：分
     */
    private Integer price;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除状态
     */
    private Integer deleted;


}
