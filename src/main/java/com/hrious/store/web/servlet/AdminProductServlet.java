package com.hrious.store.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.hrious.store.pojo.Category;
import com.hrious.store.pojo.PageModel;
import com.hrious.store.pojo.Product;
import com.hrious.store.service.CategoryService;
import com.hrious.store.service.ProductService;
import com.hrious.store.service.impl.CategoryServiceImpl;
import com.hrious.store.service.impl.ProductServiceImpl;
import com.hrious.store.utils.UUIDUtils;
import com.hrious.store.utils.UploadUtils;
import com.hrious.store.web.base.BaseServlet;

public class AdminProductServlet extends BaseServlet {

	private ProductService productService = new ProductServiceImpl();
	private CategoryService categoryService = new CategoryServiceImpl();

	// findAllProductsWithPage
	public String findAllProductsWithPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取请求参数
		String pflag = request.getParameter("pflag");
		int page = Integer.parseInt(request.getParameter("page"));
		
		PageModel pageModel = null;
		
		// 调用业务层查询结果
		if ("1".equals(pflag)) { // 查询已下架
			pageModel = productService.findAllProductsByPflagWithPage(pflag, page);
		} else { // 查询所有
			pageModel = productService.findAllProductsWithPage(page);
		}
		
		// 把PageModel放入request中
		request.setAttribute("page", pageModel);
		// 转发到/admin/product/list.jsp
		return "/admin/product/list.jsp";
	}

	// addProductUI
	public String addProductUI(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 调用业务层查询所有商品分类
		List<Category> categories = categoryService.getAllCategory();
		// 跳转到/admin/product/add.jsp
		request.setAttribute("categories", categories);
		return "/admin/product/add.jsp";
	}

	// addProduct
	public String addProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 判断是否为multipart/form-data
		if (ServletFileUpload.isMultipartContent(request)) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = null;

			try {
				items = upload.parseRequest(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
				return null;
			}
			if (null != items) {
				// 保存数据
				Map<String, String> map = new HashMap<String, String>();
				// 传递数据
				Product product = new Product();
				for (FileItem item : items) {
					if (item.isFormField()) { // 是普通属性
						map.put(item.getFieldName(), item.getString("utf-8"));
					} else { // 是文件
						// 文件名
						String name = UploadUtils.getUUIDName(item.getName());
						String dirPath = UploadUtils.getDir(name);
						// 文件路径
						String path = this.getServletContext().getRealPath("/products/3") + dirPath;

						// 判断文件夹是否存在
						File dir = new File(path);
						if (!dir.exists()) { // 文件夹不存在
							dir.mkdirs(); // 防止多级文件夹不存在
						}
						File img = new File(path, name);
						if (!img.exists()) { // 图片不存在
							img.createNewFile();
						}

						// 通过流对接实现图片的保存

						InputStream input = item.getInputStream();
						OutputStream output = new FileOutputStream(img);
						IOUtils.copy(input, output);

						IOUtils.closeQuietly(input);
						IOUtils.closeQuietly(output);

						map.put(item.getFieldName(), "products/3" + dirPath + "/" + name);

					}
				}
				try {
					BeanUtils.populate(product, map);
				} catch (Exception e) {
				}
				product.setPid(UUIDUtils.getCode());
				product.setPdata(new Date());
				product.setPflag("0");
				// 保存产品到数据库
				productService.saveProduct(product);
				response.sendRedirect(request.getContextPath() + "/adminProduct?method=findAllProductsWithPage&page=1");
			}
		}
		return null;
	}
}
