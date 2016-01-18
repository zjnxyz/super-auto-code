package com.xtuone.yaml;

import java.util.Arrays;

public class Person {

	 	private String name;  
	    private int age;  
	    private Person spouse;  
	    private Person[] children;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public Person getSpouse() {
			return spouse;
		}
		public void setSpouse(Person spouse) {
			this.spouse = spouse;
		}
		public Person[] getChildren() {
			return children;
		}
		public void setChildren(Person[] children) {
			this.children = children;
		}
		@Override
		public String toString() {
			return "Person [name=" + name + ", age=" + age + ", spouse="
					+ spouse + ", children=" + Arrays.toString(children) + "]";
		}  
	    
	    
}
