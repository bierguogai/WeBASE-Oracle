/**
 * Copyright 2014-2019 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.webank.oracle.history;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webank.oracle.base.pojo.vo.BaseResponse;
import com.webank.oracle.base.pojo.vo.ConstantCode;
import com.webank.oracle.repository.ReqHistoryRepository;
import com.webank.oracle.repository.domian.ReqHistory;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * return encrypt type to web 0 is standard, 1 is guomi
 */
@Api(value = "/history", tags = "Query request history by request id")
@Slf4j
@RestController
@RequestMapping(value = "/history")
public class ReqHistoryController {

    @Autowired private ReqHistoryRepository reqHistoryRepository;

    @GetMapping("query/{requestId}")
    public BaseResponse query(@PathVariable("requestId") String requestId) {
        if (StringUtils.isBlank(requestId)) {
            return new BaseResponse(ConstantCode.PARAM_EXCEPTION);
        }
        Optional<ReqHistory> reqHistory = reqHistoryRepository.findByReqId(requestId);
        if (reqHistory.isPresent()) {
            return new BaseResponse(ConstantCode.SUCCESS, reqHistory.get());
        }

        return new BaseResponse(ConstantCode.DATA_NOT_EXISTS, requestId);
    }
}
