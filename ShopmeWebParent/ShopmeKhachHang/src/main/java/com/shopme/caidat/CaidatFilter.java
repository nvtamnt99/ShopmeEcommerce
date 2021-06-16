package com.shopme.caidat;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shopme.common.entity.CaiDat;

@Component
public class CaidatFilter implements Filter {

	@Autowired private CaidatService caiDatService;
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		String url = servletRequest.getRequestURL().toString();
		
		if (url.endsWith(".css") || url.endsWith(".js") || url.endsWith(".png") ||
				url.endsWith(".jpg")) {
			chain.doFilter(request, response);
			return;
		}
		
		List<CaiDat> generalSettings = (List<CaiDat>) caiDatService.getGeneralSettings();
		
		generalSettings.forEach(setting -> {
			request.setAttribute(setting.getTuKhoa(), setting.getGiaTri());
		});
		
		chain.doFilter(request, response);
		
	}

}
