package com.revature;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.revature.utils.PasswordHash;

public class Driver {

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
		String hashed = PasswordHash.generateHash("original");
		System.out.println(PasswordHash.generateHash("different"));
		System.out.println(PasswordHash.checkMatch("original", hashed));

	}
}
