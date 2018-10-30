package com.daka.controller.customer;

import com.daka.entry.CustomerVO;
import com.daka.entry.Page;
import com.daka.entry.PageData;
import com.daka.service.customer.CustomerService;
import com.daka.util.MD5;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping("/toList")
    public Object toList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("customer/list");
        return mv;
    }

    @RequestMapping("/queryData")
    @ResponseBody
    public Object queryData(HttpServletRequest request) {
        PageData pd = new PageData(request);
        Page page = new Page();
        page.setPd(pd);
        List<PageData> customerList = customerService.queryCustomerlistPage(page);
        JSONObject result = new JSONObject();
        result.put("status", "200");
        result.put("message", "OK");
        result.put("count",page.getTotalResult());
        result.put("data",customerList);
        return result;
    }

    @RequestMapping("/toAdd")
    public Object toAdd(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("customer/add");
        return mv;
    }

    @RequestMapping("/save")
    @ResponseBody
    public Object save(HttpServletRequest request, CustomerVO customerVO) {
    	customerVO.setPassword(MD5.md5(customerVO.getPassword()));
        customerService.insertCustomer(customerVO);
        JSONObject result = new JSONObject();
        result.put("status","200");
        result.put("message","OK");
        return result;
    }

    @RequestMapping("/toReview")
    public Object toReview(HttpServletRequest request) {
        String id = request.getParameter("id");
        CustomerVO customerVO = customerService.queryCustomerById(Integer.parseInt(id));
        ModelAndView mv = new ModelAndView("/customer/review");
        mv.addObject("result", customerVO);
        return mv;
    }

    @RequestMapping("/toUpdate")
    @ResponseBody
    public Object toUpdate(HttpServletRequest request,CustomerVO customerVO) {
    	customerVO.setPassword(MD5.md5(customerVO.getPassword()));
        customerService.updateByCustomer(customerVO);
        JSONObject result = new JSONObject();
        result.put("status","200");
        result.put("message","OK");
        return result;
    }

    @RequestMapping("/updateState1")
    @ResponseBody
    public Object updateState1(HttpServletRequest request,Integer id) {
        customerService.updateState1(id);
        JSONObject result = new JSONObject();
        result.put("status","200");
        result.put("message","OK");
        return result;
    }

    @RequestMapping("/updateState2")
    @ResponseBody
    public Object updateState2(HttpServletRequest request,Integer id) {
        customerService.updateState2(id);
        JSONObject result = new JSONObject();
        result.put("status","200");
        result.put("message","OK");
        return result;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Object deleteById(HttpServletRequest request,Integer id) {
        customerService.deleteById(id);
        JSONObject result = new JSONObject();
        result.put("status","200");
        result.put("message","OK");
        return result;
    }
    
    /**后台激活用户
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/activation")
    @ResponseBody
    public Object updateActivation(HttpServletRequest request,Integer id){
    	customerService.updateActivation(id);
    	 JSONObject result = new JSONObject();
         result.put("status","200");
         result.put("message","OK");
         return result;
    }
    

}
