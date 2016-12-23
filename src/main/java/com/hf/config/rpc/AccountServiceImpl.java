package com.hf.config.rpc;

import java.util.List;

import com.google.common.collect.Lists;

public class AccountServiceImpl implements AccountService {

    public void insertAccount(Account acc) {
        // do something...
    }

    public List<Account> getAccounts(String name) {
    	List<Account> list=Lists.newArrayList();
    	Account account=new Account();
    	account.setName(name);
    	list.add(account);
		return list;
    }

}
