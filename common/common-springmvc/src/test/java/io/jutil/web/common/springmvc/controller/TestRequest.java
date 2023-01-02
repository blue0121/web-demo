package io.jutil.web.common.springmvc.controller;

import io.jutil.web.common.springmvc.validation.group.GetOperation;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jin Zheng
 * @since 2022-12-16
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestRequest {
	@NotEmpty(groups = {GetOperation.class}, message = "名称不能为空")
	private String name;

}
