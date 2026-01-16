package com.example.shopping;

import jakarta.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("userForm") UserForm form, 
                          BindingResult bindingResult, Model model) {
        // バリデーションエラーがある場合
        if (bindingResult.hasErrors()) {
            return "register";
        }
        
        // ユーザー名の前後の空白を削除
        String username = form.getUsername() != null ? form.getUsername().trim() : "";
        
        // バリデーション: ユーザー名の空チェック
        if (username.isEmpty()) {
            model.addAttribute("error", "ユーザー名を入力してください");
            return "register";
        }
        
        // バリデーション: ユーザー名の重複チェック
        if (userRepository.existsByUsername(username)) {
            model.addAttribute("error", "そのユーザー名は既に使われています");
            return "register";
        }
        
        // バリデーション: メールアドレスの重複チェック
        String email = form.getEmail() != null ? form.getEmail().trim() : "";
        if (email.isEmpty()) {
            model.addAttribute("error", "メールアドレスを入力してください");
            return "register";
        }
        if (userRepository.existsByEmail(email)) {
            model.addAttribute("error", "そのメールアドレスは既に使われています");
            return "register";
        }
        
        // バリデーション: パスワード確認のチェック
        if (form.getPassword() == null || form.getPasswordConfirm() == null) {
            model.addAttribute("error", "パスワードを入力してください");
            return "register";
        }
        
        if (!form.getPassword().equals(form.getPasswordConfirm())) {
            model.addAttribute("error", "パスワードが一致しません");
            return "register";
        }
        
        // パスワードの長さチェック
        if (form.getPassword().length() < 4) {
            model.addAttribute("error", "パスワードは4文字以上で入力してください");
            return "register";
        }
        
        // 電話番号の全角数字を半角に変換し、ハイフンを削除
        String telephone = form.getTelephone() != null ? convertTelephoneToHalfWidth(form.getTelephone().trim()) : "";
        
        // 住所の数字とハイフンを半角に変換
        String address = form.getAddress() != null ? convertToHalfWidth(form.getAddress().trim()) : "";
        
        // ユーザー登録
        try {
            User u = new User();
            u.setUsername(username);
            u.setPassword(passwordEncoder.encode(form.getPassword()));
            u.setRole("ROLE_USER");
            u.setEmail(email);
            u.setAddress(address);
            u.setTelephone(telephone);
            userRepository.save(u);
            return "redirect:/login?registered";
        } catch (Exception e) {
            model.addAttribute("error", "登録中にエラーが発生しました。もう一度お試しください。");
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLogin(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "registered", required = false) String registered,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("error", "ユーザー名またはパスワードが正しくありません");
        }
        if (registered != null) {
            model.addAttribute("message", "登録が完了しました。ログインしてください。");
        }
        if (logout != null) {
            model.addAttribute("message", "ログアウトしました。");
        }
        return "login";
    }
    
    /**
     * 住所の数字とハイフンを半角に変換する
     */
    private String convertToHalfWidth(String address) {
        if (address == null || address.isEmpty()) {
            return address;
        }
        
        StringBuilder result = new StringBuilder();
        for (char c : address.toCharArray()) {
            if (c >= '０' && c <= '９') {
                // 全角数字を半角数字に変換
                result.append((char)(c - '０' + '0'));
            } else if (c == '－') {
                // 全角ハイフンを半角ハイフンに変換
                result.append('-');
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
    
    /**
     * 電話番号の全角数字を半角に変換し、ハイフンを削除する
     */
    private String convertTelephoneToHalfWidth(String telephone) {
        if (telephone == null || telephone.isEmpty()) {
            return telephone;
        }
        
        StringBuilder result = new StringBuilder();
        for (char c : telephone.toCharArray()) {
            if (c >= '０' && c <= '９') {
                // 全角数字を半角数字に変換
                result.append((char)(c - '０' + '0'));
            } else if (c == '-' || c == '－') {
                // ハイフン（全角・半角）を削除
                // 何も追加しない
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}

