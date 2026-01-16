package com.example.shopping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public String home(Model model) {
        List<Product> recommendations = productRepository.findTop6ByOrderByCreatedAtDesc();
        model.addAttribute("products", recommendations);
        return "home";
    }

    @GetMapping("/products")
    public String showProductList(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "product_list";
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        // 簡易検索：名前にキーワードを含む商品を取得するメソッドをリポジトリに追加するのが望ましい
        if (keyword == null || keyword.trim().isEmpty()) {
            return "redirect:/products";
        }
        // ここでは簡易実装（全件取得してフィルタ）だが、実運用ではリポジトリにクエリを追加する
        List<Product> all = productRepository.findAll();
        List<Product> filtered = all.stream()
                .filter(p -> p.getName() != null && p.getName().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
        model.addAttribute("products", filtered);
        model.addAttribute("keyword", keyword);
        return "product_list";
    }
}
