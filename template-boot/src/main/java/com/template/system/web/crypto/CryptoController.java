package com.template.system.web.crypto;

import com.template.comon.entity.Response;
import com.template.system.web.config.CryptoConfig;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.Setter;

/**
 * 解密信息
 * 
 * @author 薛超
 * @since 2019年12月7日
 * @version 1.0.8
 */
@RestController
@RequestMapping("crypto")
@Setter
public class CryptoController {

	private CryptoConfig cryptoConfig;

	@RequestMapping("/key/{type}")
	public Response<String> key(@PathVariable String type) {
		CryptoConfig.CryptoKey key = cryptoConfig.getKeys().get(type);
		if (key == null) {
			return Response.error("no config");
		}
		CryptoTypeEnum typeEnum = CryptoTypeEnum.getByType(type);
		String result = "system key error";
		switch (typeEnum) {
		case RSA:
			result = key.getPublicKey();
			break;
		case DES:
			result = key.getSecretKey();
			break;
		default:
			break;
		}
		return Response.ok(result);
	}

}
