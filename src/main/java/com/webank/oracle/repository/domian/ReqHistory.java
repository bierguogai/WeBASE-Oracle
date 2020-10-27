package com.webank.oracle.repository.domian;

import static com.webank.oracle.base.properties.ConstantProperties.MAX_ERROR_LENGTH;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.webank.oracle.base.enums.OracleVersionEnum;
import com.webank.oracle.base.enums.SourceTypeEnum;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 */

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
//@DynamicUpdate
@Table(name = "req_history", schema = "webaseoracle")

public class ReqHistory {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(columnDefinition = "BIGINT(20) UNSIGNED")
    private Long id;

    /**
     * 请求编号，唯一
     */
    @Column(unique = true, nullable = false, length = 66)
    @EqualsAndHashCode.Include
    private String reqId;

    /**
     * Oracle 合约版本号，默认 1
     */
    @Column(unique = false, nullable = false, columnDefinition = "INT(11) UNSIGNED")
    @ColumnDefault("1")
    private int oracleVersion = 1;

    /**
     * 数据来源，0. url。默认0
     */
    @ColumnDefault("0")
    @Column(unique = false, nullable = false, columnDefinition = "INT(11) UNSIGNED")
    private int sourceType;

    /**
     * 请求地址格式
     */
    @Column(unique = false, nullable = false, length = 512)
    private String reqQuery;

    /**
     * 请求状态, 0. 请求中；1. 请求失败；2. 请求成功。默认 0
     */
    @ColumnDefault("0")
    @Column(unique = false, nullable = false, columnDefinition = "INT(11) UNSIGNED")
    private int reqStatus;


    /**
     * 来源合约地址
     */
    @Column(unique = false, nullable = false, length = 128)
    private String userContract;

    /**
     * 请求处理时长，默认 0
     */
    @ColumnDefault("0")
    @Column(unique = false, nullable = false, columnDefinition = "BIGINT(20) UNSIGNED")
    private long processTime;

    /**
     * 请求结果
     */
    @Column(nullable = true, length = 512)
    private String result;

    /**
     * 请求失败是错误信息
     */
    @Column(nullable = true, length = MAX_ERROR_LENGTH)
    private String error;

    /**
     * 证明类型, 0. 无证明；1. 签名认证。默认0
     */
    @ColumnDefault("0")
    @Column(columnDefinition = "INT(11) UNSIGNED")
    private int proofType;

    /**
     * 证明
     */
    private String proof;

    @CreationTimestamp
    private LocalDateTime createTime;
    @UpdateTimestamp
    private LocalDateTime modifyTime;


    /**
     * @return
     */
    public static ReqHistory build(String reqId, String userContract,
                                   OracleVersionEnum oracleVersionEnum, SourceTypeEnum sourceTypeEnum, String reqQuery) {
        ReqHistory reqHistory = new ReqHistory();
        reqHistory.setReqId(reqId);
        reqHistory.setOracleVersion(oracleVersionEnum.getId());
        reqHistory.setUserContract(userContract);
        reqHistory.setSourceType(sourceTypeEnum.getId());
        reqHistory.setReqQuery(reqQuery);
        return reqHistory;
    }
}