package com.example.shopping;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.example.shopping.validation.HalfWidthNumbersAndHyphen;
import com.example.shopping.validation.NoHyphen;

public class UserForm {
    @NotBlank(message = "ユーザー名を入力してください")
    @Size(min = 1, max = 50, message = "ユーザー名は1文字以上50文字以下で入力してください")
    private String username;
    
    @NotBlank(message = "パスワードを入力してください")
    @Size(min = 4, message = "パスワードは4文字以上で入力してください")
    private String password;
    
    @NotBlank(message = "パスワード（確認）を入力してください")
    private String passwordConfirm;

    @NotBlank(message = "メールアドレスを入力してください")
    @Email(message = "有効なメールアドレスを入力してください")
    private String email;

    @NotBlank(message = "住所を入力してください")
    @HalfWidthNumbersAndHyphen(message = "住所の数字とハイフンは半角で入力してください")
    private String address;

    @NotBlank(message = "電話番号を入力してください")
    @Size(max = 20, message = "電話番号は20文字以下で入力してください")
    @NoHyphen(message = "電話番号は半角数字のみで入力してください（ハイフン不可）")
    private String telephone;

    // getters / setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPasswordConfirm() { return passwordConfirm; }
    public void setPasswordConfirm(String passwordConfirm) { this.passwordConfirm = passwordConfirm; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
}
