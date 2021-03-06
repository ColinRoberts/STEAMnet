package org.friendscentral.steamnet.BaseClasses;

import java.io.Serializable;

/**
 * 
 * @author sambeckley 
 * 
 */

@SuppressWarnings("serial")
public class Spark extends Jawn implements Serializable{
	int id;
	char sparkType;
	char contentType;
	String content;
	String[] createdAts;
	String firstCreatedAt;
	int[] userIds;
	String[] tags;
	String firstUser;
	
	/*
	 * Don't need updated_at since they should never be edited, but perhaps we need a list of "uploaded at" dates
	 * Should Sparks have a name/caption type field?
	 * 
	 * Users/CreatedAts need to be plural since multiple users (at multiple times) can be credited with the same Spark
	 * 
	 * Just setting everything in the creator, since after we grab it you should never need to edit it
	 */
	/**
	 * @param (id, char, char, String, String)
	 * @param int - id
	 * @param char - sparkType
	 * @param char - contentType
	 * @param String - content
	 * @param String[] - createdAts
	 * @param String - firstCreatedAt
	 * @param int[] - User IDs
	 * @param String - firstUser
	 */
	public Spark(int i, char st, char ct, String c, String[] ca, String fca, int[] u, String fu){
		id = i;
		sparkType = st;
		contentType = ct;
		content = c;
		firstCreatedAt = fca;
		createdAts = ca;
		userIds = u;
		firstUser = fu;
	}
	/**
	 * @param (id, char, char, String, String[], int[], String)
	 * @param int - id
	 * @param char - sparkType
	 * @param char - contentType
	 * @param String - content
	 * @param String[] - createdAts
	 * @param int[] - User IDs
	 * @param String - firstUser
	 */
	public Spark(int i, char st, char ct, String c, String[] ca, int[] u, String fu){
		id = i;
		sparkType = st;
		contentType = ct;
		content = c;
		createdAts = ca;
		userIds = u;
		firstUser = fu;
	}
	/**
	 * @param (id, char, char, String, String)
	 * @param int - id
	 * @param char - sparkType
	 * @param char - contentType
	 * @param String - content
	 * @param String - createdAt
	 */
	public Spark(int i, char st, char ct, String c, String[] ca) {
		id = i;
		sparkType = st;
		contentType = ct;
		content = c;
		createdAts = ca;
	}
	/**
	 * (int, char, char, String)
	 * @param int - id
	 * @param char - sparkType
	 * @param char - contentType
	 * @param String - content
	 */
	public Spark(int i, char st, char ct, String c) {
		id = i;
		sparkType = st;
		contentType = ct;
		content = c;
	}
	/**
	 * @param char - sparkType
	 * @param char - contentType
	 * @param String - content
	 */
	public Spark(char st, char ct, String c){
		sparkType = st;
		contentType = ct;
		content = c;
	}
	
	/**
	 * id = -1, sparkType/contentType/content = " "
	 */
	
	public Spark(){
		id = -1;
		sparkType = " ".charAt(0);
		contentType = " ".charAt(0);
		content = " ";
	}
	
	public Spark getSelfSpark(){
		return this;
	}
	
	public Idea getSelfIdea(){
		return null;
	}
	
	public int getId() {
		return id;
	}
	
	public char getSparkType() {
		return sparkType;
	}
	
	public String getSparkTypeString(){
		return String.valueOf(sparkType);
	}
	
	public char getContentType() {
		return contentType;
	}
	
	public String getContentTypeString(){
		return String.valueOf(contentType);
	}
	
	public String getContent() {
		return content;
	}
	
	public String getCreatedAt(){
		if(createdAts[0] != null){
			return createdAts[0];
		} else {
			return null;
		}
	}
	
	public char getType(){
		return "S".charAt(0);
	}
	
	/**
	 * 
	 * @return The first User - Presumably the first person to Spark the Spark
	 */
	public String getUser(){
		return firstUser;
	}
	
	/**
	 * 
	 * @return The entire Users[]
	 */
	public int[] getUsers(){
		return userIds;
	}
	
	public String getDate(){
		return createdAts[0];
	}
	
}
