package com.mybatis.model;

import java.util.Date;

import com.hf.entity.base.DataEntity;

public class User extends DataEntity {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.id
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.create_date
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    private Date createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.create_user
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    private String createUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.sort
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    private Integer sort;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.update_date
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    private Date updateDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.update_user
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    private String updateUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.email
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    private String email;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.name
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.password
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    private String password;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.role
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    private String role;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.username
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    private String username;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.id
     *
     * @return the value of user.id
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.id
     *
     * @param id the value for user.id
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.create_date
     *
     * @return the value of user.create_date
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.create_date
     *
     * @param createDate the value for user.create_date
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.create_user
     *
     * @return the value of user.create_user
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.create_user
     *
     * @param createUser the value for user.create_user
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.sort
     *
     * @return the value of user.sort
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.sort
     *
     * @param sort the value for user.sort
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.update_date
     *
     * @return the value of user.update_date
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.update_date
     *
     * @param updateDate the value for user.update_date
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.update_user
     *
     * @return the value of user.update_user
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.update_user
     *
     * @param updateUser the value for user.update_user
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.email
     *
     * @return the value of user.email
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.email
     *
     * @param email the value for user.email
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.name
     *
     * @return the value of user.name
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.name
     *
     * @param name the value for user.name
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.password
     *
     * @return the value of user.password
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.password
     *
     * @param password the value for user.password
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.role
     *
     * @return the value of user.role
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    public String getRole() {
        return role;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.role
     *
     * @param role the value for user.role
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.username
     *
     * @return the value of user.username
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.username
     *
     * @param username the value for user.username
     *
     * @mbg.generated Sun Sep 18 15:13:05 CST 2016
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }
}