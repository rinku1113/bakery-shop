package com.example.shopping;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import com.example.shopping.validation.HalfWidthNumbersAndHyphen;
import com.example.shopping.validation.NoHyphen;

public class UserUpdateForm {
    @Size(min = 1, max = 50, message = "ユーザー名は1文字以上50文字以下で入力してください")
    private String username;
    
    // パスワードは空欄の場合は更新しないため、バリデーションはコントローラー側で処理
    private String password;
    
    private String passwordConfirm;

    @Email(message = "有効なメールアドレスを入力してください")
    private String email;

    @HalfWidthNumbersAndHyphen(message = "住所の数字とハイフンは半角で入力してください")
    private String address;

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
