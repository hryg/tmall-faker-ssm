package com.hryg.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.hryg.tmall.pojo.*;
import com.hryg.tmall.service.*;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
public class ForeController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ReviewService reviewService;

    @RequestMapping("forehome")
    public String home(Model model) {
        List<Category> categories = categoryService.list();
        productService.fill(categories);
        productService.fillByRow(categories);
        model.addAttribute("categories", categories);
        return "fore/home";
    }

    @RequestMapping("foreregister")
    public String register(Model model, User user) {
        String name = user.getName();
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);
        if (userService.isExist(name)) {
            String msg = "用户名已被占用！";
            model.addAttribute("msg", msg);
            model.addAttribute("user", null);
            return "fore/register";
        }
        userService.add(user);
        return "redirect:registerSuccessPage";
    }

    @RequestMapping("forelogin")
    public String login(@RequestParam("name") String name, @RequestParam("password") String password, Model model, HttpSession session) {
        name = HtmlUtils.htmlEscape(name);
        User user = userService.get(name, password);
        if (null != user) {
            session.setAttribute("user", user);
            return "redirect:forehome";
        } else {
            model.addAttribute("msg", "帐号密码错误");
            return "fore/login";
        }
    }

    @RequestMapping("forelogout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:forehome";
    }

    @RequestMapping("foreproduct")
    public String product(int pid, Model model) {
        Product p = productService.get(pid);

        List<ProductImage> productSingleImages = productImageService.list(p.getId(), ProductImageService.TYPE_SINGLE);
        List<ProductImage> productDetailImages = productImageService.list(p.getId(), ProductImageService.TYPE_DETAIL);
        p.setProductSingleImages(productSingleImages);
        p.setProductDetailImages(productDetailImages);

        List<PropertyValue> pvs = propertyValueService.list(p.getId());
        List<Review> reviews = reviewService.list(p.getId());
        productService.setSaleAndReviewNumber(p);
        model.addAttribute("reviews", reviews);
        model.addAttribute("p", p);
        model.addAttribute("pvs", pvs);
        return "fore/product";
    }

    @RequestMapping("forecheckLogin")
    @ResponseBody
    public String checkLogin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user == null ? "fail" : "success";
    }

    @RequestMapping("foreloginAjax")
    @ResponseBody
    public String loginAjax(@RequestParam("name") String name, @RequestParam("password") String password, HttpSession session) {
        name = HtmlUtils.htmlEscape(name);
        User user = userService.get(name, password);

        if (null == user) {
            return "fail";
        }
        session.setAttribute("user", user);
        return "success";
    }

    @RequestMapping("forecategory")
    public String category(int cid, String sort, Model model) {
        Category category = categoryService.get(cid);
        productService.fill(category);
        productService.setSaleAndReviewNumber(category.getProducts());

        if (null != sort) {
            switch (sort) {
                case "review":
                    Collections.sort(category.getProducts(), (p1, p2) -> p2.getReviewCount() - p1.getReviewCount());
                    break;
                case "date":
                    Collections.sort(category.getProducts(), (p1, p2) -> p1.getCreateDate().compareTo(p2.getCreateDate()));
                    break;
                case "saleCount":
                    Collections.sort(category.getProducts(), (p1, p2) -> p2.getSaleCount() - p1.getSaleCount());
                    break;
                case "price":
                    Collections.sort(category.getProducts(), (p1, p2) -> (int) (p1.getPromotePrice() - p2.getPromotePrice()));
                    break;
                case "all":
                    Collections.sort(category.getProducts(), (p1, p2) -> p2.getReviewCount() * p2.getSaleCount() - p1.getReviewCount() * p1.getSaleCount());
                    break;
                default:
                    break;
            }
        }
        model.addAttribute("c", category);
        return "fore/category";
    }

    @RequestMapping("foresearch")
    public String search(String keyword, Model model) {
        PageHelper.offsetPage(0, 20);
        List<Product> ps = productService.search(keyword);
        productService.setSaleAndReviewNumber(ps);
        model.addAttribute("ps", ps);
        return "fore/searchResult";
    }

    @RequestMapping("forebuyone")
    public String bugone(int pid, int num, HttpSession session) {
        Product product = productService.get(pid);
        int oiid = 0;
        boolean found = false;
        User user = (User) session.getAttribute("user");
        List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getProduct().getId().intValue() == product.getId()) {
                orderItem.setNumber(orderItem.getNumber() + num);
                orderItemService.update(orderItem);
                found = true;
                oiid = orderItem.getId();
                break;
            }
        }

        if (!found) {
            OrderItem orderItem = new OrderItem();
            orderItem.setNumber(num);
            orderItem.setProduct(product);
            orderItem.setPid(pid);
            orderItem.setUid(user.getId());
            orderItemService.add(orderItem);
            oiid = orderItem.getId();
        }
        return "redirect:forebuy?oiid=" + oiid;
    }

    @RequestMapping("forebuy")
    public String buy(Model model, String[] oiid, HttpSession session) {
        List<OrderItem> orderItems = new ArrayList<>();
        float total = 0;

        for (String strid : oiid) {
            int id = Integer.parseInt(strid);
            OrderItem orderItem = orderItemService.get(id);
            total += orderItem.getNumber() * orderItem.getProduct().getOriginalPrice();
            orderItems.add(orderItem);
        }

        session.setAttribute("ois", orderItems);
        model.addAttribute("total", total);
        return "fore/buy";
    }

    @RequestMapping("foreaddCart")
    @ResponseBody
    public String addCart(int pid, int num, Model model,HttpSession session) {
        Product p = productService.get(pid);
        User user =(User)  session.getAttribute("user");
        boolean found = false;

        List<OrderItem> ois = orderItemService.listByUser(user.getId());
        for (OrderItem oi : ois) {
            if(oi.getProduct().getId().intValue()==p.getId().intValue()){
                oi.setNumber(oi.getNumber()+num);
                orderItemService.update(oi);
                found = true;
                break;
            }
        }

        if(!found){
            OrderItem oi = new OrderItem();
            oi.setUid(user.getId());
            oi.setNumber(num);
            oi.setPid(pid);
            orderItemService.add(oi);
        }
        return "success";
    }

    @RequestMapping("forecart")
    public String cart( Model model,HttpSession session) {
        User user =(User)  session.getAttribute("user");
        List<OrderItem> ois = orderItemService.listByUser(user.getId());
        model.addAttribute("ois", ois);
        return "fore/cart";
    }

    @RequestMapping("forecreateOrder")
    public String createOrder( Model model,Order order,HttpSession session){
        User user =(User)  session.getAttribute("user");
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(10000);
        order.setOrderCode(orderCode);
        order.setCreateDate(new Date());
        order.setUid(user.getId());
        order.setStatus(OrderService.WAIT_PAY);
        List<OrderItem> ois= (List<OrderItem>)  session.getAttribute("ois");

        float total =orderService.add(order,ois);
        return "redirect:forealipay?oid="+order.getId() +"&total="+total;
    }

    @RequestMapping("forepayed")
    public String payed(int oid, float total, Model model) {
        Order order = orderService.get(oid);
        order.setStatus(OrderService.WAIT_DELIVERY);
        order.setPayDate(new Date());
        orderService.update(order);
        model.addAttribute("o", order);
        return "fore/payed";
    }
}
